# Changelog

## 1.0.0 (NexVora Motion Studio, unreleased: never built)
Based on CapCraft for Android 0.0.4-beta (ArtCraft Team; engine from Concat). AGPL-3.0-or-later.
- Identity: name, `com.nexvora.motionstudio`, version 1.0.0 (code 1000099), original icon/logo/splash, credits and contact in About.
- Design system: NexVora palette, shapes, spacing, shared components (`ui/common/NexVora.kt`).
- Redesigned: Home Edit tab, project cards/sheets, Projects tab header and states, Settings, About, dialogs and sheets, editor top bar and play bar, export settings sheet, export screen, media picker header, Effects/Filters and shared panel tabs/tiles/search, panel frame and slider.
- Restyled only: timeline drawing, dock tiles, remaining panels (palette, selection colour).
- Changed behaviour: export file names and folders use "NexVora Motion"; `.srt` export name prefix; application ID differs from CapCraft.
- Not changed: Rust engine, FFmpeg/sherpa scripts, JNI/UniFFI, Kotlin namespace, stored-project keys, effect ids.
- Known gaps: see `NEXVORA_REMAINING_TASKS.md`.

- Latest: Projects tab pills/empty states/select bar, cyan bottom tab, button ink contrast; Reset and active-tab colours across panels; shared pill tabs in Basic and Shape style; speed curve, collage and body-effects touches.

- Latest: palette/shape/type tokens aligned to the new spec (Success, Warning, Error, muted text, 20/24dp shapes, Display/Subtitle/Label/Numeric); NexVoraChip, NexVoraSegmentedTabs, NexVoraSearchField added and used; first-launch Welcome screen; About links to the NexVora repository URL and the upstream project; NEXVORA_SOURCE_AUDIT.md.

- Latest: timeline zoom buttons; dock breadcrumb; Opacity panel header; SwitchRow/CheckChip restyled; PANEL_ROUTE_AUDIT.md (48 routes, none fully redesigned).

- Latest: 14 panel routes restructured (Fade, Ratio, Crop controls, Background, Voiceover, Enhance, Relight, Motion blur, Isolate voice, Auto reframe, Retouch, Stabilize, Effect object, Layer adjust); shared NexVoraProgressCard; timeline zoom buttons moved to a strip under the tracks; PANEL_COMPLETION_MATRIX.md.

- See `REDESIGN_CHANGELOG.md` for the panel passes.

- Reference pass: approved palette and gradients; Home rebuilt (hero, action cards, tool grid, project row); 5-slot bottom bar with Create; Tools Center; My Projects grid; Settings center with persisted haptics and export defaults; editor back arrow. See REFERENCE_IMPLEMENTATION_REPORT.md.

- Track mute/eye buttons (engine setTrackFlag), Export progress ring and success emblem, Stickers tool, About buttons, workflow APK name.

- Phase 1: Speed curve reset/numerals, Opacity reset/presets/fade link, Basic flip tab, Shape style fill opacity and size, Body effects reset, Graphs reset/numerals, export quality presets.

- Cyber Creator pass: new palette; editor Tools drawer and Project Info sheet (info, rename, quick actions, AI Magic).
