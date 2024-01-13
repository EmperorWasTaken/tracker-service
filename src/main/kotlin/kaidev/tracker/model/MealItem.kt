import kotlinx.serialization.Serializable

@Serializable
data class MealItem(
        val name: String,
        val calories: Int
)
