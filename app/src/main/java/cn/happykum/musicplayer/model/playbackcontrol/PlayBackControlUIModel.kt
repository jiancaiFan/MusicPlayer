package cn.happykum.musicplayer.model.playbackcontrol

import cn.happykum.musicplayer.model.response.PlayItem

data class PlayBackControlUIModel(
    val audioItem: PlayItem? = null
) {
    companion object{
        val Empty = PlayBackControlUIModel(
            audioItem = null
        )
    }
}