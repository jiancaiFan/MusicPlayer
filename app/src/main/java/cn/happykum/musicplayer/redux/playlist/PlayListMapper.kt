package cn.happykum.musicplayer.redux.playlist

import cn.happykum.musicplayer.model.playlist.PlayListUIModel
import cn.happykum.musicplayer.model.response.PlayListResponse

object PlayListMapper {

    fun toPlayListUIModel(response: PlayListResponse) = PlayListUIModel(
        response.list.reversed(),
        totalPage = 0
    )
}