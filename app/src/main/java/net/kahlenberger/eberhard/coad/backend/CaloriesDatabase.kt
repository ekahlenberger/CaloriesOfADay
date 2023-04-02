package net.kahlenberger.eberhard.coad.backend

import android.content.Context
import androidx.room.*
import net.kahlenberger.eberhard.coad.backend.migrations.Migrate1_2

@Database(entities = [ConsumedItemEntity::class, DishEntity::class, DishCrossRef::class],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to = 2, spec = Migrate1_2::class),
    ])
@TypeConverters(Converters::class)
abstract class CaloriesDatabase : RoomDatabase() {
    abstract fun consumedItemsDao(): ConsumedItemsDao
    abstract fun dishDao(): DishDao
    companion object {
        @Volatile
        private var INSTANCE: CaloriesDatabase? = null

        fun getInstance(context: Context): CaloriesDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CaloriesDatabase::class.java,
                        "calories_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}