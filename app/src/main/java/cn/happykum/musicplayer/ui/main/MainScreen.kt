package cn.happykum.musicplayer.ui.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cn.happykum.musicplayer.R
import cn.happykum.musicplayer.ui.TabScreen
import cn.happykum.musicplayer.ui.home.HomeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainScreen(onNavigateToPlayBack: (String) -> Unit) {

    val tabNavController = rememberNavController()
    val mainTabItems = listOf(TabScreen.Home, TabScreen.Favorites)

    Scaffold(topBar = {
        TopAppBar(
            title = { Text(stringResource(R.string.home_page_navigation_title)) })
    }, modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight(), bottomBar = {
        MainBottomNavigationBar(
            mainTabItems = mainTabItems, tabNavController = tabNavController
        )
    }) { innerPadding ->
        ColumContent(
            tabNavController = tabNavController,
            modifier = Modifier.padding(innerPadding),
            onNavigateToPlayBack = onNavigateToPlayBack
        )
    }
}

@Composable
private fun ColumContent(
    tabNavController: NavHostController,
    modifier: Modifier = Modifier,
    onNavigateToPlayBack: (String) -> Unit,
) {
    NavHost(
        modifier = modifier.fillMaxSize(),
        navController = tabNavController,
        startDestination = TabScreen.Home.route,
    ) {
        composable(TabScreen.Home.route) {
            HomeScreen(
                onNavigateToPlayBack = onNavigateToPlayBack
            )
        }
        composable(TabScreen.Favorites.route) {

        }
    }
}