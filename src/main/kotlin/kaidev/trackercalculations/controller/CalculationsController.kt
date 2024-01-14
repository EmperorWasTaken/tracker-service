package kaidev.trackercalculations.controller

import kaidev.trackercalculations.service.CalculationsService
import org.springframework.web.bind.annotation.*


@CrossOrigin
@RestController
@RequestMapping("/feature/calculations")
class CalculationsController(
        val service: CalculationsService
) {

    @GetMapping("")
    fun getBMR(): Double {
        return service.getBMR()
    }

    @PostMapping("/bmr")
    fun postBMR(@RequestBody data: Map<String?, String>): Double {
        val eq = data["eq"].toString()
        val age = data["age"]!!.toInt()
        val weight = data["weight"]!!.toInt()
        val height = data["height"]!!.toInt()
        val gender = data["gender"].toString()
        val bf = data["bf"]?.toInt()
        return service.postBMR(age, weight, height, gender, bf, eq)
    }
}