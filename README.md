# BlossomTune

> Pemutar musik Android. Dibangun dari basis [Musicfy](https://github.com/realidkroo/musicfy), di-rebrand jadi aplikasi sendiri.

| | |
|---|---|
| Package | `id.meowlabs.blossomtune` |
| Versi | `0.1.0` (`versionCode` 1) |
| Lisensi | GPL-3.0 (lihat [`LICENSE`](LICENSE) dan [`NOTICE.md`](NOTICE.md)) |
| Min SDK | 26 (Android 8.0) |
| Compile / Target SDK | 36 |
| Stack | Kotlin 2.3, Jetpack Compose, Media3, Room 2.8, Hilt, KSP |
| Basis | Musicfy `8.0.0 build#1150` (GPL-3.0) |
| Status | Rebrand tahap awal. Kode, paket, nama, CI, dan struktur rilis sudah milik sendiri; ikon dan palet **belum** diganti. Belum pernah diuji di perangkat. |

## Keadaan sekarang

Sudah dikerjakan:

- Nama paket `com.example.musicfy` → `id.meowlabs.blossomtune`, termasuk kode,
  resource, manifest, skema Room, dan ketiga flavor (`foss`, `gms`, `fdroid`).
- Nama app `BlossomTune`, nama proyek Gradle `blossomtune`, versi direset ke `0.1.0`.
- Berkas khas basis dihapus: `build.sh`, `crowdin.yml`, `fastlane/`, `assets/`,
  `Roadmap.md`, `docs/`, `Frame 201.png`, serta workflow `build_pr.yml` dan `stale.yml`.
- CI sendiri: lint, unit test, APK debug, APK rilis, plus job rilis berbasis tag.
- Tautan akun basis dibersihkan: Instagram dan kanal donasi (Ko-fi, Sociabuzz,
  Buy Me a Coffee) dihapus; layar Tentang/Dukungan/Donasi menunjuk
  `buanadahbisagorengnuggetsendiri/blossomtune`.
- Atribusi dan catatan lisensi ditulis ulang di [`NOTICE.md`](NOTICE.md).

Belum dikerjakan:

- Ikon aplikasi, splash, dan palet masih milik basis.
- Avatar di kartu Tentang masih foto pengembang basis.
- Belum ada rilis. Belum ada build yang pernah jalan.

Fitur yang ikut dari basis — Audio Lab, lirik multi-sumber, impor/ekspor,
Cast, equalizer — semuanya masih kode basis, bukan hasil tulis ulang. Lihat
`app/src/main/kotlin/com/meowlabs/blossomtune/` dan `providers/`.

## Build

Build tidak dijalankan di mesin pengembangan proyek ini (host bersama, RAM
kecil — Gradle pernah mematikan proses lain). Compiler-nya GitHub Actions:

- **Build** — `:app:lintUniversalFossDebug`, unit test app dan modul, APK debug
  (`assembleUniversalFossDebug`), APK rilis (`assembleUniversalFossRelease`)
  beserta pemeriksaan "bukan debuggable" dan verifikasi tanda tangan.
- **Release** — dipicu tag `0.1.0` atau `v0.1.0`: memeriksa tag == `versionName`
  di `app/build.gradle.kts`, membangun APK rilis, lalu menerbitkan GitHub
  Release berisi APK, checksum, dan `changelog.json`. Berkas terakhir itu yang
  dibaca pemeriksa pembaruan di app.

```bash
git tag 0.1.0 && git push origin 0.1.0
```

Varian build: dua dimensi flavor (`abi` × `variant`), jadi nama task-nya
panjang — CI memakai `universalFossDebug` dan `universalFossRelease`. Tanda
tangan rilis memakai kunci sendiri bila `STORE_PASSWORD`, `KEY_ALIAS`,
`KEY_PASSWORD`, dan `keystore/release.keystore` tersedia; kalau tidak,
`app/build.gradle.kts` jatuh ke debug keystore.

## Lisensi

GPL-3.0. Projek ini turunan Musicfy, jadi distribusi ulang wajib tetap GPL-3.0,
menyertakan kode sumber, dan mempertahankan atribusi. Rinciannya di
[`NOTICE.md`](NOTICE.md).
