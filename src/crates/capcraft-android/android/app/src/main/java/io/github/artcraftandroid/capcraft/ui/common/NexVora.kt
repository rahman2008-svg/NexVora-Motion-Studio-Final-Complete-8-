package io.github.artcraftandroid.capcraft.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import io.github.artcraftandroid.capcraft.ui.LucideIcons
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.artcraftandroid.capcraft.ui.Glyph
import io.github.artcraftandroid.capcraft.ui.NexShape
import io.github.artcraftandroid.capcraft.ui.Palette
import io.github.artcraftandroid.capcraft.ui.Type

// NexVora Motion's shared components. Dialogs, sheets, toasts, loading and failure screens are built from
// these, so the look changes in one place. They only draw and forward a click: no editor logic in here.

/** The main action of a dialog or page: cyan, dark ink. [destructive] makes it red for deletes. */
@Composable
fun NexVoraPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    destructive: Boolean = false,
) {
    val fill = when {
        !enabled -> Modifier.background(Palette.Card)
        destructive -> Modifier.background(Palette.Danger)
        else -> Modifier.background(Palette.PrimaryBrush)
    }
    Box(
        modifier
            .heightIn(min = 44.dp)
            .clip(NexShape.Button)
            .then(fill)
            .clickable(enabled = enabled, role = Role.Button, onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text,
            style = Type.BodyStrong.copy(
                color = when {
                    !enabled -> Palette.Disabled
                    destructive -> Palette.Text
                    else -> Palette.OnAccent
                },
                textAlign = TextAlign.Center,
            ),
            maxLines = 1,
        )
    }
}

/** A quieter action beside a primary one: raised surface, hairline border. */
@Composable
fun NexVoraSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Box(
        modifier
            .heightIn(min = 44.dp)
            .clip(NexShape.Button)
            .background(Palette.Card)
            .border(1.dp, Palette.Divider, NexShape.Button)
            .clickable(enabled = enabled, role = Role.Button, onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text,
            style = Type.BodyStrong.copy(color = if (enabled) Palette.Text else Palette.Disabled, textAlign = TextAlign.Center),
            maxLines = 1,
        )
    }
}

/** A round 44dp touch target around an icon; [description] is what a screen reader says. */
@Composable
fun NexVoraIconButton(
    icon: ImageVector,
    description: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = Palette.Text,
    enabled: Boolean = true,
) {
    Box(
        modifier.size(44.dp).clip(CircleShape).clickable(enabled = enabled, role = Role.Button, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Glyph(icon, if (enabled) tint else Palette.Disabled, size = 22.dp, description = description)
    }
}

/** A raised card: Card fill, hairline border, 16dp corners. Clickable when [onClick] is given. */
@Composable
fun NexVoraCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    Box(
        modifier
            .clip(NexShape.Card)
            .background(Palette.Card)
            .border(1.dp, Palette.Divider, NexShape.Card)
            .then(if (onClick != null) Modifier.clickable(role = Role.Button, onClick = onClick) else Modifier),
    ) { content() }
}

/** A heading with an optional action on the right (for example "See all"). */
@Composable
fun NexVoraSectionHeader(title: String, modifier: Modifier = Modifier, action: String? = null, onAction: (() -> Unit)? = null) {
    Row(modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(title, style = Type.Title, modifier = Modifier.weight(1f))
        if (action != null && onAction != null) {
            Text(
                action,
                style = Type.Small.copy(color = Palette.Accent),
                modifier = Modifier.clip(NexShape.Chip).clickable(role = Role.Button, onClick = onAction).padding(horizontal = 8.dp, vertical = 6.dp),
            )
        }
    }
}

/** Nothing here yet: an icon, a line, a hint, and optionally the action that fills the space. */
@Composable
fun NexVoraEmptyState(
    title: String,
    message: String,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
) {
    Column(modifier.fillMaxWidth().padding(32.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        if (icon != null) {
            Box(Modifier.size(56.dp).clip(CircleShape).background(Palette.Card), contentAlignment = Alignment.Center) {
                Glyph(icon, Palette.TextSecondary, size = 26.dp)
            }
            Spacer(Modifier.height(16.dp))
        }
        Text(title, style = Type.Title.copy(textAlign = TextAlign.Center))
        Spacer(Modifier.height(6.dp))
        Text(message, style = Type.Small.copy(textAlign = TextAlign.Center))
        if (actionLabel != null && onAction != null) {
            Spacer(Modifier.height(20.dp))
            NexVoraPrimaryButton(actionLabel, onAction)
        }
    }
}

/** The loading screen: the mark, a thin spinner and, if given, what is being waited for. */
@Composable
fun NexVoraLoadingState(message: String? = null, modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize().background(Palette.Black), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painterResource(io.github.artcraftandroid.capcraft.R.drawable.nexvora_logo),
                contentDescription = "NexVora Motion Studio",
                modifier = Modifier.size(72.dp),
            )
            Spacer(Modifier.height(24.dp))
            CircularProgressIndicator(color = Palette.Accent, strokeWidth = 2.dp, modifier = Modifier.size(26.dp))
            if (message != null) {
                Spacer(Modifier.height(16.dp))
                Text(message, style = Type.Small.copy(textAlign = TextAlign.Center))
            }
        }
    }
}

