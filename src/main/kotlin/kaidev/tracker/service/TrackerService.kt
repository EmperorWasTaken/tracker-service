package kaidev.tracker.service

import org.slf4j.LoggerFactory
import io.github.jan.supabase.postgrest.from
import kaidev.utils.SupabaseClient.client
import kaidev.tracker.model.TrackedDay
import org.springframework.stereotype.Service
import kotlinx.serialization.json.Json


@Service
class TrackerService {
    private val logger = LoggerFactory.getLogger(TrackerService::class.java)
    private val supabase = client
    private val json = Json { ignoreUnknownKeys = true}


    suspend fun get(userId: String): TrackedDay? {
        val id: Int = 1
        return try {
            supabase.from("tracked_day")
                    .select() {
                        filter {
                            eq("userId", userId)
                            eq("id", id)
                        }
                    }

                    .decodeSingleOrNull<TrackedDay>()
        } catch (e: Exception) {
            logger.error("Error fetching tracked day for user $userId: ${e.message}", e)
            null
        }
    }

    suspend fun getAll(userId: String): List<TrackedDay>? {
        return try {
            supabase.from("tracked_day")
                    .select() {
                        filter {
                            eq("userId", userId)
                        }
                    }

                    .decodeList<TrackedDay>()
        } catch (e: Exception) {
            logger.error("Error fetching tracked day for user $userId: ${e.message}", e)
            null
        }
    }

    suspend fun getOrCreateTrackedDay(date: String, userId: String): Int? {
        val existingItemsResponse = supabase.from("tracked_day")
            .select() {
                filter {
                    eq("userId", userId)
                    eq("date", date)
                }
            }

        val existingItems = existingItemsResponse.decodeList<TrackedDay>()

        return if (existingItems.isNotEmpty()) {
            existingItems.first().id
        } else {
            val newItem = TrackedDay(
                userId = userId,
                date = date,
                breakfast = emptyList(),
                lunch = emptyList(),
                dinner = emptyList(),
                snacks = emptyList()
            )
            val response = supabase.from("tracked_day").insert(newItem) { select() }.decodeSingle<TrackedDay>()
            response.id
        }
    }




    suspend fun add(item: TrackedDay, userId: String) {
        val itemWithUser = item.copy(userId = userId)
        val response = supabase.from("tracked_day")
                .insert(itemWithUser)
        println(response.data)
    }

    fun update() {
        println("edit")
    }

    suspend fun delete(id: String, userId: String) {
        val response = supabase.from("tracked_day")
                .delete() {
                    filter {
                        eq("userId", userId)
                        eq("id", id)
                    }
                }
        println(response.data)
    }

    suspend fun addOrUpdate(item: TrackedDay, userId: String) {
        val existingItem = supabase.from("tracked_day")
                .select() {
                    filter {
                        eq("userId", userId)
                        item.date?.let { eq("date", it) }
                    }
                }
                .decodeSingleOrNull<TrackedDay>()

        if (existingItem != null) {
            val updatedItem = existingItem.copy(
                    breakfast = item.breakfast?.ifEmpty { existingItem.breakfast },
                    lunch = item.lunch?.ifEmpty { existingItem.lunch },
                    dinner = item.dinner?.ifEmpty { existingItem.dinner },
                    snacks = item.snacks?.ifEmpty { existingItem.snacks }
            )

            supabase.from("tracked_day")
                    .update(updatedItem) {
                        filter {
                            eq("userId", userId)
                            item.date?.let { eq("date", it) }
                        }
                    }
        } else {
            add(item, userId)
        }
    }


}