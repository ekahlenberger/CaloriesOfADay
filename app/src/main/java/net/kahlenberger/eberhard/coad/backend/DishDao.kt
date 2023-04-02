package net.kahlenberger.eberhard.coad.backend

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface DishDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dish: DishEntity): Long

    @Query("SELECT * FROM dishes")
    suspend fun getAll(): List<DishEntity>

    @Query("SELECT * FROM dishes WHERE id = :dishId")
    fun getDish(dishId: Int): LiveData<DishEntity>

    @Query("DELETE FROM dishes WHERE id = :dishId")
    suspend fun deleteById(dishId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCrossRef(dishCrossRef: DishCrossRef)

    @Query("SELECT * FROM DishCrossRef")
    suspend fun getAllCrossRefs(): List<DishCrossRef>

    @Transaction
    @Query("SELECT * FROM dishes WHERE id IN (SELECT childDishId FROM DishCrossRef WHERE parentDishId = :dishId)")
    suspend fun getChildDishes(dishId: Int): List<DishEntity>

    @Query("DELETE FROM DishCrossRef WHERE parentDishId = :parentId AND childDishId = :childId")
    suspend fun deleteCrossRef(parentId: Int, childId: Int)
}
