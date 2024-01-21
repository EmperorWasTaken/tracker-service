package kaidev.recipe.model

import kotlinx.serialization.Serializable

@Serializable
data class Recipe(
        val id: Int? = null,
        val name: String,
        val description: String,
        val ingredients: List<Ingredient>? = null,
        val steps: List<Step>? = null,
        val tags: List<Tag>? = null,
        val image: String? = null,
        val user_id: String? = null
)
