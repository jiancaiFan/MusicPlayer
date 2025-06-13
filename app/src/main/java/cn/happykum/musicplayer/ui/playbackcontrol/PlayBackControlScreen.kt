package cn.happykum.musicplayer.ui.playbackcontrol

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import cn.happykum.musicplayer.viewmodel.PlayBackControlViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cn.happykum.musicplayer.R
import cn.happykum.musicplayer.utils.musicplayer.MusicPlayerUtils
import androidx.core.net.toUri
import kotlinx.coroutines.flow.MutableStateFlow

@SuppressLint("ResourceType", "StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PlayBackControlScreen(
    audioId: String?,
    onBackClicked: () -> Unit,
    onNextSongClicked: () -> Unit = {},
    onPreviousSongClicked: () -> Unit = {},
) {

    val playBackControlViewModel: PlayBackControlViewModel = viewModel()
    val playBackControlState by playBackControlViewModel.playBackControlState.collectAsState()
    val isPlaying = remember { MutableStateFlow(false) }
    val currentPosition by playBackControlViewModel.progress.collectAsState()

    LaunchedEffect(Unit) {

        isPlaying.value = MusicPlayerUtils.isPlaying()
        MusicPlayerUtils.setOnPlaybackStateChangeListener(object : MusicPlayerUtils.OnPlaybackStateChangeListener {
            override fun onPlaybackCompleted() {
                isPlaying.value = false
            }
            override fun onPlaybackError(error: Exception?) {
                isPlaying.value = false
            }
        })
    }

    LaunchedEffect(audioId) {
        playBackControlViewModel.init(audioId ?: "")
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = stringResource(id = R.string.play_back_control_detail))
            }, navigationIcon = {
                IconButton(onClick = onBackClicked) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(R.drawable.ic_collapse),
                        contentDescription = stringResource(R.string.app_navigationBar_back_button)
                    )
                }
            }, actions = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = stringResource(R.string.app_navigationBar_more_button)
                    )
                }
            })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterVertically)
        ) {
            PlayBackControlAudioInfo(
                isPlaying = isPlaying.value,
                playBackControlState = playBackControlState
            )

            PlayBackControlToolBar(
                currentPosition = currentPosition,
                onPreviousSongClicked = onPreviousSongClicked,
                onPlayOrPauseClicked = {
                    if (isPlaying.value) {
                        MusicPlayerUtils.pause()
                    } else {
                        MusicPlayerUtils.play(
                            (playBackControlState.playBackControlUIModel.audioItem?.media?.url
                                ?: "").toUri()
                        )
                    }
                    isPlaying.value = !isPlaying.value
                },
                isPlaying = isPlaying.value,
                onNextSongClicked = onNextSongClicked,
                playBackControlState = playBackControlState
            )
        }
    }
}