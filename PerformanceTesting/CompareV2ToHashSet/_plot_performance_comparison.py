import matplotlib.pyplot as plt
import numpy as np
import csv

# Read data from both CSV files
def read_csv(filename):
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

    with open(filename, 'r') as csvfile:
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

# Load data for both implementations
hashmap_data = read_csv('CustomSetV2 - V2_performance_average.csv')
hashset_data = read_csv('HashSet - performance_average.csv')

# List of methods to compare
methods = ['add', 'addAll', 'clear', 'contains', 'containsAll', 'isEmpty',
           'remove', 'removeAll', 'retainAll', 'size', 'toArray', 'toString']

# Generate comparison plots
for method in methods:
    plt.figure(figsize=(10, 6))
    # plt.plot(array_data['sizes'], array_data[method], marker='', linestyle='-', label='CustomSetV1')
    plt.plot(hashmap_data['sizes'], hashmap_data[method], marker='', linestyle='-', label='CustomSetV2', linewidth=0.8)
    plt.plot(hashset_data['sizes'], hashset_data[method], marker='', linestyle='-', label='HashSet', linewidth=0.8)

    plt.xlabel('Input Size')
    plt.ylabel('Time (ns)')
    plt.title(f'Performance Comparison: {method}')
    plt.grid(True)
    plt.legend()
    plt.savefig(f'{method}_comparison.png')
    plt.close()

print("Comparison charts generated: " + ", ".join(f"{method}_comparison.png" for method in methods))