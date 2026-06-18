#!/usr/bin/env python3
"""
Generate performance benchmark charts as PNG files with transparent background.
Matches the style of the reference charts exactly.

Runs directly without requiring command-line arguments.
Legend positioning is anchored to prevent overlapping X-axis size values.
"""

import matplotlib

matplotlib.use('Agg')
import matplotlib.pyplot as plt
import matplotlib.ticker as mticker
from matplotlib.lines import Line2D
import csv
import os
import sys
import numpy as np

# ──────────────────────────────────────────────────────────────────────────────
# Configuration
# ──────────────────────────────────────────────────────────────────────────────

# HARDCODED PATHS: Runs directly using your uploaded files
V1_CSV_PATH = "V1_performance_data.csv"
V2_CSV_PATH = "V2_performance_data.csv"
OUTPUT_DIR = "../charts"

COLORS = {
    'teal': '#2ECFBF',     # Neon Teal for V1
    'purple': '#9B6EF3',   # Neon Purple for V2
    'bg': '#0D0D0D',
    'grid': '#252525',
}

FIGURE_SIZE = (12, 6.2)
DPI = 150

OPERATIONS = {
    'AddTime': 'add',
    'AddAllTime': 'addAll',
    'ClearTime': 'clear',
    'ContainsTime': 'contains',
    'ContainsAllTime': 'containsAll',
    'IsEmptyTime': 'isEmpty',
    'RemoveTime': 'remove',
    'RemoveAllTime': 'removeAll',
    'RetainAllTime': 'retainAll',
    'SizeTime': 'size',
    'ToArrayTime': 'toArray',
    'ToStringTime': 'toString',
}


# ──────────────────────────────────────────────────────────────────────────────
# CSV Loading
# ──────────────────────────────────────────────────────────────────────────────

def load_csv(filepath):
    """Load CSV file and return dict: {size: {op_name: time_value}}"""
    data = {}
    with open(filepath, 'r') as f:
        reader = csv.DictReader(f)
        for row in reader:
            size = int(row['Size'])
            data[size] = {
                k: int(v) for k, v in row.items() if k != 'Size'
            }
    return data


# ──────────────────────────────────────────────────────────────────────────────
# Chart Generation
# ──────────────────────────────────────────────────────────────────────────────

def format_y_axis(value, pos):
    """Format y-axis labels with comma separators."""
    if value == 0:
        return '0'
    return f'{int(value):,}'


