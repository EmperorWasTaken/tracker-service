package kaidev.tracker.model

import MealItem
import kotlinx.serialization.Serializable

@Serializable
data class TrackedDay(
        val id: Int,
        val userId: String,
        val date: String,
        val breakfast: List<MealItem>?,
        val lunch: List<MealItem>?,
        val dinner: List<MealItem>?,
        val snacks: List<MealItem>?,
        val water: Int?,
        val weight: Int?,
        val calories: Int?,
        val exercise: Int?,
        val sleep: Int?,
        val steps: Int?,
        val mood: Int?,
        val notes: String?,

)