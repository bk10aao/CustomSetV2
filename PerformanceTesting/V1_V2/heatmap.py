import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns

v1_df = pd.read_csv('V1_performance_data.csv')
v2_df = pd.read_csv('V2_performance_data.csv')

common_sizes = sorted(list(set(v1_df['Size']).intersection(set(v2_df['Size']))))
methods = [col for col in v1_df.columns if col != 'Size' and col.lower() != 'cleartime']

print("Common sizes:", common_sizes)
print("Methods:", methods)

# Construct relative performance matrix (Log2 ratios)
# ratio = log2(V1_time / V2_time) -> positive means V2 is faster (takes less time)
heatmap_data = np.zeros((len(methods), len(common_sizes)))
text_labels = []

for i, m in enumerate(methods):
    row_labels = []
    for j, size in enumerate(common_sizes):
        v1_val = v1_df.loc[v1_df['Size'] == size, m].values[0]
        v2_val = v2_df.loc[v2_df['Size'] == size, m].values[0]

        if v1_val == 0: v1_val = 1
        if v2_val == 0: v2_val = 1

        ratio = np.log2(v1_val / v2_val)
        heatmap_data[i, j] = ratio

        if v1_val > v2_val:
            factor = v1_val / v2_val
            row_labels.append(f"+{factor:.1f}x")
        else:
            factor = v2_val / v1_val
            row_labels.append(f"-{factor:.1f}x")
    text_labels.append(row_labels)

text_labels = np.array(text_labels)

# Sort methods by average performance trend
avg_ratios = np.mean(heatmap_data, axis=1)
sorted_idx = np.argsort(avg_ratios)

heatmap_data = heatmap_data[sorted_idx]
text_labels = text_labels[sorted_idx]
sorted_methods = [methods[idx] for idx in sorted_idx]

# Clip the log2 ratios to [-3.0, 3.0] (8x variations)
clipped_heatmap_data = np.clip(heatmap_data, -3.0, 3.0)

fig, ax = plt.subplots(figsize=(12, 9), facecolor='none')
ax.set_facecolor('none')

cmap = sns.diverging_palette(15, 240, as_cmap=True)

sns.heatmap(clipped_heatmap_data,
            annot=text_labels,
            fmt="",
            cmap=cmap,
            center=0,
            xticklabels=common_sizes,
            yticklabels=sorted_methods,
            ax=ax,
            cbar_kws={
                'label': '← V1 Faster  |  Relative Speedup Scale (Clipped at 8x)  |  V2 Faster →'
            },
            linewidths=0.8,
            linecolor='#555555',
            annot_kws={'size': 10, 'weight': 'bold'})

ax.set_title('Custom Set Performance Speedup Matrix Heatmap (V1 vs V2)\n(Positive/Blue = V2 Faster, Negative/Red = V1 Faster)',
             color='#ffffff', fontsize=14, fontweight='bold', pad=20)
ax.set_ylabel('Set Interface Methods', color='#aaaaaa', fontsize=12, labelpad=10)
ax.set_xlabel('Collection Size (Elements)', color='#aaaaaa', fontsize=12, labelpad=10)

ax.tick_params(colors='#ffffff', labelsize=10)
plt.xticks(rotation=45)
plt.yticks(rotation=0)

cbar = ax.collections[0].colorbar
cbar.ax.tick_params(colors='#ffffff', labelsize=10)
cbar.ax.yaxis.label.set_color('#ffffff')
cbar.ax.yaxis.label.set_fontsize(11)

plt.tight_layout()
plt.savefig('v1_v2_performance_heatmap.png', dpi=300, transparent=True)
plt.close()
print("Heatmap generated successfully!")