# BlossomTune

> Pemutar musik Android: file lokal dan streaming YouTube Music.

| | |
|---|---|
| Package | `dev.meowlabs.blossomtune` |
| Versi | `0.5.7` (`versionCode` 12) |
| Lisensi | GPL-3.0 (lihat [`LICENSE`](LICENSE) dan [`NOTICE.md`](NOTICE.md)) |
| Min SDK | 26 (Android 8.0) |
| Compile / Target SDK | 36 |
| Stack | Kotlin 2.3, Jetpack Compose, Media3, Room 2.8, Hilt, KSP |
| Rilis | [GitHub Releases](https://github.com/buanadahbisagorengnuggetsendiri/BlossomTune/releases) |
| Compiler | GitHub Actions |

## Fitur

- Putar musik dari penyimpanan lokal dan dari YouTube Music.
- Lirik dari beberapa sumber, termasuk lirik terjemahan dan romanisasi.
- Unduhan offline, antrean, playlist, dan pemutaran latar lewat Media3.
- Equalizer dan efek audio.
- Widget layar utama.
- Pemeriksaan pembaruan dari dalam app, membaca `changelog.json` pada aset rilis.

## Build

Build tidak dijalankan di mesin pengembangan proyek ini (host bersama, RAM
kecil, Gradle pernah mematikan proses lain). Compiler-nya GitHub Actions:

- **Build** — `:app:lintUniversalFossDebug`, unit test app dan modul, APK debug
  (`assembleUniversalFossDebug`), APK rilis (`assembleUniversalFossRelease`),
  plus pemeriksaan "bukan debuggable" dan verifikasi tanda tangan.
- **Release** — dipicu tag `v0.5.7`: memeriksa tag == `versionName` di
  `app/build.gradle.kts`, membangun APK rilis, lalu menerbitkan GitHub Release
  berisi APK, checksum `sha256`, dan `changelog.json`. Berkas terakhir itu yang
  dibaca pemeriksa pembaruan di app.

```bash
git tag v0.5.7 && git push origin v0.5.7
```

Varian build: dua dimensi flavor (`abi` × `variant`), jadi nama task-nya
panjang — CI memakai `universalFossDebug` dan `universalFossRelease`. Tanda
tangan rilis memakai kunci sendiri bila `STORE_PASSWORD`, `KEY_ALIAS`,
`KEY_PASSWORD`, dan `keystore/release.keystore` tersedia; kalau tidak,
`app/build.gradle.kts` jatuh ke debug keystore.

## Lisensi

GPL-3.0. Distribusi ulang wajib tetap GPL-3.0, menyertakan kode sumber, dan
mempertahankan pemberitahuan hak cipta pada berkas terkait. Daftar komponen,
pemegang hak cipta, dan rincian kewajibannya ada di [`NOTICE.md`](NOTICE.md).
