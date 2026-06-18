import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from scipy.stats import gmean

# 1. Load the performance CSV datasets (Swapped to use standard comma delimiter)
custom_df = pd.read_csv('V2_performance_data.csv')
jdk_df = pd.read_csv('HashSet_performance_data.csv')

# Align rows by matching common 'Size' thresholds if they differ
common_sizes = set(custom_df['Size']).intersection(set(jdk_df['Size']))
custom_df = custom_df[custom_df['Size'].isin(common_sizes)].sort_values('Size').reset_index(drop=True)
jdk_df = jdk_df[jdk_df['Size'].isin(common_sizes)].sort_values('Size').reset_index(drop=True)

# Include all method columns dynamically except the baseline 'Size' column
methods = [col for col in custom_df.columns if col != 'Size']

print(f"Methods included ({len(methods)} total):", methods)

# Copy and replace 0s to prevent geometric mean errors
custom_df_fixed = custom_df.copy()
jdk_df_fixed = jdk_df.copy()
for col in methods:
    custom_df_fixed[col] = custom_df_fixed[col].replace(0, 1)
    jdk_df_fixed[col] = jdk_df_fixed[col].replace(0, 1)

# Calculate relative speedups
ratios = []
labels = []
colors = []

custom_win_color = '#4DA6FF'  # Light Blue
jdk_win_color = '#FF4D4D'     # Light Red

for m in methods:
    g_c = gmean(custom_df_fixed[m])
    g_j = gmean(jdk_df_fixed[m])

    if g_c < g_j:
        speedup = g_j / g_c
        ratios.append(speedup - 1)
        colors.append(custom_win_color)
    else:
        speedup = g_c / g_j
        ratios.append(-(speedup - 1))
        colors.append(jdk_win_color)
    labels.append(m)

# Sort by performance
sorted_indices = np.argsort(ratios)
sorted_ratios = [ratios[idx] for idx in sorted_indices]
sorted_labels = [labels[idx] for idx in sorted_indices]
sorted_colors = [colors[idx] for idx in sorted_indices]

# Define figure size proportional to the number of methods
fig_height = max(6, len(methods) * 0.45)
fig, ax = plt.subplots(figsize=(12, fig_height), facecolor='none')
ax.set_facecolor('none')

# Plot bars
bars = ax.barh(range(len(sorted_labels)), sorted_ratios, color=sorted_colors, alpha=0.9, height=0.6)
ax.axvline(x=0, color='#ffffff', linewidth=1.2)

# Dynamic scaling of the X-axis (Symmetric layout restored)
max_abs = max(abs(r) for r in sorted_ratios) if sorted_ratios else 1.0
x_limit = np.ceil(max_abs * 10) / 10  # 10% buffer
ax.set_xlim(-x_limit, x_limit)

# Dynamic axis ticks
ticks = np.linspace(-x_limit, x_limit, 5)
ax.set_xticks(ticks)
ax.set_xticklabels([f'{abs(t)+1:.2f}x' if abs(t) > 0.05 else 'Tie' for t in ticks],
                   color='#ffffff', fontsize=11)

# Fix Y-axis to exactly match the data
ax.set_ylim(-0.5, len(methods) - 0.5)
ax.set_yticks(range(len(sorted_labels)))
ax.set_yticklabels(sorted_labels, color='#ffffff', fontsize=10)

# Labels and Styling
ax.set_title('Overall Relative Performance Comparison\n(Geometric Mean Across All Sizes)',
             fontsize=14, fontweight='bold', pad=15, color='#ffffff')
ax.set_xlabel('← JDK Faster  |  Relative Speedup Factor  |  Custom Faster →',
              fontsize=12, labelpad=10, color='#ffffff')

ax.grid(True, axis='x', linestyle='--', alpha=0.3, color='#888888')
ax.tick_params(colors='#ffffff', which='both', length=0)

for spine in ax.spines.values():
    spine.set_edgecolor('#555555')

plt.tight_layout()
plt.savefig('charts/geometric_set_performance.png', dpi=300, transparent=True)
plt.close()

print(f"Chart generated with scale ±{x_limit:.2f}x.")