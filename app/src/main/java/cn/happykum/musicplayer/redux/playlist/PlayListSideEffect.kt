package cn.happykum.musicplayer.redux.playlist

import android.util.Log
import cn.happykum.musicplayer.model.request.PlayListRequest
import cn.happykum.musicplayer.redux.StoreManager
import cn.happykum.musicplayer.repository.PlayListRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlayListSideEffect {

    private lateinit var action: PlayListAction

    fun setAction(action: PlayListAction): PlayListSideEffect {
        this.action = action
        return this
    }

    fun invoke(): PlayListAction {

        when (action) {
            is PlayListAction.PlayListFetch -> {
                (action as PlayListAction.PlayListFetch).let {
                    fetchPlayList(
                        starNumber = it.starNumber, totalCount = it.totalCount
                    )
                }
            }

            else -> Unit
        }
        return this.action
    }

    private fun fetchPlayList(starNumber: Int, totalCount: Int) {

        CoroutineScope(Dispatchers.IO).launch {

            val store = StoreManager.getPlayListStore()

            val request = PlayListRequest(starNumber = starNumber, totalCount = totalCount)

            PlayListRepository.retrieve(request).fold(
                onSuccess = { response ->
                    //Log.d("playListSuccess", response.toString())
                    store.send(
                        PlayListAction.PlayListLoad(
                            PlayListMapper.toPlayListUIModel(response)
                        )
                    )
                },
                onFailure = { error ->

                }
            )
        }
    }
}