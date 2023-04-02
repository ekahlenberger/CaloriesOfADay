package net.kahlenberger.eberhard.coad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import kotlinx.coroutines.*
import net.kahlenberger.eberhard.coad.uidata.DishesViewModel
import net.kahlenberger.eberhard.coad.uidata.MainViewModel
import java.time.Duration
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit


class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels {
        (application as CaloriesApp).viewModelFactory
    }
    private val dishesViewModel: DishesViewModel by viewModels {
        (application as CaloriesApp).viewModelFactory
    }

    private val hourlyJob = Job()
    private val hourlyScope = CoroutineScope(Dispatchers.Main + hourlyJob)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigation(mainViewModel, dishesViewModel)
        }
    }
    override fun onResume() {
        super.onResume()
        mainViewModel.removeAllConsumedItemsBeforeToday()
        scheduleHourlyTask(mainViewModel)
    }

    override fun onPause() {
        super.onPause()
        hourlyJob.cancel()
    }

    fun scheduleHourlyTask(viewModel: MainViewModel) {
        hourlyScope.launch {
            while (true) {
                val currentTime = LocalDateTime.now()
                val nextHour = currentTime.plusHours(1).truncatedTo(ChronoUnit.HOURS)
                val delayMillis = Duration.between(currentTime, nextHour).toMillis()

                delay(delayMillis)
                viewModel.removeAllConsumedItemsBeforeToday()
            }
        }
    }
}


