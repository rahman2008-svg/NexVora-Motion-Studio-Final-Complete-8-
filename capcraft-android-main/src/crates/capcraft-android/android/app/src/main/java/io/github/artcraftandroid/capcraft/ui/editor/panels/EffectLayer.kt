package io.github.artcraftandroid.capcraft.ui.editor.panels

import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.border
import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import io.github.artcraftandroid.capcraft.core.RowKind
import io.github.artcraftandroid.capcraft.ui.Glyph
import io.github.artcraftandroid.capcraft.ui.LucideIcons
import io.github.artcraftandroid.capcraft.ui.Palette
import io.github.artcraftandroid.capcraft.ui.Type
import io.github.artcraftandroid.capcraft.ui.editor.EditorViewModel
import io.github.artcraftandroid.capcraft.ui.editor.Thumbs
import org.json.JSONObject
import kotlin.math.roundToInt

/**
 * What an effect on the timeline treats, as the reference's Object sheet asks: every picture
 * beneath it, overlays too, or the main track alone (where a new effect starts). The engine's
 * effects treat what lies under their row, so a choice moves the effect under the overlays or
 * over them; one overlay alone is not something a row can single out.
 */
@Composable
fun EffectObjectPanel(vm: EditorViewModel) {
    val clip = vm.selectedClip?.takeIf { it.kind == "layer" } ?: run { vm.panel = null; return }
    val all = vm.rowOf(clip.id)?.allVideos == true
    // the main track's picture where the effect starts
    val main = vm.timeline?.rows?.firstOrNull { it.kind == RowKind.MAIN }?.clips
        ?.firstOrNull { clip.startUs >= it.startUs && clip.startUs < it.startUs + it.durationUs }
        ?: vm.timeline?.rows?.firstOrNull { it.kind == RowKind.MAIN }?.clips?.firstOrNull()
    @Suppress("UNUSED_VARIABLE") val thumbs = Thumbs.version.intValue
    val bitmap = main?.let { cutFrame(it, end = false) }
    val picture = remember(bitmap) { bitmap?.asImageBitmap() }

    fun choose(every: Boolean) {
        if (every != all) vm.edit(select = { clip.id }) { setReach(clip.id, every) }
    }

    PanelFrame("Object", vm, height = 260.dp) {
        // two options as rows: a picture or glyph, what it treats, and a mark for the one in force
        Column(Modifier.fillMaxWidth().weight(1f).padding(horizontal = 16.dp, vertical = 12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            ObjectOption("All videos", "Every picture beneath it, overlays too", all, { choose(true) }) {
                Glyph(LucideIcons.Database, Palette.Text, size = 24.dp)
            }
            ObjectOption("Main video", "The main track alone", !all, { choose(false) }) {
                picture?.let { Image(it, contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize()) }
                    ?: Glyph(LucideIcons.Film, Palette.Text, size = 24.dp)
            }
        }
    }
}

@Composable
private fun ObjectOption(title: String, about: String, selected: Boolean, onClick: () -> Unit, thumb: @Composable androidx.compose.foundation.layout.BoxScope.() -> Unit) {
    val shape = io.github.artcraftandroid.capcraft.ui.NexShape.Card
    Row(
        Modifier.fillMaxWidth().clip(shape).background(if (selected) Palette.AccentSoft else Palette.Card)
            .border(if (selected) 2.dp else 1.dp, if (selected) Palette.Accent else Palette.Divider, shape)
            .clickable(onClick = onClick).padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            Modifier.size(56.dp).clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(Palette.Panel),
            contentAlignment = Alignment.Center,
            content = thumb,
        )
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(title, style = Type.BodyStrong.copy(fontSize = 15.sp, color = if (selected) Palette.Accent else Palette.Text))
            Text(about, style = Type.Small)
        }
        Box(
            Modifier.size(22.dp).clip(androidx.compose.foundation.shape.CircleShape)
                .background(if (selected) Palette.Accent else androidx.compose.ui.graphics.Color.Transparent)
                .border(if (selected) 0.dp else 1.5.dp, Palette.TextSecondary, androidx.compose.foundation.shape.CircleShape),
            contentAlignment = Alignment.Center,
        ) { if (selected) Glyph(LucideIcons.Check, Palette.OnAccent, size = 14.dp) }
    }
}

/**
 * An effect's own settings on the timeline, each on its slider, as the reference's Adjust sheet
 * for an effect: Reset at the left of the title, the tick at the right.
 */
@Composable
fun LayerAdjustPanel(vm: EditorViewModel) {
    val clip = vm.selectedClip?.takeIf { it.kind == "layer" } ?: run { vm.panel = null; return }
    val p = rememberPackages("effect").firstOrNull { it.id == clip.effectId } ?: run { vm.panel = null; return }
    val knobs = adjustable(p)
    val params = remember(clip.json) {
        val chain = vm.clipJson(clip.id)?.optJSONArray("videoEffects")
        (0 until (chain?.length() ?: 0)).map { chain!!.getJSONObject(it) }
            .firstOrNull { it.optString("id") == p.id }?.optJSONObject("params") ?: JSONObject()
    }

    fun reset() {
        knobs.forEach { setKnob(vm, clip.id, p.id, it.key, it.default, "effect-reset") }
        vm.endGesture()
    }

    PanelFrame(
        "Adjust",
        vm,
        height = (110 + 84 * knobs.size.coerceIn(1, 3)).dp,
        left = { io.github.artcraftandroid.capcraft.ui.common.NexVoraSecondaryButton("Reset", ::reset, Modifier.padding(start = 12.dp)) },
    ) {
        // one card a setting: its name, its value in numerals, its slider
        Column(
            Modifier.weight(1f).fillMaxWidth().verticalScroll(rememberScrollState()).padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            if (knobs.isEmpty()) {
                Text("This effect has nothing to set", style = Type.Small, modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            knobs.forEach { knob ->
                key(knob.key) {
                    // held while the finger is down, so the knob does not wait on the engine
                    var held by remember { mutableStateOf<Float?>(null) }
                    val step = knob.step.takeIf { it > 0 } ?: 0.0
                    val shown = held ?: params.optDouble(knob.key, knob.default).toFloat()
                    fun show(v: Float) = if (step >= 1.0) "${v.roundToInt()}" else "%.1f".format(v)
                    io.github.artcraftandroid.capcraft.ui.common.NexVoraCard(Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(12.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(knob.label, style = Type.BodyStrong, modifier = Modifier.weight(1f))
                                Text(show(shown), style = Type.Numeric.copy(color = Palette.Accent, fontSize = 16.sp))
                            }
                            CapSlider(
                                value = shown,
                                range = knob.min.toFloat()..knob.max.toFloat(),
                                label = { v -> show(v) },
                                onChange = { v ->
                                    val snapped = if (step > 0) (kotlin.math.round(v / step) * step) else v.toDouble()
                                    held = snapped.toFloat()
                                    setKnob(vm, clip.id, p.id, knob.key, snapped, "effect-${knob.key}")
                                },
                                onDone = {
                                    held = null
                                    vm.endGesture()
                                },
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }
                    }
                }
            }
        }
    }
}
