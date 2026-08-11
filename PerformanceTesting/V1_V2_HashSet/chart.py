#!/usr/bin/env python3
"""
Generate performance benchmark charts as PNG files with transparent background.
Supports tracking 3 implementations: JDK, V1, and V2, using the new
semicolon-delimited CSV format.

Runs directly or via command-line arguments:
    python3 generate_charts.py <hashset_csv> <v1_csv> <v2_csv> <output_dir>
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

COLORS = {
    'JDK': '#2ECFBF',  # Neon Teal
    'v1': '#FF9F43',       # Neon Orange
    'v2': '#9B6EF3',       # Neon Purple
    'grid': '#252525',
}

FIGURE_SIZE = (12, 6.2)
DPI = 150

# Updated OPERATIONS dictionary mapping exact new CSV column headings
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


def create_chart(operation_key, operation_label, hashset_data, v1_data, v2_data,
                 canonical_sizes, output_path):
    """
    Create a single performance chart comparing three datasets.
    """

    # Extract values, using NaN for missing points across varying size grids safely
    hashset_values = [
        hashset_data[s][operation_key] if s in hashset_data and operation_key in hashset_data[s] else np.nan
        for s in canonical_sizes
    ]
    v1_values = [
        v1_data[s][operation_key] if s in v1_data and operation_key in v1_data[s] else np.nan
        for s in canonical_sizes
    ]
    v2_values = [
        v2_data[s][operation_key] if s in v2_data and operation_key in v2_data[s] else np.nan
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
        color=COLORS['JDK'],
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
        color=COLORS['JDK'],
        s=35,
        marker='o',
        edgecolors=COLORS['JDK'],
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
    ax.set_xlabel('Size', color='white', fontsize=12, labelpad=12)
    ax.set_ylabel('Time (ns/op)', color='white', fontsize=11, labelpad=10)
    ax.set_title(operation_key, color='white', fontsize=15, fontweight='bold', pad=14)

    # ── Legend ────────────────────────────────────────────────────────────────
    legend_elements = [
        Line2D(
            [0], [0],
            marker='o',
            color='none',
            markerfacecolor=COLORS['JDK'],
            markeredgecolor=COLORS['JDK'],
            markeredgewidth=1.5,
            markersize=8,
            label='JDK',
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

    leg = ax.legend(
        handles=legend_elements,
        loc='upper center',
        bbox_to_anchor=(0.5, -0.22),
        ncol=3,
        frameon=False,
        fontsize=12,
        handlelength=1.5,
        handletextpad=0.6,
        columnspacing=2.0
    )

    for text in leg.get_texts():
        text.set_color('white')
        text.set_fontsize(12)

    plt.tight_layout(rect=[0, 0.12, 1, 1])
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
    if len(sys.argv) == 5:
        hashset_path = sys.argv[1]
        v1_path = sys.argv[2]
        v2_path = sys.argv[3]
        output_dir = sys.argv[4]
    else:
        # Default fallback paths if run without arguments
        hashset_path = "HashSet_performance.csv"
        v1_path = "CustomSetV1_performance.csv"
        v2_path = "CustomSetV2_performance.csv"
        output_dir = "."

    os.makedirs(output_dir, exist_ok=True)

    print(f"Loading {hashset_path}...")
    hashset_data = load_csv(hashset_path)
    print(f"  Loaded {len(hashset_data)} sizes")

    print(f"Loading {v1_path}...")
    v1_data = load_csv(v1_path)
    print(f"  Loaded {len(v1_data)} sizes")

    print(f"Loading {v2_path}...")
    v2_data = load_csv(v2_path)
    print(f"  Loaded {len(v2_data)} sizes")

    canonical_sizes = sorted(list(set(hashset_data.keys()) | set(v1_data.keys()) | set(v2_data.keys())))
    print(f"\nUsing {len(canonical_sizes)} unified sizes for x-axis: {canonical_sizes}")

    print(f"\nGenerating 3-line comparison charts...")
    print(f"  Line 1 (Teal)   = JDK")
    print(f"  Line 2 (Orange) = V1")
    print(f"  Line 3 (Purple) = V2\n")

    for csv_col, chart_label in OPERATIONS.items():
        output_path = os.path.join(output_dir, f'{chart_label}.png')
        create_chart(csv_col, chart_label, hashset_data, v1_data, v2_data,
                     canonical_sizes, output_path)
        print(f"  ✓ {chart_label}.png")

    print(f"\n✓ All 3-way charts successfully saved to {output_dir}")


if __name__ == '__main__':
    main()