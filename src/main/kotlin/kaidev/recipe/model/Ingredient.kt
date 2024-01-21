package kaidev.recipe.model

import kotlinx.serialization.Serializable

@Serializable
data class Ingredient(
        val recipe_id: Int? = null,
        val id: Int? = null,
        val name: String?,
        val amount: String?,
        val unit: String?
)
