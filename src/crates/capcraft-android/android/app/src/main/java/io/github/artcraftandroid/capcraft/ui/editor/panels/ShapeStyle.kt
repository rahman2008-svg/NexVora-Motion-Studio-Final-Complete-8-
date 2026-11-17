package io.github.artcraftandroid.capcraft.ui.editor.panels

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.artcraftandroid.capcraft.ui.Palette
import io.github.artcraftandroid.capcraft.ui.Type
import io.github.artcraftandroid.capcraft.ui.editor.EditorViewModel
import kotlin.math.roundToInt
import org.json.JSONObject

/** The thickest outline the Outline slider reaches, as a fraction of the frame's height. */
private const val MAX_OUTLINE = 0.04

/**
 * A shape's look: its fill (or none, for an outline alone) and its outline's colour and thickness,
 * set on the engine's shape style. A line and an arrow are all outline.
 */
@Composable
fun ShapeStylePanel(vm: EditorViewModel) {
    val clip = vm.selectedClip?.takeIf { it.kind == "shape" } ?: run { vm.panel = null; return }
    val style = remember(clip.json) { vm.clipJson(clip.id)?.optJSONObject("shape") ?: JSONObject() }
    val drawn = style.optString("kind") == "line" || style.optString("kind") == "arrow"
    var part by remember { mutableIntStateOf(if (drawn) 1 else 0) }
    var thickness by remember(clip.id) { mutableStateOf<Float?>(null) }
    var fillOpacity by remember(clip.id) { mutableStateOf<Float?>(null) }
    var sizeHeld by remember(clip.id) { mutableStateOf<Float?>(null) }
    // the fill is #rrggbb, or #rrggbbaa with its opacity at the end
    val fillHex = style.optString("fill")
    val alpha = if (fillHex.length == 9) fillHex.substring(7).toIntOrNull(16) ?: 255 else 255

    /** The shape style with [edit] made, sent as one change folded into [gesture]. */
    fun change(gesture: String?, edit: JSONObject.() -> Unit) {
        val next = JSONObject(style.toString())
        next.edit()
        vm.patch(clip.id, JSONObject().put("shape", next), gesture)
    }
    PanelFrame("Style", vm, height = 250.dp) {
        // a line or arrow is all outline, so it has the one tab
        if (drawn) Tabs(listOf("Outline", "Size"), if (part == 2) 1 else 0) { part = if (it == 0) 1 else 2 }
        else Tabs(listOf("Fill", "Outline", "Size"), part) { part = it }
        Column(Modifier.fillMaxWidth().padding(horizontal = 12.dp)) {
            if (part == 0) {
                SwatchRow(fillHex.takeIf { it.length >= 7 }?.take(7), allowNone = true, vm = vm) { c ->
                    change(null) { put("fill", if (c == null) "" else c + (if (alpha < 255) "%02x".format(alpha) else "")) }
                }
                if (fillHex.length >= 7) {
                    Spacer(Modifier.height(4.dp))
                    ValueSlider(
                        value = fillOpacity ?: (alpha / 255f * 100f),
                        range = 0f..100f,
                        label = { "${it.roundToInt()}" },
                        onChange = { v ->
                            fillOpacity = v
                            change("shape-fill-opacity") { put("fill", fillHex.take(7) + "%02x".format((v / 100f * 255f).roundToInt())) }
                        },
                        onDone = {
                            fillOpacity = null
                            vm.endGesture()
                        },
                        title = "Opacity",
                    )
                }
            } else if (part == 2) {
                // the figure's longer side, as a share of the frame's height
                val size = style.optDouble("size", 0.3)
                ValueSlider(
                    value = sizeHeld ?: (size * 100).toFloat().coerceIn(5f, 150f),
                    range = 5f..150f,
                    label = { "${it.roundToInt()}" },
                    onChange = { v ->
                        sizeHeld = v
                        change("shape-size") { put("size", v / 100.0) }
                    },
                    onDone = {
                        sizeHeld = null
                        vm.endGesture()
                    },
                    title = "Size",
                )
            } else {
                val width = style.optDouble("strokeWidth", 0.0)
                SwatchRow(if (width > 0) style.optString("stroke", "#ffffff").takeIf { it.length >= 7 }?.take(7) else null, allowNone = !drawn, vm = vm) { c ->
                    change(null) {
                        if (c == null) {
                            put("strokeWidth", 0.0)
                        } else {
                            put("stroke", c)
                            if (width <= 0) put("strokeWidth", 0.006)
                        }
                    }
                }
                Spacer(Modifier.height(4.dp))
                ValueSlider(
                    value = thickness ?: (width / MAX_OUTLINE * 100).toFloat().coerceIn(0f, 100f),
                    range = 0f..100f,
                    label = { "${it.roundToInt()}" },
                    onChange = { v ->
                        thickness = v
                        change("shape-outline") { put("strokeWidth", v / 100.0 * MAX_OUTLINE) }
                    },
                    onDone = {
                        thickness = null
                        vm.endGesture()
                    },
                    title = "Thickness",
                )
            }
        }
    }
}
