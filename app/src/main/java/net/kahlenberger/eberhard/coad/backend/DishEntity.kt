package net.kahlenberger.eberhard.coad.backend

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import net.kahlenberger.eberhard.coad.uidata.Dish
import kotlin.math.roundToInt

@Entity(tableName = "dishes")
data class DishEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
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
        val childDishes = childEntities.map {
                it.dish.adjusted(it.quantity).toUiModelWithChildDishes(dishDao)
        }
        return Dish(id, name, calories, unit, basicQuantity, childDishes)
    }
    fun adjusted(adjustedQuantity: String): DishEntity {
        val adjustedCal = ((calories.toDouble() / basicQuantity.toDouble()) * adjustedQuantity.toDouble()).roundToInt()
        val adjustedDish = copy(calories = adjustedCal, basicQuantity = adjustedQuantity)
        return adjustedDish
    }
}

data class DishWithQuantity(
    @Embedded val dish: DishEntity,
    @ColumnInfo(name = "quantity") val quantity: String
)
