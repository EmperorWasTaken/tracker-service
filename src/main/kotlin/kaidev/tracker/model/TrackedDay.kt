package kaidev.tracker.model

import kotlinx.serialization.Serializable

@Serializable
data class TrackedDay(
        val id: Int? = null,
        val userId: String? = null,
        val date: String?= null,
        val breakfast: List<MealItem>?,
        val lunch: List<MealItem>?= null,
        val dinner: List<MealItem>?= null,
        val snacks: List<MealItem>?= null
        )