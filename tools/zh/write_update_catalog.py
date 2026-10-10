#!/usr/bin/env python3
"""Write the static update catalog the Chinese build's AppUpdates fetches."""
import json
import sys

def main():
    tag, commit, ver, code, name, sha, signer, size, summary, published, out = sys.argv[1:]
    catalog = {
        "schema": 1,
        "sourceRepo": "cnsunsz/DroidDeck",
        "ciRepo": "cnsunsz/DroidDeck",
        "generatedAt": int(published),
        "stable": {
            "tag": tag,
            "title": f"DroidDeck 中文版 {tag}",
            "summary": summary,
            "commit": commit,
            "pr": 0,
            "version": ver,
            "versionCode": int(code),
            "publishedAt": int(published),
            "url": f"https://github.com/cnsunsz/DroidDeck/releases/tag/{tag}",
            "apks": {
                "com.droiddeck.launcher.zh": {
                    "name": name,
                    "url": f"https://github.com/cnsunsz/DroidDeck/releases/download/{tag}/{name}",
                    "size": int(size),
                    "sha256": sha,
                    "packageName": "com.droiddeck.launcher.zh",
                    "versionCode": int(code),
                    "signerSha256": signer,
                }
            },
        },
        "preview": None,
        "tests": [],
        "recentPreviews": [],
    }
    with open(out, "w", encoding="utf-8") as f:
        json.dump(catalog, f, ensure_ascii=False, indent=2)
        f.write("\n")

if __name__ == "__main__":
    main()
