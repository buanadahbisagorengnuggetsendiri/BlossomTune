// FilterType.kt

package dev.meowlabs.blossomtune.eq.data

import kotlinx.serialization.Serializable

@Serializable
enum class FilterType {

    PK,

    LSC,

    HSC,

    LPQ,

    HPQ
}
