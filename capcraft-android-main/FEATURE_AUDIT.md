# Feature audit

**Read this first.** This is an inventory from reading the source, not a runtime audit. No feature was run, no callback was traced end to end, and no bug hunt was performed, so "no bugs found" would be meaningless: none were looked for beyond static reference checks. Every control in the original code is still wired to the same callbacks (my edits changed drawing and shared wrappers, and one dock/export layout each), but that is an intention checked by reading diffs, not a test result.

Sources: dock tool ids in `Dock.kt` (91), panels in `ui/editor/panels` (about 40 files), Home shortcuts in `HomeScreen.kt`.

## Home shortcuts (10)
AutoCut, Retouch, Photo tools (Collage, Smart lighting, Frame capture), Shoot and record, Auto enhance, Cover maker, Auto captions, Remove background, Adjust speed, Audio tools (Record and others). Status: present, callbacks untouched, restyled.

## Dock tools (ids found in the source)
addoverlay, addtext, adjust, adjustlayer, advanced, animation, audio, autocaptions, autolyrics, autoreframe, autoremove, autosplit, background, basic, beats, bgblur, bgcolor, bgimage, blend, bodyeffects, cameratracking, captions, captiontemplates, changebg, chroma, collage, crop, curve, customremove, delete, deselect, draw, duplicate, edit, editcaptions, effects, enhance, enhancequality, entercaptions, exportcaptions, extract, extracted, fade, filterlayer, filters, freeze, graphs, importcaptions, isolate, lasting, layer, mask, mirror, motionblur, multiselect, noise, normal, object, opacity, overlay, ratio, record, recover, relight, removebg, replace, retouch, reverse, rotate, soundfx, sounds, speed, split, stabilize, stickers, style, text, texteffects, texttemplate, texttoaudio, tomain, tooverlay, tracking, transcript, transform, tts, unlink, videoeffects, voice, voiceover, volume.
Status: all still present, same ids, order and actions (only `ToolItem` drawing changed). None removed, renamed or disabled by me.

## Not found in this application (not invented)
No onboarding, no grid/list switch or sorting control on Projects that I identified, no quality presets in export, no per-track mute/visibility buttons, no separate project-settings page. These were requested in generic terms; they do not exist in the original and were not added.

## Per-feature verification columns the brief asked for
UI present: yes for all of the above (by reading). Callback connected / updates state / engine / persistence / error handling: **not verified**. Works with navigation: **not verified**.

## Bugs found and fixed
One in my own code: the About screen's copyright sign was written with a doubled backslash and would have printed `\u00A9`; fixed. No bugs in the original were investigated.
