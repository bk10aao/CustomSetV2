import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns

# 1. Load the benchmark data files
custom_set_df = pd.read_csv('../CompareAll/CustomSetV2.csv')
hash_set_df = pd.read_csv('../CompareAll/HashSet - performance_average.csv')

# 2. Extract intersection bounds and filter tracking operations
common_sizes = sorted(list(set(custom_set_df['Size']).intersection(set(hash_set_df['Size']))))
methods = [col for col in custom_set_df.columns if col != 'Size']

# 3. Construct the relative performance matrix (Log2 ratios)
heatmap_data = np.zeros((len(methods), len(common_sizes)))
text_labels = []

for i, m in enumerate(methods):
    row_labels = []
    for j, size in enumerate(common_sizes):
        c_val = custom_set_df.loc[custom_set_df['Size'] == size, m].values[0]
        j_val = hash_set_df.loc[hash_set_df['Size'] == size, m].values[0]

        # Prevent division by zero errors
        if c_val == 0: c_val = 1
        if j_val == 0: j_val = 1

        # Log2 ratio calculation (Positive values indicate CustomSetV2 is faster)
        ratio = np.log2(j_val / c_val)
        heatmap_data[i, j] = ratio

        if j_val > c_val:
            factor = j_val / c_val
            row_labels.append(f"+{factor:.1f}x")
        else:
            factor = c_val / j_val
            row_labels.append(f"-{factor:.1f}x")
    text_labels.append(row_labels)

text_labels = np.array(text_labels)

# 4. Sort methods from top to bottom by their geometric trends
avg_ratios = np.mean(heatmap_data, axis=1)
sorted_idx = np.argsort(avg_ratios)

heatmap_data = heatmap_data[sorted_idx]
text_labels = text_labels[sorted_idx]
sorted_methods = [methods[idx] for idx in sorted_idx]

# 5. Initialize figure with a fully transparent background canvas
fig, ax = plt.subplots(figsize=(14, 11), facecolor='none')
ax.set_facecolor('none')

# 6. Clip the log2 ratios to [-3.0, 3.0] (maps color range bounds to 8x variations)
# Keeps your color visualization punchy and structurally readable
clipped_heatmap_data = np.clip(heatmap_data, -3.0, 3.0)

# 7. Create a custom divergent colormap (Red = HashSet Faster, Blue = CustomSetV2 Faster)
cmap = sns.diverging_palette(15, 240, as_cmap=True)

# 8. Render the Seaborn Heatmap
sns.heatmap(clipped_heatmap_data,
            annot=text_labels,
            fmt="",
            cmap=cmap,
            center=0,
            xticklabels=common_sizes,
            yticklabels=sorted_methods,
            ax=ax,
            cbar_kws={
                'label': '← JDK Faster (HashSet)  |  Relative Speedup Scale (Clipped at 8x)  |  Custom Faster (CustomSetV2) →'},
            linewidths=0.8,
            linecolor='#555555',
            annot_kws={'size': 10, 'weight': 'bold'})

# 9. Format Title, Labels, and Colorbar styling to match transparent aesthetics
ax.set_title(
    'Java Set Performance Speedup Matrix Heatmap Across Sizes\n(Positive = CustomSetV2 Faster, Negative = HashSet Faster)',
    color='#ffffff', fontsize=16, fontweight='bold', pad=20)
ax.set_ylabel('Set Interface Methods', color='#aaaaaa', fontsize=13, labelpad=10)
ax.set_xlabel('Collection Size (Elements)', color='#aaaaaa', fontsize=13, labelpad=10)

ax.tick_params(colors='#ffffff', labelsize=11)
plt.xticks(rotation=45)
plt.yticks(rotation=0)

# Style colorbar elements to blend with clean background pages
cbar = ax.collections[0].colorbar
cbar.ax.tick_params(colors='#ffffff', labelsize=10)
cbar.ax.yaxis.label.set_color('#ffffff')
cbar.ax.yaxis.label.set_fontsize(12)

# 10. Process tight constraints and write to disc
plt.tight_layout()
plt.savefig('heatmap.png', dpi=300, transparent=True)
plt.close()