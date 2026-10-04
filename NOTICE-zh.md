# DroidDeck 中文版 — 第三方组件与许可声明

DroidDeck 中文版是 [Droid-Deck/DroidDeck](https://github.com/Droid-Deck/DroidDeck) 的分支，整体以 **GPL-3.0** 发布（见 [LICENSE](LICENSE)）。本分支的源代码即本仓库。

## 随 APK 预装的组件

以下文件在构建时由 `tools/zh/fetch_bundled.py` 按 `tools/zh/bundled.json` 中固定的版本和 GitHub SHA-256 摘要下载，原样打包进 APK 的 `assets/bundled/`。应用安装时会再次校验 SHA-256，与在线下载走同一套校验。各组件的许可证全文随 APK 一起分发，位于 `assets/bundled/licenses/`。

| 组件 | 来源（版本） | 许可证 | 对应源代码 |
|---|---|---|---|
| Banners-Turnip（Mesa Turnip Vulkan 驱动）：Adreno 6xx/7xx、8xx、8 Gen 2 One UI、710/720 测试版，Android 与 Linux 各一份 | [The412Banner/Banners-Turnip](https://github.com/The412Banner/Banners-Turnip) `v26.3.0-20261003-r4` | 打包与构建脚本 GPL-3.0；Mesa 本身为 MIT | [该版本的源代码与构建脚本](https://github.com/The412Banner/Banners-Turnip/tree/v26.3.0-20261003-r4)、[Mesa](https://gitlab.freedesktop.org/mesa/mesa) |
| WinNative Turnip（Balanced / Performance），Android `v1.19` 与 Linux `linux-v0.1.2` | [WinNative-Emu/Drivers](https://github.com/WinNative-Emu/Drivers) | 打包与构建脚本 GPL-3.0；Mesa 本身为 MIT | [v1.19](https://github.com/WinNative-Emu/Drivers/tree/v1.19)、[linux-v0.1.2](https://github.com/WinNative-Emu/Drivers/tree/linux-v0.1.2) |
| Decky Loader（PluginLoader-arm64） | [Droid-Deck/decky-loader](https://github.com/Droid-Deck/decky-loader) `droiddeck-arm64-preview.2`（预览版） | GPL-2.0 | [该版本源代码](https://github.com/Droid-Deck/decky-loader/tree/droiddeck-arm64-preview.2) |

## 不预装、仍在线下载的内容

许可证不明确或不允许再分发的内容不会打包进 APK，仍由应用在需要时从原始来源下载：

- Linux 运行时（约 3 GB）及桌面与应用包（The412Banner/winlator-contents 等，未声明许可证）
- Nightlies 组件包（The412Banner/Nightlies，未声明许可证）
- Steam 客户端与 Proton（Valve 所有）
- DD-Turnip（Droid-Deck/Drivers-CI，未声明许可证）
- droiddeck-esync 包（由上游签名索引校验）

Steam 和 Proton 是 Valve Corporation 的商标或财产，本项目与 Valve 无关联。
