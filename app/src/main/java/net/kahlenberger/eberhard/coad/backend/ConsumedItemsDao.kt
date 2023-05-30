package net.kahlenberger.eberhard.coad.backend

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import java.time.LocalDateTime

@Dao
interface ConsumedItemsDao {
    @Query("SELECT * FROM consumed_items")
    suspend fun getAll(): List<ConsumedItemEntity>

    @Insert
    suspend fun insert(item: ConsumedItemEntity)

    @Query("DELETE FROM consumed_items")
    suspend fun deleteAll()

    @Query("DELETE FROM consumed_items WHERE id = :itemId")
    suspend fun deleteById(itemId: Int)

    @Query("DELETE FROM consumed_items WHERE date < :moment")
    suspend fun deleteItemsBefore(moment: LocalDateTime): Int
}