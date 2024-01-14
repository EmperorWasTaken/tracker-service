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
        val snacks: List<MealItem>?= null,
        val water: Int?= null,
        val weight: Int?= null,
        val calories: Int?= null,
        val exercise: Int?= null,
        val sleep: Int?= null,
        val steps: Int?= null,
        val mood: Int? = null,
        val notes: String? = null,

        )