package net.kahlenberger.eberhard.coad.backend

import androidx.room.*
import net.kahlenberger.eberhard.coad.uidata.Dish
import net.kahlenberger.eberhard.coad.uidata.MeasurementUnit

@Entity(tableName = "dishes")
data class DishEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val calories: Int,
    val unit: MeasurementUnit,
    val basicQuantity: String,
    ) {
    fun toUiModel(): Dish {
        return Dish(id, name, calories, unit, basicQuantity)
    }
    suspend fun toUiModelWithChildDishes(dishDao: DishDao): Dish {
        val childEntities = dishDao.getChildDishes(id)
        val childDishes = childEntities.map { it.toUiModelWithChildDishes(dishDao) }
        return Dish(id, name, calories, unit, basicQuantity, childDishes)
    }
}