# Pemberitahuan lisensi & atribusi

BlossomTune adalah perangkat lunak bebas berlisensi **GNU General Public License
versi 3** (GPL-3.0). Teks lengkapnya ada di [`LICENSE`](LICENSE).

## Kenapa GPL-3.0

BlossomTune dibangun dari [Mochy Music](https://github.com/realidkroo/Mochy-Music)
(© realidkroo, GPL-3.0). Mochy sendiri turunan dari **ViviMusic**
(© vivizzz007Z), yang berbasis **Metrolist** (© Mostafa Alagamy) dan keluarga
aplikasi InnerTune. Kode GPL-3.0 yang digabungkan ke sebuah program mewajibkan
program gabungannya ikut GPL-3.0 (GPL-3.0 §5), jadi seluruh BlossomTune memakai
lisensi yang sama.

Repo Mochy sebelumnya bernama `realidkroo/musicfy` (kini diarsipkan). Fork ini
dulu ngikut `musicfy`; sejak versi 0.6.0 basisnya pindah ke Mochy 8.4.2.

## Kewajiban bagi yang mendistribusikan ulang

- Sertakan teks lisensi ([`LICENSE`](LICENSE)).
- Sertakan kode sumber lengkap, atau cara mendapatkannya.
- Pertahankan pemberitahuan hak cipta dan atribusi pada berkas terkait.
- Distribusi ulang tetap GPL-3.0 — tidak boleh dilisensikan ulang secara tertutup,
  dan tidak boleh ditambahi syarat baru (GPL-3.0 §7).

## Atribusi pihak ketiga di dalam kode

| Proyek | Penulis | Peran |
|---|---|---|
| [Mochy Music](https://github.com/realidkroo/Mochy-Music) | realidkroo | basis langsung (v8.4.2) |
| [ViviMusic](https://github.com/vivizzz007/vivi-music) | vivizzz007Z | basis aplikasi Mochy |
| [Metrolist](https://github.com/mostafaalagamy/Metrolist) | Mostafa Alagamy | kerangka dasar |
| [InnertubeX](https://github.com/metrolistGroup/innertubex) | nyxireal | akses API YouTube |

## Perubahan dari basis

| Hal | Basis (Mochy 8.4.2) | BlossomTune 0.6.0 |
|---|---|---|
| `applicationId` / `namespace` | `com.mochyy.mochymusic` | `dev.meowlabs.blossomtune` |
| Nama tampilan | Mochy | BlossomTune |
| Versi | `8.4.2 build#1268` (`versionCode` 73) | `0.6.0` (`versionCode` 100) |
| Nama proyek Gradle | `mochy` | `blossomtune` |
| CI | `build.yml`, `build_pr.yml`, `pages.yml`, `stale.yml` | `build.yml`, `release.yml`, `prune-caches.yml` |
| Berkas yang dihapus | — | `build.sh`, `crowdin.yml`, `fastlane/`, `assets/`, `Roadmap.md`, `docs/`, `vercel.json`, `Frame 201.png`, `.github/CONTRIBUTING.md`, `.github/pull_request_template.md` |

`applicationId` **tidak** berubah dari BlossomTune 0.5.x, jadi 0.6.0 bisa
memasang menimpa versi lama tanpa kehilangan database dan unduhan offline.

## Ketergantungan pihak ketiga

Daftar lengkap ada di `gradle/libs.versions.toml`. Yang utama:

| Komponen | Lisensi |
|---|---|
| NewPipe/InnerTube providers (`providers/`) | GPL-3.0 |
| Media3 / ExoPlayer, Room, Hilt, kotlinx.serialization, Coil | Apache-2.0 |

## Layanan luar saat runtime

App menghubungi layanan pihak ketiga untuk lirik, artwork, canvas, dan metadata
(LRCLIB, Kugou, SimpMusic, YouTube/YouTube Music, Last.fm, Apple Music, Genius,
Musixmatch, dan sejenisnya). Masing-masing punya ketentuan pemakaiannya sendiri.
BlossomTune tidak berafiliasi dengan, disponsori oleh, atau didukung oleh layanan
mana pun tersebut.

## Identitas yang dibersihkan

Kanal donasi basis (Ko-fi, SociaBuzz, Buy Me a Coffee) dan tautan sosial
pengembang basis dihapus dari sheet Support/Donate serta Developer Space —
BlossomTune belum punya kanal donasi, jadi barisnya tidak diganti. Tautan GitHub
di dalam app menunjuk `buanadahbisagorengnuggetsendiri/BlossomTune`.

Yang **belum** dikerjakan di 0.6.0: ikon launcher, splash, dan palet masih milik
basis Mochy; palet es/azure dan perbaikan tinta tema terang dari 0.5.x belum
diport; sheet Support kini tinggal tombol "Star the repo".
