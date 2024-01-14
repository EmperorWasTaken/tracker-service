package kaidev.tracker.service

import org.slf4j.LoggerFactory
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.PropertyConversionMethod
import io.github.jan.supabase.postgrest.from
import kaidev.tracker.model.TrackedDay
import org.springframework.stereotype.Service
import kotlinx.serialization.json.Json


@Service
class TrackerService {
    private val logger = LoggerFactory.getLogger(TrackerService::class.java)


    private val json = Json { ignoreUnknownKeys = true}

    private val supabaseUrl = "https://bcllbxrncwnefiumqusk.supabase.co"
    private val supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImJjbGxieHJuY3duZWZpdW1xdXNrIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MDQ5MTY4MTgsImV4cCI6MjAyMDQ5MjgxOH0.499mjtqgskk9qTr_wodztoHRALs3OOaz8srx7NNPkDc"


    private val supabase = createSupabaseClient(supabaseUrl, supabaseKey) {
        install(Auth) {
            alwaysAutoRefresh = false
            autoLoadFromStorage = false
        }
        install(Postgrest) {
            defaultSchema = "public" // default: "public"
            propertyConversionMethod = PropertyConversionMethod.SERIAL_NAME // default: PropertyConversionMethod.CAMEL_CASE_TO_SNAKE_CASE
        }
    }

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



    suspend fun add(item: TrackedDay, userId: String) {
        val itemWithUser = item.copy(userId = userId)
        val response = supabase.from("tracked_day")
                .insert(itemWithUser)
        if (response.data != null) {
            println(response.data)
        }
    }

    fun update() {
        println("edit")
    }

    fun delete() {
        println("delete")
    }
}