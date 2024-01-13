package kaidev.tracker.service

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.PropertyConversionMethod
import io.github.jan.supabase.postgrest.from
import io.ktor.client.*
import io.ktor.client.plugins.kotlinx.serializer.*
import io.ktor.client.request.*
import kaidev.tracker.model.TrackedDay
import org.springframework.stereotype.Service
import kotlinx.serialization.json.Json


@Service
class TrackerServicer {


    private val json = Json

    private val supabaseUrl = "https://bcllbxrncwnefiumqusk.supabase.co"
    private val supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImJjbGxieHJuY3duZWZpdW1xdXNrIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MDQ5MTY4MTgsImV4cCI6MjAyMDQ5MjgxOH0.499mjtqgskk9qTr_wodztoHRALs3OOaz8srx7NNPkDc"


    private val supabase = createSupabaseClient(supabaseUrl, supabaseKey) {
        install(Auth) {
            alwaysAutoRefresh = false
            autoLoadFromStorage = false
        }
        install(Postgrest) {
            defaultSchema = "schema" // default: "public"
            propertyConversionMethod = PropertyConversionMethod.SERIAL_NAME // default: PropertyConversionMethod.CAMEL_CASE_TO_SNAKE_CASE
        }
    }

    suspend fun get(): TrackedDay? {
        return supabase.from("tracked_day")
                .select()
                .decodeSingleOrNull<TrackedDay>()
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