def create_chart(operation_key, operation_label, v1_data, v2_data,
                 canonical_sizes, output_path):
    """
    Create a single performance chart comparing V1 and V2.
    """

    # Extract values, using NaN for missing points safely
    v1_values = [
        v1_data[s][operation_key] if s in v1_data else np.nan
        for s in canonical_sizes
    ]
    v2_values = [
        v2_data[s][operation_key] if s in v2_data else np.nan
        for s in canonical_sizes
    ]

    # Create figure with transparent background
    fig, ax = plt.subplots(figsize=FIGURE_SIZE, dpi=DPI)
    fig.patch.set_alpha(0)
    ax.set_facecolor('none')

    # X-axis positions (evenly spaced indices)
    x_positions = list(range(len(canonical_sizes)))

    # ── Plot Lines ────────────────────────────────────────────────────────────
    ax.plot(
        x_positions, v1_values,
        color=COLORS['teal'],
        linewidth=1.5,
        zorder=2
    )

    ax.plot(
        x_positions, v2_values,
        color=COLORS['purple'],
        linewidth=1.5,
        zorder=2
    )

    # ── Plot Scatter Markers ──────────────────────────────────────────────────
    ax.scatter(
        x_positions, v1_values,
        color=COLORS['teal'],
        s=35,
        marker='o',
        edgecolors=COLORS['teal'],
        linewidths=1.5,
        zorder=3
    )

    ax.scatter(
        x_positions, v2_values,
        color=COLORS['purple'],
        s=35,
        marker='o',
        edgecolors=COLORS['purple'],
        linewidths=1.5,
        zorder=3
    )

    # ── Grid ──────────────────────────────────────────────────────────────────
    ax.grid(True, color=COLORS['grid'], linewidth=0.8, linestyle='-', zorder=0)
    ax.set_axisbelow(True)

    # ── X-axis ────────────────────────────────────────────────────────────────
    ax.set_xticks(x_positions)
    ax.set_xticklabels(
        [f'{s:,}' for s in canonical_sizes],
        color='white',
        fontsize=10
    )
    ax.tick_params(axis='x', colors='white', length=0, pad=8)
    ax.set_xlim(-0.4, len(canonical_sizes) - 0.6)

    # ── Y-axis ────────────────────────────────────────────────────────────────
    ax.yaxis.set_major_formatter(mticker.FuncFormatter(format_y_axis))
    ax.tick_params(axis='y', colors='white', length=0, pad=8)
    for label in ax.get_yticklabels():
        label.set_color('white')
        label.set_fontsize(10)

    # ── Spines ────────────────────────────────────────────────────────────────
    for spine in ax.spines.values():
        spine.set_visible(False)

    # ── Labels ────────────────────────────────────────────────────────────────
    ax.set_xlabel('Size', color='white', fontsize=12, labelpad=12)
    ax.set_ylabel('Time (ns)', color='white', fontsize=11, labelpad=10)
    ax.set_title(operation_label, color='white', fontsize=15, fontweight='bold', pad=14)

    # ── Legend ────────────────────────────────────────────────────────────────
    # Custom legend elements (solid circles, no horizontal lines through them)
    legend_elements = [
        Line2D(
            [0], [0],
            marker='o',
            color='none',
            markerfacecolor=COLORS['teal'],
            markeredgecolor=COLORS['teal'],
            markeredgewidth=1.5,
            markersize=8,
            label='V1',
            linestyle='none'
        ),
        Line2D(
            [0], [0],
            marker='o',
            color='none',
            markerfacecolor=COLORS['purple'],
            markeredgecolor=COLORS['purple'],
            markeredgewidth=1.5,
            markersize=8,
            label='V2',
            linestyle='none'
        ),
    ]

    # FIXED POSITIONING:
    # Anchors the TOP of the legend box safely below the "Size" x-label (-0.26 coordinate)
    leg = ax.legend(
        handles=legend_elements,
        loc='upper center',
        bbox_to_anchor=(0.5, -0.26),
        ncol=2,
        frameon=False,
        fontsize=12,
        handlelength=1.5,
        handletextpad=0.6,
        columnspacing=2.0
    )

    # Style legend text
    for text in leg.get_texts():
        text.set_color('white')
        text.set_fontsize(12)

    # Expanded bottom safety buffer to give the legend plenty of breathing room
    plt.tight_layout(rect=[0, 0.18, 1, 1])
    fig.savefig(
        output_path,
        dpi=DPI,
        transparent=True,
        bbox_inches='tight',
        facecolor='none',
        edgecolor='none'
    )
    plt.close(fig)


# ──────────────────────────────────────────────────────────────────────────────
# Main
# ──────────────────────────────────────────────────────────────────────────────

def main():
    # Verify input files exist before processing
    if not os.path.exists(V1_CSV_PATH):
        print(f"Error: Required file '{V1_CSV_PATH}' not found in the folder.")
        sys.exit(1)
    if not os.path.exists(V2_CSV_PATH):
        print(f"Error: Required file '{V2_CSV_PATH}' not found in the folder.")
        sys.exit(1)

    # Create output directory
    os.makedirs(OUTPUT_DIR, exist_ok=True)

    # Load data
    print(f"Loading {V1_CSV_PATH}...")
    v1_data = load_csv(V1_CSV_PATH)
    print(f"  Loaded {len(v1_data)} sizes")

    print(f"Loading {V2_CSV_PATH}...")
    v2_data = load_csv(V2_CSV_PATH)
    print(f"  Loaded {len(v2_data)} sizes")

    # Combine unique sizes for x-axis
    canonical_sizes = sorted(list(set(v1_data.keys()) | set(v2_data.keys())))
    print(f"\nUsing {len(canonical_sizes)} unified sizes for x-axis: {canonical_sizes}")

    print(f"\nGenerating comparison charts...")
    print(f"  Teal Line   = V1")
    print(f"  Purple Line = V2\n")

    for csv_col, chart_label in OPERATIONS.items():
        output_path = os.path.join(OUTPUT_DIR, f'{chart_label}.png')
        create_chart(csv_col, chart_label, v1_data, v2_data,
                     canonical_sizes, output_path)
        print(f"  ✓ {chart_label}.png")

    print(f"\n✓ All charts saved cleanly to {OUTPUT_DIR}")


if __name__ == '__main__':
    main()