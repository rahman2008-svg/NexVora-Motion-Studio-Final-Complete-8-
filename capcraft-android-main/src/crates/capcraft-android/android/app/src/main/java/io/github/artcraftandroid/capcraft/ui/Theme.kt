package io.github.artcraftandroid.capcraft.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/** NexVora Motion's palette, from the approved reference: deep navy, purple-to-blue gradients, cyan highlights. */
object Palette {
    val Black = Color(0xFF03040B)
    /** The editor's chrome: top bar, timeline and dock. */
    val Chrome = Color(0xFF070A16)
    /** Panels and sheets over the editor. */
    val Panel = Color(0xFF0B1024)
    /** Cards and raised rows. */
    val Card = Color(0xFF10152C)
    val CardPressed = Color(0xFF1A2045)
    val Divider = Color(0xFF252D49)
    /** The neon highlight: selected text, rings and icons on a dark surface. */
    val Accent = Color(0xFFA020F0)
    /** The second purple, for gradients and the Create button. */
    val Purple = Color(0xFFC026FF)
    /** The primary purple the buttons start from. */
    val PurpleDeep = Color(0xFF7B2CFF)
    val Blue = Color(0xFF245BFF)
    val Cyan = Color(0xFF00D9FF)
    /** Words on a gradient button or a filled accent. */
    val OnAccent = Color(0xFFFFFFFF)
    /** The editor's top part: top bar, preview surround and play bar. */
    val EditorTop = Color(0xFF03040B)
    /** The timeline and the bottom bar under it. */
    val Timeline = Color(0xFF070A16)
    /** The bottom bar while a clip is selected, and the panels. */
    val DockSelected = Color(0xFF0B1024)
    /** The back button of a bottom bar level. */
    val DockBack = Color(0xFF252D49)
    /** Labels and icons in the bottom bar. */
    val DockText = Color(0xFFD9E2FF)
    /** The resolution chip in the top bar. */
    val Chip = Color(0xFF151E3A)
    /** The "add audio" / "add text" rows and their tiles. */
    val HintRow = Color(0xFF111A32)
    val HintIcon = Color(0xFF6F7BA0)
    val Text = Color(0xFFFFFFFF)
    val TextSecondary = Color(0xFF8993B8)
    val TextTertiary = Color(0xFF737D9E)
    val Disabled = Color(0xFF454D6A)
    /** Timeline clip colours by row. */
    val TextClip = Color(0xFF3A3F66)
    /** Captions, a lighter amber than titles. */
    val CaptionClip = Color(0xFFDA9208)
    val EffectClip = Color(0xFF9B35FF)
    val FilterClip = Color(0xFFE36BBF)
    val StickerClip = Color(0xFFDCB108)
    val AudioClip = Color(0xFF4A2BB8)
    /** Beat marks on sound clips. */
    val Beat = Color(0xFFFFD60A)
    val AudioWave = Color(0xFFB892FF)
    val OverlayClip = Color(0xFF1A2444)
    val Danger = Color(0xFFFF5572)
    /** Dialogs, menus and sheets that sit above a Panel. */
    val Elevated = Color(0xFF151B38)
    /** A faint purple wash for selected rows and chips. */
    val AccentSoft = Color(0x337B2CFF)
    val Success = Color(0xFF25D695)
    val Warning = Color(0xFFFFB547)
    /** The primary button: purple easing to electric blue. White ink reads on both ends. */
    val PrimaryBrush = Brush.horizontalGradient(listOf(Color(0xFF7B2CFF), Color(0xFF245BFF)))
    /** The New Project card and the Create button: a lighter violet into deep purple. */
    val PurpleBrush = Brush.linearGradient(listOf(Color(0xFFA020F0), Color(0xFF7B2CFF)))
    /** The Home hero: navy into purple into blue, as the reference's banner. */
    val HeroBrush = Brush.linearGradient(listOf(Color(0xFF0B1024), Color(0xFF3A1FA8), Color(0xFF007BFF)))
}

/** Corner radii: one scale for the whole app. */
object NexShape {
    val Chip = RoundedCornerShape(8.dp)
    val Button = RoundedCornerShape(12.dp)
    val Card = RoundedCornerShape(16.dp)
    /** A large panel that docks to the bottom edge (editing panels, export settings). */
    val PanelTop = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    val PanelBottom = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
    /** Modals and bottom sheets. */
    val Dialog = RoundedCornerShape(24.dp)
    val Sheet = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
}

/** Spacing steps (dp). */
object Space {
    val XS = 4.dp
    val S = 8.dp
    val M = 12.dp
    val L = 16.dp
    val XL = 24.dp
}

object Type {
    val Title = TextStyle(color = Palette.Text, fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
    val Body = TextStyle(color = Palette.Text, fontSize = 14.sp)
    val BodyStrong = TextStyle(color = Palette.Text, fontSize = 14.sp, fontWeight = FontWeight.Medium)
    val Small = TextStyle(color = Palette.TextSecondary, fontSize = 12.sp)
    val Tiny = TextStyle(color = Palette.TextSecondary, fontSize = 10.sp)
    val Display = TextStyle(color = Palette.Text, fontSize = 32.sp, fontWeight = FontWeight.Bold)
    val Subtitle = TextStyle(color = Palette.TextSecondary, fontSize = 15.sp, fontWeight = FontWeight.Medium)
    val Label = TextStyle(color = Palette.TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Medium)
    /** Values on sliders and rulers: semibold, fixed-width digits so they do not jiggle while dragging. */
    val Numeric = TextStyle(color = Palette.Text, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, fontFeatureSettings = "tnum")
    val Headline = TextStyle(color = Palette.Text, fontSize = 22.sp, fontWeight = FontWeight.Bold)
    val Caption = TextStyle(color = Palette.TextSecondary, fontSize = 11.sp)
    val DockLabel = TextStyle(color = Palette.Text, fontSize = 10.sp)
}

@Composable
fun CapCraftTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = Palette.Accent,
            onPrimary = Palette.OnAccent,
            background = Palette.Black,
            surface = Palette.Panel,
            onSurface = Palette.Text,
            onBackground = Palette.Text,
            secondary = Palette.Purple,
            surfaceVariant = Palette.Card,
            error = Palette.Danger,
            outline = Palette.Divider,
        ),
        content = content,
    )
}
