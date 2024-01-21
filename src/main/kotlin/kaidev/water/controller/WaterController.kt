package kaidev.water.controller

import kaidev.tracker.model.TrackedDay
import kaidev.water.model.WaterData
import kaidev.water.service.WaterService
import org.springframework.web.bind.annotation.*

@CrossOrigin
@RestController
@RequestMapping("/feature/water")
class WaterController(
    val service: WaterService
) {

    @PostMapping()
    suspend fun add(@RequestBody item: WaterData, @RequestParam userId: String) {
        return service.addWater(item.date, item, userId)
    }

}