# NexVora Motion Studio 1.0.0: modification report

Base: `capcraft-android-main` (CapCraft for Android 0.0.4-beta, AGPL-3.0-or-later; engine from Concat 0.2.8).
Original upstream: https://github.com/artcraft-android/capcraft-android

Project root: the ZIP's single top folder. Android project: `src/crates/capcraft-android/android/`; Rust workspace: `src/`.
The original tree was copied before any edit; `NEXVORA_CHANGES.patch` is the exact diff against it.

## Identity
- Name NexVora Motion Studio (launcher label "NexVora Motion"), version 1.0.0 (`capcraft.version=1.0.0`, `capcraft.versionCode=1000099`; the build checks the two agree).
- `applicationId = com.nexvora.motionstudio` (debug builds add the original `.debug` suffix). FileProvider authority and the Kotlin code that builds it both follow `applicationId`.
- minSdk 29, targetSdk 36, compileSdk 37 unchanged.

## Deliberately NOT changed (and why)
- **Kotlin namespace / source packages stay `io.github.artcraftandroid.capcraft`.** The Rust library exports `Java_io_github_artcraftandroid_capcraft_NativeContext_init` and UniFFI generates bindings into `...capcraft.core`; renaming means changing Rust, `uniffi.toml`, ProGuard and every import together, and I cannot compile or run any of it here. `applicationId` is independent of the namespace, so the installed identity is fully NexVora.
- **Internal `capcraft*` identifiers** (project data keys such as `capcraftBackground`, effect ids such as `capcraft.outline`, native library `libcapcraft_android.so`, thread names): they are stored in users' project files and effect catalogues; renaming would break saved projects. They are not user-visible.
- Engine, FFmpeg/sherpa scripts, timeline, rendering, export code, encoder settings, SDK levels, dependencies: untouched. No new dependencies.

