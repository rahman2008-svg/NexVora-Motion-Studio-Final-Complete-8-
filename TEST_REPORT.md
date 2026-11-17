# Test report

Environment: Linux sandbox with OpenJDK 21 and Python/PIL. No Android SDK, NDK, Gradle distribution, Rust/cargo, emulator or network.

| Check | Result |
|---|---|
| Project root and structure inspected | Done |
| License inspected | AGPL-3.0-or-later confirmed (`LICENSE`, `NOTICE`, Cargo `license`) |
| All 11 Android XML resources parse as well-formed XML | Pass |
| Resource references (`@drawable`, `@mipmap`, `@color`, theme name in manifest) resolve to files defined | Pass (static grep) |
| applicationId, FileProvider authority, `packageName + ".files"` consistent | Pass (static) |
| JNI symbol, UniFFI package, ProGuard keep rule, `NativeContext` package still agree | Pass (unchanged, static) |
| versionCode matches the build script's formula for 1.0.0 (1000099) | Pass (computed) |
| Residual user-facing "CapCraft" strings | Only intentional attribution remains |
| Kotlin compilation | **Not run** |
| Rust build / `cargo test` | **Not run** |
| Gradle configuration / unit tests / lint | **Not run** |
| Debug APK, release APK | **Not built. No APK exists.** |
| Launch, home, project loading, import, editor, export | **Not tested** (no device/emulator) |

The Kotlin edits are string/colour changes plus one rewritten composable (About) that uses only imports already in that file; still, treat them as uncompiled until you run a build.


## Phase 2 additions
| Check | Result |
|---|---|
| Android XML resources still parse | Pass |
| Rough brace/paren balance of all .kt files vs. baseline | Same as baseline (one file, TextPanels.kt, trips the crude checker both before and after; not caused by these edits) |
| Name clashes for new identifiers (`NexShape`, `Space`, `Elevated`, `NexVora*`) | None found by grep |
| Kotlin compilation, Compose rendering, any runtime or visual test | **Not run**. Treat the new `NexVora.kt` and the edited `Common.kt`, `Theme.kt`, `MainActivity.kt`, `PanelHost.kt` and `HomeScreen.kt` as uncompiled until built. |
| Screenshots | **None captured** |
| APKs | **None built** |


## Phase 3 additions
| Check | Result |
|---|---|
| Braces/parentheses of the edited files (`EditorScreen`, `ExportScreen`, `HomeScreen`, `MediaPicker`, `Common`, `NexVora`) balanced, same as baseline where one exists | Pass (crude static check) |
| Imports for identifiers used in the new code present or fully qualified | Checked by grep for the common ones; not compiler-verified |
| Sealed `Stage` handled exhaustively in the new export title `when` | Pass (read the three subtypes) |
| Kotlin compile, Compose rendering, layout on any screen size, runtime behaviour | **Not run** |
| Screenshots / visual review | **None** |
| APK | **None built** |


## Phase 4 additions
| Check | Result |
|---|---|
| Brace/parenthesis balance of `ExportSheet`, `Dock`, `Timeline`, `PanelHost` | Pass (crude static check) |
| Settings/licence rows and page header still receive the same callbacks | Pass (read) |
| Kotlin compile, any runtime test, screenshots, APK | **Not run / none** |


## Final pass: what actually ran
| Step | Result |
|---|---|
| `./gradlew --offline assembleDebug` in `src/crates/capcraft-android/android` | **Failed**: the wrapper tried to download Gradle 9.5.1 (`services.gradle.org`) and there is no network. No compilation happened. |
| Android SDK / NDK / Rust / cargo-ndk / emulator | **Not available** (no `ANDROID_HOME`, no `cargo`, `rustc`, `gradle`; only a JDK) |
| Unit tests, lint, Rust tests | **Not run** (need the above) |
| Token/resource reference check (script) | Pass: no unresolved `Palette`, `Type`, `Space`, `NexShape` members; resources used by Kotlin and the manifest exist |
| XML well-formedness | Pass |
| Brace balance of edited Kotlin | Pass (crude) |
| APK | **None built** |
| ZIP integrity | Pass (`testzip`) |
