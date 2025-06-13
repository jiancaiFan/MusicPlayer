package cn.happykum.musicplayer.ui.playbackcontrol

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import cn.happykum.musicplayer.redux.playbackcontrol.PlayBackControlState
import cn.happykum.musicplayer.ui.home.cleanTitleTailNumberAndRemoveSpaces

@Composable
internal fun PlayBackControlAudioInfo(
    isPlaying: Boolean,
    playBackControlState: PlayBackControlState
) {
    val uiModel = playBackControlState.playBackControlUIModel
    val rotation = remember { Animatable(0f) }

    if (isPlaying) {
        LaunchedEffect(Unit) {
            var lastTimestamp = withFrameNanos { it }
            while (true) {
                val frameTime = withFrameNanos { it }
                val delta = (frameTime - lastTimestamp) / 1_000_000f
                lastTimestamp = frameTime
                val newValue = (rotation.value + 360f * delta / 8000f) % 360f
                rotation.snapTo(newValue)
            }
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Card(
            modifier = Modifier
                .size(260.dp)
                .shadow(16.dp, CircleShape)
                .clip(CircleShape),
            shape = CircleShape,
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Box(Modifier.fillMaxSize()) {
                Canvas(Modifier.matchParentSize()) {
                    // 1. 主体黑胶本体（径向渐变，中心偏灰，边缘黑）
                    drawCircle(
                        brush = Brush.radialGradient(
                            listOf(
                                Color(0xFF2B2B2D), // 中心偏灰
                                Color(0xFF16171A)  // 边缘更黑
                            ),
                            center = center,
                            radius = size.minDimension / 2
                        ),
                        radius = size.minDimension / 2
                    )

                    // 2. 彩色光带（淡淡的 sweepGradient，仅覆盖外圈）
                    drawCircle(
                        brush = Brush.sweepGradient(
                            listOf(
                                Color.Transparent,
                                Color(0xFFB4C7ED).copy(alpha = 0.35f), // 淡蓝
                                Color(0xFFC7F1E6).copy(alpha = 0.22f), // 淡青
                                Color(0xFFE2E3B4).copy(alpha = 0.15f), // 淡金
                                Color(0xFFD0B4ED).copy(alpha = 0.20f), // 淡紫
                                Color.Transparent
                            )
                        ),
                        radius = size.minDimension / 2 * 0.97f,
                        style = Stroke(width = size.minDimension * 0.16f)
                    )

                    // 3. 黑胶刻纹
                    for (i in 1..18) {
                        drawCircle(
                            color = Color.White.copy(alpha = 0.015f),
                            radius = (size.minDimension / 2 - 10.dp.toPx()) * (1 - i * 0.037f),
                            style = Stroke(0.7.dp.toPx())
                        )
                    }

                    // 4. 高光
                    drawArc(
                        brush = Brush.linearGradient(
                            listOf(
                                Color.White.copy(alpha = 0.15f),
                                Color.Transparent
                            ),
                            start = Offset(size.width * 0.18f, size.height * 0.10f),
                            end = Offset(size.width * 0.65f, size.height * 0.4f)
                        ),
                        startAngle = -38f, sweepAngle = 50f, useCenter = false,
                        topLeft = Offset(size.width * 0.12f, size.height * 0.07f),
                        size = androidx.compose.ui.geometry.Size(size.width * 0.7f, size.height * 0.33f)
                    )
                }
                // 封面图片
                AsyncImage(
                    model = uiModel.audioItem?.media?.img,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(36.dp)
                        .clip(CircleShape)
                        .rotate(rotation.value),
                    contentScale = ContentScale.Crop
                )
                // 中心贴纸
                Canvas(
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.Center)
                ) {
                    drawCircle(
                        brush = Brush.radialGradient(
                            listOf(
                                Color(0xFFE6E6E6), // 银白
                                Color(0xFF7C7C7C), // 炭灰
                                Color(0xFF232325)  // 深灰
                            ),
                            radius = size.minDimension / 2
                        ),
                        radius = size.minDimension / 2
                    )
                    drawCircle(
                        color = Color(0xFF19191B),
                        radius = size.minDimension / 2 * 0.70f
                    )
                    drawCircle(
                        color = Color(0xFF222327),
                        radius = size.minDimension / 12
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(22.dp))
        Text(
            text = uiModel.audioItem?.title?.cleanTitleTailNumberAndRemoveSpaces() ?: "",
            style = MaterialTheme.typography.headlineSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "宁夏交通广播",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}