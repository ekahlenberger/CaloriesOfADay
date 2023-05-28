package net.kahlenberger.eberhard.coad.backend

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["parentDishId", "childDishId"])
data class DishCrossRef(
    @ColumnInfo(name = "parentDishId")
    val parentDishId: Long,
    @ColumnInfo(name = "childDishId")
    val childDishId: Long,
    @ColumnInfo(name = "quantity", defaultValue = "1")
    val quantity: String,
)

