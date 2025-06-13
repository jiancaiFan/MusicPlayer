package cn.happykum.musicplayer.redux.playbackcontrol

import cn.happykum.musicplayer.model.playbackcontrol.PlayBackControlUIModel

object PlayBackControlReducer {

    fun reduce(state: PlayBackControlState, action: PlayBackControlAction): PlayBackControlState = when (action) {
        is PlayBackControlAction.PlayBackControlFetch -> state.copy(
            playBackControlUIModel = PlayBackControlUIModel.Empty
        ).withFlowEffect(
            PlayBackControlSideEffect().setAction(action).invoke()
        )

        is PlayBackControlAction.PlayBackControlLoad -> state.copy(
            playBackControlUIModel = action.playBackControlUIModel
        )
    }
}