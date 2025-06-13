package cn.happykum.musicplayer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.happykum.musicplayer.redux.StoreManager
import cn.happykum.musicplayer.redux.playlist.PlayListAction
import cn.happykum.musicplayer.redux.playlist.PlayListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PlayListViewModel : ViewModel() {

    private val playlistStore = StoreManager.getPlayListStore()
    private val _playListState = MutableStateFlow(PlayListState())
    val playListState = _playListState.asStateFlow()

    init {
        viewModelScope.launch {
            playlistStore.state.collectLatest {
                _playListState.emit(it)
            }
        }
    }

    fun init() {
        fetchPlayList()
    }

    fun loadByCategory(category: String) {
        fetchPlayList(starNumber = category.toInt())
    }

    private fun fetchPlayList(starNumber: Int = 45, totalCount: Int = 10) {
        playlistStore.send(
            PlayListAction.PlayListFetch(starNumber = starNumber, totalCount = totalCount)
        )
    }
}