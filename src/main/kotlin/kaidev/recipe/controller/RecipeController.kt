package kaidev.recipe.controller

import kaidev.recipe.model.Recipe
import kaidev.recipe.service.RecipeService
import org.springframework.web.bind.annotation.*

@CrossOrigin
@RestController
@RequestMapping("/feature/recipe")
class RecipeController(
        val service: RecipeService
) {
    @GetMapping("/{id}")
    suspend fun get(@RequestParam userId: String, @PathVariable id: Int): Recipe? {
        return service.get(userId, id)
    }

    @GetMapping()
    suspend fun getAll(@RequestParam userId: String): List<Recipe?>? {
        return service.getAll(userId)
    }

    @PostMapping()
    suspend fun add(@RequestBody item: Recipe, @RequestParam userId: String): Recipe? {
        return service.add(userId, item)
    }
}