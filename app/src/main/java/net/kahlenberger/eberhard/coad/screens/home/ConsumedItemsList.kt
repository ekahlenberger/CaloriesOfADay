package net.kahlenberger.eberhard.coad.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import net.kahlenberger.eberhard.coad.R
import net.kahlenberger.eberhard.coad.uidata.ConsumedItem

@Composable
fun ConsumedItemsList(consumedItems: List<ConsumedItem>,
                      onDelete: (ConsumedItem) -> Unit,
                      onIncrement: (ConsumedItem, Int) -> Unit) {
    Surface(
        elevation = 4.dp,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = MaterialTheme.colors.surface)
                .padding(8.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = stringResource(R.string.consumptionHeadline),
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(8.dp),
                )
                if (consumedItems.isEmpty()) {
                    Spacer(Modifier.heightIn(min = 16.dp, max = 48.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .padding(8.dp)
                            .align(Alignment.CenterHorizontally),
                        elevation = 8.dp,
                    )
                    {
                        Text(
                            text = stringResource(R.string.emptyConsumptionHint),
                            style = MaterialTheme.typography.subtitle1,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                        )
                    }

                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(8.dp)
                    )
                    {
                        items(consumedItems) { item ->
                            ConsumedItemRow(item, onDelete) {onIncrement(item, consumedItems.indexOf(item))}
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ConsumedItemRow(item: ConsumedItem,
                    onDelete: (ConsumedItem) -> Unit,
                    onIncrement: (ConsumedItem) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${item.name} ${if (item.count > 1) " x${item.count}" else ""}",
            style = MaterialTheme.typography.body1,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "${item.totalCalories()} kcal",
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.End
        )
        IconButton(onClick = { onIncrement(item) }) {
            Icon(
                Icons.Filled.Add,
                contentDescription = stringResource(R.string.incrementConsumedItem),
                tint = MaterialTheme.colors.secondary
            )
        }
        IconButton(onClick = { onDelete(item) }) {
            Icon(Icons.Filled.Delete, contentDescription = stringResource(R.string.deleteConsumedItem))
        }
    }
}