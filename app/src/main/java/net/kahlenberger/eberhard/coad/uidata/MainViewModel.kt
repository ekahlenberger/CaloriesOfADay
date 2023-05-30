package net.kahlenberger.eberhard.coad.uidata

import android.app.Application
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import net.kahlenberger.eberhard.coad.backend.ConsumedItemsDao
import java.time.LocalDateTime

val Application.dataStore by preferencesDataStore("prefs")

class MainViewModel(application: Application, private val consumedItemsDao: ConsumedItemsDao)
    : AndroidViewModel(application) {
    private val _consumedItems = MutableLiveData<List<ConsumedItem>>()
    private val _consumedSum = MutableLiveData<Int>()
    val consumedItems: LiveData<List<ConsumedItem>>
        get() = _consumedItems
    val consumedSum: LiveData<Int>
        get() = _consumedSum

    private val dataStore = application.dataStore
    private val maxCalorieValueKey = intPreferencesKey("max_calorie_value")

    val maxCalorieValue = dataStore.data
        .map { preferences -> preferences[maxCalorieValueKey] ?: 1800 }
        .asLiveData()


    init {
        removeAllConsumedItemsBeforeToday(false)
        viewModelScope.launch {
            LoadConsumedItems()
            _consumedSum.value = _consumedItems.value?.sumOf { it.calories } ?: 0
        }
    }

    fun addConsumedItem(consumedItem: ConsumedItem) {
        viewModelScope.launch {
            val consumedItemEntity = consumedItem.toEntityModel()
            consumedItemsDao.insert(consumedItemEntity)
            LoadConsumedItems()
            _consumedSum.value = consumedSum.value?.plus(consumedItem.calories)
        }
    }

    fun deleteConsumedItem(itemId: Int) {
        viewModelScope.launch {
            consumedItemsDao.deleteById(itemId)
            LoadConsumedItems()
            _consumedSum.value = _consumedItems.value?.sumOf { it.calories } ?: 0
        }
    }

    private suspend fun LoadConsumedItems() {
        _consumedItems.value =
            consumedItemsDao.getAll().map { e -> e.toDomainModel() }.sortedByDescending { it.date }
    }

    fun updateMaxCalorieValue(newValue: Int) {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[maxCalorieValueKey] = newValue
            }
        }
    }

    fun removeAllConsumedItemsBeforeToday(load: Boolean = false) {
        viewModelScope.launch {
            if (consumedItemsDao.deleteItemsBefore(LocalDateTime.now().truncatedTo(java.time.temporal.ChronoUnit.DAYS)) > 0) {
                if (load) {
                    LoadConsumedItems()
                    _consumedSum.value = _consumedItems.value?.sumOf { it.calories } ?: 0
                }
            }
        }
    }
}