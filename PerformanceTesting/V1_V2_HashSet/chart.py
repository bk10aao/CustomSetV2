#!/usr/bin/env python3
"""
Generate performance benchmark charts as PNG files with transparent background.
Supports tracking 3 implementations: HashSet, V1, and V2.

Usage:
    python3 generate_charts.py <hashset_csv> <v1_csv> <v2_csv> <output_dir>
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

COLORS = {
    'hashset': '#2ECFBF',  # Neon Teal
    'v1': '#FF9F43',       # Neon Orange
    'v2': '#9B6EF3',       # Neon Purple
    'grid': '#252525',
}

FIGURE_SIZE = (12, 6.0)
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


def create_chart(operation_key, operation_label, hashset_data, v1_data, v2_data,
                 canonical_sizes, output_path):
    """
    Create a single performance chart comparing three datasets.
    """

    # Extract values, using NaN for missing points across varying size grids
    hashset_values = [
        hashset_data[s][operation_key] if s in hashset_data else np.nan
        for s in canonical_sizes
    ]
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
        x_positions, hashset_values,
        color=COLORS['hashset'],
        linewidth=1.5,
        zorder=2
    )

    ax.plot(
        x_positions, v1_values,
        color=COLORS['v1'],
        linewidth=1.5,
        zorder=2
    )

    ax.plot(
        x_positions, v2_values,
        color=COLORS['v2'],
        linewidth=1.5,
        zorder=2
    )

    # ── Plot Scatter Markers ──────────────────────────────────────────────────
    ax.scatter(
        x_positions, hashset_values,
        color=COLORS['hashset'],
        s=35,
        marker='o',
        edgecolors=COLORS['hashset'],
        linewidths=1.5,
        zorder=3
    )

    ax.scatter(
        x_positions, v1_values,
        color=COLORS['v1'],
        s=35,
        marker='o',
        edgecolors=COLORS['v1'],
        linewidths=1.5,
        zorder=3
    )

    ax.scatter(
        x_positions, v2_values,
        color=COLORS['v2'],
        s=35,
        marker='o',
        edgecolors=COLORS['v2'],
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
    ax.set_xlabel('Size', color='white', fontsize=12, labelpad=10)
    ax.set_ylabel('Time (ns)', color='white', fontsize=11, labelpad=10)
    ax.set_title(operation_label, color='white', fontsize=15, fontweight='bold', pad=14)

    # ── Legend ────────────────────────────────────────────────────────────────
    legend_elements = [
        Line2D(
            [0], [0],
            marker='o',
            color='none',
            markerfacecolor=COLORS['hashset'],
            markeredgecolor=COLORS['hashset'],
            markeredgewidth=1.5,
            markersize=8,
            label='HashSet',
            linestyle='none'
        ),
        Line2D(
            [0], [0],
            marker='o',
            color='none',
            markerfacecolor=COLORS['v1'],
            markeredgecolor=COLORS['v1'],
            markeredgewidth=1.5,
            markersize=8,
            label='V1',
            linestyle='none'
        ),
        Line2D(
            [0], [0],
            marker='o',
            color='none',
            markerfacecolor=COLORS['v2'],
            markeredgecolor=COLORS['v2'],
            markeredgewidth=1.5,
            markersize=8,
            label='V2',
            linestyle='none'
        ),
    ]

    # ADJUSTED: bbox_to_anchor moved from -0.20 down to -0.28
    leg = ax.legend(
        handles=legend_elements,
        loc='lower center',
        bbox_to_anchor=(0.5, -0.28),
        ncol=3,
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

    # ADJUSTED: Changed bottom boundary rect allocation from 0.08 to 0.15
    # This prevents the lowered legend from getting clipped off the image canvas
    plt.tight_layout(rect=[0, 0.15, 1, 1])
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
    if len(sys.argv) != 5:
        print("Usage: python3 generate_charts.py <hashset_csv> <v1_csv> <v2_csv> <output_dir>")
        print("\nExample:")
        print("  python3 generate_charts.py HashSet_performance_data.csv V1_performance_data.csv V2_Hash_2_performance_data.csv ./charts")
        sys.exit(1)

    hashset_path = sys.argv[1]
    v1_path = sys.argv[2]
    v2_path = sys.argv[3]
    output_dir = sys.argv[4]

    # Create output directory
    os.makedirs(output_dir, exist_ok=True)

    # Load all three datasets
    print(f"Loading {hashset_path}...")
    hashset_data = load_csv(hashset_path)
    print(f"  Loaded {len(hashset_data)} sizes")

    print(f"Loading {v1_path}...")
    v1_data = load_csv(v1_path)
    print(f"  Loaded {len(v1_data)} sizes")

    print(f"Loading {v2_path}...")
    v2_data = load_csv(v2_path)
    print(f"  Loaded {len(v2_data)} sizes")

    # Construct robust canonical X-axis by joining unique sizes from all logs
    canonical_sizes = sorted(list(set(hashset_data.keys()) | set(v1_data.keys()) | set(v2_data.keys())))
    print(f"\nUsing {len(canonical_sizes)} unified sizes for x-axis: {canonical_sizes}")

    print(f"\nGenerating 3-line comparison charts...")
    print(f"  Line 1 (Teal)   = HashSet")
    print(f"  Line 2 (Orange) = V1")
    print(f"  Line 3 (Purple) = V2\n")

    for csv_col, chart_label in OPERATIONS.items():
        output_path = os.path.join(output_dir, f'{chart_label}.png')
        create_chart(csv_col, chart_label, hashset_data, v1_data, v2_data,
                     canonical_sizes, output_path)
        print(f"  ✓ {chart_label}.png")

    print(f"\n✓ All charts successfully saved to {output_dir}")


if __name__ == '__main__':
    main()