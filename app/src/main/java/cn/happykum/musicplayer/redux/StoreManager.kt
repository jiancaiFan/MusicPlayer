package cn.happykum.musicplayer.redux

import android.util.Log
import cn.happykum.musicplayer.redux.playbackcontrol.PlayBackControlAction
import cn.happykum.musicplayer.redux.playbackcontrol.PlayBackControlReducer
import cn.happykum.musicplayer.redux.playbackcontrol.PlayBackControlState
import cn.happykum.musicplayer.redux.playlist.PlayListAction
import cn.happykum.musicplayer.redux.playlist.PlayListReducer
import cn.happykum.musicplayer.redux.playlist.PlayListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface MusicPlayerAction

interface MusicPlayerState<S : MusicPlayerState<S, A>, A : MusicPlayerAction> {
    @Suppress("UNCHECKED_CAST")
    fun withFlowEffect(action: A): S = this as S
}

interface MusicPlayerStore<S : MusicPlayerState<S, A>, A : MusicPlayerAction> {
    val state: StateFlow<S>
    fun send(action: A)
}

object StoreManager {

    private val playListStore by lazy {
        object : MusicPlayerStore<PlayListState, PlayListAction> {
            private val _state = MutableStateFlow(PlayListState())

            override val state: StateFlow<PlayListState> = _state.asStateFlow()

            override fun send(action: PlayListAction) {
                _state.value = PlayListReducer.reduce(state = state.value, action = action)
                Log.d("playListInfo", "${state.value}")
            }
        }
    }

    private val playBackControlStore by lazy {
        object : MusicPlayerStore<PlayBackControlState, PlayBackControlAction> {
            private val _state = MutableStateFlow(PlayBackControlState())

            override val state: StateFlow<PlayBackControlState> = _state.asStateFlow()

            override fun send(action: PlayBackControlAction) {
                _state.value = PlayBackControlReducer.reduce(state = state.value, action = action)
            }
        }
    }

    fun getPlayListStore() = playListStore
    fun getPlayBackControlStore() = playBackControlStore
}