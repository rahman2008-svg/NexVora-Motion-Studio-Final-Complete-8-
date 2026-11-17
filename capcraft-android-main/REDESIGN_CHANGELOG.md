# Redesign changelog

All source-only; nothing compiled or run.

## This pass
- Panels moved to SOURCE COMPLETE: Volume, Blend, Sound FX, Speech, Adjust (Adjust tab and the Presets tab).
- Speed tab (readout, duration, preset pills) and Transition (favourites chip, empty state, Apply to all, Duration bar) partly rebuilt: still IN PROGRESS.
- One search field: `SearchField` now delegates to `NexVoraSearchField` (which gained a clear cross, search IME action and optional autofocus); the private `Chip` in `SpeechPanel` was removed in favour of `NexVoraChip`.
- CI: release APK artifact renamed to `NexVora-Motion-Studio-$VERSION.apk`.

## Earlier passes (cumulative)
- Fade, Ratio, Crop (controls), Background, Voiceover, Enhance, Relight, Motion blur, Isolate voice, Auto reframe, Retouch, Stabilize, Effect object, Layer adjust restructured.
- Shared `NexVoraProgressCard`, `NexVoraChip`, `NexVoraSegmentedTabs`, `NexVoraSearchField`; timeline zoom strip under the tracks; dock breadcrumb; Opacity header; card switch rows.
- First-launch Welcome screen; About with credits, contact and source links; Home, Projects, Settings, dialogs, sheets, export screen and settings sheet redesigned earlier.

## Files
Full per-file status: `NEXVORA_FILE_INVENTORY.md`, `NEXVORA_CUSTOMIZATION_REPORT.md`. Removed files: none. Removed functions: `RetouchSlider`, `RecordTool`, `SpeechPanel.Chip` (replaced, no references remain).
