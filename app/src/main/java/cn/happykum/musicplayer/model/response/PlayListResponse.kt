package cn.happykum.musicplayer.model.response

data class PlayListResponse(
    val errcode: Int,
    val errmsg: String,
    val list: List<PlayItem>,
    val total: Int,
    val count: Int,
    val getnum: Int,
    val pagetotal: Int,
    val page: Int,
)

data class PlayItem(
    val id: Long,
    val title: String,
    val media: MediaData,
    val viewcount: Int,
    val dateline_short: String
)

data class MediaData(
    val url: String,
    val duration: Long,
    val img: String,
    val preview: String
)