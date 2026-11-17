# NexVora customization report

File | Original State | Changes Made | Verification. Only files whose content actually changed are listed.

| File | Original State | Changes Made | Status | Verification |
|---|---|---|---|---|
| `.github/workflows/release.yml` | Original CapCraft | See NEXVORA_CHANGES.patch | Modified | Build Unverified |
| `BUILD_INSTRUCTIONS.md` | did not exist | See NEXVORA_CHANGES.patch | Newly Created | Build Unverified |
| `BUILD_VERIFICATION_REPORT.md` | did not exist | See NEXVORA_CHANGES.patch | Newly Created | Build Unverified |
| `CHANGELOG.md` | did not exist | See NEXVORA_CHANGES.patch | Newly Created | Build Unverified |
| `DESIGN_SYSTEM.md` | did not exist | See NEXVORA_CHANGES.patch | Newly Created | Build Unverified |
| `FEATURE_AUDIT.md` | did not exist | See NEXVORA_CHANGES.patch | Newly Created | Build Unverified |
| `MODIFICATION_REPORT.md` | did not exist | See NEXVORA_CHANGES.patch | Newly Created | Build Unverified |
| `NEXVORA_CUSTOMIZATION_REPORT.md` | did not exist | See NEXVORA_CHANGES.patch | Newly Created | Build Unverified |
| `NEXVORA_FILE_INVENTORY.md` | did not exist | See NEXVORA_CHANGES.patch | Newly Created | Build Unverified |
| `NEXVORA_REMAINING_TASKS.md` | did not exist | See NEXVORA_CHANGES.patch | Newly Created | Build Unverified |
| `NEXVORA_SOURCE_AUDIT.md` | did not exist | See NEXVORA_CHANGES.patch | Newly Created | Build Unverified |
| `NEXVORA_TEST_REPORT.md` | did not exist | See NEXVORA_CHANGES.patch | Newly Created | Build Unverified |
| `NOTICE` | Original CapCraft | NexVora header added; original text kept | Modified | Build Unverified |
| `PANEL_COMPLETION_MATRIX.md` | did not exist | See NEXVORA_CHANGES.patch | Newly Created | Build Unverified |
| `PANEL_ROUTE_AUDIT.md` | did not exist | See NEXVORA_CHANGES.patch | Newly Created | Build Unverified |
| `PHASE_1_BUILD_REPORT.md` | did not exist | See NEXVORA_CHANGES.patch | Newly Created | Build Unverified |
| `PHASE_1_COMPLETION_REPORT.md` | did not exist | See NEXVORA_CHANGES.patch | Newly Created | Build Unverified |
| `PHASE_1_MODIFIED_FILES.md` | did not exist | See NEXVORA_CHANGES.patch | Newly Created | Build Unverified |
| `PHASE_1_PANEL_MATRIX.md` | did not exist | See NEXVORA_CHANGES.patch | Newly Created | Build Unverified |
| `PHASE_1_TEST_REPORT.md` | did not exist | See NEXVORA_CHANGES.patch | Newly Created | Build Unverified |
| `README.md` | Original CapCraft | NexVora header and origin/licence note; original text kept | Modified | Build Unverified |
| `REDESIGN_CHANGELOG.md` | did not exist | See NEXVORA_CHANGES.patch | Newly Created | Build Unverified |
| `REFERENCE_IMPLEMENTATION_REPORT.md` | did not exist | See NEXVORA_CHANGES.patch | Newly Created | Build Unverified |
| `RELEASE_NOTES.md` | did not exist | See NEXVORA_CHANGES.patch | Newly Created | Build Unverified |
| `REMAINING_WORK.md` | did not exist | See NEXVORA_CHANGES.patch | Newly Created | Build Unverified |
| `SCREEN_INVENTORY.md` | did not exist | See NEXVORA_CHANGES.patch | Newly Created | Build Unverified |
| `TEST_REPORT.md` | did not exist | See NEXVORA_CHANGES.patch | Newly Created | Build Unverified |
| `THIRD_PARTY_NOTICES.md` | Original CapCraft | Provenance section appended; original kept | Modified | Build Unverified |
| `src/crates/capcraft-android/NOTICE.md` | Original CapCraft | NexVora header added; camera-glyph line clarified; original entries kept | Modified | Build Unverified |
| `…/android/app/build.gradle.kts` | Original CapCraft | applicationId = com.nexvora.motionstudio (namespace kept) | Modified | Build Unverified |
| `…/main/AndroidManifest.xml` | Original CapCraft | Theme name and comments only | Modified | XML parses; build unverified |
| `…/capcraft/MainActivity.kt` | Original CapCraft | Loading and failure screens use NexVoraLoadingState / NexVoraErrorState; shows WelcomeScreen on first launch (not when files are shared in); startup/engine logic untouched | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/Theme.kt` | Original CapCraft | Tokens aligned to the 1.0 spec (Success/Warning/Error/muted, 20/24dp shapes, Display/Subtitle/Label/Numeric); palette remapped to NexVora tokens; added Elevated, AccentSoft, Success, Warning, PrimaryBrush, Purple; added NexShape, Space, Headline/Caption; Material scheme extended | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/common/Common.kt` | Original CapCraft | ActionSheet (grabber, elevated, 22dp), ConfirmDialog (elevated card, secondary+primary buttons), Toast restyled; same signatures and callbacks | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/common/MediaPicker.kt` | Original CapCraft | Header (icon button, album pill), segmented tabs, shared empty state, Add button ink; grid, permissions, import logic untouched | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/common/NexPrefs.kt` | did not exist | New: persisted settings (haptics, default export resolution and fps, reset) and gated haptics | Newly Created | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/common/NexVora.kt` | did not exist | New shared components: primary/secondary button, icon button, card, section header, empty/loading/error state, grabber, chip, segmented tabs, search field | Newly Created | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/editor/CoverEditor.kt` | Original CapCraft | Primary-button surface and grey literal remap | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/editor/Dock.kt` | Original CapCraft | Tool tiles (rounded tile, cyan icon), back button radius, group-name breadcrumb beside the back button; tools, order, actions unchanged | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/editor/EditorScreen.kt` | Original CapCraft | Top bar rebuilt; cyan play button; cyan full-screen scrubber; Busy dialog; darker preview stage | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/editor/EditorSheets.kt` | did not exist | See NEXVORA_CHANGES.patch | Newly Created | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/editor/EditorViewModel.kt` | Original CapCraft | FUNCTIONAL (minor): exported .srt name prefix CapCraft_ → NexVora_Motion_ | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/editor/ExportSheet.kt` | Original CapCraft | Rounded sheet, setting cards, estimate card, shared Export button; same state and estimate math | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/editor/PreviewOverlay.kt` | Original CapCraft | One grey literal remapped | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/editor/Timeline.kt` | Original CapCraft | Canvas drawing (cyan selection/handles/playhead with head, 6dp clips, gradient add button); canvas wrapped in a Column with a 40dp zoom strip under it (minus/plus, x1.5, zoom readout, same clamps as pinch); gestures untouched | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/editor/panels/AdjustSheets.kt` | Original CapCraft | Presentation only: grey literals remapped to NexVora navy; selected-state ring white→cyan; Reset control and active text-tab in cyan where present | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/editor/panels/ArrangeLayers.kt` | Original CapCraft | Presentation only: grey literals remapped to NexVora navy; selected-state ring white→cyan; Reset control and active text-tab in cyan where present | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/editor/panels/AudioEffects.kt` | Original CapCraft | Presentation only: grey literals remapped to NexVora navy; selected-state ring white→cyan; Reset control and active text-tab in cyan where present | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/editor/panels/AutoCaptions.kt` | Original CapCraft | Presentation only: grey literals remapped to NexVora navy; selected-state ring white→cyan; Reset control and active text-tab in cyan where present | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/editor/panels/AutoReframe.kt` | Original CapCraft | Output-shape stage, pills, Reframe/progress | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/editor/panels/BasicPanel.kt` | Original CapCraft | Position/Zoom/Rotate tabs now the shared pill Tabs; Reset in cyan; ruler logic untouched | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/editor/panels/BatchEditPanel.kt` | Original CapCraft | Presentation only: grey literals remapped to NexVora navy; selected-state ring white→cyan; Reset control and active text-tab in cyan where present | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/editor/panels/BeatsPanel.kt` | Original CapCraft | Presentation only: grey literals remapped to NexVora navy; selected-state ring white→cyan; Reset control and active text-tab in cyan where present | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/editor/panels/BodyEffects.kt` | Original CapCraft | Stop button is the shared secondary button; same cancel callback | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/editor/panels/Collage.kt` | Original CapCraft | Canvas: unselected edge and selected fill follow the palette | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/editor/panels/CropPage.kt` | Original CapCraft | Control area rebuilt (Rotate header with value and Reset, ratio tiles); crop canvas unchanged | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/editor/panels/CustomCutout.kt` | Original CapCraft | Presentation only: grey literals remapped to NexVora navy; selected-state ring white→cyan; Reset control and active text-tab in cyan where present | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/editor/panels/Draw.kt` | Original CapCraft | Presentation only: grey literals remapped to NexVora navy; selected-state ring white→cyan; Reset control and active text-tab in cyan where present | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/editor/panels/EffectLayer.kt` | Original CapCraft | Effect object option rows; Layer adjust setting cards | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/editor/panels/Enhance.kt` | Original CapCraft | Header, primary action, shared progress card; same enhance calls | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/editor/panels/EnterCaptions.kt` | Original CapCraft | Presentation only: grey literals remapped to NexVora navy; selected-state ring white→cyan; Reset control and active text-tab in cyan where present | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/editor/panels/GraphsPanel.kt` | Original CapCraft | Presentation only: grey literals remapped to NexVora navy; selected-state ring white→cyan; Reset control and active text-tab in cyan where present | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/editor/panels/IsolateVoice.kt` | Original CapCraft | Original/Voice only switch, shared progress card | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/editor/panels/MaskPanel.kt` | Original CapCraft | Presentation only: grey literals remapped to NexVora navy; selected-state ring white→cyan; Reset control and active text-tab in cyan where present | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/editor/panels/MotionBlur.kt` | Original CapCraft | Slider card with numerals, Direction/Times pills, shared progress card | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/editor/panels/OtherPanels.kt` | Original CapCraft | Voiceover (recorder column + option rows) and Background (current-background header, tiles) layouts rebuilt; recorder/session logic untouched; plus colour work | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/editor/panels/PanelHost.kt` | Original CapCraft | Volume, Blend, Speed tab, Transition pieces, Fade, Ratio, Opacity header rebuilt; SearchField delegates to the shared field; PanelFrame (22dp sheet, border, title, cyan Done), CapSlider (3dp track, cyan thumb), slider title weight, Tabs as pills, Opacity panel header with large numeric value, SwitchRow as card row (used by many panels), CheckChip, GridTile/ChoiceTile/SearchField restyled | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/editor/panels/Pickers.kt` | Original CapCraft | Presets tab (cards, empty state) and Adjust tab (pills, rounded tiles, numeric header, action row) rebuilt; Effects/Filters: tile radius and cyan ring, round chips, shared empty state, Apply to all as shared button, Reset and active tab in cyan | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/editor/panels/Relight.kt` | Original CapCraft | Pill header, strength header, 12dp sample tiles; same relight writes | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/editor/panels/RemoveBgPanel.kt` | Original CapCraft | Presentation only: grey literals remapped to NexVora navy; selected-state ring white→cyan; Reset control and active text-tab in cyan where present | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/editor/panels/Retouch.kt` | Original CapCraft | Two icon cards (Smooth, Brighten); same chain writes | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/editor/panels/ShapeStyle.kt` | Original CapCraft | Fill/Outline tabs now the shared pill Tabs (single Outline tab for lines/arrows); logic untouched | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/editor/panels/SoundFx.kt` | Original CapCraft | List rebuilt as cards with play badge, category/length and add button; empty state; synth code untouched | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/editor/panels/SoundsPage.kt` | Original CapCraft | Primary button surface and ink, cyan selection ring | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/editor/panels/SpeechPanel.kt` | Original CapCraft | Words field with counter, sectioned model/voice pills (shared chips; private Chip removed), shared progress card, primary Generate/Download button; same speech calls | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/editor/panels/SpeedCurveEditor.kt` | Original CapCraft | Canvas: curve in cyan, grid in divider colour, chosen point white, others cyan | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/editor/panels/StabilizePanel.kt` | Original CapCraft | Strength header, named level, shared progress card | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/editor/panels/Stickers.kt` | Original CapCraft | Presentation only: grey literals remapped to NexVora navy; selected-state ring white→cyan; Reset control and active text-tab in cyan where present | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/editor/panels/TextPanels.kt` | Original CapCraft | Presentation only: grey literals remapped to NexVora navy; selected-state ring white→cyan; Reset control and active text-tab in cyan where present | Modified | Crude check flagged; not compiled |
| `…/capcraft/ui/editor/panels/Tracking.kt` | Original CapCraft | Presentation only: grey literals remapped to NexVora navy; selected-state ring white→cyan; Reset control and active text-tab in cyan where present | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/editor/panels/Transcript.kt` | Original CapCraft | Primary button surface, greys remapped | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/editor/panels/VideoQuality.kt` | Original CapCraft | Presentation only: grey literals remapped to NexVora navy; selected-state ring white→cyan; Reset control and active text-tab in cyan where present | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/export/ExportScreen.kt` | Original CapCraft | Stage title, shared buttons, preview frame, summary card, scrolling; logic untouched; file prefix/folder renamed to NexVora Motion | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/home/HomeScreen.kt` | Original CapCraft | REFERENCE PASS: Edit tab rebuilt (header, hero with code-drawn clapperboard, New Project/Edit Photo cards, tool grid, project row), ProjectCard grid for My Projects, 5-slot BottomBar with Create, Tools Center, Settings center (persisted haptics/export defaults, permissions, help mail, reset). Earlier: dark palette forced; Edit tab header/sections/tool cards; project cards, tiles, create cards, sheets; Projects tab pills, empty states, select bar, cyan bottom bar; Settings cards; About rewritten; button ink for contrast | Modified | Braces/parens balanced (static); not compiled |
| `…/capcraft/ui/home/Welcome.kt` | did not exist | New first-launch welcome (3 pages, Skip, Get started); no permission requested | Newly Created | Braces/parens balanced (static); not compiled |
| `…/main/res/drawable/ic_launcher_background.xml` | Original CapCraft | New NexVora background | Modified | XML parses; build unverified |
| `…/main/res/drawable/ic_launcher_foreground.xml` | Original CapCraft | New NexVora N-in-frame mark | Modified | XML parses; build unverified |
| `…/main/res/drawable/ic_launcher_monochrome.xml` | did not exist | See NEXVORA_CHANGES.patch | Newly Created | XML parses; build unverified |
| `…/main/res/drawable/nexvora_logo.xml` | did not exist | See NEXVORA_CHANGES.patch | Newly Created | XML parses; build unverified |
| `…/main/res/mipmap-anydpi/ic_launcher.xml` | Original CapCraft | Adds monochrome layer | Modified | XML parses; build unverified |
| `…/main/res/values-v31/themes.xml` | did not exist | Theme.NexVoraMotion | Newly Created | XML parses; build unverified |
| `…/main/res/values/colors.xml` | did not exist | See NEXVORA_CHANGES.patch | Newly Created | XML parses; build unverified |
| `…/main/res/values/strings.xml` | Original CapCraft | app_name, app_full_name, share_label | Modified | XML parses; build unverified |
| `…/main/res/values/themes.xml` | Original CapCraft | Theme.NexVoraMotion | Modified | XML parses; build unverified |
| `…/android/gradle.properties` | Original CapCraft | capcraft.version=1.0.0, capcraft.versionCode=1000099 | Modified | Build Unverified |
| `…/android/settings.gradle.kts` | Original CapCraft | rootProject.name | Modified | Build Unverified |

## Functional changes
- `EditorViewModel.kt`: exported subtitle file name prefix.
- `ExportScreen.kt`: exported files named `NexVora_Motion_*`, saved under `Movies/NexVora Motion` / `Pictures/NexVora Motion`.
- `applicationId` is `com.nexvora.motionstudio`: a different app identity from CapCraft.
- Everything else is presentation only, with the original callbacks.

## Not verified
Nothing was compiled or run. "Braces balanced" is a text-level check. `TextPanels.kt` trips that crude check in the original too.
