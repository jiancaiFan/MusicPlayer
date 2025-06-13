package cn.happykum.musicplayer

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import cn.happykum.musicplayer.ui.PlayerNavGraph
import cn.happykum.musicplayer.ui.theme.MusicPlayerTheme
import cn.happykum.musicplayer.utils.musicplayer.MusicPlayerUtils

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { _, insets ->
            insets
        }
        setContent {
            MusicPlayerTheme {
                PlayerNavGraph()
            }
        }

        init()
    }

    private fun init(){
        initMusicPlayer()
        initScreenSetup()
    }

    private fun initScreenSetup(){
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

    private fun initMusicPlayer(){
        MusicPlayerUtils.init(applicationContext)
    }
}