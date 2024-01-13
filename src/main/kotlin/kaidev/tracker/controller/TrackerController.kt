package kaidev.tracker.controller

import kaidev.tracker.model.TrackedDay
import kaidev.tracker.service.TrackerServicer
import org.springframework.web.bind.annotation.*


@CrossOrigin
@RestController
@RequestMapping("/feature/tracker")
class TrackerController(
        val service: TrackerServicer
) {

    @GetMapping("/get")
    suspend fun get(): TrackedDay? {
        return service.get()
    }

    @PostMapping("/add")
    suspend fun add(@RequestBody item: TrackedDay, @RequestParam userId: String) {
        return service.add(item, userId)
    }

    @GetMapping("/update")
    fun edit() {
        return service.update()
    }

    @GetMapping("/delete")
    fun delete() {
        return service.delete()
    }
}