package net.kahlenberger.eberhard.coad.uidata

import net.kahlenberger.eberhard.coad.backend.DishEntity


enum class MeasurementUnit {
    Grams,
    Kilograms,
    Liters,
    Milliliters,
    Pints,
    Quarts,
    Gallons,
    Pounds,
    Ounces,
    Drams,

    // Add more units as needed
}


data class Dish(
    val id: Int = 0,
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
}

