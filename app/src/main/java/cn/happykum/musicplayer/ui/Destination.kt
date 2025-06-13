package cn.happykum.musicplayer.ui

import cn.happykum.musicplayer.R

class Destination {
    companion object {
        const val MAIN = "main"
        const val PLAY_BACK_CONTROL = "details"
    }
}

sealed class TabScreen(val route: String, val selectedIcon: Int, val unselectedIcon: Int, val label: String) {
    object Home : TabScreen("home", R.drawable.ic_home_selected, R.drawable.ic_home_unselected, "首页")
    object Favorites : TabScreen("favorites", R.drawable.ic_favorites_selected, R.drawable.ic_favorites_unselected, "收藏")
}