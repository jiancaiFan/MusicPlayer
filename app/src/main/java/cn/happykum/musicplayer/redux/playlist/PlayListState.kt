package cn.happykum.musicplayer.redux.playlist

import cn.happykum.musicplayer.model.playlist.PlayListUIModel
import cn.happykum.musicplayer.model.common.BusinessError
import cn.happykum.musicplayer.redux.MusicPlayerState

data class PlayListState(
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val errorInfo: BusinessError? = null,
    val playListUIModel: PlayListUIModel = PlayListUIModel.Empty
): MusicPlayerState<PlayListState, PlayListAction>