package kaidev.trackercalculations.service

import org.springframework.stereotype.Service

@Service
class CalculationsService() {

    fun getBMR(): Double {

        // Mifflin-St Jeor Equation:
        //For men:
        //BMR = 10W + 6.25H - 5A + 5

        // Dummy data
        val age: Int = 25
        val weight: Int = 80
        val height: Int = 185

        return 10 * weight + 6.25 * height - 5 * age + 5
    }

    fun postBMR(age: Int, weight: Int, height: Int, gender: String, bf: Int?, eq: String): Double {
        return when (eq) {
            "M" -> {
                mifflinStJeorEq(age, weight, height, gender)
            }
            "H" -> {
                harrisBenedictEq(age, weight, height, gender)
            }
            else -> {
                katchMcArdleEq(weight, bf!!)
            }
        }
    }

    fun mifflinStJeorEq(age: Int, weight: Int, height: Int, gender: String): Double {
        return if (gender == "M") {
            10 * weight + 6.25 * height - 5 * age + 5
        } else {
            10 * weight + 6.25 * height - 5 * age - 161
        }
    }

    fun harrisBenedictEq(age: Int, weight: Int, height: Int, gender: String): Double {
        return if (gender == "M") {
            13.39 * weight + 4.799 * height - 5.677 * age + 88.362
        } else {
            9.247 * weight + 3.098 * height - 4.330 * age + 447.593
        }
    }

    fun katchMcArdleEq(weight: Int, bodyFat: Int): Double {
        return 370 + 21.6 * (weight * (100 - bodyFat) / 100)
    }

}