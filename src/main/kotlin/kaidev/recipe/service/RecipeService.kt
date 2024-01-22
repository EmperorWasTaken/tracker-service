package kaidev.recipe.service

import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.ktor.util.reflect.*
import kaidev.recipe.model.*
import kaidev.tracker.service.TrackerService
import kaidev.utils.SupabaseClient
import kotlinx.coroutines.selects.select
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service


@Service
class RecipeService(
        private val trackerService: TrackerService
) {
    private val logger = LoggerFactory.getLogger(RecipeService::class.java)
    private val supabase = SupabaseClient.client
    private val json = Json { ignoreUnknownKeys = true}
    suspend fun get(userId: String, id: Int): Recipe? {
        return try {
            val recipeColumns = Columns.list(
                    "id",
                    "name",
                    "description",
                    "image",
                    "servings",
                    "cook_time",
                    "prep_time",
                    "author",
                    "ingredients:ingredients(id, name, amount, unit)",
                    "steps:recipe_steps(id, description, image, video)",
                    "nutrition:nutrition(id, calories, carbs, fat, protein)"
            )

            val recipe = supabase.from("recipes")
                    .select(recipeColumns) {
                        filter {
                            eq("user_id", userId)
                            eq("id", id)
                        }
                    }
                    .decodeSingleOrNull<Recipe>()

            if (recipe != null) {
                val tagIds = supabase.from("recipe_tags")

                        .select() {
                            filter {
                                recipe.id?.let { eq("recipe_id", it) }
                            }
                        }
                        .decodeList<TagMap>()

                val tags = tagIds.mapNotNull { tagId ->
                    supabase.from("tags")
                            .select() {
                                filter {
                                    tagId.tag_id?.let { eq("id", it) }
                                }
                            }
                            .decodeSingleOrNull<Tag>()
                }

                recipe.copy(tags = tags)
            } else {
                null
            }
        } catch (e: Exception) {
            logger.error("Error fetching recipe for user $userId: ${e.message}", e)
            null
        }
    }


    suspend fun getAll(userId: String): List<Recipe?>? {
        return try {
            val recipeColumns = Columns.list(
                    "id",
                    "name",
                    "description",
                    "image",
                    "servings",
                    "cook_time",
                    "prep_time",
                    "author",
                    "ingredients:ingredients(id, name, amount, unit)",
                    "steps:recipe_steps(id, description, image, video)",
                    "nutrition:nutrition(id, calories, carbs, fat, protein)"
            )
            val recipes = supabase.from("recipes")
                    .select(recipeColumns) {
                        filter {
                            eq("user_id", userId)
                        }
                    }
                    .decodeList<Recipe>()

            recipes.map { recipe ->
                recipe.let {
                    val tagIds = supabase.from("recipe_tags")
                            .select() {
                                filter {
                                    recipe.id?.let { eq("recipe_id", it) }
                                }
                            }
                            .decodeList<TagMap>()

                    val tags = tagIds.mapNotNull { tagId ->
                        supabase.from("tags")
                                .select() {
                                    filter {
                                        tagId.tag_id?.let { eq("id", it) }
                                    }
                                }
                                .decodeSingleOrNull<Tag>()
                    }
                    recipe.copy(tags = tags)
                }
            }
        } catch (e: Exception) {
            logger.error("Error fetching recipes for user $userId: ${e.message}", e)
            emptyList<Recipe>()
        }
    }


    suspend fun add(userId: String, recipe: Recipe): Recipe? {
        return try {
            val newRecipeMap = mapOf(
                    "name" to recipe.name,
                    "description" to recipe.description,
                    "image" to recipe.image,
                    "user_id" to userId
            )
            val insertedRecipe = supabase.from("recipes")
                    .insert(newRecipeMap) { select() }
                    .decodeSingleOrNull<Recipe>()

            if (insertedRecipe != null) {
                recipe.ingredients?.forEach { ingredient ->
                    val ingredientMap = Ingredient(
                            recipe_id = insertedRecipe.id,
                            name = ingredient.name,
                            amount = ingredient.amount,
                            unit = ingredient.unit
                    )

                    supabase.from("ingredients")
                            .insert(ingredientMap)
                }

                recipe.steps?.forEach { step ->

                    val stepMap = Step(
                            recipe_id = insertedRecipe.id,
                            description = step.description,
                            image = step.image,
                            video = step.video
                    )

                    supabase.from("recipe_steps")
                            .insert(stepMap)
                }

                recipe.tags?.forEach { tag ->

                    val newTag = Tag(
                            type = tag.type
                    )

                    val tagId = supabase.from("tags")
                            .insert(newTag) {
                                select()
                            }
                            .decodeSingleOrNull<Tag>()

                    val tagMap = TagMap(
                            recipe_id = insertedRecipe.id,
                            tag_id = tagId?.id,
                    )

                    supabase.from("recipe_tags")
                            .insert(tagMap)
                }

                get(userId, insertedRecipe.id!!)
            } else {
                null
            }
        } catch (e: Exception) {
            logger.error("Error adding recipe for user $userId: ${e.message}", e)
            null
        }
    }


}