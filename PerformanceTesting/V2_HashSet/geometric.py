import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from scipy.stats import gmean

# 1. Load data from CustomSetV2 and JDK HashSet semicolon-delimited CSV files
v2_df = pd.read_csv('CustomSetV2_performance.csv', sep=';')
hs_df = pd.read_csv('HashSet_performance.csv', sep=';')

common_sizes = set(v2_df['Size']).intersection(set(hs_df['Size']))
v2_df = v2_df[v2_df['Size'].isin(common_sizes)].sort_values('Size').reset_index(drop=True)
hs_df = hs_df[hs_df['Size'].isin(common_sizes)].sort_values('Size').reset_index(drop=True)

# Exclude 'Size' and filter out 'clear()'
methods = [col for col in v2_df.columns if col != 'Size' and col.lower() != 'clear()']

v2_df_fixed = v2_df.copy()
hs_df_fixed = hs_df.copy()
for col in methods:
    v2_df_fixed[col] = v2_df_fixed[col].replace(0, 1)
    hs_df_fixed[col] = hs_df_fixed[col].replace(0, 1)

ratios = []
labels = []
colors = []

# Colors: CustomSet V2 wins vs HashSet wins
v2_win_color = '#4DA6FF'
hs_win_color = '#FF4D4D'

for m in methods:
    g_v2 = gmean(v2_df_fixed[m])
    g_hs = gmean(hs_df_fixed[m])

    # If HashSet is slower, V2 is faster (speedup = g_hs / g_v2)
    if g_v2 < g_hs:
        speedup = g_hs / g_v2
        ratios.append(speedup - 1)
        colors.append(v2_win_color)
    else:
        speedup = g_v2 / g_hs
        ratios.append(-(speedup - 1))
        colors.append(hs_win_color)
    labels.append(m)

sorted_indices = np.argsort(ratios)
sorted_ratios = [ratios[idx] for idx in sorted_indices]
sorted_labels = [labels[idx] for idx in sorted_indices]
sorted_colors = [colors[idx] for idx in sorted_indices]

# Asymmetric limits
min_ratio = min(sorted_ratios)
max_ratio = max(sorted_ratios)

# Give 10% buffer
left_limit = min_ratio - 0.2
right_limit = max_ratio + 0.2

fig_height = max(6, len(methods) * 0.45)
fig, ax = plt.subplots(figsize=(12, fig_height), facecolor='none')
ax.set_facecolor('none')

bars = ax.barh(range(len(sorted_labels)), sorted_ratios, color=sorted_colors, alpha=0.9, height=0.6)
ax.axvline(x=0, color='#ffffff', linewidth=1.2)

ax.set_xlim(left_limit, right_limit)

# Create intelligent ticks including 0 exactly
ticks = []
if left_limit < -1.0:
    ticks.append(-1.0)
ticks.append(0.0)
for t in [1.0, 2.0, 3.0, 4.0, 5.0]:
    if t <= right_limit:
        ticks.append(t)

ax.set_xticks(ticks)
ax.set_xticklabels([f'{abs(t)+1:.1f}x' if abs(t) > 0.05 else 'Tie' for t in ticks],
                   color='#ffffff', fontsize=11)

ax.set_ylim(-0.5, len(methods) - 0.5)
ax.set_yticks(range(len(sorted_labels)))
ax.set_yticklabels(sorted_labels, color='#ffffff', fontsize=10)

ax.set_title('Overall Relative Performance Comparison (V2 vs JDK)\n(Geometric Mean Across All Sizes)',
             fontsize=14, fontweight='bold', pad=15, color='#ffffff')
ax.set_xlabel('← JDK Faster  |  Relative Speedup Factor  |  V2 Faster →',
              fontsize=12, labelpad=10, color='#ffffff')

ax.grid(True, axis='x', linestyle='--', alpha=0.3, color='#888888')
ax.tick_params(colors='#ffffff', which='both', length=0)

for spine in ax.spines.values():
    spine.set_edgecolor('#555555')

plt.tight_layout()
# Saves directly to the current directory
plt.savefig('geometric.png', dpi=300, transparent=True)
plt.close()
print("Generated asymmetric comparison graph successfully for V2 vs JDK!")