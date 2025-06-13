package cn.happykum.musicplayer.redux.playlist

object PlayListReducer {

    fun reduce(state: PlayListState, action: PlayListAction): PlayListState = when (action) {

        is PlayListAction.PlayListFetch -> state.copy(
            isLoading = true,
            isError = false,
            errorInfo = null
        ).withFlowEffect(
            PlayListSideEffect().setAction(action).invoke()
        )

        is PlayListAction.PlayListLoad -> state.copy(
            isLoading = false,
            isError = false,
            errorInfo = null,
            playListUIModel = action.playListUIModel
        )

        is PlayListAction.PlayListError -> state.copy(
            isLoading = false,
            isError = true,
            errorInfo = action.errorInfo
        )
    }
}