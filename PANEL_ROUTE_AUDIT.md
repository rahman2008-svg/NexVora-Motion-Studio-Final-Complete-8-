# Panel route audit

Each Panel route in the `when` of `PanelHost` is listed separately. **No route is counted as Fully redesigned**: none has had its whole inner layout replaced. "Partially redesigned" means something in its layout or components really changed. "(direct calls)" means the panel file calls a shared component I reworked; I did not check per tab whether that is the whole panel or one tab. Nothing was tested on a device.

Routes found: 48.

| Route | Composable | File | Status | What changed |
|---|---|---|---|---|
| `Panel.Speed` | `SpeedPanel` | `PanelHost.kt` | Layout redesigned (source only; not compiled or run) | Speed tab: numerals, before/after duration, preset pills, ruler. Curve tab: presets, drag editor, Add/Delete beat, Reset (clears the curve), beat readout, mean-speed duration; same setClipSpeedCurve command with undo gestures |
| `Panel.Volume` | `VolumePanel` | `PanelHost.kt` | Layout redesigned (source only; not compiled or run) | Level card (state word, large percent, slider with 0/100/1000 notches named), Sound clean-up section with card switch rows; same volume patch and filter toggles |
| `Panel.Opacity` | `OpacityPanel` | `PanelHost.kt` | Layout redesigned (source only; not compiled or run) | Header with numerals, slider, preset pills (0/25/50/75/100), Reset, link to Fade; same opacity patch with undo gestures. No opacity keyframes (not found in the engine API used here) |
| `Panel.Fade` | `FadePanel` | `PanelHost.kt` | Layout redesigned (source only; not compiled or run) | Two side-by-side cards (Fade in / Fade out), each with its length in large numerals, a ramp drawing to scale, and its slider. Same patch keys fadeIn/fadeOut |
| `Panel.Ratio` | `RatioPanel` | `PanelHost.kt` | Layout redesigned (source only; not compiled or run) | Stage showing the current frame at true proportions, size readout, and ratio pills; same setCanvas/collage re-layout command |
| `Panel.Transition` | `TransitionPanel` | `PanelHost.kt` | Partially redesigned | Browser kept (pills, tiles); new favourites chip, empty state, Apply to all pill and a Duration bar with numerals |
| `Panel.Packages` | `PackagesPanel` | `Pickers.kt` | Partially redesigned | Effects/filter picker: pills, 12dp tiles with cyan ring, search field, favourites chip, empty state |
| `Panel.Adjust` | `AdjustPanel` | `Pickers.kt` | Layout redesigned (source only; not compiled or run) | Adjust tab: Smart/Customize pills, rounded tool tiles, name and numeric header over the slider, Reset / Apply to all / Save preset row. (The Presets tab is also rebuilt; Filters and Video quality tabs are separate) |
| `Panel.Quality` | `LookPanel` | `Pickers.kt` | Partially redesigned | Opens the Look panel on its Video quality tab: unchanged layout |
| `Panel.Text` | `TextPanel` | `TextPanels.kt` | Partially redesigned (direct calls) | Its own body calls the reworked shared Tabs (helpers it calls were not followed); inner layout otherwise original |
| `Panel.Stickers` | `StickersPanel` | `Stickers.kt` | Partially redesigned (direct calls) | Its own body calls the reworked shared ChoiceGrid, SearchField (helpers it calls were not followed); inner layout otherwise original |
| `Panel.Animation` | `AnimationPanel` | `OtherPanels.kt` | Color only | Palette: grey literals remapped, selection ring cyan, Reset cyan where present; layout original |
| `Panel.Crop` | `CropPanel` | `CropPage.kt` | Layout redesigned (source only; not compiled or run) | Control area rebuilt: Rotate header with value and Reset, ruler, aspect-ratio tiles; the crop canvas and its gestures are unchanged |
| `Panel.Background` | `BackgroundPanel` | `OtherPanels.kt` | Layout redesigned (source only; not compiled or run) | Current-background header (swatch + name), Apply to all as a button, choices as new tiles/swatches; same session commands |
| `Panel.Voiceover` | `VoiceoverPanel` | `OtherPanels.kt` | Layout redesigned (source only; not compiled or run) | Recorder column (status/timer, ringed record button, hint) beside option rows (Teleprompter, Enhance voice, Voice changer with pill chooser); recorder logic untouched |
| `Panel.SoundFx` | `SoundFxPanel` | `SoundFx.kt` | Layout redesigned (source only; not compiled or run) | One card per sound with play badge, category and length, add button; empty state; same preview/placeFx calls |
| `Panel.Transcript` | `TranscriptPanel` | `Transcript.kt` | Color only | Palette: grey literals remapped, selection ring cyan, Reset cyan where present; layout original |
| `Panel.Draw` | `DrawPanel` | `Draw.kt` | Color only | Palette: grey literals remapped, selection ring cyan, Reset cyan where present; layout original |
| `Panel.Tracking` | `TrackingPanel` | `Tracking.kt` | Color only | Palette: grey literals remapped, selection ring cyan, Reset cyan where present; layout original |
| `Panel.Enhance` | `EnhancePanel` | `Enhance.kt` | Layout redesigned (source only; not compiled or run) | Header with state text, primary Enhance/Show original action, progress card with Stop; same enhance/cancel calls |
| `Panel.Captions` | `AutoCaptionsSheet` | `AutoCaptions.kt` | Color only | Palette: grey literals remapped, selection ring cyan, Reset cyan where present; layout original |
| `Panel.Lyrics` | `AutoCaptionsSheet` | `AutoCaptions.kt` | Color only | Palette: grey literals remapped, selection ring cyan, Reset cyan where present; layout original |
| `Panel.Speech` | `SpeechPanel` | `SpeechPanel.kt` | Layout redesigned (source only; not compiled or run) | Words field with counter, sectioned model and voice pills, shared progress card with Cancel, primary Generate/Download; same speech calls |
| `Panel.RemoveBg` | `RemoveBgPanel` | `RemoveBgPanel.kt` | Color only | Palette: grey literals remapped, selection ring cyan, Reset cyan where present; layout original |
| `Panel.Chroma` | `ChromaPanel` | `RemoveBgPanel.kt` | Color only | Palette: grey literals remapped, selection ring cyan, Reset cyan where present; layout original |
| `Panel.Blend` | `BlendPanel` | `PanelHost.kt` | Layout redesigned (source only; not compiled or run) | Mode name and strength in the header, sample tiles, Strength slider; same blend/opacity patches |
| `Panel.Mask` | `MaskPanel` | `MaskPanel.kt` | Color only | Palette: grey literals remapped, selection ring cyan, Reset cyan where present; layout original |
| `Panel.Basic` | `BasicPanel` | `BasicPanel.kt` | Layout redesigned (source only; not compiled or run) | Position, Zoom, Rotate rulers with numerals, plus a Flip tab (Mirror, Rotate 90° through the existing VM steps); Reset as a button; keyed clips still set at the key under the playhead |
| `Panel.CustomCutout` | `CustomCutoutPanel` | `CustomCutout.kt` | Color only | Palette: grey literals remapped, selection ring cyan, Reset cyan where present; layout original |
| `Panel.ShapeStyle` | `ShapeStylePanel` | `ShapeStyle.kt` | Layout redesigned (source only; not compiled or run) | Fill (colour, new opacity slider through the #rrggbbaa fill), Outline (colour, thickness), new Size tab; only fields the shape model has (no corner radius field exists) |
| `Panel.CaptionTemplates` | `CaptionTemplatesPanel` | `AutoCaptions.kt` | Color only | Palette: grey literals remapped, selection ring cyan, Reset cyan where present; layout original |
| `Panel.Collage` | `CollagePanel` | `Collage.kt` | Color only | Palette: grey literals remapped, selection ring cyan, Reset cyan where present; layout original |
| `Panel.Relight` | `RelightPanel` | `Relight.kt` | Layout redesigned (source only; not compiled or run) | Pill row (None + categories), strength header with light name and value, 12dp sample tiles; same relight chain writes |
| `Panel.Sounds` | `SoundsPage` | `SoundsPage.kt` | Color only | Palette: grey literals remapped, selection ring cyan, Reset cyan where present; layout original |
| `Panel.CameraTracking` | `CameraTrackingPanel` | `Tracking.kt` | Color only | Palette: grey literals remapped, selection ring cyan, Reset cyan where present; layout original |
| `Panel.MotionBlur` | `MotionBlurPanel` | `MotionBlur.kt` | Layout redesigned (source only; not compiled or run) | Sliders in one card with numeric values; Direction and Times as visible pills instead of menus; progress card; same run/cancel calls |
| `Panel.BodyEffects` | `BodyEffectsPanel` | `BodyEffects.kt` | Layout redesigned (source only; not compiled or run) | Eleven looks as tiles, Reset (back to None), shared progress card with Stop; the engine call has no strength parameter, so no intensity control |
| `Panel.IsolateVoice` | `IsolateVoicePanel` | `IsolateVoice.kt` | Layout redesigned (source only; not compiled or run) | Original / Voice only segmented switch, progress card with Stop; same isolateVoice/cancel calls |
| `Panel.AutoReframe` | `AutoReframePanel` | `AutoReframe.kt` | Layout redesigned (source only; not compiled or run) | Output-shape stage with pills, Reframe action or progress card; same setCanvas/autoReframe calls |
| `Panel.Retouch` | `RetouchPanel` | `Retouch.kt` | Layout redesigned (source only; not compiled or run) | Two icon cards (Smooth, Brighten) with hint, value and slider; same capcraft.retouch chain writes |
| `Panel.Graphs` | `GraphsPanel` | `GraphsPanel.kt` | Layout redesigned (source only; not compiled or run) | Nine ease presets plus a Custom handle editor with numerals; Reset to a straight line; colours on the palette; same setClipKey ease writes |
| `Panel.Layer` | `LayerPanel` | `LayerPanel.kt` | Color only | Palette: grey literals remapped, selection ring cyan, Reset cyan where present; layout original |
| `Panel.EffectObject` | `EffectObjectPanel` | `EffectLayer.kt` | Layout redesigned (source only; not compiled or run) | Two selectable option rows (All videos / Main video) with thumbnail, description and a radio mark; same setReach edit |
| `Panel.LayerAdjust` | `LayerAdjustPanel` | `EffectLayer.kt` | Layout redesigned (source only; not compiled or run) | One card per effect setting with name, numeric value and slider; Reset as a button; same setKnob writes |
| `Panel.EnterCaptions` | `EnterCaptionsPage` | `EnterCaptions.kt` | Color only | Palette: grey literals remapped, selection ring cyan, Reset cyan where present; layout original |
| `Panel.BatchEdit` | `BatchEditPanel` | `BatchEditPanel.kt` | Color only | Palette: grey literals remapped, selection ring cyan, Reset cyan where present; layout original |
| `Panel.Beats` | `BeatsPanel` | `BeatsPanel.kt` | Color only | Palette: grey literals remapped, selection ring cyan, Reset cyan where present; layout original |
| `Panel.Stabilize` | `StabilizePanel` | `StabilizePanel.kt` | Layout redesigned (source only; not compiled or run) | Strength header with named level, stop slider with highlighted label, progress card with Cancel; same stabilize/cancel calls |

Totals: Layout redesigned (source only; not compiled or run): 25, Partially redesigned: 3, Partially redesigned (direct calls): 2, Color only: 18.

## Tabs and sections inside panels

| Panel | Tabs | Status |
|---|---|---|
| Basic | Position, Zoom, Rotate | Shared pill tabs; rulers unchanged |
| Shape style | Fill, Outline | Shared pill tabs; controls unchanged |
| Filters (Look) | tab row via `startTab` | Pills and tiles; the Presets and Adjust tab contents keep original layouts |
| Effects / filter packages | categories | Pills, tiles, search, favourites |
| Text | style tabs | Active tab text cyan; contents original |
| Audio effects, Draw, Mask, Relight | tab rows | Active tab text cyan; contents original |
| Background (Color, Image, Blur) | 3 tabs | Not individually verified |

## Not done
Every route marked Color only or Partially redesigned keeps most of its original inner layout.
