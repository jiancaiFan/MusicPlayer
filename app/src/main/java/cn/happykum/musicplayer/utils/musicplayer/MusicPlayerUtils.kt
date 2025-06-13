package cn.happykum.musicplayer.utils.musicplayer

import android.content.Context
import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer

object MusicPlayerUtils {

    interface OnPlaybackStateChangeListener {
        fun onPlaybackCompleted()
        fun onPlaybackError(error: Exception?)
    }

    private var exoPlayer: ExoPlayer? = null
    private var listener: OnPlaybackStateChangeListener? = null
    private var isInitialized = false
    private var currentUri: Uri? = null

    fun init(context: Context) {
        if (!isInitialized) {
            exoPlayer = ExoPlayer.Builder(context.applicationContext).build().apply {
                addListener(object : Player.Listener {
                    override fun onPlaybackStateChanged(state: Int) {
                        if (state == Player.STATE_ENDED) {
                            listener?.onPlaybackCompleted()
                        }
                    }

                    override fun onPlayerError(error: PlaybackException) {
                        listener?.onPlaybackError(error)
                    }
                })
            }
            isInitialized = true
        }
    }

    /**
     * 设置播放状态监听器
     */
    fun setOnPlaybackStateChangeListener(l: OnPlaybackStateChangeListener?) {
        listener = l
    }

    /**
     * 播放音乐
     * - 如果是新资源，则重头播放
     * - 如果是同一资源，暂停时恢复播放，播放结束时重头
     * - 如果正在播放同一资源，则不做操作
     */
    fun play(uri: Uri, looping: Boolean = false) {
        checkInitialized()
        if (currentUri != uri || exoPlayer?.mediaItemCount == 0) {
            // 切歌或首次播放
            exoPlayer?.setMediaItem(MediaItem.fromUri(uri))
            exoPlayer?.repeatMode = if (looping) Player.REPEAT_MODE_ONE else Player.REPEAT_MODE_OFF
            currentUri = uri
            exoPlayer?.prepare()
            exoPlayer?.play()
        } else {
            // 同一首歌
            when {
                exoPlayer?.playbackState == Player.STATE_ENDED -> {
                    // 播放完毕，重头播放
                    exoPlayer?.seekTo(0)
                    exoPlayer?.play()
                }
                exoPlayer?.isPlaying == false -> {
                    // 暂停状态，恢复播放
                    exoPlayer?.play()
                }
                // 如果已经在播放，不做任何事
            }
        }
    }

    /**
     * 恢复播放（推荐只在明确需要恢复时调用）
     */
    fun resume() {
        checkInitialized()
        if (exoPlayer?.isPlaying == false) {
            exoPlayer?.play()
        }
    }

    /**
     * 暂停播放
     */
    fun pause() {
        checkInitialized()
        if (exoPlayer?.isPlaying == true) {
            exoPlayer?.pause()
        }
    }

    /**
     * 停止播放并重置进度
     */
    fun stop() {
        checkInitialized()
        exoPlayer?.stop()
        exoPlayer?.seekTo(0)
    }

    /**
     * 跳转到指定进度（单位：毫秒）
     */
    fun seekTo(positionMs: Long) {
        checkInitialized()
        exoPlayer?.seekTo(positionMs)
    }

    /**
     * 是否正在播放
     */
    fun isPlaying(): Boolean {
        checkInitialized()
        return exoPlayer?.isPlaying ?: false
    }

    /**
     * 获取当前播放进度（单位：毫秒）
     */
    fun getCurrentPosition(): Long {
        checkInitialized()
        return exoPlayer?.currentPosition ?: 0L
    }

    /**
     * 获取音频总时长（单位：毫秒）
     */
    fun getDuration(): Long {
        checkInitialized()
        return exoPlayer?.duration ?: 0L
    }

    /**
     * 获取当前播放进度百分比（范围 0.0f ~ 1.0f）
     */
    fun getProgress(): Float {
        checkInitialized()
        val duration = exoPlayer?.duration ?: 0L
        val position = exoPlayer?.currentPosition ?: 0L
        return if (duration > 0) position.toFloat() / duration else 0f
    }

    /**
     * 切歌（和 play 效果一致，保留接口便于扩展）
     */
    fun playNext(uri: Uri, looping: Boolean = false) {
        play(uri, looping)
    }

    /**
     * 重新播放当前歌曲（从头播放）
     */
    fun replay() {
        checkInitialized()
        exoPlayer?.seekTo(0)
        exoPlayer?.play()
    }

    /**
     * 释放播放器资源，建议在退出或不再使用时调用
     */
    fun release() {
        exoPlayer?.release()
        exoPlayer = null
        currentUri = null
        isInitialized = false
    }

    /**
     * 确保播放器初始化
     */
    private fun checkInitialized() {
        check(isInitialized) { "MusicPlayerUtils must be initialized with init(context) before use." }
    }
}