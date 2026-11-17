package io.github.artcraftandroid.capcraft.ui.editor.panels

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.artcraftandroid.capcraft.core.SpeechListener
import io.github.artcraftandroid.capcraft.core.TimelineView
import io.github.artcraftandroid.capcraft.engine.Threads
import io.github.artcraftandroid.capcraft.engine.userMessage
import io.github.artcraftandroid.capcraft.ui.Palette
import io.github.artcraftandroid.capcraft.ui.Type
import io.github.artcraftandroid.capcraft.ui.editor.EditorViewModel
import kotlin.math.roundToInt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/** The shapes Auto reframe offers, width to height. */
private val SHAPES = listOf("9:16" to (9 to 16), "16:9" to (16 to 9), "1:1" to (1 to 1), "4:5" to (4 to 5), "4:3" to (4 to 3), "3:4" to (3 to 4))

/**
 * Auto reframe: the frame given a new shape and the selected clip zoomed to fill it, moved along so
 * the person in it stays in view. The person is found on the phone (the model is fetched the
 * first time).
 */
@Composable
fun AutoReframePanel(vm: EditorViewModel) {
    val clip = vm.selectedClip ?: run { vm.panel = null; return }
    val t = vm.timeline ?: return
    val scope = rememberCoroutineScope()
    val current = SHAPES.indexOfFirst { (_, r) -> r.first * t.height.toInt() == r.second * t.width.toInt() }
    var chosen by remember { mutableIntStateOf(if (current >= 0) current else 0) }
    var job by remember { mutableStateOf<Pair<String, Float>?>(null) }
    val listener = remember {
        object : SpeechListener {
            override fun onProgress(fraction: Float, stage: String) {
                scope.launch(Dispatchers.Main) { job = (if (stage == "download") "Downloading the model" else "Finding the person") to fraction }
            }

            override fun onDone(result: TimelineView?) {
                scope.launch(Dispatchers.Main) {
                    job = null
                    result?.let { vm.applyView(it) }
                    vm.panel = null
                    vm.message = "Reframed"
                }
            }

            override fun onFailed(message: String) {
                scope.launch(Dispatchers.Main) {
                    job = null
                    vm.message = message
                }
            }
        }
    }
    fun reframe() {
        val s = vm.session ?: return
        if (job != null) return
        val (w, h) = SHAPES[chosen].second
        val (fw, fh) = frameFor(w, h)
        job = "Finding the person" to 0f
        vm.stop()
        scope.launch(Threads.commands) {
            runCatching {
                if (fw != t.width.toInt() || fh != t.height.toInt()) s.setCanvas(fw.toUInt(), fh.toUInt())
                s.autoReframe(clip.id, listener)
            }.onFailure { e -> launch(Dispatchers.Main) { job = null; vm.message = e.userMessage } }
        }
    }
    PanelFrame("Auto reframe", vm, height = 290.dp, titleOnTop = true) {
        Column(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp)) {
            val running = job
            // the output frame shown at its shape, with the shapes to choose beside it
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier.size(96.dp).clip(io.github.artcraftandroid.capcraft.ui.NexShape.Card).background(Palette.Card)
                        .border(1.dp, Palette.Divider, io.github.artcraftandroid.capcraft.ui.NexShape.Card),
                    contentAlignment = Alignment.Center,
                ) {
                    val aspect = SHAPES[chosen].second.let { it.first.toFloat() / it.second }
                    val bw = if (aspect >= 1f) 70f else 70f * aspect
                    val bh = if (aspect >= 1f) 70f / aspect else 70f
                    Box(
                        Modifier.size(bw.dp, bh.dp).clip(RoundedCornerShape(4.dp)).background(Palette.AccentSoft)
                            .border(2.dp, Palette.Accent, RoundedCornerShape(4.dp)),
                        contentAlignment = Alignment.Center,
                    ) { io.github.artcraftandroid.capcraft.ui.Glyph(io.github.artcraftandroid.capcraft.ui.LucideIcons.Crosshair, Palette.Accent, size = 18.dp) }
                }
                Spacer(Modifier.width(16.dp))
                Column(Modifier.weight(1f)) {
                    Text(SHAPES[chosen].first, style = Type.Numeric.copy(color = Palette.Accent, fontSize = 18.sp))
                    Text("Shape to reframe to", style = Type.Small)
                    Spacer(Modifier.height(10.dp))
                    Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)) {
                        SHAPES.forEachIndexed { i, (label, _) ->
                            io.github.artcraftandroid.capcraft.ui.common.NexVoraChip(label, i == chosen, { if (running == null) chosen = i })
                        }
                    }
                }
            }
            Spacer(Modifier.height(18.dp))
            if (running != null) {
                io.github.artcraftandroid.capcraft.ui.common.NexVoraProgressCard(running.first, running.second, Modifier.fillMaxWidth())
            } else {
                io.github.artcraftandroid.capcraft.ui.common.NexVoraPrimaryButton("Reframe", ::reframe, Modifier.fillMaxWidth())
            }
        }
    }
}
