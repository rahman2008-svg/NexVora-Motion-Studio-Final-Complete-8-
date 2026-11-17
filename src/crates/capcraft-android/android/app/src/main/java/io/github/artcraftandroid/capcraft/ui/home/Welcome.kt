package io.github.artcraftandroid.capcraft.ui.home

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.artcraftandroid.capcraft.ui.Glyph
import io.github.artcraftandroid.capcraft.ui.LucideIcons
import io.github.artcraftandroid.capcraft.ui.Palette
import io.github.artcraftandroid.capcraft.ui.Type
import io.github.artcraftandroid.capcraft.ui.common.NexVoraPrimaryButton

private const val PREFS = "nexvora_app"
private const val SEEN = "welcome_seen"

/** Whether the welcome pages have been shown (or skipped) before. */
fun welcomeSeen(context: Context): Boolean =
    context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getBoolean(SEEN, false)

/** Remembers that the welcome pages need not be shown again. */
fun markWelcomeSeen(context: Context) {
    context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit().putBoolean(SEEN, true).apply()
}

private class WelcomePage(val icon: ImageVector?, val title: String, val message: String)

private val PAGES = listOf(
    WelcomePage(null, "Welcome to NexVora Motion Studio", "Cut, arrange and finish videos on your phone."),
    WelcomePage(
        LucideIcons.Layers,
        "Everything on one timeline",
        "Trim and split clips, layer video, text, stickers and audio, then add effects, filters, transitions and captions.",
    ),
    WelcomePage(
        LucideIcons.Folder,
        "Your projects stay on this device",
        "Android asks for access to your photos and videos when you add media, and to the microphone when you record.",
    ),
)

/**
 * The first-launch welcome: three pages, Skip, and Get started. It asks for no permission itself;
 * [onFinish] is called on Skip and on the last page's button.
 */
@Composable
fun WelcomeScreen(onFinish: () -> Unit) {
    var page by remember { mutableIntStateOf(0) }
    val last = page == PAGES.lastIndex
    val p = PAGES[page]
    Column(Modifier.fillMaxSize().background(Palette.Black).statusBarsPadding().navigationBarsPadding()) {
        Row(Modifier.fillMaxWidth().height(56.dp).padding(horizontal = 12.dp), verticalAlignment = Alignment.CenterVertically) {
            Spacer(Modifier.weight(1f))
            if (!last) {
                Text(
                    "Skip",
                    style = Type.Subtitle,
                    modifier = Modifier.clip(RoundedCornerShape(8.dp)).clickable(onClick = onFinish).padding(horizontal = 12.dp, vertical = 10.dp),
                )
            }
        }
        Column(
            Modifier.weight(1f).fillMaxWidth().padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            if (p.icon == null) {
                Image(
                    painterResource(io.github.artcraftandroid.capcraft.R.drawable.nexvora_logo),
                    contentDescription = "NexVora Motion Studio logo",
                    modifier = Modifier.size(120.dp),
                )
            } else {
                Box(
                    Modifier.size(96.dp).clip(RoundedCornerShape(24.dp)).background(Palette.Card),
                    contentAlignment = Alignment.Center,
                ) { Glyph(p.icon, Palette.Accent, size = 44.dp) }
            }
            Spacer(Modifier.height(32.dp))
            Text(p.title, style = Type.Headline.copy(textAlign = TextAlign.Center))
            Spacer(Modifier.height(12.dp))
            Text(p.message, style = Type.Subtitle.copy(textAlign = TextAlign.Center))
        }
        Row(Modifier.fillMaxWidth().padding(bottom = 16.dp), horizontalArrangement = Arrangement.Center) {
            PAGES.indices.forEach { i ->
                Box(
                    Modifier.padding(horizontal = 4.dp).size(if (i == page) 20.dp else 8.dp, 8.dp).clip(CircleShape)
                        .background(if (i == page) Palette.Accent else Palette.Divider),
                )
            }
        }
        NexVoraPrimaryButton(
            if (last) "Get started" else "Next",
            { if (last) onFinish() else page++ },
            Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 16.dp),
        )
    }
}
