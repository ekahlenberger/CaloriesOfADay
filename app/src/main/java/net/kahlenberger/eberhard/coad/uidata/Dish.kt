package net.kahlenberger.eberhard.coad.uidata

import net.kahlenberger.eberhard.coad.backend.DishEntity
import net.kahlenberger.eberhard.coad.backend.MeasurementUnit
import kotlin.math.roundToInt


data class Dish(
    val id: Long = 0,
    val name: String,
    val calories: Int,
    val unit: MeasurementUnit,
    val basicQuantityInput: String,
    val childDishes: List<Dish> = emptyList()
) {
    val basicQuantity: Double
        get() = basicQuantityInput.toDouble()

    val totalCalories: Int
        get() = calories + childDishes.sumOf { it.totalCalories }

    fun toEntityModel(): DishEntity {
        return DishEntity(id, name, calories, unit, basicQuantityInput)
    }
    fun adjusted(adjustedQuantity: String): Dish {
        val adjustedCal = ((calories.toDouble() / basicQuantity) * adjustedQuantity.toDouble()).roundToInt()
        val adjustedDish = copy(calories = adjustedCal, basicQuantityInput = adjustedQuantity)
        return adjustedDish
    }
}

