package cn.happykum.musicplayer.ui.playbackcontrol

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.happykum.musicplayer.R
import cn.happykum.musicplayer.redux.playbackcontrol.PlayBackControlState
import cn.happykum.musicplayer.ui.home.toMinuteSecond
import cn.happykum.musicplayer.utils.musicplayer.MusicPlayerUtils

@Composable
internal fun PlayBackControlToolBar(
    currentPosition: Float,
    onPreviousSongClicked: () -> Unit,
    onPlayOrPauseClicked: () -> Unit,
    isPlaying: Boolean,
    onNextSongClicked: () -> Unit,
    playBackControlState: PlayBackControlState,
) {

    val uiModel = playBackControlState.playBackControlUIModel

    var sliderPosition by remember { mutableFloatStateOf(currentPosition) }
    var isUserDragging by remember { mutableStateOf(false) }

    LaunchedEffect(currentPosition) {
        if (!isUserDragging) sliderPosition = currentPosition
    }

    Column {
        Slider(
            value = sliderPosition,
            onValueChange = { value ->
                isUserDragging = true
                sliderPosition = value
            },
            onValueChangeFinished = {
                isUserDragging = false
                val duration = MusicPlayerUtils.getDuration()
                MusicPlayerUtils.seekTo((duration * sliderPosition).toLong())
            },
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.primary
            )
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = (currentPosition * MusicPlayerUtils.getDuration()).toLong().toMinuteSecond(), fontSize = 12.sp)
            Text(text = uiModel.audioItem?.media?.duration?.toMinuteSecond() ?: "", fontSize = 12.sp)
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onPreviousSongClicked) {
            Icon(
                painter = painterResource(R.drawable.ic_previous_song),
                contentDescription = stringResource(R.string.play_back_controlBar_previous_song),
                tint = MaterialTheme.colorScheme.primary
            )
        }
        FilledIconButton(
            onClick = onPlayOrPauseClicked,
            modifier = Modifier
                .size(72.dp)
        ) {
            Icon(
                painter = if (isPlaying) painterResource(R.drawable.ic_pause_song)
                else painterResource(R.drawable.ic_play_song),
                contentDescription = if (isPlaying)
                    stringResource(R.string.play_back_controlBar_pause)
                else
                    stringResource(R.string.play_back_controlBar_play),
                modifier = Modifier.size(36.dp)
            )
        }
        IconButton(onClick = onNextSongClicked) {
            Icon(
                painter = painterResource(R.drawable.ic_next_song),
                contentDescription = stringResource(R.string.play_back_controlBar_next_song),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}