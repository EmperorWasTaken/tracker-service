package kaidev.tracker.model

import kotlinx.serialization.Serializable

@Serializable
data class MealItem(
        val name: String? = null,
        val calories: Int? = null
)
