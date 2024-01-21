package kaidev.recipe.model

import kotlinx.serialization.Serializable

@Serializable
data class Tag(
        val id: Int? = null,
        val type: String,
)
