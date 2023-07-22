package net.kahlenberger.eberhard.coad.uidata

import net.kahlenberger.eberhard.coad.backend.ConsumedItemEntity
import java.time.LocalDateTime

data class ConsumedItem(val id: Int = 0, val name: String, val calories: Int, val date: LocalDateTime, val count: Int=1) {
    fun toEntityModel(): ConsumedItemEntity {
        return ConsumedItemEntity(id, name, calories, date, count)
    }
    fun totalCalories(): Int {
        return calories * count
    }
}