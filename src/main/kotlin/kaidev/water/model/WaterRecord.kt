package kaidev.water.model

import kotlinx.serialization.Serializable

@Serializable
data class WaterRecord(
    val id: Int? = null,
    val tracked_day_id: Int,
    val water_amount: WaterData,
)
