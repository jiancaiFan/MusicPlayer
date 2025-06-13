package cn.happykum.musicplayer.ui.main

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import cn.happykum.musicplayer.ui.TabScreen

@Composable
internal fun MainBottomNavigationBar(
    mainTabItems: List<TabScreen>,
    tabNavController: NavHostController
) {

    val navBackStackEntry by tabNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        windowInsets = WindowInsets(0.dp)
    ) {
        mainTabItems.forEach { item ->
            NavigationBarItem(
                label = { Text(item.label) },
                icon = {
                    Icon(
                        modifier = Modifier
                            .size(26.dp)
                            .padding(bottom = 6.dp),
                        painter = if(currentRoute == item.route)
                            painterResource(item.selectedIcon)
                        else  painterResource(item.unselectedIcon) ,
                        contentDescription = item.label
                    )
                },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        tabNavController.navigate(item.route) {
                            popUpTo(tabNavController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}