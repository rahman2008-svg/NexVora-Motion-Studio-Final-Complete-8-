package io.github.artcraftandroid.capcraft.ui.editor

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.artcraftandroid.capcraft.core.listProjects
import io.github.artcraftandroid.capcraft.engine.Threads
import io.github.artcraftandroid.capcraft.engine.clock
import io.github.artcraftandroid.capcraft.ui.Glyph
import io.github.artcraftandroid.capcraft.ui.LucideIcons
import io.github.artcraftandroid.capcraft.ui.NexShape
import io.github.artcraftandroid.capcraft.ui.Palette
import io.github.artcraftandroid.capcraft.ui.Type
import io.github.artcraftandroid.capcraft.ui.common.ConfirmDialog
import io.github.artcraftandroid.capcraft.ui.common.NexVoraCard
import io.github.artcraftandroid.capcraft.ui.common.NexVoraIconButton
import io.github.artcraftandroid.capcraft.ui.common.NexVoraSectionHeader
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

// The editor's two overlays for a phone: a tools drawer in place of a wide sidebar, and a project sheet
// with the project's own facts, quick actions and the AI tools the app really has.

/** A tool in the drawer: its glyph and name, what opens it, and when the open panel means it is the one in use. */
private class DrawerTool(val label: String, val icon: ImageVector, val id: String, val inUse: (Panel?) -> Boolean)

private val DRAWER_TOOLS = listOf(
    DrawerTool("Effects", LucideIcons.Sparkles, "effects") { it is Panel.Packages && it.kind == "effect" },
    DrawerTool("Filters", LucideIcons.Blend, "filters") { it is Panel.Packages && it.kind == "filter" },
    DrawerTool("Text", LucideIcons.Type, "text") { it is Panel.Text },
    DrawerTool("Stickers", LucideIcons.Sticker, "stickers") { it is Panel.Stickers },
    DrawerTool("Audio", LucideIcons.Music2, "audio") { it is Panel.Sounds || it is Panel.SoundFx || it is Panel.Voiceover },
    DrawerTool("Speed", LucideIcons.Gauge, "speed") { it is Panel.Speed },
    DrawerTool("Transition", LucideIcons.Blend, "transition") { it is Panel.Transition },
    DrawerTool("Adjust", LucideIcons.SlidersHorizontal, "adjust") { it is Panel.Adjust },
)

/** More tools under the drawer's More: each opens the same panel its bottom-toolbar twin does. */
private val DRAWER_MORE = listOf(
    DrawerTool("Captions", LucideIcons.Captions, "captions") { it is Panel.Captions },
    DrawerTool("Transcript", LucideIcons.FileText, "transcript") { it is Panel.Transcript },
    DrawerTool("Aspect ratio", LucideIcons.Crop, "ratio") { it is Panel.Ratio },
    DrawerTool("Retouch", LucideIcons.Smile, "retouch") { it is Panel.Retouch },
    DrawerTool("Stabilize", LucideIcons.Film, "stabilize") { it is Panel.Stabilize },
)

/** A scrim over the editor that closes on a tap outside the content. */
@Composable
private fun Overlay(onDismiss: () -> Unit, alignment: Alignment, content: @Composable () -> Unit) {
    Box(
        Modifier.fillMaxSize().background(Color(0xAA000000)).clickable(onClick = onDismiss),
        contentAlignment = alignment,
    ) {
        // taps on the content itself must not close it
        Box(Modifier.clickable(enabled = false) {}) { content() }
    }
}

