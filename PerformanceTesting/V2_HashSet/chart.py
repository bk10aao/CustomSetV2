#!/usr/bin/env python3
"""
Generate performance benchmark charts as PNG files comparing CustomSet V2 and JDK HashSet
with a transparent background. Matches the style of the reference charts exactly.

Runs directly without requiring command-line arguments.
Legend positioning is anchored to prevent overlapping X-axis size values.
"""

import matplotlib

matplotlib.use('Agg')
import matplotlib.pyplot as plt
import matplotlib.ticker as mticker
from matplotlib.lines import Line2D
import pandas as pd
import os
import sys
import numpy as np

# ──────────────────────────────────────────────────────────────────────────────
# Configuration
# ──────────────────────────────────────────────────────────────────────────────

V2_CSV_PATH = "CustomSetV2_performance.csv"
HASHSET_CSV_PATH = "HashSet_performance.csv"
OUTPUT_DIR = "."  # Saves output files in the current directory

COLORS = {
    'purple': '#9B6EF3',   # Neon Purple for CustomSet V2
    'blue': '#4DA6FF',     # Bright Blue for JDK HashSet
    'bg': '#0D0D0D',
    'grid': '#252525',
}

FIGURE_SIZE = (12, 6.2)
DPI = 150

# Maps clean chart labels/file names to exact column headings in the CSVs
OPERATIONS = {
    'add(E)': 'add',
    'addAll(Collection)': 'addAll',
    'clear()': 'clear',
    'clone()': 'clone',
    'constructor()': 'constructor',
    'constructor(Collection)': 'constructorCollection',
    'contains(Object)': 'contains',
    'containsAll(Collection)': 'containsAll',
    'equals(Object)': 'equals',
    'hashCode()': 'hashCode',
    'isEmpty()': 'isEmpty',
    'iterator()': 'iterator',
    'remove(Object)': 'remove',
    'removeAll(Collection)': 'removeAll',
    'retainAll(Collection)': 'retainAll',
    'size()': 'size',
    'toArray()': 'toArray',
    'toArray(T[])': 'toArrayT',
    'toString()': 'toString',
}


# ──────────────────────────────────────────────────────────────────────────────
# CSV Loading
# ──────────────────────────────────────────────────────────────────────────────

def load_csv(filepath):
    """Load semicolon-delimited JMH CSV file and return dict: {size: {op_name: time_value}}"""
    df = pd.read_csv(filepath, sep=';')
    data = {}
    for _, row in df.iterrows():
        size = int(row['Size'])
        data[size] = {
            col: int(row[col]) for col in df.columns if col != 'Size'
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


def create_chart(csv_col, operation_label, v2_data, hashset_data,
                 canonical_sizes, output_path):
    """
    Create a single performance chart comparing CustomSet V2 and JDK HashSet.
    """

    # Extract values, using NaN for missing points safely
    v2_values = [
        v2_data[s][csv_col] if s in v2_data and csv_col in v2_data[s] else np.nan
        for s in canonical_sizes
    ]
    hashset_values = [
        hashset_data[s][csv_col] if s in hashset_data and csv_col in hashset_data[s] else np.nan
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
        x_positions, v2_values,
        color=COLORS['purple'],
        linewidth=1.5,
        zorder=2
    )

    ax.plot(
        x_positions, hashset_values,
        color=COLORS['blue'],
        linewidth=1.5,
        zorder=2
    )

    # ── Plot Scatter Markers ──────────────────────────────────────────────────
    ax.scatter(
        x_positions, v2_values,
        color=COLORS['purple'],
        s=35,
        marker='o',
        edgecolors=COLORS['purple'],
        linewidths=1.5,
        zorder=3
    )

    ax.scatter(
        x_positions, hashset_values,
        color=COLORS['blue'],
        s=35,
        marker='o',
        edgecolors=COLORS['blue'],
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
    ax.set_ylabel('Time (ns/op)', color='white', fontsize=11, labelpad=10)
    ax.set_title(csv_col, color='white', fontsize=15, fontweight='bold', pad=14)

    # ── Legend ────────────────────────────────────────────────────────────────
    legend_elements = [
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
        Line2D(
            [0], [0],
            marker='o',
            color='none',
            markerfacecolor=COLORS['blue'],
            markeredgecolor=COLORS['blue'],
            markeredgewidth=1.5,
            markersize=8,
            label='JDK',
            linestyle='none'
        ),
    ]

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

    for text in leg.get_texts():
        text.set_color('white')
        text.set_fontsize(12)

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
    if not os.path.exists(V2_CSV_PATH):
        print(f"Error: Required file '{V2_CSV_PATH}' not found in the folder.")
        sys.exit(1)
    if not os.path.exists(HASHSET_CSV_PATH):
        print(f"Error: Required file '{HASHSET_CSV_PATH}' not found in the folder.")
        sys.exit(1)

    os.makedirs(OUTPUT_DIR, exist_ok=True)

    print(f"Loading {V2_CSV_PATH}...")
    v2_data = load_csv(V2_CSV_PATH)
    print(f"  Loaded {len(v2_data)} sizes")

    print(f"Loading {HASHSET_CSV_PATH}...")
    hashset_data = load_csv(HASHSET_CSV_PATH)
    print(f"  Loaded {len(hashset_data)} sizes")

    canonical_sizes = sorted(list(set(v2_data.keys()) | set(hashset_data.keys())))
    print(f"\nUsing {len(canonical_sizes)} unified sizes for x-axis: {canonical_sizes}")

    print(f"\nGenerating comparison charts...")
    print(f"  Purple Line = V2")
    print(f"  Blue Line   = JDK\n")

    for csv_col, chart_label in OPERATIONS.items():
        output_path = os.path.join(OUTPUT_DIR, f'{chart_label}.png')
        create_chart(csv_col, chart_label, v2_data, hashset_data,
                     canonical_sizes, output_path)
        print(f"  ✓ {chart_label}.png")

    print(f"\n✓ All comparison charts saved cleanly to {OUTPUT_DIR}")


if __name__ == '__main__':
    main()