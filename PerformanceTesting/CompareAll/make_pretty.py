import os
import math
import shutil
import requests
from PIL import Image
from bs4 import BeautifulSoup

# === Settings ===
repo_url = 'https://github.com/bk10aao/CustomSetV2/tree/main/PerformanceTesting/CompareAll'
raw_base_url = 'https://raw.githubusercontent.com/bk10aao/CustomSetV2/main/PerformanceTesting/CompareAll/'
download_dir = 'images'
output_file = 'combined_grid.jpg'
cols = 2
scale_factor = 1.5

# === Step 1: Clean up download folder ===
if os.path.exists(download_dir):
    shutil.rmtree(download_dir)
os.makedirs(download_dir)

# === Step 2: Scrape image filenames from GitHub page ===
response = requests.get(repo_url)
soup = BeautifulSoup(response.text, 'html.parser')

image_filenames = []
for link in soup.find_all('a', href=True):
    href = link['href']
    if any(href.lower().endswith(ext) for ext in ['.png', '.jpg', '.jpeg']):
        filename = href.split('/')[-1]
        image_filenames.append(filename)

# Sort alphabetically
image_filenames = sorted(set(image_filenames))

# === Step 3: Download images ===
for filename in image_filenames:
    image_url = raw_base_url + filename
    image_path = os.path.join(download_dir, filename)
    img_data = requests.get(image_url).content
    with open(image_path, 'wb') as f:
        f.write(img_data)

# === Step 4: Load and scale images ===
images = []
for filename in image_filenames:
    path = os.path.join(download_dir, filename)
    img = Image.open(path)
    new_size = (int(img.width * scale_factor), int(img.height * scale_factor))
    images.append(img.resize(new_size, Image.LANCZOS))

# === Step 5: Calculate grid layout ===
total_images = len(images)
rows = math.ceil(total_images / cols)
img_width, img_height = images[0].size
grid_img = Image.new('RGB', (cols * img_width, rows * img_height), color='white')

# === Step 6: Paste images ===
for idx, img in enumerate(images):
    row = idx // cols
    col = idx % cols
    grid_img.paste(img, (col * img_width, row * img_height))

# === Step 7: Save result ===
grid_img.save(output_file)
print(f"✅ Saved combined image with {cols} columns as '{output_file}'")
