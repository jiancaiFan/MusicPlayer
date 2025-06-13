package cn.happykum.musicplayer.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
internal fun HomePlayCategory(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxHeight()
            .padding(vertical = 8.dp)
    ) {
        itemsIndexed(categories) { index, category ->
            val isSelected = category == selectedCategory
            ListItem(
                headlineContent = {
                    Text(
                        text = "第${index + 1}页",
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (isSelected)
                            MaterialTheme.colorScheme.onPrimaryContainer
                        else
                            MaterialTheme.colorScheme.onSurface
                    )
                },
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .semantics {
                        selected = isSelected
                    }
                    .background(
                        color = if (isSelected) {
                            MaterialTheme.colorScheme.primaryContainer
                        } else {
                            Color.Transparent
                        },
                        shape = MaterialTheme.shapes.medium
                    )
                    .clickable(
                        onClick = { onCategorySelected(category) }
                    ),
                colors = ListItemDefaults.colors(
                    containerColor = Color.Transparent // Already handled by .background
                )
            )
        }
    }
}