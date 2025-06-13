package cn.happykum.musicplayer.redux.playlist

import cn.happykum.musicplayer.model.playlist.PlayListUIModel
import cn.happykum.musicplayer.model.common.BusinessError
import cn.happykum.musicplayer.redux.MusicPlayerAction

sealed class PlayListAction: MusicPlayerAction {

    data class PlayListFetch(val starNumber: Int, val totalCount: Int): PlayListAction()
    data class PlayListLoad(val playListUIModel: PlayListUIModel): PlayListAction()
    data class PlayListError(val errorInfo: BusinessError): PlayListAction()
}