package cn.happykum.musicplayer.redux.playbackcontrol

import cn.happykum.musicplayer.model.playbackcontrol.PlayBackControlUIModel
import cn.happykum.musicplayer.model.response.PlayItem
import cn.happykum.musicplayer.redux.StoreManager
import cn.happykum.musicplayer.redux.playbackcontrol.PlayBackControlAction.PlayBackControlFetch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlayBackControlSideEffect {

    private lateinit var action: PlayBackControlAction

    fun setAction(action: PlayBackControlAction): PlayBackControlSideEffect {
        this.action = action
        return this
    }

    fun invoke(): PlayBackControlAction {

        when (action) {
            is PlayBackControlFetch -> {
                (action as PlayBackControlFetch).let {
                    fetchPlayBackControl(
                        audioId = it.audioId,
                        playList = it.playList
                    )
                }
            }

            else -> Unit
        }
        return this.action
    }

    private fun fetchPlayBackControl(audioId: String, playList: List<PlayItem>) {

        CoroutineScope(Dispatchers.IO).launch {

            val store = StoreManager.getPlayBackControlStore()

            store.send(
                PlayBackControlAction.PlayBackControlLoad(
                    PlayBackControlUIModel(
                        audioItem = PlayBackControlMapper.toPlayBackControlUIModel(
                            audioId = audioId,
                            playList = playList
                        )
                    )
                )
            )
        }
    }
}