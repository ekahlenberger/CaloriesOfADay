package net.kahlenberger.eberhard.coad.backend

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["parentDishId", "childDishId"])
data class DishCrossRef(
    @ColumnInfo(name = "parentDishId")
    val parentDishId: Int,
    @ColumnInfo(name = "childDishId")
    val childDishId: Int
)

