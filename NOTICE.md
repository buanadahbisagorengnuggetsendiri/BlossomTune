# Pemberitahuan lisensi &amp; atribusi

Stereonism adalah perangkat lunak bebas berlisensi **GNU General Public License
versi 3** (GPL-3.0). Teks lengkapnya ada di [`LICENSE`](LICENSE).

## Kenapa GPL-3.0

Stereonism dibangun dari [Musicfy](https://github.com/realidkroo/musicfy)
(© realidkroo, GPL-3.0), yang sendirinya turunan dari
[musicfy-app/musicfy](https://github.com/musicfy-app/musicfy) dan keluarga
aplikasi pemutar musik InnerTune/ViVi. Kode GPL-3.0 yang digabungkan ke sebuah
program mewajibkan program gabungannya ikut GPL-3.0 (GPL-3.0 §5), jadi seluruh
Stereonism memakai lisensi yang sama.

## Kewajiban bagi yang mendistribusikan ulang

- Sertakan teks lisensi ([`LICENSE`](LICENSE)).
- Sertakan kode sumber lengkap, atau cara mendapatkannya.
- Pertahankan pemberitahuan hak cipta dan atribusi pada berkas terkait.
- Distribusi ulang tetap GPL-3.0 — tidak boleh dilisensikan ulang secara tertutup,
  dan tidak boleh ditambahi syarat baru (GPL-3.0 §7).

## Perubahan dari basis

| Hal | Basis (Musicfy) | Stereonism |
|---|---|---|
| `applicationId` / `namespace` | `com.example.musicfy` | `com.meowlabs.stereonism` |
| Nama tampilan | musicfy | Stereonism |
| Versi | `8.0.0 build#1150` (`versionCode` 71) | `0.1.0` (`versionCode` 1) |
| Nama proyek Gradle | `musicfy` | `stereonism` |
| CI | `build.yml`, `build_pr.yml`, `stale.yml` | `build.yml`, `release.yml` |
| Berkas yang dihapus | — | `build.sh`, `crowdin.yml`, `fastlane/`, `assets/`, `Roadmap.md`, `docs/`, `Frame 201.png` |

Perubahan nama paket berarti versi ini **tidak bisa dipasang menimpa** Musicfy
yang sudah terpasang: keduanya duduk berdampingan, dengan database sendiri.

## Ketergantungan pihak ketiga

Daftar lengkap ada di `gradle/libs.versions.toml`. Yang utama:

| Komponen | Lisensi |
|---|---|
| NewPipe/InnerTube providers (`providers/`) | GPL-3.0 |
| Media3 / ExoPlayer, Room, Hilt, kotlinx.serialization, Coil | Apache-2.0 |
| KizzyRPC (`providers/kizzy`) | lihat berkas di dalam modulnya |

## Layanan luar saat runtime

App menghubungi layanan pihak ketiga untuk lirik, artwork, dan metadata
(LRCLIB, Kugou, SimpMusic, YouTube/YouTube Music, Last.fm, Shazam, dan
sejenisnya). Masing-masing punya ketentuan pemakaiannya sendiri. Stereonism
tidak berafiliasi dengan, disponsori oleh, atau didukung oleh layanan mana pun
tersebut.

Tautan akun basis sudah dibersihkan: kanal donasi (Ko-fi, Sociabuzz, Buy Me a
Coffee) dan tautan Instagram dihapus, tautan GitHub dalam app menunjuk
`buanadahbisagorengnuggetsendiri/stereonism`. Yang **belum** dikerjakan: avatar
bawaan (`frame_51_3`, foto pengembang basis) masih dipakai di kartu Tentang,
dan ikon aplikasi, splash, serta palet masih milik basis.
