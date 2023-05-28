package net.kahlenberger.eberhard.coad.uidata

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.kahlenberger.eberhard.coad.backend.DishCrossRef
import net.kahlenberger.eberhard.coad.backend.DishDao

class DishesViewModel (application: Application, private val dishesDao: DishDao)
    : AndroidViewModel(application) {
    private val _dishes = MutableLiveData<List<Dish>>()
    val dishes: LiveData<List<Dish>>
        get() = _dishes

    init {
        viewModelScope.launch {
            dishesDao.deleteOrphanedCrossRefs()
        }
        loadDishes()
    }

    private fun loadDishes() {
        viewModelScope.launch {
            val dishEntities = dishesDao.getAll()
            val dishesWithChildren = dishEntities.map { it.toUiModelWithChildDishes(dishesDao) }.sortedBy { dish -> dish.name }
            _dishes.value = dishesWithChildren
        }
    }

    fun addDish(dish: Dish) {
        viewModelScope.launch {
            val dishEntity = dish.toEntityModel()
            val dishId = dishesDao.insert(dishEntity)
            for (child in dish.childDishes)
                dishesDao.insertCrossRef(DishCrossRef(dishId, child.id, child.basicQuantityInput))
            loadDishes()
        }
    }

    fun deleteDish(dish: Dish) {
        viewModelScope.launch {
            dishesDao.deleteById(dish.id)
            _dishes.value = _dishes.value?.filter { it.id != dish.id }
        }
    }
}