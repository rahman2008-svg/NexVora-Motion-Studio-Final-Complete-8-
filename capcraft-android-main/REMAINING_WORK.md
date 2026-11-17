# Remaining work

Source of truth: `PANEL_ROUTE_AUDIT.md` and `PANEL_COMPLETION_MATRIX.md` (generated from the source). 19 of 48 panel routes are SOURCE COMPLETE; none is VERIFIED.

## Panel routes not yet SOURCE COMPLETE (29)
**IN PROGRESS (11):** Speed (Curve tab not done), Opacity (header only), Transition (browser kept), Packages, Quality, Text, Stickers, Basic (tabs only), Shape style (tabs only), Body effects, Graphs.
**NOT STARTED (18):** Animation, Transcript, Draw, Tracking, Camera tracking, Captions, Lyrics, Enter captions, Caption templates, Remove BG, Chroma, Mask, Custom cutout, Collage, Sounds, Layer, Batch edit, Beats.

## Nested tabs not done
Text (Font, Style, Colour), Filters / Video quality, the HSL, Graphs and Colour-wheel sheets inside Adjust, Speed / Curve, Audio effects / Draw / Mask tabs, Basic and Shape-style tab contents.

## Editor
Timeline ruler, track headers, track lock/mute/visibility (no such state exists in the original: needs a model plus engine support to be real, not decorative), dock selected-tool state (no such state), preview frame and controls, top-bar project title / save status (project name is not in the ViewModel).

## Home, Projects, Search
Home dashboard structure (template discovery does not exist in the app), Projects grid/list switch and sorting (do not exist; would need new state), Search page structure (recent searches and categories do not exist).

## Other
Cover editor, Transcript and Sound tools inner layouts; `Icons.kt` and `TransitionGlyph.kt` (icon data, unchanged); notification icon (platform icons); typography: a font family was not changed and `Type.Numeric` is used in the redesigned panels but not in every slider.

## Blocked on tooling (see BUILD_VERIFICATION_REPORT.md)
Compile, lint, unit tests, Rust tests, APK, device testing, screenshots, tablet/large-font checks.

## Owner actions
Publish this source at the repository URL in the About screen (unverified); use your own signing key; keep AGPL notices.


## Update (second reference pass)
Done in source (unverified): track mute and eye buttons on timeline rows, Export ring and success screens, Stickers in Tools, About buttons. Still open: track lock (no engine support), Photo Editor home and workspace, Export History (needs stored records), Help/FAQ content, Privacy statement (needs an audit), the 29 panel routes in `PANEL_COMPLETION_MATRIX.md`, and all compile/test/device verification.


## Update (Phase 1)
Speed, Opacity, Basic, Shape style, Body effects and Graphs are SOURCE COMPLETE; Transition, Packages, Quality, Text, Stickers keep their (already complete) functions but are IN PROGRESS for layout. See `PHASE_1_PANEL_MATRIX.md`. Everything remains uncompiled and untested.


## Update (Cyber Creator pass)
Done in source (unverified): Tools drawer, Project Info sheet with rename, quick actions, AI Magic (existing AI tools only), new palette. Not done: Pro badge (no Pro system), landscape/wide workspace, preview neon frame, the floating Edit/Audio/Text/Effects/Overlay/Filters/Adjust bottom toolbar, glow/glass effects, new animations, track lock. All compile, test and device checks remain blocked.
