package kaidev.tracker.model

import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "tracked_days")
data class TrackedDay(

        @Id
        var id: ObjectId? = null,

        var userId: String? = null,
        val date: String?= null,
        val breakfast: List<MealItem>?,
        val lunch: List<MealItem>?= null,
        val dinner: List<MealItem>?= null,
        val snacks: List<MealItem>?= null
        )

@Serializable
data class MealItem(
        val name: String? = null,
        val calories: Int? = null
)