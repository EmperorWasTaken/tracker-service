package kaidev.utils

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.PropertyConversionMethod

object SupabaseClient {
    private val supabaseUrl = "https://bcllbxrncwnefiumqusk.supabase.co"
    private val supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImJjbGxieHJuY3duZWZpdW1xdXNrIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MDQ5MTY4MTgsImV4cCI6MjAyMDQ5MjgxOH0.499mjtqgskk9qTr_wodztoHRALs3OOaz8srx7NNPkDc"

    val client: SupabaseClient = createSupabaseClient(supabaseUrl, supabaseKey) {
        install(Auth) {
            alwaysAutoRefresh = false
            autoLoadFromStorage = false
        }
        install(Postgrest) {
            defaultSchema = "public"
            propertyConversionMethod = PropertyConversionMethod.SERIAL_NAME
        }
    }
}