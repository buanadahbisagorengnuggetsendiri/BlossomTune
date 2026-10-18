// SimilarRecommendation.kt

package id.meowlabs.blossomtune.models

import com.music.innertube.models.YTItem
import id.meowlabs.blossomtune.db.entities.LocalItem

data class SimilarRecommendation(
    val title: LocalItem,
    val items: List<YTItem>,
)
