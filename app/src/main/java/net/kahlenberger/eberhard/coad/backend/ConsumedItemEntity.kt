package net.kahlenberger.eberhard.coad.backend

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import net.kahlenberger.eberhard.coad.uidata.ConsumedItem
import java.time.LocalDateTime

@Entity(tableName = "consumed_items")
data class ConsumedItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val calories: Int,
    val date: LocalDateTime,
    @ColumnInfo(defaultValue = "1")
    var count: Int
) {
    fun toDomainModel(): ConsumedItem {
        return ConsumedItem(id, name, calories, date, count)
    }
}