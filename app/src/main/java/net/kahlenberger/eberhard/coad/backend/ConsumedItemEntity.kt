package net.kahlenberger.eberhard.coad.backend

import androidx.room.Entity
import androidx.room.PrimaryKey
import net.kahlenberger.eberhard.coad.uidata.ConsumedItem
import java.time.LocalDateTime

@Entity(tableName = "consumed_items")
data class ConsumedItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val calories: Int,
    val date: LocalDateTime
) {
    fun toDomainModel(): ConsumedItem {
        return ConsumedItem(id, name, calories, date)
    }
}