/** The failure screen: what went wrong, in the same frame as loading. */
@Composable
fun NexVoraErrorState(title: String, message: String, modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize().background(Palette.Black), contentAlignment = Alignment.Center) {
        Column(Modifier.padding(32.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Box(Modifier.size(8.dp).clip(CircleShape).background(Palette.Danger))
            Spacer(Modifier.height(16.dp))
            Text(title, style = Type.Title.copy(textAlign = TextAlign.Center))
            Spacer(Modifier.height(8.dp))
            Text(message, style = Type.Small.copy(textAlign = TextAlign.Center))
        }
    }
}

/** The small bar at the top of a bottom sheet that says it can be dismissed. */
@Composable
fun NexVoraGrabber(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxWidth().padding(top = 8.dp, bottom = 4.dp), contentAlignment = Alignment.Center) {
        Box(Modifier.width(36.dp).height(4.dp).clip(RoundedCornerShape(2.dp)).background(Palette.DockBack))
    }
}

/** A selectable pill: categories, filters and panel tabs. Chosen = cyan wash and outline; the rest quiet on the card colour. */
@Composable
fun NexVoraChip(label: String, selected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val pill = RoundedCornerShape(17.dp)
    Box(
        modifier
            .height(34.dp)
            .clip(pill)
            .background(if (selected) Palette.AccentSoft else Palette.Card)
            .border(1.dp, if (selected) Palette.Accent else Palette.Divider, pill)
            .clickable(role = Role.Button, onClick = onClick)
            .padding(horizontal = 14.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            label,
            style = Type.Body.copy(
                color = if (selected) Palette.Accent else Palette.TextSecondary,
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
                fontSize = 14.sp,
            ),
            maxLines = 1,
        )
    }
}

/** A few exclusive choices in one rounded track, the chosen one raised and cyan (Videos / Photos, for example). */
@Composable
fun NexVoraSegmentedTabs(labels: List<String>, selected: Int, onSelect: (Int) -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier
            .height(40.dp)
            .clip(NexShape.Button)
            .background(Palette.Panel)
            .border(1.dp, Palette.Divider, NexShape.Button)
            .padding(3.dp),
    ) {
        labels.forEachIndexed { i, label ->
            Box(
                Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(NexShape.Chip)
                    .background(if (i == selected) Palette.Card else Color.Transparent)
                    .clickable(role = Role.Tab) { onSelect(i) },
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    label,
                    style = Type.Body.copy(
                        color = if (i == selected) Palette.Accent else Palette.TextSecondary,
                        fontWeight = if (i == selected) FontWeight.SemiBold else FontWeight.Normal,
                        fontSize = 14.sp,
                    ),
                )
            }
        }
    }
}

/** A search box: magnifier, hint, one line of input, and a cross to clear it. [autoFocus] takes the keyboard on open. */
@Composable
fun NexVoraSearchField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester? = null,
    autoFocus: Boolean = false,
) {
    val shape = RoundedCornerShape(12.dp)
    val keyboard = androidx.compose.ui.platform.LocalSoftwareKeyboardController.current
    val own = androidx.compose.runtime.remember { FocusRequester() }
    val focus = focusRequester ?: own
    if (autoFocus) androidx.compose.runtime.LaunchedEffect(Unit) { runCatching { focus.requestFocus() } }
    Row(
        modifier.height(40.dp).clip(shape).background(Palette.Card).border(1.dp, Palette.Divider, shape).padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Glyph(LucideIcons.Search, Palette.TextSecondary, size = 18.dp)
        Spacer(Modifier.width(8.dp))
        Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
            if (value.isEmpty()) Text(placeholder, style = Type.Body.copy(color = Palette.TextTertiary, fontSize = 15.sp), maxLines = 1)
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle = Type.Body.copy(fontSize = 15.sp),
                cursorBrush = SolidColor(Palette.Accent),
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(imeAction = androidx.compose.ui.text.input.ImeAction.Search),
                keyboardActions = androidx.compose.foundation.text.KeyboardActions(onSearch = { keyboard?.hide() }),
                modifier = Modifier.fillMaxWidth().focusRequester(focus),
            )
        }
        if (value.isNotEmpty()) {
            Box(Modifier.size(28.dp).clip(CircleShape).clickable(role = Role.Button) { onValueChange("") }, contentAlignment = Alignment.Center) {
                Glyph(LucideIcons.X, Palette.TextSecondary, size = 16.dp, description = "Clear search")
            }
        }
    }
}
/** Work in progress: what it is doing, how far along in numerals and a bar, and an optional way to stop it. */
@Composable
fun NexVoraProgressCard(
    title: String,
    fraction: Float,
    modifier: Modifier = Modifier,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
) {
    NexVoraCard(modifier) {
        Column(Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(title, style = Type.Subtitle, modifier = Modifier.weight(1f))
                Text("${(fraction.coerceIn(0f, 1f) * 100).toInt()}%", style = Type.Numeric.copy(color = Palette.Accent, fontSize = 18.sp))
            }
            Spacer(Modifier.height(10.dp))
            Box(Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)).background(Palette.Panel)) {
                Box(Modifier.fillMaxWidth(fraction.coerceIn(0f, 1f)).height(6.dp).background(Palette.PrimaryBrush))
            }
            if (actionLabel != null && onAction != null) {
                Spacer(Modifier.height(12.dp))
                NexVoraSecondaryButton(actionLabel, onAction)
            }
        }
    }
}
