package cn.happykum.musicplayer.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cn.happykum.musicplayer.redux.StoreManager
import cn.happykum.musicplayer.redux.playbackcontrol.PlayBackControlAction
import cn.happykum.musicplayer.redux.playbackcontrol.PlayBackControlState
import cn.happykum.musicplayer.redux.playlist.PlayListState
import cn.happykum.musicplayer.utils.musicplayer.MusicPlayerUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PlayBackControlViewModel(application: Application) : AndroidViewModel(application) {

    private val playListStore = StoreManager.getPlayListStore()
    private val _playListState = MutableStateFlow(PlayListState())
    val playListState = _playListState.asStateFlow()

    private val playBackControlStore = StoreManager.getPlayBackControlStore()
    private val _playBackControlState = MutableStateFlow(PlayBackControlState())
    val playBackControlState = _playBackControlState.asStateFlow()

    private val _progress = MutableStateFlow(0f)
    val progress: StateFlow<Float> = _progress.asStateFlow()

    init {
        viewModelScope.launch {
            playListStore.state.collectLatest {
                _playListState.emit(it)
            }
        }

        viewModelScope.launch {
            playBackControlStore.state.collectLatest {
                _playBackControlState.emit(it)
            }
        }

        viewModelScope.launch {
            var lastProgress = -1f
            while (true) {
                val progress = MusicPlayerUtils.getProgress()
                if (progress != lastProgress) {
                    _progress.value = progress
                    lastProgress = progress
                }
                delay(100)
            }
        }
    }

    fun init(audioId: String) {
        playBackControlStore.send(
            PlayBackControlAction.PlayBackControlFetch(
                audioId = audioId, playList = playListState.value.playListUIModel.playList
            )
        )
    }
}