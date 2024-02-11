package kaidev.tracker.controller

import kaidev.tracker.model.TrackedDay
import kaidev.tracker.service.TrackerService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*


@CrossOrigin
@RestController
@RequestMapping("/feature/tracker")
class TrackerController(
        val service: TrackerService
) {

    @GetMapping("")
    suspend fun get(@RequestParam userId: String, id: String): TrackedDay? {
        val test = getCurrentUserId()
        return service.get(userId, id)
    }

    @GetMapping("/getAll")
    suspend fun getAll(@RequestParam userId: String): List<TrackedDay?>? {
        return service.getAll(userId)
    }
    @PostMapping("")
    suspend fun add(@RequestBody item: TrackedDay, @RequestParam userId: String): Any {
        return service.addOrUpdate(item, userId)
    }


    @DeleteMapping("")
    suspend fun delete(@RequestParam userId: String, @RequestParam id: String): Any {
        return service.delete(id, userId)
    }

    private fun getCurrentUserId(): String {
        val authentication: org.springframework.security.core.Authentication? = SecurityContextHolder.getContext().authentication
        // Here, assume the user's ID is the 'name' field. Adjust according to your JWT claims setup.
        if (authentication != null) {
            return authentication.name
        }
        return ""
    }
}