/** The tools drawer: slides in from the left; the tool in use has the neon mark; More opens the rest. */
@Composable
fun ToolsDrawer(vm: EditorViewModel, onFind: () -> Unit, onDismiss: () -> Unit) {
    var more by remember { mutableStateOf(false) }
    fun launch(id: String) {
        onDismiss()
        vm.launchTool(id)
    }
    Overlay(onDismiss, Alignment.CenterStart) {
        Column(
            Modifier.fillMaxHeight().widthIn(max = 260.dp).fillMaxWidth(0.72f)
                .background(Palette.Panel).border(1.dp, Palette.Divider)
                .statusBarsPadding().navigationBarsPadding().verticalScroll(rememberScrollState()).padding(12.dp),
        ) {
            Row(Modifier.fillMaxWidth().padding(bottom = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                Image(painterResource(io.github.artcraftandroid.capcraft.R.drawable.nexvora_logo), contentDescription = null, modifier = Modifier.size(36.dp))
                Spacer(Modifier.width(10.dp))
                Column {
                    Text("NexVora", style = Type.Title)
                    Text("Motion Studio", style = Type.Small)
                }
            }
            DrawerRow("Tools", LucideIcons.Settings2, vm.panel == null) { onDismiss() }
            DrawerRow("Find a tool", LucideIcons.Search, false) { onDismiss(); onFind() }
            Spacer(Modifier.height(6.dp))
            DRAWER_TOOLS.forEach { tool -> DrawerRow(tool.label, tool.icon, tool.inUse(vm.panel)) { launch(tool.id) } }
            DrawerRow("More", if (more) LucideIcons.ChevronUp else LucideIcons.ChevronDown, false) { more = !more }
            if (more) DRAWER_MORE.forEach { tool -> DrawerRow(tool.label, tool.icon, tool.inUse(vm.panel)) { launch(tool.id) } }
        }
    }
}

/** A row of the drawer: the tool in use has a purple-to-blue wash and a bright bar at its edge. */
@Composable
private fun DrawerRow(label: String, icon: ImageVector, active: Boolean, onClick: () -> Unit) {
    val shape = RoundedCornerShape(14.dp)
    Row(
        Modifier.fillMaxWidth().height(48.dp).clip(shape)
            .then(if (active) Modifier.background(Palette.PrimaryBrush) else Modifier)
            .clickable(onClick = onClick).padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Glyph(icon, if (active) Color.White else Palette.Cyan, size = 22.dp)
        Spacer(Modifier.width(14.dp))
        Text(label, style = Type.Body.copy(fontSize = 15.sp, fontWeight = if (active) FontWeight.SemiBold else FontWeight.Normal), modifier = Modifier.weight(1f))
        if (active) Box(Modifier.size(width = 4.dp, height = 20.dp).clip(RoundedCornerShape(2.dp)).background(Palette.Cyan))
    }
}

private fun bytes(n: Long): String = when {
    n < 1_000_000 -> "%.0f KB".format(Locale.US, n / 1000.0)
    n < 1_000_000_000 -> "%.1f MB".format(Locale.US, n / 1_000_000.0)
    else -> "%.2f GB".format(Locale.US, n / 1_000_000_000.0)
}

/**
 * The project's facts from the project itself (name, length, frame size and rate, size on disk), a way to
 * rename it, quick actions for adding things, and the AI tools the app has. Each button opens a real panel.
 */
@Composable
fun ProjectSheet(vm: EditorViewModel, onDismiss: () -> Unit) {
    val t = vm.timeline
    val scope = rememberCoroutineScope()
    var renaming by remember { mutableStateOf(false) }
    var draft by remember { mutableStateOf("") }
    val diskBytes by produceState<Long?>(null, vm.path) {
        value = withContext(Threads.commands) { runCatching { listProjects().firstOrNull { it.path == vm.path }?.sizeBytes }.getOrNull() }
    }
    fun go(block: () -> Unit) {
        onDismiss()
        block()
    }
    Overlay(onDismiss, Alignment.BottomCenter) {
        Column(
            Modifier.fillMaxWidth().fillMaxHeight(0.86f).clip(NexShape.Sheet).background(Palette.Panel)
                .border(1.dp, Palette.Divider, NexShape.Sheet).navigationBarsPadding(),
        ) {
            Row(Modifier.fillMaxWidth().padding(start = 20.dp, end = 8.dp, top = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                Text("Project Info", style = Type.Headline.copy(fontSize = 20.sp), modifier = Modifier.weight(1f))
                NexVoraIconButton(LucideIcons.X, "Close", onDismiss, tint = Palette.TextSecondary)
            }
            Column(Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = 16.dp)) {
                NexVoraCard(Modifier.fillMaxWidth()) {
                    Row(Modifier.padding(14.dp), verticalAlignment = Alignment.Top) {
                        Box(
                            Modifier.size(width = 84.dp, height = 112.dp).clip(RoundedCornerShape(12.dp)).background(Palette.Black)
                                .border(1.dp, Palette.Divider, RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center,
                        ) {
                            val frame = vm.lastFrame
                            if (frame != null) {
                                Image(frame.asImageBitmap(), contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
                            } else {
                                Glyph(LucideIcons.Film, Palette.TextSecondary, size = 28.dp)
                            }
                        }
                        Spacer(Modifier.width(14.dp))
                        Column(Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(t?.name?.ifBlank { "Untitled" } ?: "Untitled", style = Type.Title, maxLines = 1, modifier = Modifier.weight(1f, fill = false))
                                NexVoraIconButton(LucideIcons.Pencil, "Rename the project", { draft = t?.name.orEmpty(); renaming = true }, tint = Palette.Cyan)
                            }
                            InfoLine("Duration", clock((t?.durationUs ?: 0L)))
                            InfoLine("Size", diskBytes?.let(::bytes) ?: "\u2026")
                            InfoLine("Resolution", t?.let { "${it.width} \u00D7 ${it.height}" } ?: "")
                            InfoLine("Frame rate", t?.let { "%.0f fps".format(Locale.US, it.fpsNum.toDouble() / it.fpsDen.coerceAtLeast(1L)) } ?: "")
                        }
                    }
                }
                NexVoraSectionHeader("Quick Actions", Modifier.padding(top = 8.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    QuickCard("Add Music", LucideIcons.Music2, Modifier.weight(1f)) { go { vm.launchTool("audio") } }
                    QuickCard("Add Text", LucideIcons.Type, Modifier.weight(1f)) { go { vm.launchTool("text") } }
                    QuickCard("Add Sticker", LucideIcons.Sticker, Modifier.weight(1f)) { go { vm.launchTool("stickers") } }
                    QuickCard("Add Effect", LucideIcons.Sparkles, Modifier.weight(1f)) { go { vm.launchTool("effects") } }
                }
                NexVoraSectionHeader("AI Magic", Modifier.padding(top = 8.dp))
                AiRow("Auto Captions", "Words on screen from the speech", LucideIcons.Captions, true) { go { vm.launchTool("captions") } }
                AiRow("Speech Recognition", "A transcript of what is said", LucideIcons.FileText, true) { go { vm.launchTool("transcript") } }
                AiRow("Video Enhancement", "A clearer, sharper picture", LucideIcons.Sparkles, true) { go { vm.launchTool("enhance") } }
                // no assistant exists in the app, so it is shown as not available rather than as a button that does nothing
                AiRow("AI Assistant", "Coming soon", LucideIcons.Smile, false) {}
                Spacer(Modifier.height(16.dp))
            }
        }
    }
    if (renaming) {
        ConfirmDialog(
            text = "Rename project",
            confirm = "Save",
            onDismiss = { renaming = false },
            content = {
                Box(Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(Palette.Card).border(1.dp, Palette.Divider, RoundedCornerShape(12.dp)).padding(12.dp)) {
                    BasicTextField(
                        value = draft,
                        onValueChange = { draft = it.take(60) },
                        singleLine = true,
                        textStyle = Type.Body.copy(fontSize = 16.sp),
                        cursorBrush = SolidColor(Palette.Accent),
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            },
        ) {
            renaming = false
            val name = draft.trim()
            if (name.isNotEmpty()) {
                scope.launch {
                    val view = withContext(Threads.commands) {
                        runCatching { vm.session?.let { it.rename(name); it.view() } }.getOrNull()
                    }
                    if (view != null) vm.applyView(view) else vm.message = "Could not rename the project"
                }
            }
        }
    }
}

@Composable
private fun InfoLine(label: String, value: String) {
    Row(Modifier.fillMaxWidth().padding(top = 6.dp)) {
        Text(label, style = Type.Small.copy(fontSize = 13.sp), modifier = Modifier.weight(1f))
        Text(value, style = Type.Numeric.copy(fontSize = 13.sp))
    }
}

/** A quick action: its glyph on a tile and its name under it. */
@Composable
private fun QuickCard(label: String, icon: ImageVector, modifier: Modifier, onClick: () -> Unit) {
    val shape = RoundedCornerShape(14.dp)
    Column(
        modifier.clip(shape).background(Palette.Card).border(1.dp, Palette.Divider, shape).clickable(onClick = onClick).padding(vertical = 12.dp, horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Glyph(icon, Palette.Cyan, size = 24.dp)
        Spacer(Modifier.height(6.dp))
        Text(label, style = Type.Tiny.copy(color = Palette.Text), maxLines = 2)
    }
}

/** An AI tool: what it does in a line, and a way in; one that does not exist yet is dimmed and does nothing. */
@Composable
private fun AiRow(title: String, about: String, icon: ImageVector, enabled: Boolean, onClick: () -> Unit) {
    NexVoraCard(Modifier.fillMaxWidth().padding(vertical = 4.dp), onClick = if (enabled) onClick else null) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(40.dp).clip(CircleShape).background(Palette.AccentSoft), contentAlignment = Alignment.Center) {
                Glyph(icon, if (enabled) Palette.Cyan else Palette.Disabled, size = 20.dp)
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(title, style = Type.BodyStrong.copy(color = if (enabled) Palette.Text else Palette.Disabled))
                Text(about, style = Type.Small.copy(fontSize = 12.sp, color = if (enabled) Palette.TextSecondary else Palette.Disabled))
            }
            if (enabled) Glyph(LucideIcons.ChevronRight, Palette.TextSecondary, size = 18.dp)
        }
    }
}
