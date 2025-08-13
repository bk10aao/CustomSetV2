import matplotlib
matplotlib.use('Agg')  # Non-interactive backend
import matplotlib.pyplot as plt
import numpy as np
import csv
import sys
import os
from matplotlib.ticker import FuncFormatter

# Read data from a CSV file
def read_csv(filename):
    print(f"Reading file: {filename}")
    if not os.path.exists(filename):
        raise FileNotFoundError(f"File {filename} not found")

    sizes = []
    add_times = []
    add_all_times = []
    clear_times = []
    contains_times = []
    contains_all_times = []
    is_empty_times = []
    remove_times = []
    remove_all_times = []
    retain_all_times = []
    size_times = []
    to_array_times = []
    to_string_times = []

    try:
        with open(filename, 'r') as csvfile:
            reader = csv.DictReader(csvfile)
            print(f"CSV headers: {reader.fieldnames}")
            for row in reader:
                sizes.append(int(row['Size']))
                add_times.append(int(row['AddTime']))
                add_all_times.append(int(row['AddAllTime']))
                clear_times.append(int(row['ClearTime']))
                contains_times.append(int(row['ContainsTime']))
                contains_all_times.append(int(row['ContainsAllTime']))
                is_empty_times.append(int(row['IsEmptyTime']))
                remove_times.append(int(row['RemoveTime']))
                remove_all_times.append(int(row['RemoveAllTime']))
                retain_all_times.append(int(row['RetainAllTime']))
                size_times.append(int(row['SizeTime']))
                to_array_times.append(int(row['ToArrayTime']))
                to_string_times.append(int(row['ToStringTime']))
    except (KeyError, ValueError) as e:
        raise ValueError(f"Error parsing {filename}: {e}")

    return {
        'sizes': np.array(sizes),
        'add': np.array(add_times),
        'addAll': np.array(add_all_times),
        'clear': np.array(clear_times),
        'contains': np.array(contains_times),
        'containsAll': np.array(contains_all_times),
        'isEmpty': np.array(is_empty_times),
        'remove': np.array(remove_times),
        'removeAll': np.array(remove_all_times),
        'retainAll': np.array(retain_all_times),
        'size': np.array(size_times),
        'toArray': np.array(to_array_times),
        'toString': np.array(to_string_times)
    }

# Check command-line arguments
if len(sys.argv) != 3:
    print("Usage: python plot_performance_comparison.py <customset_csv> <hashset_csv>")
    sys.exit(1)

customset_file = sys.argv[1]
hashset_file = sys.argv[2]

# Load data for both implementations
try:
    customset_data = read_csv(customset_file)
    hashset_data = read_csv(hashset_file)
except Exception as e:
    print(f"Error loading data: {e}")
    sys.exit(1)

# List of methods to compare
methods = ['add', 'addAll', 'clear', 'contains', 'containsAll', 'isEmpty',
           'remove', 'removeAll', 'retainAll', 'size', 'toArray', 'toString']

# Create a single figure with vertically stacked plots
output_file = 'performance_comparison.jpg'
print(f"Generating all plots to {output_file}")
fig = plt.figure(figsize=(10, 36))  # Reduced height to 36 inches (3 inches per plot)

# Custom formatter for full numbers with commas
def format_number(x, pos):
    return f'{int(x):,}' if x >= 0 else f'{x:,.0f}'

for idx, method in enumerate(methods):
    print(f"Generating plot for {method}")
    ax = fig.add_subplot(12, 1, idx + 1)  # 12 rows, 1 column
    ax.plot(customset_data['sizes'], customset_data[method], marker='', linestyle='-', label='CustomSetV2', linewidth=1.5)
    ax.plot(hashset_data['sizes'], hashset_data[method], marker='', linestyle='-', label='HashSet', linewidth=1.5)

    ax.set_xlabel('Input Size')
    ax.set_ylabel('Time (ns)')
    ax.set_title(f'{method}')
    ax.set_xlim(100, 100000)  # Set x-axis limits
    ax.set_xticks([100, 10000, 20000, 30000, 40000, 50000, 60000, 70000, 80000, 90000, 100000])  # Explicit tick positions
    # Enhanced dynamic y-axis limits with tighter buffer
    all_values = np.concatenate([customset_data[method], hashset_data[method]])
    if method in ['removeAll', 'retainAll']:  # Logarithmic scale
        min_val = max(1, np.min(all_values[all_values > 0]))  # Smallest positive value, capped at 1
        max_val = np.max(all_values) * 1.02  # 2% buffer above max
        ax.set_yscale('log')
        ax.set_ylim(min_val, max_val)
        ax.yaxis.set_major_formatter(FuncFormatter(format_number))  # Custom formatter for log scale
    else:  # Linear scale
        min_val = np.min(all_values) * 0.98  # 2% below min
        max_val = np.max(all_values) * 1.02  # 2% above max
        ax.set_ylim(max(0, min_val), max_val)  # Ensure y-axis doesn't go below 0
        ax.ticklabel_format(style='plain', axis='y', scilimits=(0, 0))  # Full numbers for linear scale

    ax.grid(True, which='major', linestyle='-', linewidth=0.5)  # Only major grid lines
    ax.legend()

# Adjust layout to prevent overlap with minimal padding
plt.tight_layout(pad=0.5, h_pad=0.5, w_pad=0.5)
print(f"Saving plot to {output_file}")
plt.savefig(output_file, dpi=300)  # Save as JPG with high resolution
plt.close(fig)
print(f"Save attempt completed for {output_file}")

# Verify output file
if os.path.exists(output_file):
    print(f"Generated chart: {output_file}")
else:
    print(f"Failed to generate: {output_file}")