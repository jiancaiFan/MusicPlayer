package cn.happykum.musicplayer.redux.playbackcontrol

import cn.happykum.musicplayer.model.playbackcontrol.PlayBackControlUIModel
import cn.happykum.musicplayer.redux.MusicPlayerState

data class PlayBackControlState(
    val playBackControlUIModel: PlayBackControlUIModel = PlayBackControlUIModel.Empty
): MusicPlayerState<PlayBackControlState, PlayBackControlAction>