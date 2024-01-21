package kaidev.water.service

import io.github.jan.supabase.postgrest.from
import kaidev.tracker.service.TrackerService
import kaidev.utils.SupabaseClient.client
import kaidev.water.model.WaterData
import kaidev.water.model.WaterRecord
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class WaterService(
    private val trackerService: TrackerService
) {
    private val logger = LoggerFactory.getLogger(WaterService::class.java)
    private val supabase = client

    suspend fun addWater(date: String, waterData: WaterData, userId: String) {
        val trackedDayId = trackerService.getOrCreateTrackedDay(date, userId)

        val waterRecord = WaterRecord(tracked_day_id = trackedDayId!!, water_amount = waterData)

        val response = supabase.from("water").insert(waterRecord) { select() }.decodeSingleOrNull<WaterRecord>()

        logger.info("Added water record: $response")
    }
}
