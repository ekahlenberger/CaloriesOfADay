package net.kahlenberger.eberhard.coad.backend

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
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

    @Query("SELECT * FROM consumed_items WHERE id = :id")
    suspend fun getById(id: Int): ConsumedItemEntity?

    @Update
    suspend fun update(consumedItem: ConsumedItemEntity)
    @Query("UPDATE consumed_items SET count = count + 1 WHERE id = :itemId")
    suspend fun incrementCount(itemId: Int)

}