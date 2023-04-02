package net.kahlenberger.eberhard.coad.backend

import androidx.room.TypeConverter
import java.time.LocalDateTime

class Converters {

    @TypeConverter
    fun fromLocalDate(date: LocalDateTime): String {
        return date.toString()
    }

    @TypeConverter
    fun toLocalDate(dateString: String): LocalDateTime {
        return LocalDateTime.parse(dateString)
    }
}