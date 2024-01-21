package kaidev.recipe.model

import kotlinx.serialization.Serializable

@Serializable
data class Step(
        val recipe_id: Int? = null,
        val id: Int? = null,
        val description: String?,
        val image: String? = null,
        val video: String? = null
)
