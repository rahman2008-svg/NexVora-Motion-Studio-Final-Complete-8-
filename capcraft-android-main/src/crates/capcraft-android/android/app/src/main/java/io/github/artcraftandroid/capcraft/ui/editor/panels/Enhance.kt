package io.github.artcraftandroid.capcraft.ui.editor.panels

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import io.github.artcraftandroid.capcraft.core.cancelEnhance
import io.github.artcraftandroid.capcraft.engine.Threads
import io.github.artcraftandroid.capcraft.engine.userMessage
import io.github.artcraftandroid.capcraft.ui.Palette
import io.github.artcraftandroid.capcraft.ui.Type
import io.github.artcraftandroid.capcraft.ui.editor.EditorViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Enhance: the selected clip shown from a clearer copy the engine makes on the phone, or from its
 * original again. The first time fetches the model and makes the copy; after that the switch only
 * chooses which of the two the clip shows.
 */
@Composable
fun EnhancePanel(vm: EditorViewModel) {
    val clip = vm.selectedClip ?: run { vm.panel = null; return }
    val scope = rememberCoroutineScope()
    val on = remember(clip.json) { vm.clipJson(clip.id)?.optJSONObject("enhanced")?.optBoolean("on") == true }
    var job by remember { mutableStateOf<Pair<String, Float>?>(null) }
    val listener = remember {
        object : SpeechListener {
            override fun onProgress(fraction: Float, stage: String) {
                scope.launch(Dispatchers.Main) { job = (if (stage == "download") "Downloading" else "Enhancing") to fraction }
            }

            override fun onDone(result: TimelineView?) {
                scope.launch(Dispatchers.Main) {
                    job = null
                    if (result != null) vm.applyView(result)
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
    fun set(want: Boolean) {
        val s = vm.session ?: return
        if (want) job = "Starting" to 0f
        scope.launch(Threads.commands) {
            runCatching { s.enhance(clip.id, want, listener) }
                .onFailure { e -> launch(Dispatchers.Main) { job = null; vm.message = e.userMessage } }
        }
    }
    PanelFrame("Super resolution", vm, height = 300.dp, titleOnTop = true) {
        Column(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp)) {
            // what it is, and whether the clip shows the clearer copy now
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(40.dp).clip(RoundedCornerShape(12.dp)).background(Palette.AccentSoft), contentAlignment = Alignment.Center) {
                    io.github.artcraftandroid.capcraft.ui.Glyph(io.github.artcraftandroid.capcraft.ui.LucideIcons.Sparkles, Palette.Accent, size = 22.dp)
                }
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text("Super resolution", style = Type.BodyStrong.copy(fontSize = 16.sp))
                    Text(if (on) "Showing the clearer copy" else "Showing the original", style = Type.Small)
                }
            }
            Spacer(Modifier.height(14.dp))
            val running = job
            if (running != null) {
                io.github.artcraftandroid.capcraft.ui.common.NexVoraProgressCard(running.first, running.second, Modifier.fillMaxWidth(), "Stop", { runCatching { cancelEnhance() }; job = null })
            } else {
                io.github.artcraftandroid.capcraft.ui.common.NexVoraPrimaryButton(
                    if (on) "Show the original" else "Enhance this clip",
                    { set(!on) },
                    Modifier.fillMaxWidth(),
                )
                Spacer(Modifier.height(10.dp))
                Text(
                    "A clearer, sharper picture, made on this phone. A photo takes a moment; a video goes frame by frame and takes a while.",
                    style = Type.Small,
                )
            }
        }
    }
}
