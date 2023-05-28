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
    fun getDish(dishId: Long): LiveData<DishEntity>

    @Query("DELETE FROM dishes WHERE id = :dishId")
    suspend fun deleteById(dishId: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCrossRef(dishCrossRef: DishCrossRef)

    @Query("SELECT * FROM DishCrossRef")
    suspend fun getAllCrossRefs(): List<DishCrossRef>

    @Transaction
    @Query("""
    SELECT dishes.*, DishCrossRef.quantity as quantity 
    FROM dishes 
    INNER JOIN DishCrossRef 
    ON dishes.id = DishCrossRef.childDishId 
    WHERE DishCrossRef.parentDishId = :dishId
    """)
    suspend fun getChildDishes(dishId: Long): List<DishWithQuantity>

    @Query("DELETE FROM DishCrossRef WHERE parentDishId = :parentId AND childDishId = :childId")
    suspend fun deleteCrossRef(parentId: Long, childId: Long)

    @Query("DELETE FROM DishCrossRef WHERE parentDishId = :parentId")
    suspend fun deleteParentsCrossRefs(parentId: Long)

    @Query("""
        DELETE FROM DishCrossRef 
        WHERE parentDishId NOT IN (SELECT id FROM dishes)
        OR childDishId NOT IN (SELECT id FROM dishes)
    """)
    suspend fun deleteOrphanedCrossRefs()
}
