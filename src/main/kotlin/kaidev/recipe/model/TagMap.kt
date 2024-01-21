package kaidev.recipe.model

import kotlinx.serialization.Serializable

@Serializable
data class TagMap(
        val recipe_id: Int? = null,
        val tag_id: Int? = null
)
