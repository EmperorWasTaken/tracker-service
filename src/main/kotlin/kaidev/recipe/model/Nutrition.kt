package kaidev.recipe.model

import kotlinx.serialization.Serializable

@Serializable
data class Nutrition(
        val recipe_id: Int? = null,
        val id: Int? = null,
        val calories: Int? = null,
        val fat: Int? = null,
        val carbs: Int? = null,
        val protein: Int? = null
)
