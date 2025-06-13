package cn.happykum.musicplayer.repository

import android.util.Log
import cn.happykum.musicplayer.model.request.PlayListRequest
import cn.happykum.musicplayer.model.response.PlayListResponse
import cn.happykum.musicplayer.utils.network.NetworkRequestUtil
import com.google.gson.Gson

object PlayListRepository {

    private val gson = Gson()

    suspend fun retrieve(request: PlayListRequest): Result<PlayListResponse> {
        return runCatching {
            val url = "https://api.ningxiahuangheyun.com?mod=get_column_article&type=list&cid=1005293&page=${request.starNumber}&num=${request.totalCount}&keywords="
            val response = NetworkRequestUtil.get(url = url)
            val json = response.body?.string() ?: throw Exception("response body is null")

            gson.fromJson(json, PlayListResponse::class.java)
        }
    }
}