package cn.happykum.musicplayer.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cn.happykum.musicplayer.ui.main.MainScreen
import cn.happykum.musicplayer.ui.playbackcontrol.PlayBackControlScreen

@Composable
internal fun PlayerNavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = Destination.MAIN
    ) {
        composable(
            route = Destination.MAIN
        ) {
            MainScreen(
                onNavigateToPlayBack = { itemId ->
                    navController.navigate("${Destination.PLAY_BACK_CONTROL}/${itemId}")
                }
            )
        }
        composable(
            route = "${Destination.PLAY_BACK_CONTROL}/{audioId}",
            arguments = listOf(navArgument("audioId") { type = NavType.StringType }),
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(durationMillis = 400)
                ) + fadeIn(animationSpec = tween(durationMillis = 400))
            },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(durationMillis = 400)
                ) + fadeOut(animationSpec = tween(durationMillis = 400))
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 400))
            },
            popExitTransition = {
                slideOutVertically(
                    targetOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(durationMillis = 400)
                ) + fadeOut(animationSpec = tween(durationMillis = 400))
            }
        ) { backStackEntry ->
            val audioId = backStackEntry.arguments?.getString("audioId")
            PlayBackControlScreen(
                audioId = audioId, onBackClicked = {
                    navController.popBackStack()
                }
            )
        }
    }
}