import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns

# 1. Load the benchmark data files
custom_set_df = pd.read_csv('V2_performance_data.csv').sort_values('Size').reset_index(drop=True)
hash_set_df = pd.read_csv('HashSet_performance_data.csv').sort_values('Size').reset_index(drop=True)

# 2. Extract methods from Custom V2 data (Excluding 'Size' and 'ClearTime')
methods = [col for col in custom_set_df.columns if col != 'Size' and col.lower() != 'cleartime']

# Use row mapping to dynamically align datasets regardless of differences in size keys
num_rows = min(len(custom_set_df), len(hash_set_df))
custom_set_df = custom_set_df.iloc[:num_rows]
hash_set_df = hash_set_df.iloc[:num_rows]

# Generate labels showing Custom V2's sizes vs JDK HashSet's matching row sizes
display_size_labels = [
    f"V2: {c_sz} \n(JDK: {j_sz})"
    for c_sz, j_sz in zip(custom_set_df['Size'], hash_set_df['Size'])
]

# 3. Construct the relative performance matrix (Log2 ratios)
heatmap_data = np.zeros((len(methods), num_rows))
text_labels = []

for i, m in enumerate(methods):
    row_labels = []
    for j in range(num_rows):
        c_val = custom_set_df.loc[j, m]
        j_val = hash_set_df.loc[j, m]

        # Prevent division by zero errors
        if c_val == 0: c_val = 1
        if j_val == 0: j_val = 1

        # Log2 ratio calculation (Positive values indicate V2 is faster)
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
clipped_heatmap_data = np.clip(heatmap_data, -3.0, 3.0)

# 7. Create a custom divergent colormap (Red = HashSet Faster, Blue = Custom V2 Faster)
cmap = sns.diverging_palette(15, 240, as_cmap=True)

# 8. Render the Seaborn Heatmap
sns.heatmap(clipped_heatmap_data,
            annot=text_labels,
            fmt="",
            cmap=cmap,
            center=0,
            xticklabels=display_size_labels,
            yticklabels=sorted_methods,
            ax=ax,
            cbar_kws={
                'label': '← JDK Faster (HashSet)  |  Relative Speedup Scale (Clipped at 8x)  |  V2 Faster (Custom Set) →'
            },
            linewidths=0.8,
            linecolor='#555555',
            annot_kws={'size': 9, 'weight': 'bold'})

# 9. Format Title, Labels, and Colorbar styling to match transparent aesthetics
ax.set_title(
    'Java Set Performance Speedup Matrix Heatmap Across Sizes\n(Positive/Blue = V2 Faster, Negative/Red = HashSet Faster)',
    color='#ffffff', fontsize=16, fontweight='bold', pad=20)
ax.set_ylabel('Set Interface Methods', color='#aaaaaa', fontsize=13, labelpad=10)
ax.set_xlabel('Collection Size Index Pairings (Elements)', color='#aaaaaa', fontsize=13, labelpad=10)

ax.tick_params(colors='#ffffff', labelsize=10)
plt.xticks(rotation=0)
plt.yticks(rotation=0)

# Style colorbar elements to blend with clean background pages
cbar = ax.collections[0].colorbar
cbar.ax.tick_params(colors='#ffffff', labelsize=10)
cbar.ax.yaxis.label.set_color('#ffffff')
cbar.ax.yaxis.label.set_fontsize(12)

# 10. Process tight constraints and write to disc
plt.tight_layout()
plt.savefig('charts/heatmap.png', dpi=300, transparent=True)
plt.close()

print("Heatmap successfully generated displaying all tracking operational methods!")