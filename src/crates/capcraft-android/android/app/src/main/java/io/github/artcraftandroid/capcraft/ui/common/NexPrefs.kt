package io.github.artcraftandroid.capcraft.ui.common

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback

/**
 * The app's own preferences, kept in a small private file. Each one is read where it takes effect:
 * haptics at every vibration the editor makes, the export defaults when an editor opens.
 */
object NexPrefs {
    private const val FILE = "nexvora_settings"
    private fun prefs(context: Context) = context.getSharedPreferences(FILE, Context.MODE_PRIVATE)

    /** The export sheet's resolutions (the short side in pixels) and frame rates: the same lists it offers. */
    val RESOLUTIONS = listOf(480, 720, 1080, 1440, 2160)
    val FRAME_RATES = listOf(24, 25, 30, 50, 60)

    fun haptics(context: Context): Boolean = prefs(context).getBoolean("haptics", true)
    fun setHaptics(context: Context, on: Boolean) = prefs(context).edit().putBoolean("haptics", on).apply()

    /** The resolution a new editor starts its export at, or null for the video's own size. */
    fun defaultResolution(context: Context): Int? = prefs(context).getInt("default_resolution", 0).takeIf { it in RESOLUTIONS }
    fun setDefaultResolution(context: Context, short: Int?) =
        prefs(context).edit().putInt("default_resolution", short ?: 0).apply()

    fun defaultFps(context: Context): Int = prefs(context).getInt("default_fps", 30).takeIf { it in FRAME_RATES } ?: 30
    fun setDefaultFps(context: Context, fps: Int) = prefs(context).edit().putInt("default_fps", fps).apply()

    /** Every preference back to what it was on first launch. Projects and caches are not touched. */
    fun reset(context: Context) = prefs(context).edit().clear().apply()
}

/** The phone's haptics, silent when the Haptics setting is off. */
private class GatedHaptics(private val context: Context, private val real: HapticFeedback) : HapticFeedback {
    override fun performHapticFeedback(hapticFeedbackType: HapticFeedbackType) {
        if (NexPrefs.haptics(context)) real.performHapticFeedback(hapticFeedbackType)
    }
}

@Composable
fun rememberGatedHaptics(): HapticFeedback {
    val context = LocalContext.current
    val real = LocalHapticFeedback.current
    return remember(real) { GatedHaptics(context, real) }
}
