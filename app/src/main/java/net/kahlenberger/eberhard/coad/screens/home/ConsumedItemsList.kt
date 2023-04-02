package net.kahlenberger.eberhard.coad.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import net.kahlenberger.eberhard.coad.uidata.ConsumedItem

@Composable
fun ConsumedItemsList(consumedItems: List<ConsumedItem>, onDelete: (ConsumedItem) -> Unit) {
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
                    text = "Today's consumption",
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
                            text = "Nothing eaten yet, time for breakfast. Hunger is not healthy. Eat something healthy.",
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
                            ConsumedItemRow(item, onDelete)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ConsumedItemRow(item: ConsumedItem, onDelete: (ConsumedItem) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item.name,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "${item.calories} kcal",
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.End
        )
        IconButton(onClick = { onDelete(item) }) {
            Icon(Icons.Filled.Delete, contentDescription = "Delete Consumed Item")
        }
    }
}