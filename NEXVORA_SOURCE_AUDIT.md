# Source audit

Based on reading the supplied project and comparing it with the original ZIP. Nothing was compiled or run (no Android SDK/NDK, Rust, Gradle download or network in the sandbox), so everything below is from the source text.

## Structure
- Root: `src/` (Rust workspace: `concat-*` engine crates and `capcraft-android`), `docs/`, `.github/workflows`, `LICENSE` (AGPL-3.0-or-later), `NOTICE`, `THIRD_PARTY_NOTICES.md`.
- Android project: `src/crates/capcraft-android/android/` (one module, `app`). 61 Kotlin files, about 22,300 lines; 39 files in `ui/editor/panels`.
- Package/namespace `io.github.artcraftandroid.capcraft`; native library `libcapcraft_android.so`; the JNI entry point is exported from `src/android.rs` under that package; UniFFI bindings are generated into `...capcraft.core`.

## Build facts (from `app/build.gradle.kts`, `rust-toolchain.toml`, wrapper properties)
compileSdk 37, targetSdk 36, minSdk 29, NDK 29.0.14206865, Java 17, Rust pinned in `src/rust-toolchain.toml` (1.93 line), Gradle wrapper 9.5.1 (it tried to download this and failed offline). Not changed.

## Navigation and state
- `MainActivity` shows loading until the engine is ready, then `App`, which switches between `Screen.Home` and `Screen.Editor(path, incoming, then)`; a media picker host sits above both. Files shared into the app (`SEND`, `SEND_MULTIPLE`) are handed to Home. Portrait-only.
- Editor state lives in `EditorViewModel` (Compose state, `panel` selects the open panel, `selectedClip`, `timeline`, export fields `exportResolution/exportFps/exportMbps/exportGif/exportPhoto`). Editing goes through JSON commands to the native session (`vm.command`, `vm.patch`, `vm.edit`).
- Home is one 1,400-line file (`HomeScreen.kt`) with tabs (Edit, Projects, Me), sheets and pages.

## Screens and areas
See `SCREEN_INVENTORY.md`. Added by this work: a first-launch Welcome screen (`ui/home/Welcome.kt`).

## Permissions and network (relevant to any privacy statement)
Manifest declares INTERNET, RECORD_AUDIO, READ_MEDIA_VIDEO/IMAGES/AUDIO/VISUAL_USER_SELECTED, READ_EXTERNAL_STORAGE (to API 32), FOREGROUND_SERVICE and FOREGROUND_SERVICE_DATA_SYNC (export service), POST_NOTIFICATIONS. INTERNET is present, and the transcription panel offers to download a model, so the app does use the network. I did not audit what else is sent, so **no privacy policy was written** and the Welcome page makes no claim about data leaving the device.

## Existing features relevant to the requested lists (from dock ids and panel files)
Present: trim/split, crop, rotate, mirror, speed (and speed curve), reverse, freeze, volume, opacity, transform, keyframes, layers, mask, chroma, background and blur, drawing, stickers, text with styles/templates/animation, captions (auto, manual, import, export), sounds, sound effects, extract/isolate voice, record/voiceover, effects, filters, adjust, transitions, vignette, sharpen, retouch, relight, stabilize, tracking, collage, auto reframe, remove background, export to MP4/GIF/photo with resolution, fps and bitrate.
Not found by keyword search: temperature, tint (as named controls), project share/import, project sort, grid project view, audio-only waveform preview panel, track lock, track-level mute/visibility buttons, quality presets, language settings, theme switching. These were not invented.

## Risks and open issues
1. Nothing has been compiled: the largest risk is a small compile error in the edited Kotlin (imports, parameter names).
2. `applicationId` differs from CapCraft, so it installs as a separate app and will not see CapCraft's projects (project files are in app-private storage). Opening an old CapCraft project in NexVora needs the project folder to be copied by the user; this was not tested.
3. Export names and folders now say NexVora Motion; any user script expecting `CapCraft_*` will not find them.
4. `SOURCE_URL` points to the repository URL the developer gave (`github.com/rahman2008-svg/NexVora-Motion-Studio-Final-Complete`). I could not verify that it exists or contains this source (no network); the AGPL requires that it does.
5. Timeline is drawn on a Canvas with its own hit-testing; any structural redesign (track headers, lock/mute) needs device testing.
6. `TextPanels.kt` has brace patterns that confuse a crude brace-counter (also in the original); a real compiler is needed to confirm it.
