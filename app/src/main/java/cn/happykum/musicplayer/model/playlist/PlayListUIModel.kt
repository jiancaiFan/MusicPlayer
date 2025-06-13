package cn.happykum.musicplayer.model.playlist

import cn.happykum.musicplayer.model.response.PlayItem

data class PlayListUIModel(
    val playList: List<PlayItem> = emptyList(),
    val totalPage: Int
) {
    companion object{
        val Empty =  PlayListUIModel(
            playList = emptyList(),
            totalPage = 0
        )
    }
}