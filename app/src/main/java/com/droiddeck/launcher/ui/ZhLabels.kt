package com.droiddeck.launcher.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.droiddeck.launcher.R
import com.droiddeck.launcher.session.SessionPrefs

/*
 * DroidDeck 中文版: display labels for choices whose ids and names are fixed in code
 * (SessionPrefs.upscalerChoices, SessionPrefs.fpsLimitChoices, ScreenEffectLooks.LOOKS). Only the
 * label shown in the menu is translated; the stored ids, and the Look name the compositor is
 * handed, stay as they are.
 */

@Composable
internal fun upscalerLabels(): List<Pair<Int, String>> = SessionPrefs.upscalerChoices.map { (id, label) ->
    id to when (id) {
        0 -> stringResource(R.string.hc_upscaler_linear)
        2 -> stringResource(R.string.hc_upscaler_nearest)
        8 -> stringResource(R.string.hc_upscaler_gsr_quality)
        6 -> stringResource(R.string.hc_upscaler_sharpen_only)
        else -> label
    }
}

@Composable
internal fun fpsLimitLabels(): List<Pair<Int, String>> = SessionPrefs.fpsLimitChoices.map { (fps, label) ->
    fps to if (fps == 0) stringResource(R.string.hc_fps_off) else label
}

@Composable
internal fun lookLabel(name: String): String = when (name) {
    "Off" -> stringResource(R.string.hc_look_off)
    "Game Clarity" -> stringResource(R.string.hc_look_game_clarity)
    "Vivid" -> stringResource(R.string.hc_look_vivid)
    "Cinematic" -> stringResource(R.string.hc_look_cinematic)
    "Competitive" -> stringResource(R.string.hc_look_competitive)
    "Adaptive Sharpen" -> stringResource(R.string.hc_look_adaptive_sharpen)
    "Filmic" -> stringResource(R.string.hc_look_filmic)
    "Arcade" -> stringResource(R.string.hc_look_arcade)
    "Retro CRT" -> stringResource(R.string.hc_look_retro_crt)
    "Upscale Sharp" -> stringResource(R.string.hc_look_upscale_sharp)
    "Pixel Clean" -> stringResource(R.string.hc_look_pixel_clean)
    "Anime Edge" -> stringResource(R.string.hc_look_anime_edge)
    else -> name
}
