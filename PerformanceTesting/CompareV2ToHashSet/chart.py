from PIL import Image

# List of chart image files
chart_files = ["add.png", "addAll.png", "clear.png", "contains.png", "containsAll.png", 
               "isEmpty.png", "remove.png", "removeAll.png", "retainAll.png", "size.png", 
               "toArray.png", "toString.png"]

# Load images
images = [Image.open(file) for file in chart_files]

# Get dimensions (assuming same width)
width = images[0].width
total_height = sum(img.height for img in images)

# Create a new blank image
combined = Image.new('RGB', (width, total_height))

# Paste images vertically
y_offset = 0
for img in images:
    combined.paste(img, (0, y_offset))
    y_offset += img.height

# Save the combined image
combined.save("v2_vs_hashset_stacked.png")
