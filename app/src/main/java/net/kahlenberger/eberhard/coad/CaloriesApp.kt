package net.kahlenberger.eberhard.coad

import android.app.Application
import net.kahlenberger.eberhard.coad.backend.CaloriesDatabase
import net.kahlenberger.eberhard.coad.backend.ConsumedItemsDao
import net.kahlenberger.eberhard.coad.backend.DishDao
import net.kahlenberger.eberhard.coad.uidata.ViewModelFactory

class CaloriesApp : Application() {
    val consumedItemsDao: ConsumedItemsDao
        get() = CaloriesDatabase.getInstance(this).consumedItemsDao()
    val dishesDao: DishDao
        get() = CaloriesDatabase.getInstance(this).dishDao()
    val viewModelFactory: ViewModelFactory
        get() = ViewModelFactory(this, consumedItemsDao, dishesDao)
}