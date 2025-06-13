package cn.happykum.musicplayer.ui.home

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.happykum.musicplayer.R

@Composable
internal fun HomePlayLoading(
    message: String = stringResource(id = R.string.play_list_loading_title),
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    indicatorColor: Color = MaterialTheme.colorScheme.primary
) {
    Surface(
        color = backgroundColor.copy(alpha = 0.95f),
        tonalElevation = 6.dp,
        modifier = modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val infiniteTransition = rememberInfiniteTransition(label = "fade-in")
                val alpha by infiniteTransition.animateFloat(
                    initialValue = 0.6f,
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(900, easing = FastOutSlowInEasing),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "alpha-animation"
                )
                CircularProgressIndicator(
                    color = indicatorColor,
                    modifier = Modifier
                        .size(54.dp)
                        .alpha(alpha),
                    trackColor = indicatorColor.copy(alpha = 0.18f),
                    strokeWidth = 5.dp
                )
                Spacer(modifier = Modifier.height(26.dp))
                Text(
                    text = message,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            }
        }
    }
}