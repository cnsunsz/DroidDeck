package com.droiddeck.launcher.core

import android.content.Context
import android.util.Log
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * DroidDeck 中文版: GPL-licensed components shipped inside the APK (assets/bundled), so the GPU
 * drivers and Decky Loader install without a download. Written at build time by
 * tools/zh/fetch_bundled.py from the pinned list in tools/zh/bundled.json: every file is the exact
 * GitHub release asset, checked there against GitHub's sha256 digest, and checked again here
 * after it is copied out of the APK - the same digest the online path checks, so a bundled copy
 * and a download are interchangeable. Nothing here touches the signed esync index or the runtime.
 */
object BundledComponents {
    private const val TAG = "BundledComponents"
    private const val DIR = "bundled"
    const val URL_PREFIX = "bundled:"

    /** [kind] is "turnip" or "decky"; [source] the menu's source label (e.g. "Banners-Turnip"). */
    class Entry(
        val kind: String, val source: String, val repo: String, val tag: String, val name: String,
        val sha256: String, val size: Long, val prerelease: Boolean, val license: String,
    ) {
        val url: String get() = URL_PREFIX + name
    }

    @Volatile private var cache: List<Entry>? = null

    fun all(context: Context): List<Entry> {
        cache?.let { return it }
        val list = runCatching {
            val json = context.assets.open("$DIR/manifest.json").bufferedReader().use { JSONObject(it.readText()) }
            val items = json.getJSONArray("items")
            (0 until items.length()).map { i ->
                val o = items.getJSONObject(i)
                Entry(o.getString("kind"), o.getString("source"), o.getString("repo"), o.getString("tag"),
                    o.getString("name"), o.getString("sha256").lowercase(), o.getLong("size"),
                    o.optBoolean("prerelease", false), o.optString("license", ""))
            }
        }.getOrElse { e ->
            if (e !is java.io.FileNotFoundException) Log.w(TAG, "manifest", e)
            emptyList()
        }
        cache = list
        return list
    }

    fun ofKind(context: Context, kind: String): List<Entry> = all(context).filter { it.kind == kind }

    /** The bundled copy of a release asset, when the APK carries that exact file (same name and digest). */
    fun find(context: Context, name: String, sha256: String?): Entry? {
        if (sha256.isNullOrEmpty()) return null
        return all(context).firstOrNull { it.name == name && it.sha256.equals(sha256, ignoreCase = true) }
    }

    fun byUrl(context: Context, url: String): Entry? =
        if (!url.startsWith(URL_PREFIX)) null else all(context).firstOrNull { it.url == url }

    /** Copy [entry] out of the APK to [target] and check its digest; false (and no file) when it fails. */
    fun copy(context: Context, entry: Entry, target: File, progress: ((Int) -> Unit)? = null): Boolean {
        return try {
            target.parentFile?.mkdirs()
            context.assets.open("$DIR/${entry.name}").use { input ->
                FileOutputStream(target).use { out ->
                    val buf = ByteArray(1 shl 16)
                    var done = 0L
                    var last = -1
                    while (true) {
                        val r = input.read(buf)
                        if (r <= 0) break
                        out.write(buf, 0, r)
                        done += r
                        if (progress != null && entry.size > 0) {
                            val pct = (done * 100 / entry.size).toInt()
                            if (pct != last) { last = pct; progress(pct) }
                        }
                    }
                }
            }
            if (target.length() != entry.size || !Hashes.sha256(target).equals(entry.sha256, ignoreCase = true)) {
                throw IOException("bundled ${entry.name} does not match its digest")
            }
            true
        } catch (e: Exception) {
            Log.w(TAG, "copy ${entry.name}", e)
            target.delete()
            false
        }
    }
}
