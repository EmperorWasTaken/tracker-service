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
    suspend fun get(): TrackedDay? {
        return service.get()
    }

    @PostMapping("")
    suspend fun add(@RequestBody item: TrackedDay, @RequestParam userId: String) {
        return service.add(item, userId)
    }

    @PutMapping("")
    fun edit() {
        return service.update()
    }

    @DeleteMapping("")
    fun delete() {
        return service.delete()
    }
}