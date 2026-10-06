#!/usr/bin/env python3
"""DroidDeck 中文版: download the pinned GPL components of tools/zh/bundled.json into
app/src/main/assets/bundled/, check each against its GitHub sha256, and write the manifest the app
reads (core/BundledComponents.kt) plus the licence texts. Usage: fetch_bundled.py [out_dir]"""
import hashlib, json, os, sys, urllib.request, shutil

here = os.path.dirname(os.path.abspath(__file__))
repo = os.path.abspath(os.path.join(here, "..", ".."))
out = sys.argv[1] if len(sys.argv) > 1 else os.path.join(repo, "app/src/main/assets/bundled")
spec = json.load(open(os.path.join(here, "bundled.json")))
# Licence texts shipped beside the components: (file in assets/bundled/licenses, where it comes from).
LICENSES = [
    ("The412Banner_Banners-Turnip.txt", "https://raw.githubusercontent.com/The412Banner/Banners-Turnip/HEAD/LICENSE"),
    ("WinNative-Emu_Drivers.txt", "https://raw.githubusercontent.com/WinNative-Emu/Drivers/HEAD/LICENSE"),
    ("Droid-Deck_decky-loader.txt", "https://raw.githubusercontent.com/Droid-Deck/decky-loader/HEAD/LICENSE"),
    # Noto Sans CJK: SIL Open Font License 1.1, pinned with the fonts (same commit).
    ("notofonts_noto-cjk_OFL.txt", "https://raw.githubusercontent.com/notofonts/noto-cjk/f8d157532fbfaeda587e826d4cd5b21a49186f7c/Sans/LICENSE"),
]

def get(url, dest):
    req = urllib.request.Request(url, headers={"User-Agent": "droiddeck-zh-build"})
    with urllib.request.urlopen(req, timeout=120) as r, open(dest, "wb") as f:
        shutil.copyfileobj(r, f)

def sha256(path):
    h = hashlib.sha256()
    with open(path, "rb") as f:
        for b in iter(lambda: f.read(1 << 20), b""):
            h.update(b)
    return h.hexdigest()

os.makedirs(os.path.join(out, "licenses"), exist_ok=True)
for it in spec["items"]:
    dest = os.path.join(out, it["name"])
    if not (os.path.isfile(dest) and sha256(dest) == it["sha256"]):
        url = it.get("url") or f"https://github.com/{it['repo']}/releases/download/{it['tag']}/{it['name']}"
        print("fetch", url, flush=True)
        get(url, dest + ".part")
        got = sha256(dest + ".part")
        if got != it["sha256"] or os.path.getsize(dest + ".part") != it["size"]:
            sys.exit(f"{it['name']}: sha256 {got} != pinned {it['sha256']}")
        os.replace(dest + ".part", dest)
for name, url in LICENSES:
    get(url, os.path.join(out, "licenses", name))
json.dump({"items": spec["items"]}, open(os.path.join(out, "manifest.json"), "w"), indent=1, ensure_ascii=False)
print("bundled", len(spec["items"]), "files into", out)
