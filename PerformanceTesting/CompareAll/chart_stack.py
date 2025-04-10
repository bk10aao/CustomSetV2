import os
import shutil
import requests
from PIL import Image

# === Settings ===
raw_base_url = 'https://raw.githubusercontent.com/bk10aao/CustomSetV2/main/PerformanceTesting/CompareAll/'
download_dir = 'images'
output_file = 'combined_grid.jpg'
target_width = 1000  # All images will be resized to this width, keeping aspect ratio

# Files to ignore (still respected, but not needed if we hardcode the list)
ignore_files = {'combined.png', 'combined_grid.jpg'}

# Exact order of images as specified
image_filenames = [
    'add_comparison.png',
    'addAll_comparison.png',
    'clear_comparison.png',
    'contains_comparison.png',
    'containsAll_comparison.png',
    'isEmpty_comparison.png',
    'remove_comparison.png',
    'removeAll_comparison.png',
    'retainAll_comparison.png',
    'size_comparison.png',
    'toArray_comparison.png',
    'toString_comparison.png'
]

# === Step 1: Clean up old images ===
if os.path.exists(download_dir):
    shutil.rmtree(download_dir)
os.makedirs(download_dir)

# === Step 2: Download and scale images in the specified order ===
images = []
for filename in image_filenames:
    image_url = raw_base_url + filename
    image_path = os.path.join(download_dir, filename)

    # Download the image
    img_data = requests.get(image_url).content
    with open(image_path, 'wb') as f:
        f.write(img_data)

    # Open and resize
    img = Image.open(image_path).convert("RGB")
    w_percent = target_width / float(img.size[0])
    new_height = int((float(img.size[1]) * float(w_percent)))
    resized = img.resize((target_width, new_height), Image.LANCZOS)
    images.append(resized)
    print(f"Added: {filename}")

# === Step 3: Create a vertically stacked image ===
total_height = sum(img.height for img in images)
output_image = Image.new('RGB', (target_width, total_height), 'white')

y_offset = 0
for img in images:
    output_image.paste(img, (0, y_offset))
    y_offset += img.height

# === Step 4: Save the final image ===
output_image.save(output_file)
print(f"✅ Combined image saved as '{output_file}' with {len(images)} charts.")
print("Order (top to bottom):", ", ".join(image_filenames))