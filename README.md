# BlossomTune

An Android music player for YouTube Music, built as a self-branded fork of
[Mochy Music](https://github.com/realidkroo/Mochy-Music) (GPL-3.0).

- Package: `dev.meowlabs.blossomtune`
- Kotlin + Jetpack Compose + Media3
- License: GPL-3.0 — see [`LICENSE`](LICENSE) and [`NOTICE.md`](NOTICE.md)

## Lineage

BlossomTune ← Mochy (realidkroo) ← ViviMusic (vivizzz007Z) ← Metrolist
(Mostafa Alagamy). YouTube access through the InnertubeX providers. All GPL-3.0
obligations of those projects carry over; the code stays open.

## Install

Grab the APK from the [latest release](https://github.com/buanadahbisagorengnuggetsendiri/BlossomTune/releases/latest)
and install it over an older BlossomTune. The package id is unchanged since
0.5.x, so library, playlists and offline downloads survive the upgrade.

## Build

Requires JDK 21 and the Android SDK (compileSdk 36).

```bash
./gradlew :app:assembleUniversalFossDebug     # debug APK, all ABIs
./gradlew :app:assembleUniversalFossRelease   # release APK (needs a keystore)
```

Flavors are two-dimensional: `foss | fdroid | gms` × `universal | arm64 | …`.
CI builds `universalFoss`; the other variants are untested here.

Release signing reads `app/keystore/release.keystore` (or `STORE_PASSWORD`,
`KEY_ALIAS`, `KEY_PASSWORD` env vars); without it Gradle falls back to the debug
keystore, and the release workflow rejects such an APK.

## Releases

Published as GitHub Releases on
[`buanadahbisagorengnuggetsendiri/BlossomTune`](https://github.com/buanadahbisagorengnuggetsendiri/BlossomTune).
The in-app updater reads that release list.

## What's changed from the base

Brand, package, CI, repository. The 0.5.x light-theme palette and ink fixes are
**not** ported to the Mochy 8.4.2 codebase yet — see `NOTICE.md`.

## Status

0.6.0 is the first release on the Mochy base. Not verified on a physical device
yet; launcher icon, splash and palette are still the base's.
