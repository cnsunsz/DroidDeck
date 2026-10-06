# DroidDeck 中文版 — 第三方组件与许可声明

DroidDeck 中文版是 [Droid-Deck/DroidDeck](https://github.com/Droid-Deck/DroidDeck) 的分支，整体以 **GPL-3.0** 发布（见 [LICENSE](LICENSE)）。本分支的源代码即本仓库。

## 随 APK 预装的组件

以下文件在构建时由 `tools/zh/fetch_bundled.py` 按 `tools/zh/bundled.json` 中固定的版本和 GitHub SHA-256 摘要下载，原样打包进 APK 的 `assets/bundled/`。应用安装时会再次校验 SHA-256，与在线下载走同一套校验。各组件的许可证全文随 APK 一起分发，位于 `assets/bundled/licenses/`。

| 组件 | 来源（版本） | 许可证 | 对应源代码 |
|---|---|---|---|
| Banners-Turnip（Mesa Turnip Vulkan 驱动）：Adreno 6xx/7xx、8xx、8 Gen 2 One UI、710/720 测试版，Android 与 Linux 各一份 | [The412Banner/Banners-Turnip](https://github.com/The412Banner/Banners-Turnip) `v26.3.0-20261003-r4` | 打包与构建脚本 GPL-3.0；Mesa 本身为 MIT | [该版本的源代码与构建脚本](https://github.com/The412Banner/Banners-Turnip/tree/v26.3.0-20261003-r4)、[Mesa](https://gitlab.freedesktop.org/mesa/mesa) |
| WinNative Turnip（Balanced / Performance），Android `v1.19` 与 Linux `linux-v0.1.2` | [WinNative-Emu/Drivers](https://github.com/WinNative-Emu/Drivers) | 打包与构建脚本 GPL-3.0；Mesa 本身为 MIT | [v1.19](https://github.com/WinNative-Emu/Drivers/tree/v1.19)、[linux-v0.1.2](https://github.com/WinNative-Emu/Drivers/tree/linux-v0.1.2) |
| Decky Loader（PluginLoader-arm64） | [Droid-Deck/decky-loader](https://github.com/Droid-Deck/decky-loader) `droiddeck-arm64-preview.2`（预览版） | GPL-2.0 | [该版本源代码](https://github.com/Droid-Deck/decky-loader/tree/droiddeck-arm64-preview.2) |
| Noto Sans SC（思源黑体的 Noto 版，简体中文子集）Regular 与 Bold，`NotoSansSC-Regular.otf` / `NotoSansSC-Bold.otf` | [notofonts/noto-cjk](https://github.com/notofonts/noto-cjk) 提交 `f8d15753`（`Sans/SubsetOTF/SC/`） | SIL Open Font License 1.1（全文见下） | [该提交](https://github.com/notofonts/noto-cjk/tree/f8d157532fbfaeda587e826d4cd5b21a49186f7c/Sans) |

## 中文字体 Noto Sans SC

Linux 运行时自带的字体只有 DejaVu，其中没有汉字，Steam 大屏幕模式切换到简体中文后会显示成方块。因此 APK 内附带 Noto Sans SC 的 Regular 和 Bold 两个字重：

- `NotoSansSC-Regular.otf`：8,331,336 字节，SHA-256 `faa6c9df652116dde789d351359f3d7e5d2285a2b2a1f04a2d7244df706d5ea9`
- `NotoSansSC-Bold.otf`：8,543,168 字节，SHA-256 `c6cb5a93abaa9edc8ee7463b7ebb7f42d618d40e6ed2f7a5371c97b0b64767c0`

字体版本 2.004，版权所有 © 2014-2021 Adobe (http://www.adobe.com/)。字体原样分发，未作修改。应用会把它们安装到运行时的 `/usr/local/share/fonts/droiddeck-zh/`（附 `LICENSE-OFL.txt`），同时写入 fontconfig 回退规则 `/etc/fonts/conf.d/64-droiddeck-zh-cjk.conf`（源文件为 `app/src/main/assets/zh/64-droiddeck-zh-cjk.conf`）。许可证全文同时随 APK 分发，位于 `assets/bundled/licenses/notofonts_noto-cjk_OFL.txt`。

Noto is a trademark of Google Inc.（“Noto” 是 Google 的商标。）依照 OFL，本分支不出售这些字体本身，也不以原名发布修改版。

<details>
<summary>SIL Open Font License 1.1 全文</summary>

```
This Font Software is licensed under the SIL Open Font License,
Version 1.1.

This license is copied below, and is also available with a FAQ at:
http://scripts.sil.org/OFL

-----------------------------------------------------------
SIL OPEN FONT LICENSE Version 1.1 - 26 February 2007
-----------------------------------------------------------

PREAMBLE
The goals of the Open Font License (OFL) are to stimulate worldwide
development of collaborative font projects, to support the font
creation efforts of academic and linguistic communities, and to
provide a free and open framework in which fonts may be shared and
improved in partnership with others.

The OFL allows the licensed fonts to be used, studied, modified and
redistributed freely as long as they are not sold by themselves. The
fonts, including any derivative works, can be bundled, embedded,
redistributed and/or sold with any software provided that any reserved
names are not used by derivative works. The fonts and derivatives,
however, cannot be released under any other type of license. The
requirement for fonts to remain under this license does not apply to
any document created using the fonts or their derivatives.

DEFINITIONS
"Font Software" refers to the set of files released by the Copyright
Holder(s) under this license and clearly marked as such. This may
include source files, build scripts and documentation.

"Reserved Font Name" refers to any names specified as such after the
copyright statement(s).

"Original Version" refers to the collection of Font Software
components as distributed by the Copyright Holder(s).

"Modified Version" refers to any derivative made by adding to,
deleting, or substituting -- in part or in whole -- any of the
components of the Original Version, by changing formats or by porting
the Font Software to a new environment.

"Author" refers to any designer, engineer, programmer, technical
writer or other person who contributed to the Font Software.

PERMISSION & CONDITIONS
Permission is hereby granted, free of charge, to any person obtaining
a copy of the Font Software, to use, study, copy, merge, embed,
modify, redistribute, and sell modified and unmodified copies of the
Font Software, subject to the following conditions:

1) Neither the Font Software nor any of its individual components, in
Original or Modified Versions, may be sold by itself.

2) Original or Modified Versions of the Font Software may be bundled,
redistributed and/or sold with any software, provided that each copy
contains the above copyright notice and this license. These can be
included either as stand-alone text files, human-readable headers or
in the appropriate machine-readable metadata fields within text or
binary files as long as those fields can be easily viewed by the user.

3) No Modified Version of the Font Software may use the Reserved Font
Name(s) unless explicit written permission is granted by the
corresponding Copyright Holder. This restriction only applies to the
primary font name as presented to the users.

4) The name(s) of the Copyright Holder(s) or the Author(s) of the Font
Software shall not be used to promote, endorse or advertise any
Modified Version, except to acknowledge the contribution(s) of the
Copyright Holder(s) and the Author(s) or with their explicit written
permission.

5) The Font Software, modified or unmodified, in part or in whole,
must be distributed entirely under this license, and must not be
distributed under any other license. The requirement for fonts to
remain under this license does not apply to any document created using
the Font Software.

TERMINATION
This license becomes null and void if any of the above conditions are
not met.

DISCLAIMER
THE FONT SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO ANY WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT
OF COPYRIGHT, PATENT, TRADEMARK, OR OTHER RIGHT. IN NO EVENT SHALL THE
COPYRIGHT HOLDER BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
INCLUDING ANY GENERAL, SPECIAL, INDIRECT, INCIDENTAL, OR CONSEQUENTIAL
DAMAGES, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
FROM, OUT OF THE USE OR INABILITY TO USE THE FONT SOFTWARE OR FROM
OTHER DEALINGS IN THE FONT SOFTWARE.
```

</details>

## 不预装、仍在线下载的内容

许可证不明确或不允许再分发的内容不会打包进 APK，仍由应用在需要时从原始来源下载：

- Linux 运行时（约 3 GB）及桌面与应用包（The412Banner/winlator-contents 等，未声明许可证）
- Nightlies 组件包（The412Banner/Nightlies，未声明许可证）
- Steam 客户端与 Proton（Valve 所有）
- DD-Turnip（Droid-Deck/Drivers-CI，未声明许可证）
- droiddeck-esync 包（由上游签名索引校验）

Steam 和 Proton 是 Valve Corporation 的商标或财产，本项目与 Valve 无关联。
