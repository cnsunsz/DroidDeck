package com.droiddeck.launcher.session

import android.content.Context
import android.util.Log
import com.droiddeck.launcher.core.BundledComponents
import java.io.File

/**
 * DroidDeck 中文版: the Chinese font for the Linux runtime.
 *
 * The runtime image carries DejaVu only, which has no Han glyphs, so Steam (whose Chromium UI
 * draws through the runtime's fontconfig) showed Chinese as boxes. The APK carries Noto Sans SC
 * Regular and Bold (SIL OFL 1.1, pinned by sha256 in tools/zh/bundled.json, kind "font"); this
 * copies them into /usr/local/share/fonts/droiddeck-zh - a directory the runtime's fonts.conf
 * already lists - and drops the fallback rules in /etc/fonts/conf.d. libfontconfig scans a
 * directory it has no cache for and writes that cache itself, and the session runs fc-cache on
 * the directory too when the runtime has it, so nothing else needs to change.
 *
 * Called from [SessionFiles.stage] at every launch, which is also how a runtime installed by an
 * older version gets the font: the first launch after the upgrade copies it. Idempotent - a stamp
 * records which files (name and digest) are in place, so later launches only compare a few bytes.
 */
object CjkFonts {
    private const val TAG = "CjkFonts"
    const val FONT_DIR = "usr/local/share/fonts/droiddeck-zh"
    private const val CONF_ASSET = "zh/64-droiddeck-zh-cjk.conf"
    private const val CONF_TARGET = "etc/fonts/conf.d/64-droiddeck-zh-cjk.conf"
    private const val LICENSE_ASSET = "bundled/licenses/notofonts_noto-cjk_OFL.txt"
    private const val STAMP = ".droiddeck-fonts"

    fun install(context: Context, root: File) {
        try {
            installFonts(context, root)
            installConf(context, root)
        } catch (e: Exception) {
            Log.w(TAG, "CJK font setup failed", e)
        }
    }

    private fun installFonts(context: Context, root: File) {
        val fonts = BundledComponents.ofKind(context, "font")
        if (fonts.isEmpty()) return
        val dir = File(root, FONT_DIR)
        val stamp = File(dir, STAMP)
        val wanted = fonts.joinToString("") { "${it.name} ${it.sha256}\n" }
        val current = runCatching { if (stamp.isFile) stamp.readText() else "" }.getOrDefault("")
        if (current == wanted && fonts.all { File(dir, it.name).length() == it.size }) return
        if (!dir.isDirectory && !dir.mkdirs()) {
            Log.e(TAG, "could not create $dir")
            return
        }
        var ok = true
        for (font in fonts) {
            val target = File(dir, font.name)
            if (current.contains("${font.name} ${font.sha256}\n") && target.length() == font.size) continue
            val staged = File(dir, font.name + ".staged")
            if (BundledComponents.copy(context, font, staged) && staged.setReadable(true, false) && staged.renameTo(target)) {
                Log.i(TAG, "installed ${font.name}")
            } else {
                staged.delete()
                ok = false
                Log.e(TAG, "${font.name} NOT installed")
            }
        }
        // Files an older build put here and this one no longer ships.
        val names = fonts.map { it.name }.toSet() + setOf(STAMP, "LICENSE-OFL.txt")
        dir.listFiles()?.filter { it.isFile && !it.name.startsWith(".") && it.name !in names }?.forEach { it.delete() }
        copyAsset(context, LICENSE_ASSET, File(dir, "LICENSE-OFL.txt"))
        if (ok) {
            val staged = File(dir, "$STAMP.staged")
            runCatching { staged.writeText(wanted); staged.renameTo(stamp) }.onFailure { staged.delete() }
        }
    }

    private fun installConf(context: Context, root: File) {
        val text = context.assets.open(CONF_ASSET).bufferedReader().use { it.readText() }
        val target = File(root, CONF_TARGET)
        if (target.isFile && runCatching { target.readText() }.getOrNull() == text) return
        val staged = File(target.parentFile, target.name + ".staged")
        target.parentFile?.mkdirs()
        // conf.d may hold a dangling link of the same name; a rename replaces it either way.
        if (runCatching { staged.writeText(text) }.isSuccess && staged.setReadable(true, false) && staged.renameTo(target)) {
            Log.i(TAG, "wrote $CONF_TARGET")
        } else {
            staged.delete()
            Log.e(TAG, "$CONF_TARGET NOT written")
        }
    }

    private fun copyAsset(context: Context, asset: String, target: File) {
        runCatching {
            val bytes = context.assets.open(asset).use { it.readBytes() }
            if (target.isFile && target.readBytes().contentEquals(bytes)) return
            target.writeBytes(bytes)
            target.setReadable(true, false)
        }.onFailure { Log.w(TAG, "could not copy $asset", it) }
    }
}
