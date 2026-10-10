<p align="center">
  <picture>
    <source media="(prefers-color-scheme: dark)" srcset="artwork/droiddeck-banner-dark.svg">
    <img alt="DroidDeck" src="artwork/droiddeck-banner-light.svg" width="100%">
  </picture>
</p>

DroidDeck brings the SteamOS experience to Android: Valve's Steam client in Big Picture on your Adreno handheld, with Windows games through Valve's ARM64 Proton.

<p align="center"><a href="https://discord.gg/JRGAvawjsm"><img src="https://img.shields.io/badge/Discord-Join%20the%20community-5865F2?logo=discord&logoColor=white" alt="Join the DroidDeck Discord"></a></p>

<p align="center"><img src="docs/releases/media/0.2.0/launch-into-steam.gif" width="80%" alt="Tapping DroidDeck on the Android home screen and landing in Steam Big Picture"></p>

> Note: DroidDeck does not have a stand-alone website. Do not click on any download links from websites claiming to be the DroidDeck team. 

## 中文版说明（DroidDeck 中文版）

这是 [Droid-Deck/DroidDeck](https://github.com/Droid-Deck/DroidDeck) 的简体中文分支，下载地址见本仓库的 [Releases](https://github.com/cnsunsz/DroidDeck/releases)。

- **当前基于上游 0.3.2**（中文版 v0.3.2-zh.1），通过合并（merge）跟进上游版本。
- **完整简体中文界面**：`app/src/main/res/values-zh-rCN/strings.xml`，系统语言为简体中文时自动启用。
- **包名 `com.droiddeck.launcher.zh`**，应用名“DroidDeck 中文版”，可与原版**同时安装**。原版由上游私有密钥签名，第三方构建无法覆盖安装原版。
- **预装 GPU 驱动（Banners-Turnip、WinNative Turnip）和 Decky Loader**，首次运行无需下载，安装前会校验 SHA-256。许可证与源代码链接见 [NOTICE-zh.md](NOTICE-zh.md)。
- **应用内更新**指向本仓库 Releases（`catalog` 分支上的目录），签名为本分支密钥。
- **内置中文字体 Noto Sans SC**（SIL OFL 1.1），启动时自动装进 Linux 运行时并配置 fontconfig 回退，Steam 大屏幕模式切换到中文后不会再显示方块。手机语言为简体中文时，全新的 Steam 默认使用简体中文（不会覆盖已有的语言设置）。
- Linux 运行时、Nightlies 组件、Steam 和 Proton 仍按原方式在线下载。
- 发布流程：推送 `v*-zh*` 标签（发布说明写在 `docs/releases/<标签>.md`），由 `.github/workflows/release-zh.yml` 构建，并用本分支的发布密钥签名、发布。

## Requirements and install

Use Android 9 or newer on a supported Adreno device (730 or newer, or 8xx). Adreno 6xx is experimental: DirectX 11 uses DXVK 2 and may run, and DirectX 12 games can still crash. Mali, Xclipse, PowerVR, and Adreno 710 are unsupported. No root is required. Allow about 3 GB for the runtime and 1.1 GB more for the desktop and emulators. Install the APK from [Releases](https://github.com/Droid-Deck/DroidDeck/releases), install the Linux runtime, then press **Play** and sign in. Steam downloads on first launch. Install **Desktop & apps** to use the desktop and emulators. The **Store** installs Linux apps and games from Flathub (ARM64 builds) with Flatpak; with additional options to install Appimages and set up scripts.

Before Steam launches, you must turn off **Restrict child processes** in Developer options. If this option is not available in developer settings (Android 12 and 13 devices), first launch of Steam will present a "Fix it for me" button, which will help automate the setup process.

## Community

Join the [DroidDeck Discord](https://discord.gg/JRGAvawjsm) for help, Preview builds, and device reports. Bug reports go in its **#bug-reports** forum; attach the session folder from `Download/DroidDeck/` so the logs come with it.

## Build

Run `tools/build_local.sh` with Docker, Java 17, the Android SDK/NDK, and `zstd` installed. It builds the ARM64 audio sinks from PulseAudio 13.0 and packages them into the APK at `app/build/outputs/apk/release/app-release.apk`. Set `DROIDDECK_PA13_SOURCE_DIR` to an existing PulseAudio 13.0 source directory to skip downloading it. To install the APK on an attached device, run `tools/deploy_local.sh`.

## Limits

Compatibility and performance vary by device; hardware validation is limited. Desktop compositing uses software rendering. Firefox sandboxing is reduced under proot. See the session logs in `Download/DroidDeck/` when diagnosing problems.

## Credits and licence

GPL-3.0. Runtime, shim, input, and controller work build on WinNative and Bannerlator (maxjivi05). LSFG frame generation is from the work of Camille LaVey and the [Eden](https://eden-emu.dev) emulator project, following [lsfg-vk](https://github.com/PancakeTAS/lsfg-vk), ported to WinNative and DroidDeck by [@maxjivi05](https://github.com/maxjivi05); it needs your own copy of [Lossless Scaling](https://store.steampowered.com/app/993090/) and ships none of its shaders. x86 AppImages, and any whose own runtime cannot unpack them, are unpacked with [uruntime](https://github.com/VHSgunzo/uruntime) by VHSgunzo (MIT), shipped unmodified with its licence. See [LICENSE](LICENSE). Steam and Proton belong to Valve Corporation; this project is not affiliated with Valve.
