import matplotlib.pyplot as plt
import numpy as np
import csv

# Read data from CSV
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

with open('CustomSetV2 - V2_performance_average.csv', 'r') as csvfile:
    reader = csv.DictReader(csvfile)
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

# Convert to numpy arrays
sizes = np.array(sizes)
add_times = np.array(add_times)
add_all_times = np.array(add_all_times)
clear_times = np.array(clear_times)
contains_times = np.array(contains_times)
contains_all_times = np.array(contains_all_times)
is_empty_times = np.array(is_empty_times)
remove_times = np.array(remove_times)
remove_all_times = np.array(remove_all_times)
retain_all_times = np.array(retain_all_times)
size_times = np.array(size_times)
to_array_times = np.array(to_array_times)
to_string_times = np.array(to_string_times)

# List of methods and their data
methods = [
    ("add", add_times),
    ("addAll", add_all_times),
    ("clear", clear_times),
    ("contains", contains_times),
    ("containsAll", contains_all_times),
    ("isEmpty", is_empty_times),
    ("remove", remove_times),
    ("removeAll", remove_all_times),
    ("retainAll", retain_all_times),
    ("size", size_times),
    ("toArray", to_array_times),
    ("toString", to_string_times)
]

# Generate a plot for each method
for method_name, times in methods:
    plt.figure(figsize=(10, 6))
    plt.plot(sizes, times, marker='', linestyle='-', label=method_name)
    plt.xlabel('Input Size')
    plt.ylabel('Time (ns)')
    plt.title(f'CustomSetV2.{method_name} Performance')
    plt.grid(True)
    plt.legend()
    plt.savefig(f'{method_name}_performance.png')
    plt.close()

print("Charts generated: " + ", ".join(f"{name}_performance.png" for name, _ in methods))