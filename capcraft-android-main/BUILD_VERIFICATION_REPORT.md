# Build verification report

**Result: nothing could be built, compiled, linted or tested here. No APK exists. No screenshots exist.**

## Commands attempted
| Command | Result |
|---|---|
| `cd src/crates/capcraft-android/android && ./gradlew --offline assembleDebug` | FAILED before compiling: the wrapper tried to download Gradle (`services.gradle.org`) and the sandbox has no network (`java.net` exception from `org.gradle.wrapper.Install.forceFetch`) |

## Commands that could not be run, and why
| Needed | Missing |
|---|---|
| `./gradlew assembleDebug`, `assembleRelease -Pcapcraft.abis=arm64-v8a`, `lint`, `testDebugUnitTest` | Gradle distribution (network), Android SDK (`ANDROID_HOME` unset), NDK 29.0.14206865 |
| `cargo ndk ...`, `cargo test` (workspace in `src/`) | Rust toolchain (`cargo`/`rustc` not installed), cargo-ndk, FFmpeg/sherpa-onnx native builds (`src/scripts/*.sh`) |
| Emulator / device run | none available |

## Checks that did run (static, text-level)
- All Android XML resources parse. Every `R.drawable`/`R.color`/`@mipmap`/`@style` reference resolves to a file.
- Every `Palette`/`Type`/`Space`/`NexShape` member referenced from Kotlin exists.
- Brace and parenthesis balance of every Kotlin file: balanced except `TextPanels.kt`, which also trips this crude counter in the original source.
- No original file was deleted. Functions present in the original but absent now: `RetouchSlider` (replaced by `RetouchCard`), `RecordTool` (by `RecordOption`), `SpeechPanel.Chip` (by `NexVoraChip`); no references to them remain.
- Panel routing in `PanelHost` was not changed in structure; ViewModel logic was not changed apart from the `.srt` file-name prefix.

## Known compile-risk files (never compiled)
`Timeline.kt`, `OtherPanels.kt`, `CropPage.kt`, `EffectLayer.kt`, `PanelHost.kt`, `Pickers.kt`, `SpeechPanel.kt`, `SoundFx.kt`, `NexVora.kt`, `Welcome.kt`, `MainActivity.kt`, `HomeScreen.kt`. Likely errors: a missing import, a wrong parameter name, a smart-cast that the compiler refuses.

## Next steps (outside this sandbox)
1. JDK 17, Android SDK (compileSdk 37), NDK 29.0.14206865, Rust per `src/rust-toolchain.toml`, cargo-ndk; build FFmpeg and sherpa-onnx with `src/scripts/` as `.github/workflows/release.yml` does.
2. `cd src/crates/capcraft-android/android && ./gradlew assembleDebug`; fix compile errors, then `./gradlew lint testDebugUnitTest`.
3. Install on a device; open each panel; send screenshots.

## CI and repository references
- `.github/workflows/release.yml`: the release APK is now copied to `dist/NexVora-Motion-Studio-$VERSION.apk` (was `capcraft-android-$VERSION.apk`). The workflow still reads `capcraft.version` from `gradle.properties` and passes `-Pcapcraft.abis`/`-Pcapcraft.version`; those Gradle property names are internal and were left alone. Not run.
- About screen: source link `github.com/rahman2008-svg/NexVora-Motion-Studio-Final-Complete` (given by the developer; not verified) and the upstream CapCraft link.
- Signing: no keystore or password is in the source; the workflow decodes a release key from CI secrets.
