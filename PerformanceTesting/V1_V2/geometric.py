import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from scipy.stats import gmean

v1_df = pd.read_csv('V1_performance_data.csv')
v2_df = pd.read_csv('V2_performance_data.csv')

common_sizes = set(v1_df['Size']).intersection(set(v2_df['Size']))
v1_df = v1_df[v1_df['Size'].isin(common_sizes)].sort_values('Size').reset_index(drop=True)
v2_df = v2_df[v2_df['Size'].isin(common_sizes)].sort_values('Size').reset_index(drop=True)

methods = [col for col in v1_df.columns if col != 'Size' and col.lower() != 'cleartime']

v1_df_fixed = v1_df.copy()
v2_df_fixed = v2_df.copy()
for col in methods:
    v1_df_fixed[col] = v1_df_fixed[col].replace(0, 1)
    v2_df_fixed[col] = v2_df_fixed[col].replace(0, 1)

ratios = []
labels = []
colors = []

v2_win_color = '#4DA6FF'
v1_win_color = '#FF4D4D'

for m in methods:
    g_1 = gmean(v1_df_fixed[m])
    g_2 = gmean(v2_df_fixed[m])

    if g_2 < g_1:
        speedup = g_1 / g_2
        ratios.append(speedup - 1)
        colors.append(v2_win_color)
    else:
        speedup = g_2 / g_1
        ratios.append(-(speedup - 1))
        colors.append(v1_win_color)
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
# Ticks on left (negative)
if left_limit < -1.0:
    ticks.append(-1.0)
ticks.append(0.0)
# Ticks on right (positive)
for t in [1.0, 2.0, 3.0, 4.0]:
    if t <= right_limit:
        ticks.append(t)

ax.set_xticks(ticks)
ax.set_xticklabels([f'{abs(t)+1:.1f}x' if abs(t) > 0.05 else 'Tie' for t in ticks],
                   color='#ffffff', fontsize=11)

ax.set_ylim(-0.5, len(methods) - 0.5)
ax.set_yticks(range(len(sorted_labels)))
ax.set_yticklabels(sorted_labels, color='#ffffff', fontsize=10)

ax.set_title('Overall Relative Performance Comparison (V1 vs V2)\n(Geometric Mean Across All Sizes)',
             fontsize=14, fontweight='bold', pad=15, color='#ffffff')
ax.set_xlabel('← V1 Faster  |  Relative Speedup Factor  |  V2 Faster →',
              fontsize=12, labelpad=10, color='#ffffff')

ax.grid(True, axis='x', linestyle='--', alpha=0.3, color='#888888')
ax.tick_params(colors='#ffffff', which='both', length=0)

for spine in ax.spines.values():
    spine.set_edgecolor('#555555')

plt.tight_layout()
plt.savefig('v1_v2_asymmetric.png', dpi=300, transparent=True)
plt.close()
print("Generated asymmetric graph successfully!")