package kaidev.water.model

import kotlinx.serialization.Serializable

@Serializable
data class WaterData(
    val amount: Int,
    val date: String
)
