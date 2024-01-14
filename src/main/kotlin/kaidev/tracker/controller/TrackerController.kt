package kaidev.tracker.controller

import kaidev.tracker.model.TrackedDay
import kaidev.tracker.service.TrackerService
import org.springframework.web.bind.annotation.*


@CrossOrigin
@RestController
@RequestMapping("/feature/tracker")
class TrackerController(
        val service: TrackerService
) {

    @GetMapping("")
    suspend fun get(@RequestParam userId: String): TrackedDay? {
        return service.get(userId)
    }

    @GetMapping("/getAll")
    suspend fun getAll(@RequestParam userId: String): List<TrackedDay>? {
        return service.getAll(userId)
    }
    @PostMapping("")
    suspend fun add(@RequestBody item: TrackedDay, @RequestParam userId: String) {
        return service.addOrUpdate(item, userId)
    }



    @PutMapping("")
    fun edit() {
        return service.update()
    }

    @DeleteMapping("")
    suspend fun delete(@RequestParam userId: String, @RequestParam id: String) {
        return service.delete(id, userId)
    }
}