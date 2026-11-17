# Remaining tasks

Status key: **Done** (source changed, not compiled) · **Partial** · **Not in original** (the feature does not exist in CapCraft's source; not faked) · **Not done**. "Exists" was checked by keyword search of the Kotlin source, not by running the app.

## Branding and identity
| Item | Status |
|---|---|
| App name, application ID, version/version code | Done (`NexVora Motion`, `com.nexvora.motionstudio`, 1.0.0 / 1000099) |
| Logo, adaptive icon (+ themed icon) | Done (original artwork) |
| Splash | Done for Android 12+ (system splash, mark on #090B12); older Android gets the dark window background only |
| Theme and colour palette | Done |
| Typography | Partial: type scale tokens added; the font family is still the original one |
| Notification icon | Not done: export notification still uses platform `stat_sys_*` icons |

## Onboarding and navigation
| Item | Status |
|---|---|
| Onboarding, welcome screen | Done (new `Welcome.kt`; 3 pages, Skip, Get started; untested) |
| Permission request screen | Partial: media-picker permission banner follows the palette; no dedicated screen |
| Bottom navigation | Done (cyan active tab) |
| Side navigation drawer | Not in original |
| Top bar (editor) | Done; Home pages use their own headers |
| Back navigation / flow | Not reviewed |

## Home and projects
| Item | Status |
|---|---|
| Home redesign, create card, recent projects, tool cards | Done |
| All projects, list layout, project cards, thumbnails, info | Done (cards) |
| Grid layout | Not in original (list only) |
| Search | Done (restyled, empty states) |
| Sorting | Not in original (only All/Video/Photo filter chips) |
| Filter chips, empty state | Done |
| Rename, duplicate, delete | Exist in the project sheet; restyled; logic untouched |
| Share project, import project | Not found in the project menu; not created |
| Trash and restore | Done (pills, empty state, sheet) |

## Editor
| Item | Status |
|---|---|
| Top bar, play bar | Done |
| Preview, preview controls | Partial: stage colour, cyan play, scrubber; frame and overlays unchanged |
| Timeline container, ruler, playhead, tracks | Partial: drawing colours, cyan selection, playhead head, 6dp clips; track headers, layout not redesigned |
| Timeline zoom controls | Done: minus/plus buttons overlaid bottom-right (x1.5 steps, same clamps as pinch); not tested. They float over the track area, so they can cover a clip's right end |
| Undo/redo | Existing controls, unchanged |
| Dock / tool selection | Partial: tile design and a group-name breadcrumb beside the back button; no "selected" state exists for tools |

## Editing tools and panels
Present in the original (dock ids / panel files): trim/split, crop, rotate, mirror (flip), speed, reverse, freeze, volume, opacity, transform, keyframes, layers/overlay, extract audio, fade, record, voiceover, sound effects, sounds, text/style/animation, captions (auto, enter, import, export), stickers, drawing, background, blur, mask, chroma, effects, filters, adjust, transitions, vignette, sharpen. Temperature and tint were not found by keyword; not verified.
| Item | Status |
|---|---|
| Panel frame, header, Done, sliders, dialogs | Done (central) |
| Effects, Filters (tabs, tiles, search, favourites, Apply to all) | Done |
| Panels using shared Tabs/Tiles/Search (text, sounds, stickers and others) | Done through those shared components |
| Panels' tab rows (Basic, Shape style, Mask, Draw, Relight, Audio effects, Text) | Done to the extent of: shared pill tabs in Basic and Shape style, cyan active tab elsewhere; Reset control cyan in 8 panels |
| Inner layouts of the remaining panels (text style/font/colour pickers, audio, speed, crop, mask shapes, captions, keyframes, adjust sliders, transitions, and so on) | **Not done**: original layouts, new palette, selection colour and the changes above only |
| Music/sound libraries, voice recording UI | Not done (restyle through shared pieces only) |
| Audio waveform | Not redesigned |

## Export
| Item | Status |
|---|---|
| Export screen, progress, cancel, success, error, retry, share, open | Done (layout), logic untouched |
| Resolution, FPS, bitrate | Done (settings sheet) |
| Format | Video / GIF / Photo tabs exist; restyled only |
| Quality presets | Not in original |
| Save to gallery | Existing behaviour (Movies/NexVora Motion), not tested |

## Settings
| Item | Status |
|---|---|
| Settings, cache management, version | Done |
| About, developer credits, open-source licences | Done |
| Appearance / dark-light theme, language, default export settings, notification settings, permission management, privacy policy | Not in original; not created. A privacy screen would need a factual statement about what the app sends over the network (it downloads models for transcription), which I have not audited |

## Verification (all still open)
Compile, resource build, lint, unit tests, Rust build, APK, emulator run, screenshots: **not possible in the sandbox** (no SDK/NDK/Gradle download/Rust). First action for you: build `./gradlew assembleDebug` locally and fix reported errors; then send screenshots so the remaining layouts can be done against real output.

## Before distributing
`SOURCE_URL` now points to `github.com/rahman2008-svg/NexVora-Motion-Studio-Final-Complete` (given by you; I could not verify it): publish this source there; sign with your own key; rename CI artifacts; keep AGPL notices.

## Panel routes
`PANEL_ROUTE_AUDIT.md` lists all 48 routes separately: **0 fully redesigned**, 17 partially redesigned (6 with a specific layout change, 11 through reworked shared components called directly), 31 colour only. Retouch, Enhance and the others are still in the colour-only group.

## Latest panel pass (see PANEL_ROUTE_AUDIT.md and PANEL_COMPLETION_MATRIX.md)
Layout redesigned in source, never compiled or run (14 routes): Fade, Ratio, Crop (control area; the canvas is original), Background, Voiceover, Enhance, Relight, Motion blur, Isolate voice, Auto reframe, Retouch, Stabilize, Effect object, Layer adjust.
**Still not redesigned (34 routes):** Speed, Volume, Opacity (header only), Transition, Packages, Adjust, Quality, Text, Stickers, Animation, Sound FX, Transcript, Draw, Tracking, Captions, Lyrics, Speech, Remove BG, Chroma, Blend, Mask, Basic, Custom cutout, Shape style, Caption templates, Collage, Sounds, Camera tracking, Body effects, Graphs, Layer, Enter captions, Batch edit, Beats. Those marked "In Progress" have some shared-component or tab changes only.
Timeline zoom buttons now sit in their own 40dp strip under the tracks instead of over them (the track area is 40dp shorter). Timeline ruler, track headers, track lock/mute, dock selected state, Home/Projects/Search structure: not redone.

## Update
See `REMAINING_WORK.md` for the current list (19/48 routes SOURCE COMPLETE).
