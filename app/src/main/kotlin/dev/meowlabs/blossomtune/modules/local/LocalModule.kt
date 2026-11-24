// a module added from a zip with no service behind it: a look, a player, a font, a glass, pages, menu rows, nothing that ever reaches the network

package dev.meowlabs.blossomtune.modules.local

import dev.meowlabs.blossomtune.module.AccountState
import dev.meowlabs.blossomtune.module.LookModule
import dev.meowlabs.blossomtune.module.ModuleArt
import dev.meowlabs.blossomtune.module.ModuleField
import dev.meowlabs.blossomtune.module.ModuleFields
import dev.meowlabs.blossomtune.module.ModuleHost
import dev.meowlabs.blossomtune.module.ModuleInfo
import dev.meowlabs.blossomtune.module.NO_ACCOUNT_LABEL
import dev.meowlabs.blossomtune.modules.skin.ModuleSkin
import dev.meowlabs.blossomtune.modules.skin.SkinGlass
import dev.meowlabs.blossomtune.modules.skin.SkinParser
import dev.meowlabs.blossomtune.modules.skin.SkinSource
import dev.meowlabs.blossomtune.modules.skin.appliedLook
import dev.meowlabs.blossomtune.modules.web.ManifestSettings
import dev.meowlabs.blossomtune.modules.web.WebModuleManifest
import dev.meowlabs.blossomtune.modules.web.resolveModuleArt
import dev.meowlabs.blossomtune.modules.web.FieldRange
import dev.meowlabs.blossomtune.modules.web.FieldRanges
import java.io.File

internal class LocalModule(private val manifest: WebModuleManifest, files: File?) : LookModule, ModuleArt, ModuleFields, SkinSource, FieldRanges {

    override val info = ModuleInfo(
        id = idFor(manifest),
        name = manifest.name.ifBlank { manifest.id },
        version = manifest.version.ifBlank { "1" },
        description = manifest.description.ifBlank { "Added from a file" },
        author = manifest.author,
    )

    override val skin: ModuleSkin? = SkinParser.parse(info.id, info.name, manifest.player, manifest.font, manifest.pages, files, base = null, manifest.actions, manifest.glass, manifest.players, manifest.bar, manifest.mini)

    override val icon: String? = resolveModuleArt(manifest.icon, files, base = null)

    override val banner: String? = resolveModuleArt(manifest.banner, files, base = null)

    private val settings = ManifestSettings(manifest)

    override val fields: List<ModuleField> get() = settings.fields

    override val ranges: Map<String, FieldRange> get() = settings.ranges

    override fun onFields(values: Map<String, String>) {
        settings.values = values
    }

    // the base look with the picked settings laid over it, so changing an option on the page changes the look
    override val look: Map<String, String> get() = appliedLook(settings.look(manifest.lookValues), skin)

    override val glass: SkinGlass? get() = settings.glass(skin?.glass)

    override fun attach(host: ModuleHost) = Unit

    // there's no server to ask, it lives on this phone
    override suspend fun verifyAccount(): AccountState = AccountState.SignedIn(NO_ACCOUNT_LABEL)

    companion object {
        // local. in front, so a file can never take the place of a built-in module or one added from a link
        fun idFor(manifest: WebModuleManifest): String =
            "local." + manifest.id.ifBlank { manifest.name }.lowercase().replace(Regex("[^a-z0-9._-]"), "-")
    }
}
