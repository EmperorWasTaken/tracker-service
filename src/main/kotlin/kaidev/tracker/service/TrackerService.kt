package kaidev.tracker.service

import com.mongodb.kotlin.client.coroutine.MongoClient
import kaidev.tracker.model.TrackedDay
import kaidev.tracker.repository.TrackedDayRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service


@Service
class TrackerService(
    private val repository: TrackedDayRepository
) {
    private val log: Logger = LoggerFactory.getLogger(TrackerService::class.java)


    private final val connectionString = "mongodb+srv://tracker_service:UotxKu6biPTuCuia@trackercluster.s43bix7.mongodb.net/?retryWrites=true&w=majority"
    private final val mongoClient = MongoClient.create(connectionString)
    private final val database = mongoClient.getDatabase("tracked_days")
    val collection = database.getCollection<TrackedDay>("tracked_days")


    suspend fun get(userId: String, id: String): TrackedDay? {
        return try {
            repository.findById(id).orElse(null)?.takeIf { it.userId == userId }
        } catch (e: Exception) {
            log.error("Error fetching tracked day for userId $userId and id $id: ${e.message}", e)
            null
        }
    }

    suspend fun getAll(userId: String): List<TrackedDay?>? {
        return try {
            repository.findByUserId(userId)
        } catch (e: Exception) {
            log.error("Error fetching all tracked days for userId $userId: ${e.message}", e)
            null
        }
    }


    suspend fun addOrUpdate(item: TrackedDay, userId: String): String {
        return try {
            item.userId = userId

            val existingTrackedDay = repository.findByUserIdAndDate(userId, item.date)
            if (existingTrackedDay != null) {
                item.id = existingTrackedDay.id
            }
            repository.save(item)

            if (existingTrackedDay != null) {
                "Tracked day updated successfully."
            } else {
                "Tracked day added successfully."
            }
        } catch (e: Exception) {
            log.error("Error saving tracked day for userId $userId: ${e.message}", e)
            "Failed to save the tracked day due to an error."
        }
    }

    suspend fun delete(id: String, userId: String): String {
        return try {
            val trackedDay = repository.findById(id)
            if (trackedDay.isPresent && trackedDay.get().userId == userId) {
                repository.deleteById(id)
                "Tracked day successfully deleted."
            } else {
                "No tracked day found with the provided ID."
            }
        } catch (e: Exception) {
            log.error("Error deleting tracked day for userId $userId and id $id: ${e.message}", e)
            "Failed to delete the tracked day due to an error."
        }
    }

}