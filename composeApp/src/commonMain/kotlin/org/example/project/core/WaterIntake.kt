package org.example.project.core

import kotlin.math.roundToInt

data class WaterIntakeParams(val weight: Int, val age: Int)

data class WaterIntakeResult(val amountInMl: Int)

val WaterIntakeResult.liters: Float get() = amountInMl / 1000f
val WaterIntakeResult.glassesOf250ml: Int get() = (amountInMl / 250f).roundToInt()
