import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns

# 1. Load and prepare data
custom_set_df = pd.read_csv('V2_performance_data.csv').sort_values('Size').reset_index(drop=True)
hash_set_df = pd.read_csv('HashSet_performance_data.csv').sort_values('Size').reset_index(drop=True)

# Define your strict vertical order here (Top to Bottom)
ordered_methods = [
    'AddTime', 'AddAllTime', 'ContainsTime', 'ContainsAllTime',
    'RemoveTime', 'RemoveAllTime', 'RetainAllTime',
    'SizeTime', 'ToArrayTime', 'ToStringTime', 'IsEmptyTime'
]

# Filter methods based on the CSV and ensure they exist in our ordering list
available_methods = [col for col in custom_set_df.columns if col != 'Size' and col.lower() != 'cleartime']
methods = [m for m in ordered_methods if m in available_methods]

num_rows = min(len(custom_set_df), len(hash_set_df))
custom_set_df = custom_set_df.iloc[:num_rows]
hash_set_df = hash_set_df.iloc[:num_rows]

# 2. Calculate data and annotations
heatmap_values = []
annot_values = []

for m in methods:
    row_vals = []
    row_annots = []
    for j in range(num_rows):
        c_val = custom_set_df.loc[j, m]
        j_val = hash_set_df.loc[j, m]

        c_val, j_val = (1 if v == 0 else v for v in (c_val, j_val))

        # Ratio: positive indicates V2 is faster
        ratio = np.log2(j_val / c_val)
        row_vals.append(ratio)

        factor = j_val / c_val if j_val > c_val else c_val / j_val
        prefix = "+" if j_val > c_val else "-"
        row_annots.append(f"{prefix}{factor:.1f}x")

    heatmap_values.append(row_vals)
    annot_values.append(row_annots)

# 3. Create DataFrames
df_heatmap = pd.DataFrame(heatmap_values, index=methods, columns=custom_set_df['Size'])
df_annot = pd.DataFrame(annot_values, index=methods, columns=custom_set_df['Size'])

# 4. Enforce categorical ordering to lock the sequence
df_heatmap.index = pd.Categorical(df_heatmap.index, categories=methods, ordered=True)
df_heatmap = df_heatmap.sort_index()
df_annot = df_annot.reindex(df_heatmap.index)

# 5. Initialize figure
fig, ax = plt.subplots(figsize=(14, 11), facecolor='none')
ax.set_facecolor('none')

# 6. Clip values for visual range consistency
clipped_data = np.clip(df_heatmap.values, -3.0, 3.0)

# 7. Render Heatmap (Pass DataFrames directly)
sns.heatmap(df_heatmap,
            annot=df_annot.values,
            fmt="",
            cmap=sns.diverging_palette(15, 240, as_cmap=True),
            center=0,
            ax=ax,
            cbar_kws={'label': '← JDK Faster (HashSet)  |  Relative Speedup Scale  |  V2 Faster (Custom Set) →'},
            linewidths=0.8,
            linecolor='#555555',
            annot_kws={'size': 9, 'weight': 'bold'})

# 8. Styling
ax.set_title('Java Set Performance Speedup Matrix (V2 vs HashSet)',
             color='#ffffff', fontsize=16, fontweight='bold', pad=20)
ax.set_ylabel('Set Interface Methods', color='#aaaaaa', fontsize=13, labelpad=10)
ax.set_xlabel('Collection Size (Elements)', color='#aaaaaa', fontsize=13, labelpad=10)
ax.tick_params(colors='#ffffff', labelsize=10)

plt.xticks(rotation=45)
plt.yticks(rotation=0)

cbar = ax.collections[0].colorbar
cbar.ax.tick_params(colors='#ffffff', labelsize=10)
cbar.ax.yaxis.label.set_color('#ffffff')

plt.tight_layout()
plt.savefig('charts/v2_hashset_performance_heatmap_final.png', dpi=300, transparent=True)
plt.close()