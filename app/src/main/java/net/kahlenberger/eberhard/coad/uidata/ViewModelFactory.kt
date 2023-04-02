package net.kahlenberger.eberhard.coad.uidata

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.kahlenberger.eberhard.coad.backend.ConsumedItemsDao
import net.kahlenberger.eberhard.coad.backend.DishDao


class ViewModelFactory(
    private val application: Application,
    private val consumedItemsDao: ConsumedItemsDao,
    private val dishesDao: DishDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(application, consumedItemsDao) as T
        }
        else if (modelClass.isAssignableFrom(DishesViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return DishesViewModel(application, dishesDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


