package io.github.artcraftandroid.capcraft.ui.editor.panels

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.artcraftandroid.capcraft.ui.Glyph
import io.github.artcraftandroid.capcraft.ui.LucideIcons
import io.github.artcraftandroid.capcraft.ui.Palette
import io.github.artcraftandroid.capcraft.ui.Type
import io.github.artcraftandroid.capcraft.ui.editor.EditorViewModel
import kotlin.math.roundToInt
import org.json.JSONArray
import org.json.JSONObject

private const val RETOUCH = "capcraft.retouch"

/** The retouch link on [clipId] with [key] set to [value], or taken off ([key] null). */
private fun setRetouch(vm: EditorViewModel, clipId: String, key: String?, value: Double, gesture: String?) {
    val chain = vm.clipJson(clipId)?.optJSONArray("videoEffects") ?: JSONArray()
    val next = JSONArray()
    var placed = false
    for (i in 0 until chain.length()) {
        val link = chain.getJSONObject(i)
        if (link.optString("id") != RETOUCH) {
            next.put(link)
        } else if (key != null) {
            val params = link.optJSONObject("params") ?: JSONObject()
            next.put(link.put("params", params.put(key, value)))
            placed = true
        }
    }
    if (key != null && !placed) {
        // a new retouch starts with no smoothing, so only the slider moved shows
        next.put(JSONObject().put("id", RETOUCH).put("params", JSONObject().put("soften", 0).put("brighten", 0).put(key, value)))
    }
    vm.patch(clipId, JSONObject().put("videoEffects", next), gesture)
}

/**
 * Retouch: skin smoothed and brightened. Skin is told from the rest by its colour, so a warm
 * wall may soften a little with it.
 */
@Composable
fun RetouchPanel(vm: EditorViewModel) {
    val clip = vm.selectedClip ?: run { vm.panel = null; return }
    val params = remember(clip.json) {
        vm.clipJson(clip.id)?.optJSONArray("videoEffects")?.let { chain ->
            (0 until chain.length()).map { chain.getJSONObject(it) }.firstOrNull { it.optString("id") == RETOUCH }
        }?.optJSONObject("params")
    }
    PanelFrame("Retouch", vm, height = 290.dp, titleOnTop = true, left = {
        Row(
            Modifier.clickable(enabled = params != null) { setRetouch(vm, clip.id, null, 0.0, null) }.padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val tint = if (params != null) Palette.Accent else Palette.Disabled
            Glyph(LucideIcons.RotateCcw, tint, size = 16.dp)
            Spacer(Modifier.width(6.dp))
            Text("Reset", style = Type.Small.copy(color = tint))
        }
    }) {
        // one card a control: what it does, its amount, and its slider
        Column(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 10.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            RetouchCard(vm, clip.id, LucideIcons.Sparkles, "Smooth", "Softens skin", "soften", params?.optDouble("soften", 0.0) ?: 0.0)
            RetouchCard(vm, clip.id, LucideIcons.Sun, "Brighten", "Lightens skin", "brighten", params?.optDouble("brighten", 0.0) ?: 0.0)
        }
    }
}

@Composable
private fun RetouchCard(
    vm: EditorViewModel,
    clipId: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    hint: String,
    key: String,
    value: Double,
) {
    var held by remember(clipId, key) { mutableStateOf<Float?>(null) }
    val shown = held ?: value.toFloat()
    io.github.artcraftandroid.capcraft.ui.common.NexVoraCard(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier.size(36.dp).clip(androidx.compose.foundation.shape.CircleShape).background(Palette.AccentSoft),
                    contentAlignment = Alignment.Center,
                ) { Glyph(icon, Palette.Accent, size = 20.dp) }
                Spacer(Modifier.width(10.dp))
                Column(Modifier.weight(1f)) {
                    Text(label, style = Type.BodyStrong)
                    Text(hint, style = Type.Small)
                }
                Text("${shown.roundToInt()}", style = Type.Numeric.copy(color = Palette.Accent, fontSize = 18.sp))
            }
            CapSlider(
                value = shown,
                range = 0f..100f,
                label = { "${it.roundToInt()}" },
                onChange = {
                    held = it
                    setRetouch(vm, clipId, key, it.toDouble(), "retouch-$key")
                },
                onDone = {
                    held = null
                    vm.endGesture()
                },
                modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
            )
        }
    }
}
