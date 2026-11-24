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

Never commit your keys. Libre.fm works without any key.

### If the build fails
- `Unresolved reference` or Hilt errors that make no sense: delete the `build/` folders and `.gradle/` and build again.
- Stuck or weird Gradle: `./build.sh -k`, then try again.
- Wrong Java: make sure Gradle uses JDK 21 (Android Studio → Settings → Build Tools → Gradle → Gradle JDK).
- Still stuck? Open an [issue](https://github.com/buanadahbisagorengnuggetsendiri/BlossomTune/issues) with the error text.

Want to send a fix? Read [CONTRIBUTING](.github/CONTRIBUTING.md) first.

---
## How to build module?
im working on it wait

---
## Wants to help? bug report? report it on issues!

Or read contribute.md there

---
## Disclaimer

BlossomTune is an independent, unofficial project made for personal and educational use. It is provided as is, without warranty of any kind.

- BlossomTune is a fully open source project that is created purely for educationa and personal use. thats why, we dont sell this software, or monetize it. we do not have any intent to add commercial values in the future. thanks!
- And were deeply respect the hardwork of artist musicians ajd contwnt creators. without them there is also no blossomtune: i strongly suggest that you, as user, subscribe to Apple music or youtube premium, to support the creators you listen. I do not intend to hurt creator with this app.
- It is **not affiliated with, endorsed by, or sponsored by** YouTube, YouTube Music, Google, Spotify, Apple, Last.fm, Shazam, Discord or any other service it can connect to. All names, logos and brands belong to their respective owners.
- BlossomTune does not host, store or distribute any music. Everything is fetched from third-party services when you ask for it, and those services can change or stop working at any time.
- You are responsible for how you use the app, including the terms of service of the accounts and services you connect and the copyright laws where you live.
- Importing from other services (Spotify, etc.) uses your own account. Don't share your login data or tokens with anyone.
- Importing music MAY need your own account with its password. WE'RE NEVER see your password.

---


<!-- mochy-credit:start · f208ad37 -->
## Forking Mochy

Forks are welcome, that's what open source is for! Just two asks:

- **Keep the credit.** If you rename it, please keep credits of this app, the other project that helped mochy possible.
- **Keep it free.** Mochy is not monetized and never will be. Please don't put features behind a paywall. A donation page is fine.

<!-- mochy-credit:end -->

---

## These incredible project helped mochy a lot! check them out too :3

- **ViviMusic** by [vivizzz007Z](https://github.com/vivizzz007Z) )[Their repo](https://github.com/vivizzz007/vivi-music)) -- For the base app. This app is a fork from this project.
- **Metrolist** by [Mostafa Alagamy](https://github.com/mostafaalagamy) ([Repository](https://github.com/mostafaalagamy/Metrolist)) -- For the base framework.
- **SimpMusic** by [maxrave-dev](https://github.com/maxrave-dev) ([Repository](https://github.com/maxrave-dev/SimpMusic)) -- For the lyrics API provider.
- **Archivetune** by [Rukamori](https://github.com/rukamori) ([Repository](https://github.com/rukamori/ArchiveTune)) -- For design and architectural inspiration.
- **RiMusic / InnerTube** by [fast4x](https://github.com/fast4x) ([Repository](https://github.com/fast4x/RiMusic)) -- For YouTube Music InnerTube API service wrapper.
- **LRCLib** by [tranxuanthang](https://github.com/tranxuanthang) ([Repository](https://github.com/tranxuanthang/lrclib) / [Website](https://lrclib.net/)) -- For synchronized LRC lyrics provider integration.
- **Monochrome Music** by [monochrome-music](https://github.com/monochrome-music) ([Repository](https://github.com/monochrome-music/monochrome) / ([Website](https://monochrome.st)) -- Flac streams, visit their web, very cool one.
- **Auto Eq** ([Web](https://autoeq.app)) -- The equalizer database
- **lrc.red** ([Web](https://lrc.red)) -- Word-timed lyrics for millions of songs, free for everyone
- **InnertubeX** by [nyxiereal](https://github.com/nyxiereal) [Their repo](https://github.com/metrolistGroup/innertubex) For The Youtube stream extration lib

---

## Open source APIs mochy talks to!!

These are the open source apis and server we get gets its lyrics, streams and data from. They r free for everyone because of these good people, so go give them a star!

- **LyricsPlus** by [Ibra Al Tabian](https://github.com/ibratabian17) ([repos](https://github.com/ibratabian17/LyricsPlus)) s
- **Better Lyrics API** by [Boidushya](https://github.com/boidushya) ([repos](https://github.com/better-lyrics/api)) 
- **LRCLIB** by [tranxuanthang](https://github.com/tranxuanthang) ([repos](https://github.com/tranxuanthang/lrclib) / [Website](https://lrclib.net/)) 
- **SimpMusic Lyrics** by [maxrave-dev](https://github.com/maxrave-dev) ([repos](https://github.com/maxrave-dev/SimpMusic))
- **Unison Lyrics** ([repos](https://github.com/better-lyrics/unison))

---
## Star History

<a href="https://www.star-history.com/?repos=realidkroo%2Fmochy-music&type=date&legend=top-left">
 <picture>
   <source media="(prefers-color-scheme: dark)" srcset="https://api.star-history.com/chart?repos=realidkroo/mochy-music&type=date&theme=dark&legend=top-left" />
   <source media="(prefers-color-scheme: light)" srcset="https://api.star-history.com/chart?repos=realidkroo/mochy-music&type=date&legend=top-left" />
   <img alt="Star History Chart" src="https://api.star-history.com/chart?repos=realidkroo/mochy-music&type=date&legend=top-left" />
 </picture>
</a>

<div align="center">
  <sub>star the repo plssss apreciate it!!!!!</sub>
</div>

0.6.0 is the first release on the Mochy base. Not verified on a physical device
yet; launcher icon, splash and palette are still the base's.
