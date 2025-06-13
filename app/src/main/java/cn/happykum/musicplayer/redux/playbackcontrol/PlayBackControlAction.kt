package cn.happykum.musicplayer.redux.playbackcontrol

import cn.happykum.musicplayer.model.playbackcontrol.PlayBackControlUIModel
import cn.happykum.musicplayer.model.response.PlayItem
import cn.happykum.musicplayer.redux.MusicPlayerAction

sealed class PlayBackControlAction: MusicPlayerAction {

    data class PlayBackControlFetch(val audioId: String, val playList: List<PlayItem>): PlayBackControlAction()
    data class PlayBackControlLoad(val playBackControlUIModel: PlayBackControlUIModel): PlayBackControlAction()
}