// SimilarRecommendation.kt

package com.meowlabs.stereonism.models

import com.music.innertube.models.YTItem
import com.meowlabs.stereonism.db.entities.LocalItem

data class SimilarRecommendation(
    val title: LocalItem,
    val items: List<YTItem>,
)