## Changes
- Branding: `strings.xml` (label, share target "Edit in NexVora Motion"), manifest comments and theme `Theme.NexVoraMotion`, export notification tip, media-picker banner, exported file names `NexVora_Motion_*.mp4/.gif/.jpg/.srt`, and save folders `Movies/NexVora Motion`, `Pictures/NexVora Motion` (previously `/CapCraft`).
- Design system: `ui/Theme.kt` palette remapped to the specified colours (#090B12 background, #111522 panel, #171D2B card, #00D5FF cyan, #8957FF purple, #F7F9FF / #9AA5B8 text, #283044 divider) with new `Palette.Purple`; effect clips use the purple; Material colour scheme gets `secondary`/`outline`. `res/values/colors.xml` holds the same tokens for XML. The Home pages now always use the dark palette; hero art gradient retuned to cyan-indigo-purple.
- Home: hero eyebrow reads "NexVora Motion Studio"; Settings entry "About NexVora Motion Studio". Layout and project logic unchanged.
- About screen rewritten: logo, name, version (read from the package), developer, publisher, the three credit lines, a plain statement that this is a modified version of CapCraft built on Concat, the AGPL notice, and a link to the original source. Licences screen keeps every original entry.
- Icons: original vector artwork (N inside cinematic frame corners, cyan to purple): adaptive foreground, background, monochrome (themed icon), `nexvora_logo.xml` (About), splash icon on Android 12+ (`values-v31/themes.xml`; older versions get the dark window background). Previously a hollow camera; `docs/images/nexvora-512.png` is a PNG render for documentation.
- Legal: `NOTICE`, `src/crates/capcraft-android/NOTICE.md` (shown in-app), `THIRD_PARTY_NOTICES.md` and `README.md` now state that this is a modified CapCraft/Concat work and keep all original text. `LICENSE` untouched.

## Not done / limits (be aware)
- **No compile, no APK, no tests.** This environment has Java only: no Android SDK/NDK, Gradle, Rust or network. Nothing here was built or run. See `TEST_REPORT.md`.
- **UI redesign is at design-system level, not a full relayout.** Colours, theme, splash, icon, Home hero/About/Settings text are changed; editor panels, timeline and export screens keep their layout and only inherit the new palette. A bolder redesign needs an emulator to check each screen, and I did not want to ship unverified layout rewrites of 21k lines of Compose.
- Some hard-coded colours (e.g. `Color(0xFF1C1C1C)` banners, white text on the hero) were left as they were.
- README screenshots still show the original interface.
- `.github/workflows/release.yml` still names artifacts `capcraft-android-<version>.apk` and says CapCraft in comments.
- Cargo workspace version stays 0.2.8 (engine version, separate from the app version).

## Before you distribute (AGPL-3.0-or-later)
1. Publish this modified source and change `SOURCE_URL` in `ui/home/HomeScreen.kt` (currently the original CapCraft repo) to where your modified source lives, so users of your build can get the corresponding source.
2. Keep `LICENSE`, `NOTICE`, both notice files and the in-app Licences screen.
3. "All Rights Reserved" applies only to your own changes; the combined work stays under AGPL, so it cannot be an all-rights-reserved proprietary app. Consider wording like "Copyright (c) 2026 NexVora Lab's Ofc, licensed under AGPL-3.0-or-later" in store listings.
4. Android lets only one app hold an application ID; installing this will not replace a CapCraft install.


---

# Phase 2: application-wide design pass (what was and was not done)

Continues from the phase-1 project; nothing was restarted. Still **uncompiled and unrun** (no SDK/NDK/Gradle/Rust/emulator here).

## Done
- **Inventory:** 21.8k lines of Compose, one Gradle module. Screens/areas found: Home (Edit tab, Projects, Trash, tools pages, Settings, About, Licences), Media picker, Editor (top bar, preview + overlay, timeline, dock, ~40 panels/sheets), Cover editor, Export sheet and Export screen, plus the shared dialogs/sheets/toast.
- **Design tokens** (`ui/Theme.kt`): added Elevated #20283A, AccentSoft, Success, Warning, `PrimaryBrush`, `NexShape` (radii 8/12/16/22), `Space` (4/8/12/16/24), `Type.Headline`/`Caption`. Material scheme gets surfaceVariant and error.
- **Reusable components** (`ui/common/NexVora.kt`): `NexVoraPrimaryButton`, `NexVoraSecondaryButton`, `NexVoraIconButton`, `NexVoraCard`, `NexVoraSectionHeader`, `NexVoraEmptyState`, `NexVoraLoadingState`, `NexVoraErrorState`, `NexVoraGrabber`. 
  Actually used by: `ConfirmDialog` (every confirm/rename/delete dialog), `ActionSheet` (grabber, Elevated surface, 22dp top corners, Cancel button), `Toast`, the editor's loading and failure screens (logo + spinner), and the Trash empty state. `NexVoraIconButton`, `NexVoraCard` and `NexVoraSectionHeader` are defined but **not yet used anywhere**; they are there for the next pass.
- **Colour clean-up:** 38 files had leftover neutral greys (about 200 literals such as #3A3A3A, #333333, #2E2E2E); they are remapped to navy-tinted equivalents, so panels, borders, chips, timeline folds and the media picker banner follow the new palette. Colour-picker swatches and the colour disc were left alone on purpose.
- **Controls:** primary buttons (editor Export, export sheet, cover editor, export screen, media picker Add, Home Create and dialog confirm, sound Use, transcript button) use the cyan `PrimaryBrush` with 10dp corners; the slider has a 3dp track and a cyan-ringed thumb.
- The timeline, ruler, clips, playhead and dock are drawn from `Palette`, so they inherit the new colours; effect clips are now electric purple.

## Not done (honest list)
- **This is not a full screen-by-screen relayout.** Layouts of the editor, panels, timeline, dock, media picker and export screens are unchanged; they have the new colours and shared components, not new designs. I could not check any redesigned layout visually without an emulator and did not want to ship blind rewrites of thousands of lines.
- No dock selected/disabled redesign beyond colours (the `Tool` model has no selected state), no tablet/landscape work, no text-scale or contrast audit, no screenshots, no animation changes (existing animations only; reduced-motion not reviewed).
- Many panels still use their own inline buttons and cards rather than the new components; hard-coded `Color.White` and some alpha-black overlays remain.
- Onboarding: none exists in the app. Splash: system splash on Android 12+ only (no custom splash activity, by design, to avoid a startup delay).
- `NexVoraCard`/`IconButton`/`SectionHeader` unused (above).


---

# Phase 3: layout-level redesign of the main screens

Continues from phase 2. Still **uncompiled, unrun, no screenshots, no APK** (no Android SDK/NDK/Gradle/Rust/emulator in this environment).

## Screens whose layout was restructured (state and callbacks unchanged)
- **Home, Edit tab:** new header (NexVora mark + "NexVora Motion" + search button), a "Recent projects" section header with "See all" (opens the same all-projects page as the old arrow card), a "Tools" section, and the tool shortcuts as bordered cards with cyan icons instead of bare icon/label cells.
- **Editor top bar:** raised bar with a hairline, shared icon buttons (Close, Find a tool, same actions), resolution chip as an outlined pill (opens the same export sheet), shared primary Export button (44dp tall).
- **Editor play bar:** the play/pause control is now a filled cyan circle; undo, redo, keyframe, linkage and full-screen controls are unchanged. The full-screen scrubber uses the cyan accent.
- **Export screen:** centred title that follows the stage (Exporting / Export complete / Export failed), shared back/close and Done buttons, a bordered preview, a summary card (format, resolution, frame rate, bitrate or "Suggested", save folder; read from the same view-model values the export uses), shared "Try again" and "New project" buttons, and the page now scrolls so the extra card cannot push content off small screens. The cancel, progress, share and open logic is untouched.
- **Media picker:** shared close button, album chooser as a pill, Videos/Photos/Collage as a segmented control, and a shared empty state (the permission banner, grid, selection and import code are untouched).
- **Shared components now used:** `NexVoraIconButton` (editor, export, media picker, Home), `NexVoraCard` (Home tool cards, export summary), `NexVoraSectionHeader` (Home), `NexVoraGrabber` (action sheets), plus the phase-2 uses. No shared component is left unused.

## Still NOT redesigned (be aware before calling the UI "finished")
- **Timeline**: drawn on a Canvas; it has the new palette only (purple effect clips, navy tracks). No new layout, track headers, zoom controls or context-menu design.
- **Editor dock** (bottom tools) and **preview area**: palette only.
- **The ~40 editing panels** (text, audio, effects, filters, transitions, crop, speed, volume, captions, stickers and so on): they inherit the shared slider, dialogs and colours but keep their original layouts and inline buttons.
- **Export settings sheet** (resolution/frame-rate/bitrate sliders), **Projects page, project cards, search, settings page**: palette/shared-component changes only.
- **Responsive review:** the activity is locked to portrait (`screenOrientation="portrait"` in the manifest), so there is no landscape editor layout to break or improve; tablets, small-phone widths and large text were not tested.
- Hard-coded `Color.White` and alpha-black overlays remain in many places.

## Risk note
All Kotlin here was written without a compiler. The most likely build problems are small (a missing import or a parameter name); run `./gradlew assembleDebug` and fix what it reports before judging the look.


---

# Phase 4: editor experience (timeline, dock, panels, export settings, settings)

Still **uncompiled, unrun, no screenshots, no APK**: this environment has no Android SDK/NDK/Gradle/Rust/emulator. Everything below is source-level work, checked only by reading and a crude brace/import check.

## Fully redesigned (presentation replaced, behaviour untouched)
- **Export settings sheet** (`ExportSheet.kt`): rounded bottom sheet on the Panel colour; each setting (resolution, frame rate, bitrate, GIF size/rate) is a bordered card with a semibold title, hint and a cyan-ringed thumb; the estimated file size is its own card with the value in cyan; the Export action is the shared primary button. Values still come from the same view-model state (`exportResolution`, `exportFps`, `exportMbps`, `gifSize`, `gifFps`) and the same size estimate. No quality presets were added: the encoder has none.
- **Panel frame** (`PanelFrame` in `PanelHost.kt`): the one container every editing panel (text, audio, music, volume, speed, crop, rotate, transform, filters, effects, transitions, stickers, captions, background, colour, keyframes, overlay and the rest) is built in. New 22dp rounded sheet with a hairline border, title in the shared title style, a cyan round Done button (same `onDone`), and a divider. Because it is central, all panels get the same header and Done action at once.
- **Settings page**: section headings (Storage, About) and each setting as a card; the licence list rows use the same card. **Page header** (About, Licences): shared back button and a hairline.

## Partially redesigned
- **Timeline** (Canvas): selected clip outline and trim handles, multi-select outline and the playhead are now cyan, the playhead has a round head, clip corners are 6dp, and the "add" button uses the cyan gradient. Layout, gestures, snapping, scrolling, thumbnails and data are untouched. Not done: track headers, track visibility/mute controls, zoom buttons, context menus and an empty-timeline state (none of these exist as separate UI in the original, and adding them would mean new behaviour, not a restyle).
- **Editor dock**: every tool sits on a rounded tile with a cyan icon and a label under it; disabled tools show an empty socket; the back button has the shared radius. All tools, order, labels, scrolling and callbacks unchanged. Not done: a cyan "active" indicator, because the dock is a navigation stack (root, group, clip) and a tool has no selected state; inventing one would be fake. 
- **Panel contents** (~40 panels): they inherit the new frame, slider, dialogs and palette but keep their inner layouts; many still use inline buttons and cards.

## Unchanged (layout)
- **Preview area** (Preview composable, transform handles, overlays): palette only; no new frame, canvas background or spacing change was made.
- **Projects page, project cards, search page, Trash page**: palette and shared empty state only.
- Native engine, view-models, state, navigation, project files, export pipeline: no functional change.

## Functional changes
None intended. UI-only edits; no callback, state or parameter was removed. One structural difference: `NexVoraCard` now wraps the export-sheet and settings rows, so a card's content sits inside one extra Box.

## Known limitations / likely build issues
- Never compiled. Likely first-build issues: a missing import in a file where I used a short name (`border`, `Role`), or a modifier ordering warning.
- The dock tile adds ~4dp of height to some labels that wrap to two lines; if it clips on a small phone, reduce `Spacer` or the tile height in `Dock.kt` `ToolItem`.
- Portrait-only app: no landscape editor exists.
- No tablet, small-phone, text-scaling or contrast testing.


---

# Final pass

- Verified the supplied tree contains the earlier changes (tokens, `NexVora.kt`, `PanelFrame`, dock, export, timeline, settings) by reading the source; ran a static check that every `Palette`/`Type`/`Space`/`NexShape` member and every app resource referenced by Kotlin or the manifest exists (all do; `stat_sys_upload*` are platform drawables).
- About screen: added the developer contact email as a mailto link; editor preview stage now has the app-background colour behind the frame.
- New documents: `SCREEN_INVENTORY.md`, `FEATURE_AUDIT.md`, `DESIGN_SYSTEM.md`, `RELEASE_NOTES.md`.
- The requested "complete redesign of every screen and every panel's contents" is **not complete**: see `SCREEN_INVENTORY.md` for the exact status of each screen. Remaining work, concretely: rebuild the inner layouts of the panels in `ui/editor/panels` on the shared components, redesign Projects/Search/Trash/preview/timeline structure, add landscape only if the editor can support it, and do all of it against screenshots from a real build.


---

# Follow-up: Effects, Filters and shared panel components

Still uncompiled and unrun.

- `Tabs` (categories and panel tabs) became pills: chosen = cyan wash + cyan outline, others on the card colour with a hairline. Used by Effects, Filters, text, sounds, stickers and other panels.
- `GridTile`, `ChoiceTile`, `FilterTile`: 12dp tiles, hairline border, cyan 2dp ring when chosen (was white), label turns cyan. `SearchField`: 40dp, card colour, hairline. `ChoiceGrid` padding 16/12.
- Filters header: search and import buttons and the favourites bookmark are round card chips (bookmark lit cyan when the favourites view is on). Empty favourites uses `NexVoraEmptyState`. "Apply to all" is a `NexVoraSecondaryButton` calling the same `filterToAll`.
- Home: project cards, strip tiles, create cards and the project/trash sheets redesigned as listed in `SCREEN_INVENTORY.md`.
- No state, callback, engine or stored-key change. The Adjust and Presets tabs, effect "Adjust" page, intensity slider logic and package loading are untouched.


---

# Latest pass

Projects tab restyled (Local/Trash and All/Video/Photo as pills, search field, empty states with a Create action, select bar with shared buttons, cyan active bottom tab, Create button ink fixed for contrast), cyan selection rings in 20 panel files, dark ink on cyan primary buttons (media picker Add, sounds Use, dialog confirm). Per-file reports: `NEXVORA_FILE_INVENTORY.md`, `NEXVORA_CUSTOMIZATION_REPORT.md`, `NEXVORA_REMAINING_TASKS.md`, `NEXVORA_TEST_REPORT.md`, `CHANGELOG.md`. Still uncompiled.


# Panel pass
14 panel routes were restructured (list in `PANEL_ROUTE_AUDIT.md`), the timeline zoom buttons were moved out of the track area, and a shared `NexVoraProgressCard` replaced five copies of the same progress block. Still uncompiled; 34 routes remain.
