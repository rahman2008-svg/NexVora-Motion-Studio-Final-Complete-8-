# Screen inventory

Built by reading the source tree (`ui/home`, `ui/editor`, `ui/editor/panels`, `ui/export`, `ui/common`). Nothing was run. "Fully" means the layout was replaced, "Partially" means palette, shared components or part of the layout changed, "Unchanged" means the original layout is kept (palette tokens still apply because everything draws from `Palette`).

| Screen / area | File | Status | What was done |
|---|---|---|---|
| System splash (Android 12+) | `res/values-v31/themes.xml` | Fully | NexVora mark on #090B12. Older Androids: dark window background only. No custom splash activity |
| Loading / failure screen at start | `MainActivity.kt` | Fully | `NexVoraLoadingState`, `NexVoraErrorState` |
| Welcome / onboarding (first launch) | `ui/home/Welcome.kt`, `MainActivity.kt` | Newly created | 3 pages, Skip, Get started; shown once (flag in app-private preferences) and not when the app is opened with shared files. No permission is requested by it. Not tested |
| Home, Edit tab | `HomeScreen.kt` | Fully | Brand header, "Recent projects" header, "Tools" cards; hero art retuned |
| Home, Projects tab: project cards, recent strip tiles, create cards, project and trash sheets | `HomeScreen.kt` | Fully (components) | Bordered 16dp cards with 12dp thumbnails, cyan select tick, gradient create glyph, 22dp elevated sheets. The tab's overall page structure (header, sort, select bar) is unchanged |
| Home, Search page | `HomeScreen.kt` | Partially | Palette only |
| Home, Trash page | `HomeScreen.kt` | Partially | Shared empty state; rest palette only |
| Home, Settings (Me tab) | `HomeScreen.kt` | Fully | Section headings, card rows |
| About | `HomeScreen.kt` | Fully | Rewritten: logo, credits, contact email, upstream attribution, source link |
| Licences | `HomeScreen.kt` | Partially | Card rows and new page header; every original licence entry kept |
| New-project / rename / delete / other confirm dialogs | `Common.kt`, `HomeScreen.kt` | Fully | Shared `ConfirmDialog` (elevated surface, secondary + primary buttons) |
| Action sheets, toast | `Common.kt` | Fully | 22dp sheet with grabber; elevated toast |
| Media picker | `MediaPicker.kt` | Fully (header/tabs/empty) | Pill album chooser, segmented tabs, shared empty state; grid, tiles, permission banner kept |
| Editor top bar | `EditorScreen.kt` | Fully | |
| Editor play bar | `EditorScreen.kt` | Partially | Cyan play button; other controls unchanged |
| Preview area | `EditorScreen.kt`, `PreviewOverlay.kt` | Partially | Darker stage behind the frame; overlays and handles unchanged |
| Timeline (Canvas) | `Timeline.kt` | Partially | Cyan selection, handles, playhead with head; 6dp clips; gradient add button. Structure, headers, zoom, context menus unchanged |
| Dock | `Dock.kt` | Partially | Icon tiles, cyan icons, disabled sockets, group-name breadcrumb; no selected state exists in the model |
| Panel frame (title bar, Done) | `PanelHost.kt` | Fully | Applies to every panel |
| Effects and Filters panels (search, categories, tile grid, favourites, intensity, Apply to all) | `Pickers.kt`, `PanelHost.kt` | Fully (components) | Category pills, 40dp search field, 12dp tiles with cyan selection ring, round bookmark chips, shared empty state, secondary button for Apply to all. Adjust and Presets tabs inside the Filters panel keep their layout |
| Other editing panel contents (about 40 files: text, audio, speed, crop, mask, stickers, captions, transitions and others) | `ui/editor/panels/*` | Partially | Those that use the shared `Tabs`, `GridTile`, `ChoiceTile`, `ChoiceGrid`, `SearchField` (text, sounds, stickers, layers, graphs, body effects and others) got the new pills and tiles; everything else inherits palette, slider, dialogs, frame |
| Export settings sheet | `ExportSheet.kt` | Fully | |
| Export progress / result | `ExportScreen.kt` | Fully | Title by stage, summary card, shared buttons, scrolling |
| Cover editor | `CoverEditor.kt` | Partially | Primary button + palette |
| Transcript, sound tools, tool search, teleprompter, draw, collage and other secondary pages | `panels/*`, `Dock.kt` | Partially / Unchanged | Palette and shared components only |
| In-app permission flows | `MediaPicker.kt` | Partially | Banner follows palette; text unchanged |
| Notifications (export) | `engine/ExportService.kt` | Unchanged | Uses platform icons; text says NexVora Motion |

Landscape: the activity is portrait-only in the manifest; no landscape layout exists.
