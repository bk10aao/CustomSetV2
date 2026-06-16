import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns

# 1. Load your sequential benchmark runs
v1_df = pd.read_csv('CustomSetV1 - V1_performance_average.csv')
v2_df = pd.read_csv('V2_Hash_2_performance_data.csv')

methods = [col for col in v1_df.columns if col != 'Size']
sizes = v1_df['Size'].tolist()

# 2. Extract comparison data matrices
heatmap_data = np.zeros((len(methods), len(sizes)))
text_labels = []

for i, m in enumerate(methods):
    row_labels = []
    for j, size in enumerate(sizes):
        v1_val = v1_df.loc[v1_df['Size'] == size, m].values[0]
        v2_val = v2_df.loc[v2_df['Size'] == size, m].values[0]

        if v1_val == 0: v1_val = 1
        if v2_val == 0: v2_val = 1

        # Log2 calculation: Positive values confirm V2 outpaced V1
        ratio = np.log2(v1_val / v2_val)
        heatmap_data[i, j] = ratio

        if v1_val >= v2_val:
            factor = v1_val / v2_val
            row_labels.append(f"+{factor:.1f}x")
        else:
            factor = v2_val / v1_val
            row_labels.append(f"-{factor:.1f}x")
    text_labels.append(row_labels)

text_labels = np.array(text_labels)

# 3. Order methods sequentially by optimization profile
avg_ratios = np.mean(heatmap_data, axis=1)
sorted_idx = np.argsort(avg_ratios)

heatmap_data = heatmap_data[sorted_idx]
text_labels = text_labels[sorted_idx]
sorted_methods = [methods[idx] for idx in sorted_idx]

# 4. Initialize transparent environment canvas
fig, ax = plt.subplots(figsize=(14, 11), facecolor='none')
ax.set_facecolor('none')

# 5. Contrast clipping bounds setup to protect mid-tier variations
clipped_heatmap_data = np.clip(heatmap_data, -4.0, 4.0)
cmap = sns.diverging_palette(15, 240, as_cmap=True)

# 6. Render the Seaborn Heatmap
sns.heatmap(clipped_heatmap_data,
            annot=text_labels,
            fmt="",
            cmap=cmap,
            center=0,
            xticklabels=sizes,
            yticklabels=sorted_methods,
            ax=ax,
            cbar_kws={
                'label': '← CustomSetV1 Faster  |  Relative Speedup Scale (Clipped at 16x)  |  CustomSetV2 Faster →'},
            linewidths=0.8,
            linecolor='#555555',
            annot_kws={'size': 9, 'weight': 'bold'})

# 7. Apply styling elements matching standard layout constraints
ax.set_title(
    'Custom Java Set Architectural Progression: V1 vs V2 Performance Heatmap\n(Positive/Blue = V2 is Faster, Negative/Red = V1 is Faster)',
    color='#ffffff', fontsize=16, fontweight='bold', pad=20)
ax.set_ylabel('Set Interface Methods', color='#aaaaaa', fontsize=13, labelpad=10)
ax.set_xlabel('Collection Size (Elements)', color='#aaaaaa', fontsize=13, labelpad=10)

ax.tick_params(colors='#ffffff', labelsize=11)
plt.xticks(rotation=45)
plt.yticks(rotation=0)

cbar = ax.collections[0].colorbar
cbar.ax.tick_params(colors='#ffffff', labelsize=10)
cbar.ax.yaxis.label.set_color('#ffffff')
cbar.ax.yaxis.label.set_fontsize(12)

plt.tight_layout()
plt.savefig('set_v1_vs_v2_heatmap_transparent.png', dpi=300, transparent=True)
plt.close()