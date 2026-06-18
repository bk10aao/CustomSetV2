import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns

# 1. Load and prepare data
custom_set_df = pd.read_csv('V2_performance_data.csv').sort_values('Size').reset_index(drop=True)
hash_set_df = pd.read_csv('HashSet_performance_data.csv').sort_values('Size').reset_index(drop=True)

methods = [col for col in custom_set_df.columns if col != 'Size' and col.lower() != 'cleartime']
num_rows = min(len(custom_set_df), len(hash_set_df))
custom_set_df = custom_set_df.iloc[:num_rows]
hash_set_df = hash_set_df.iloc[:num_rows]

# 2. Calculate data and annotations
heatmap_data = []
annot_data = []

for m in methods:
    row_vals = []
    row_annots = []
    for j in range(num_rows):
        c_val = custom_set_df.loc[j, m]
        j_val = hash_set_df.loc[j, m]
        c_val, j_val = (1 if v == 0 else v for v in (c_val, j_val))

        # Ratio
        ratio = np.log2(j_val / c_val)
        row_vals.append(ratio)

        # Annotation string
        factor = j_val / c_val if j_val > c_val else c_val / j_val
        prefix = "+" if j_val > c_val else "-"
        row_annots.append(f"{prefix}{factor:.1f}x")

    heatmap_data.append(row_vals)
    annot_data.append(row_annots)

# 3. Create a DataFrame for the heatmap
df_heatmap = pd.DataFrame(heatmap_data, index=methods, columns=custom_set_df['Size'])
df_annot = pd.DataFrame(annot_data, index=methods, columns=custom_set_df['Size'])

# 4. Sort the DataFrame by geometric mean (Reversed: True = Lowest/Regressions at top)
df_heatmap['mean_perf'] = df_heatmap.mean(axis=1)
df_heatmap = df_heatmap.sort_values(by='mean_perf', ascending=True)

# Apply same sort to annotations
df_annot = df_annot.reindex(df_heatmap.index)
# Drop sorting column
df_heatmap = df_heatmap.drop(columns=['mean_perf'])

# 5. Initialize figure
fig, ax = plt.subplots(figsize=(14, 11), facecolor='none')
ax.set_facecolor('none')

# 6. Clip data
clipped_data = np.clip(df_heatmap.values, -3.0, 3.0)

# 7. Render Heatmap
sns.heatmap(clipped_data,
            annot=df_annot.values,
            fmt="",
            cmap=sns.diverging_palette(15, 240, as_cmap=True),
            center=0,
            xticklabels=df_heatmap.columns,
            yticklabels=df_heatmap.index,
            ax=ax,
            cbar_kws={'label': '← JDK Faster (HashSet)  |  Relative Speedup Scale  |  V2 Faster (Custom Set) →'},
            linewidths=0.8,
            linecolor='#555555',
            annot_kws={'size': 9, 'weight': 'bold'})

# 8. Styling
ax.set_title('Java Set Performance Speedup Matrix (V2 vs HashSet)\n(Sorted in Reverse)', color='#ffffff', fontsize=16,
             fontweight='bold', pad=20)
ax.set_ylabel('Set Interface Methods', color='#aaaaaa', fontsize=13, labelpad=10)
ax.set_xlabel('Collection Size (Elements)', color='#aaaaaa', fontsize=13, labelpad=10)
ax.tick_params(colors='#ffffff', labelsize=10)

plt.xticks(rotation=45)
plt.yticks(rotation=0)

cbar = ax.collections[0].colorbar
cbar.ax.tick_params(colors='#ffffff', labelsize=10)
cbar.ax.yaxis.label.set_color('#ffffff')

plt.tight_layout()
plt.savefig('charts/v2_hashset_performance_heatmap_reversed.png', dpi=300, transparent=True)
plt.close()