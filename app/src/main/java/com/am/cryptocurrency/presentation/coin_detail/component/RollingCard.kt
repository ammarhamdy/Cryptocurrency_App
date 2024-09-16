package com.am.cryptocurrency.presentation.coin_detail.component

import android.graphics.PathMeasure
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.am.cryptocurrency.R
import com.am.cryptocurrency.presentation.ui.theme.CryptocurrencyTheme


private object DefaultStroke {
    val stroke = Stroke(
        width = 5f,
        cap = StrokeCap.Round,
        join = StrokeJoin.Round
    )
}

@Composable
fun RollingCard(
    name: String,
    symbol: String,
    color: Color,
    sharpnessDp: Dp,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        shape = CutCornerShape(sharpnessDp),
        border = BorderStroke(0.dp, color),
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.tiny_padding)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.small_padding))
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.headlineMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = symbol,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

fun Modifier.drawStokeOver(
    sharpnessDp: Dp,
    progress: Float,
    color: Color
) =
    drawWithContent {
        drawContent()
        val sharpness = sharpnessDp.toPx()
        val width = size.width
        val height = size.height
        val gap = 10f
        // top right path
        val topPath = Path().apply {
            moveTo(width/2, gap)
            lineTo(width - sharpness - gap, gap)
            lineTo(width - gap, sharpness + gap)
            lineTo(width - gap, height / 2)
        }
        val topPathMeasure = PathMeasure(topPath.asAndroidPath(), false)
        val animatedTopPath = Path()
        topPathMeasure.getSegment(
            0f,
            progress * topPathMeasure.length,
            animatedTopPath.asAndroidPath(),
            true
        )
        // bottom left path
        val bottomPath = Path().apply {
            moveTo(width/2, height - gap)
            lineTo(sharpness + gap, height - gap)
            lineTo(gap, height - sharpness - gap)
        }
        val bottomPathMeasure = PathMeasure(bottomPath.asAndroidPath(), false)
        val animatedBottomPath = Path()
        bottomPathMeasure.getSegment(
            0f,
            progress * bottomPathMeasure.length,
            animatedBottomPath.asAndroidPath(),
            true
        )
        // top left path
        val topLeftPath = Path().apply {
            moveTo(gap, sharpness + gap)
            lineTo(sharpness + gap, gap)
        }
        val topRightPathMeasure = PathMeasure(topLeftPath.asAndroidPath(), false)
        val animatedTopRightPath = Path()
        topRightPathMeasure.getSegment(
            0f,
            progress * topRightPathMeasure.length,
            animatedTopRightPath.asAndroidPath(),
            true
        )
        // start drawing.
        drawPath(
            path = animatedTopPath,
            color = color,
            style = DefaultStroke.stroke
        )
        drawPath(
            path = animatedBottomPath,
            color = color,
            style = DefaultStroke.stroke
        )
        drawPath(
            path = animatedTopRightPath,
            color = color,
            style = DefaultStroke.stroke
        )
    }

@Composable
fun Modifier.drawEdgesOver(
    sharpnessDp: Dp,
    progress: Float,
    color: Color
) = drawWithContent {
    drawContent()
    val sharpness = sharpnessDp.toPx()
    val width = size.width
    val height = size.height
    val gap = 10f
    // top left path
    val topLeftPath = Path().apply {
        moveTo(gap, sharpness + gap)
        lineTo(sharpness + gap, gap)
    }
    val topLeftPathMeasure = PathMeasure(topLeftPath.asAndroidPath(), false)
    val animatedTopLeftPath = Path()
    topLeftPathMeasure.getSegment(
        0f,
        progress * topLeftPathMeasure.length,
        animatedTopLeftPath.asAndroidPath(),
        true
    )
    // top right path
    val topRightPath = Path().apply {
        moveTo(width - sharpness - gap, gap)
        lineTo(width - gap, sharpness + gap)
    }
    val topRightPathMeasure = PathMeasure(topRightPath.asAndroidPath(), false)
    val animatedTopRightPath = Path()
    topRightPathMeasure.getSegment(
        0f,
        progress * topRightPathMeasure.length,
        animatedTopRightPath.asAndroidPath(),
        true
    )
    // bottom right path
    val bottomRightPath = Path().apply {
        moveTo(width - gap, height - sharpness - gap)
        lineTo(width - sharpness - gap, height - gap)
    }
    val bottomRightMeasure = PathMeasure(bottomRightPath.asAndroidPath(), false)
    val animatedBottomRightPath = Path()
    bottomRightMeasure.getSegment(
        0f,
        progress * bottomRightMeasure.length,
        animatedBottomRightPath.asAndroidPath(),
        true
    )
    // bottom left path
    val bottomLeftPath = Path().apply {
        moveTo(gap + sharpness, height - gap)
        lineTo(gap, height - gap - sharpness)
    }
    val bottomLeftMeasure = PathMeasure(bottomLeftPath.asAndroidPath(), false)
    val animatedBottomLeftPath = Path()
    bottomLeftMeasure.getSegment(
        0f,
        progress * bottomLeftMeasure.length,
        animatedBottomLeftPath.asAndroidPath(),
        true
    )
    // start drawing.
    drawPath(
        path = animatedTopLeftPath,
        color = color,
        style = DefaultStroke.stroke
    )
    drawPath(
        path = animatedTopRightPath,
        color = color,
        style = DefaultStroke.stroke
    )
    drawPath(
        path = animatedBottomRightPath,
        color = color,
        style = DefaultStroke.stroke
    )
    drawPath(
        path = animatedBottomLeftPath,
        color = color,
        style = DefaultStroke.stroke
    )
}

@Preview
@Composable
private fun RollingCardPreview() {
    CryptocurrencyTheme {
        val strokeProgressAnim = remember { Animatable(0f) }
        val sharpness = dimensionResource(R.dimen.small_padding)
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .size(width = 350.dp, height = 600.dp)
        ) {
            RollingCard(
                "title",
                "sub_title",
                MaterialTheme.colorScheme.primary,
                sharpness,
                Modifier.drawStokeOver(
                    sharpness,
                    strokeProgressAnim.value,
                    MaterialTheme.colorScheme.tertiary
                )
            )
            Spacer(modifier = Modifier.height(50.dp))
            RollingCard(
                "title",
                "sub_title",
                MaterialTheme.colorScheme.primary,
                sharpness,
                Modifier.drawEdgesOver(
                    sharpness,
                    strokeProgressAnim.value,
                    MaterialTheme.colorScheme.tertiary
                ),

            )
        }
        LaunchedEffect(Unit) {
            strokeProgressAnim.animateTo(
                targetValue = 1f,
                animationSpec = tween(7000, delayMillis = 1000)
            )
        }
    }
}