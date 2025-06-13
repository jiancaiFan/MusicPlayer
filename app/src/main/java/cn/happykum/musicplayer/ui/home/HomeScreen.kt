package cn.happykum.musicplayer.ui.home

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cn.happykum.musicplayer.viewmodel.PlayListViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(onNavigateToPlayBack: (String) -> Unit) {

    val playListViewModel: PlayListViewModel = viewModel()
    val playListState by playListViewModel.playListState.collectAsState()

    val categories = (45 downTo 1).map { it.toString() }
    var selectedCategory by remember { mutableStateOf(categories.first()) }

    LaunchedEffect(Unit) {
        playListViewModel.init()
    }
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HomePlayCategory(
            categories = categories,
            selectedCategory = selectedCategory,
            onCategorySelected = { category ->
                selectedCategory = category
                playListViewModel.loadByCategory(category)
            },
            Modifier.weight(0.3f)
        )

        VerticalDivider(modifier = Modifier.fillMaxHeight())

        HomePlayList(
            state = playListState,
            modifier =  Modifier.weight(0.7f),
            onNavigateToPlayBack = onNavigateToPlayBack,
        )
    }
}