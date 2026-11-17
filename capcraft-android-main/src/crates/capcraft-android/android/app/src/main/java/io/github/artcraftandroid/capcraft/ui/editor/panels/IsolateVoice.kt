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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import io.github.artcraftandroid.capcraft.core.cancelIsolateVoice
import io.github.artcraftandroid.capcraft.engine.Threads
import io.github.artcraftandroid.capcraft.engine.userMessage
import io.github.artcraftandroid.capcraft.ui.Palette
import io.github.artcraftandroid.capcraft.ui.Type
import io.github.artcraftandroid.capcraft.ui.editor.EditorViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

/** Where the timeline keeps each voice-only copy's original (the bridge's `ISOLATED`). */
private const val ISOLATED = "capcraftIsolated"

/**
 * Isolate voice: the clip's sound with only the voice kept, cleaned on the phone by a small speech
 * network fetched the first time. Switched off, the clip has its own sound again.
 */
@Composable
fun IsolateVoicePanel(vm: EditorViewModel) {
    val clip = vm.selectedClip ?: run { vm.panel = null; return }
    val scope = rememberCoroutineScope()
    val on = remember(clip.json, vm.timeline?.revision) {
        runCatching { vm.session?.extra(ISOLATED)?.takeIf { it.isNotBlank() }?.let(::JSONObject)?.has(clip.path) }.getOrNull() == true
    }
    var job by remember { mutableStateOf<Pair<String, Float>?>(null) }
    DisposableEffect(Unit) { onDispose { if (job != null) runCatching { cancelIsolateVoice() } } }
    val listener = remember {
        object : SpeechListener {
            override fun onProgress(fraction: Float, stage: String) {
                scope.launch(Dispatchers.Main) { job = (if (stage == "download") "Downloading the voice model" else "Isolating the voice") to fraction }
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
        if (want) job = "Isolating the voice" to 0f
        vm.stop()
        scope.launch(Threads.commands) {
            runCatching { s.isolateVoice(clip.id, want, listener) }
                .onFailure { e -> launch(Dispatchers.Main) { job = null; vm.message = e.userMessage } }
        }
    }
    PanelFrame("Isolate voice", vm, height = 250.dp, titleOnTop = true) {
        Column(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp)) {
            // what the clip plays: its own sound, or the voice alone
            Text("Sound", style = Type.Label)
            Spacer(Modifier.height(6.dp))
            io.github.artcraftandroid.capcraft.ui.common.NexVoraSegmentedTabs(
                listOf("Original", "Voice only"),
                if (on) 1 else 0,
                { choice -> if (job == null && clip.audible && (choice == 1) != on) set(choice == 1) },
                Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(14.dp))
            val running = job
            if (running != null) {
                io.github.artcraftandroid.capcraft.ui.common.NexVoraProgressCard(running.first, running.second, Modifier.fillMaxWidth(), "Stop", { runCatching { cancelIsolateVoice() }; job = null })
            } else {
                Text(
                    if (clip.audible) "Keeps the voice and takes out music and noise behind it." else "This clip has no sound.",
                    style = Type.Small,
                )
            }
        }
    }
}
