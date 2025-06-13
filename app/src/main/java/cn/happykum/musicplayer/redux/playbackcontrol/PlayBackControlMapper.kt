package cn.happykum.musicplayer.redux.playbackcontrol

import cn.happykum.musicplayer.model.response.PlayItem

object PlayBackControlMapper {

    fun toPlayBackControlUIModel(audioId: String,  playList: List<PlayItem>): PlayItem? =
         playList.find { it.id.toString() == audioId }
}