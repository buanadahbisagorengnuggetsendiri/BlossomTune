// LocalItem.kt

package com.meowlabs.stereonism.db.entities

sealed class LocalItem {
    abstract val id: String
    abstract val title: String
    abstract val thumbnailUrl: String?
}
