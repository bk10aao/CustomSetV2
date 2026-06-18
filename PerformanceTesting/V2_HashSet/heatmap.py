import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns

# 1. Load and prepare data
custom_set_df = pd.read_csv('V2_performance_data.csv')
hash_set_df = pd.read_csv('HashSet_performance_data.csv')

# Ensure 'Size' is numeric
custom_set_df['Size'] = pd.to_numeric(custom_set_df['Size'])
hash_set_df['Size'] = pd.to_numeric(hash_set_df['Size'])

# 2. Define the exact order and names matching your CSV columns
# Note: 'Size.1' is likely a duplicate of 'Size', so we exclude it.
ordered_methods = [
    'Add', 'AddAll', 'Clear', 'Contains', 'ContainsAll',
    'IsEmpty', 'Remove', 'RemoveAll', 'RetainAll',
    'ToArray', 'ToString', 'Size'
]

# Get common sizes
common_sizes = sorted(list(set(custom_set_df['Size']).intersection(set(hash_set_df['Size']))))

# Filter methods: ensure the method exists in the CSV and is in our ordered list
cols_in_df = set(custom_set_df.columns).intersection(set(hash_set_df.columns))
methods = [m for m in ordered_methods if m in cols_in_df]

# 3. Construct DataFrames
heatmap_data = []
text_labels = []

for m in methods:
    row_vals = []
    row_annots = []
    for size in common_sizes:
        c_val = custom_set_df.loc[custom_set_df['Size'] == size, m].values[0]
        j_val = hash_set_df.loc[hash_set_df['Size'] == size, m].values[0]

        # Avoid division by zero
        c_val, j_val = (1 if v <= 0 else v for v in (c_val, j_val))

        ratio = np.log2(j_val / c_val)
        row_vals.append(ratio)

        factor = j_val / c_val if j_val > c_val else c_val / j_val
        prefix = "+" if j_val > c_val else "-"
        row_annots.append(f"{prefix}{factor:.1f}x")

    heatmap_data.append(row_vals)
    text_labels.append(row_annots)

df_heatmap = pd.DataFrame(heatmap_data, index=methods, columns=common_sizes)
df_annot = pd.DataFrame(text_labels, index=methods, columns=common_sizes)

# 4. Enforce categorical ordering
df_heatmap.index = pd.Categorical(df_heatmap.index, categories=methods, ordered=True)
df_heatmap = df_heatmap.sort_index()
df_annot = df_annot.reindex(df_heatmap.index)

# 5. Render Heatmap
fig, ax = plt.subplots(figsize=(14, 11), facecolor='none')
ax.set_facecolor('none')

sns.heatmap(df_heatmap,
            annot=df_annot.values,
            fmt="",
            cmap=sns.diverging_palette(15, 240, as_cmap=True),
            center=0,
            ax=ax,
            cbar_kws={'label': '← JDK Faster  |  Relative Speedup Scale  |  V2 Faster →'},
            linewidths=0.8,
            linecolor='#555555',
            annot_kws={'size': 9, 'weight': 'bold'})

# 6. Styling
ax.set_title('Performance Speedup Matrix', color='#ffffff', fontsize=16, fontweight='bold', pad=20)
ax.set_ylabel('Set Interface Methods', color='#aaaaaa', fontsize=13, labelpad=10)
ax.set_xlabel('Collection Size (Elements)', color='#aaaaaa', fontsize=13, labelpad=10)
ax.tick_params(colors='#ffffff', labelsize=10)

plt.xticks(rotation=45)
plt.yticks(rotation=0)

plt.tight_layout()
plt.savefig('heatmap.png', dpi=300, transparent=True)
plt.close()