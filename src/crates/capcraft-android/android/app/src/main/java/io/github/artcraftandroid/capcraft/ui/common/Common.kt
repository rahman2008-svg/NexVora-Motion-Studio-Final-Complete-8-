package io.github.artcraftandroid.capcraft.ui.common

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import io.github.artcraftandroid.capcraft.ui.Palette
import io.github.artcraftandroid.capcraft.ui.Type
import kotlinx.coroutines.delay

/** A list of actions sliding up from the bottom, with a Cancel row. */
@Composable
fun ActionSheet(
    title: String? = null,
    body: String? = null,
    actions: List<Pair<String, () -> Unit>>,
    destructive: Set<String> = emptySet(),
    onDismiss: () -> Unit,
) {
    val shown = remember { MutableTransitionState(false).apply { targetState = true } }
    BackHandler(onBack = onDismiss)
    Box(
        Modifier
            .fillMaxSize()
            .background(Color(0x99000000))
            .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null, onClick = onDismiss),
        contentAlignment = Alignment.BottomCenter,
    ) {
        AnimatedVisibility(
            visibleState = shown,
            enter = slideInVertically(tween(220)) { it },
            exit = slideOutVertically(tween(180)) { it },
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .clip(io.github.artcraftandroid.capcraft.ui.NexShape.Sheet)
                    .background(Palette.Elevated)
                    .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) {}
                    .navigationBarsPadding(),
            ) {
                NexVoraGrabber()
                if (title != null || body != null) {
                    Column(Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 12.dp)) {
                        title?.let { Text(it, style = Type.BodyStrong, maxLines = 1) }
                        body?.let {
                            Spacer(Modifier.height(6.dp))
                            Text(it, style = Type.Small)
                        }
                    }
                    Divider()
                }
                for ((label, action) in actions) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .clickable { onDismiss(); action() }
                            .padding(horizontal = 20.dp, vertical = 15.dp),
                    ) {
                        Text(label, style = Type.Body.copy(color = if (label in destructive) Palette.Danger else Palette.Text))
                    }
                }
                Spacer(Modifier.height(8.dp))
                NexVoraSecondaryButton("Cancel", onDismiss, Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp))
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun Divider(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxWidth().height(0.5.dp).background(Palette.Divider))
}

/** A centred dialog with a message, optional content, and Cancel / [confirm] buttons. */
@Composable
fun ConfirmDialog(
    text: String,
    confirm: String,
    destructive: Boolean = false,
    onDismiss: () -> Unit,
    content: (@Composable () -> Unit)? = null,
    onConfirm: () -> Unit,
) {
    Dialog(onDismissRequest = onDismiss) {
        Column(
            Modifier
                .widthIn(max = 340.dp)
                .clip(io.github.artcraftandroid.capcraft.ui.NexShape.Dialog)
                .background(Palette.Elevated)
                .border(1.dp, Palette.Divider, io.github.artcraftandroid.capcraft.ui.NexShape.Dialog),
        ) {
            Column(Modifier.padding(start = 24.dp, end = 24.dp, top = 26.dp, bottom = 20.dp)) {
                Text(text, style = Type.BodyStrong.copy(textAlign = TextAlign.Center), modifier = Modifier.fillMaxWidth())
                content?.let {
                    Spacer(Modifier.height(16.dp))
                    it()
                }
            }
            Row(Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, bottom = 16.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                NexVoraSecondaryButton("Cancel", onDismiss, Modifier.weight(1f))
                NexVoraPrimaryButton(confirm, onConfirm, Modifier.weight(1f), destructive = destructive)
            }
        }
    }
}

/** A short message near the bottom of the screen that goes away by itself. */
@Composable
fun Toast(message: String?, bottom: androidx.compose.ui.unit.Dp = 120.dp, onGone: () -> Unit) {
    if (message == null) return
    LaunchedEffect(message) {
        delay(2200)
        onGone()
    }
    Box(Modifier.fillMaxSize().padding(bottom = bottom), contentAlignment = Alignment.BottomCenter) {
        AnimatedVisibility(visible = true, enter = fadeIn(), exit = fadeOut()) {
            Box(
                Modifier
                    .clip(io.github.artcraftandroid.capcraft.ui.NexShape.Button)
                    .background(Palette.Elevated)
                    .border(1.dp, Palette.Divider, io.github.artcraftandroid.capcraft.ui.NexShape.Button)
                    .padding(horizontal = 16.dp, vertical = 10.dp),
            ) { Text(message, style = Type.Body) }
        }
    }
}
