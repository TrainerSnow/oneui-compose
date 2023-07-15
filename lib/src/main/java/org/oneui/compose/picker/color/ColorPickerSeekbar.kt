package org.oneui.compose.picker.color

import androidx.annotation.FloatRange
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import org.oneui.compose.R
import org.oneui.compose.theme.SeslTheme
import org.oneui.compose.util.debugBorder
import org.oneui.compose.util.mapRange
import org.oneui.compose.widgets.NumberEditText
import kotlin.math.roundToInt

/**
 * Composable for a oneui-style seekbar, to be used on a color picker.
 * This is used for selecting an opacity or saturation value, and visualized those values with a gradient.
 *
 * @param modifier The [Modifier] to apply to the container
 * @param value The currently selected value
 * @param onValueChange The callback invoked when the value is changed
 * @param colorStart The color used at the beginning of the gradient
 * @param colorEnd The color used at the end of the gradient
 * @param title The title to use for the seekbar
 * @param colors The [ColorPickerSeekbarColors] to be used
 */
@Composable
fun ColorPickerSeekbar(
    modifier: Modifier = Modifier,
    @FloatRange(from = 0.0, to = 1.0) value: Float = 0.5F,
    onValueChange: (Float) -> Unit,
    colorStart: Color,
    colorEnd: Color,
    title: String,
    colors: ColorPickerSeekbarColors = colorPickerSeekbarColors()
) {
    val margin = PaddingValues(
        bottom = SeslTheme.dimensions.colorPickerSeekbarMarginBottom,
        start = SeslTheme.dimensions.colorPickerSeekbarMarginHorizontal,
        end = SeslTheme.dimensions.colorPickerSeekbarMarginHorizontal
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(margin)
    ) {
        Column {
            TitleSection(
                modifier = Modifier
                    .debugBorder(),
                title = title
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement
                    .spacedBy(SeslTheme.dimensions.colorPickerSeekbarLabelSpacing)
            ) {
                SliderSection(
                    modifier = Modifier
                        .debugBorder(),
                    progress = value,
                    onProgressChange = onValueChange,
                    colorStart = colorStart,
                    colorEnd = colorEnd,
                    thumbColor = colors.thumbColor,
                    thumbOutlineColor = colors.thumbOutline,
                    rippleColor = colors.ripple
                )
                LabelSection(
                    modifier = Modifier
                        .fillMaxHeight()
                        .debugBorder(),
                    value = (value * 100).roundToInt(),
                    onValueChange = { onValueChange(it / 100F) }
                )
            }
        }
    }
}

@Composable
private fun SliderSection(
    modifier: Modifier = Modifier,
    progress: Float,
    onProgressChange: (Float) -> Unit,
    colorStart: Color,
    colorEnd: Color,
    thumbColor: Color,
    thumbOutlineColor: Color,
    rippleColor: Color
) {
    val density = LocalDensity.current
    var sliderSize by remember {
        mutableStateOf(DpSize(0.dp, 0.dp))
    }
    var isDragging by remember {
        mutableStateOf(false)
    }

    val rippleAlphaFactor by animateFloatAsState(
        targetValue = if (isDragging) 1F else 0F,
        animationSpec = tween(ColorPickerSeekbarDefaults.animDuration),
        label = "Thumb ripple fade in/out"
    )
    Box(
        modifier = modifier
    ) {
        //'Transparent' background
        Image(
            modifier = Modifier
                .height(ColorPickerSeekbarDefaults.trackHeight)
                //When image is laid out, we measure its size, and apply it to the gradient overlay. Not optimal
                .onGloballyPositioned {
                    with(density) {
                        sliderSize = DpSize(
                            width = it.size.width.toDp(),
                            height = it.size.height.toDp()
                        )
                    }
                },
            painter = painterResource(R.drawable.sesl_color_picker_opacity_background),
            contentDescription = null,
            contentScale = ContentScale.FillHeight
        )
        //Gradiant overlay
        Box(
            modifier = Modifier
                .size(sliderSize)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(colorStart, colorEnd)
                    ),
                    shape = RoundedCornerShape(100)
                )
                .clip(
                    RoundedCornerShape(100)
                )
        )
        //Thumb
        Canvas(
            modifier = Modifier
                .size(sliderSize)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = {
                            isDragging = true
                        },
                        onDragEnd = {
                            isDragging = false
                        }
                    ) { change, _ ->
                        val newValue = change.position.x / size.width
                        onProgressChange(newValue.coerceIn(0f, 1f))
                        change.consume()
                    }
                }
        ) {
            val radius = ColorPickerSeekbarDefaults.thumbOuterSize.toPx()
            val start = radius / 2
            val end = size.width - (radius / 2)
            val posX = mapRange(
                value = progress,
                origStart = 0F,
                origEnd = 1F,
                targetStart = start,
                targetEnd = end
            )

            drawThumb(
                color = thumbColor,
                outline = thumbOutlineColor,
                pos = Offset(
                    x = posX,
                    y = size.height / 2
                ),
                ripple = rippleColor.copy(
                    alpha = rippleColor.alpha * rippleAlphaFactor
                )
            )
        }
    }
}

@Composable
private fun TitleSection(
    modifier: Modifier = Modifier,
    title: String
) {
    Box(
        modifier = modifier
            .height(ColorPickerSeekbarDefaults.titleSectionHeight)
            .padding(
                bottom = ColorPickerSeekbarDefaults.titleSectionMarginBottom
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = title,
            style = SeslTheme.types.coloPickerSeekbarTitle
        )
    }
}

@Composable
private fun LabelSection(
    modifier: Modifier = Modifier,
    value: Int,
    onValueChange: (Int) -> Unit
) = Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Start
) {
    Box(
        modifier = Modifier
            .width(ColorPickerSeekbarDefaults.labelEditWidth)
            .wrapContentHeight()
    ) {
        NumberEditText(
            modifier = Modifier
                .fillMaxSize(),
            value = value,
            onValueChange = onValueChange,
            style = SeslTheme.types.colorPickerSeekbarProgressEdit.copy(
                fontSize = with(LocalDensity.current) { 14.dp.toSp() }
            ),
            maxCharacters = 3L,
            minValue = 0,
            maxValue = 100
        )
    }
    Text(
        style = SeslTheme.types.colorPickerSeekbarProgressLabel.copy(
            fontSize = with(LocalDensity.current) { 14.dp.toSp() }
        ),
        text = "%"
    )
}

private fun DrawScope.drawThumb(
    color: Color,
    outline: Color,
    pos: Offset,
    ripple: Color
) {
    drawCircle(
        color = outline,
        radius = ColorPickerSeekbarDefaults.thumbOuterSize.toPx() / 2,
        center = pos
    )
    drawCircle(
        color = color,
        radius = ColorPickerSeekbarDefaults.thumbInnerSize.toPx() / 2,
        center = pos
    )
    drawCircle(
        color = ripple,
        radius = ColorPickerSeekbarDefaults.thumbRippleRadius.toPx(),
        center = pos
    )
}


/**
 * Contains default values for a [ColorPickerSeekbar]
 */
object ColorPickerSeekbarDefaults {

    val titleSectionHeight = 36.dp
    val titleSectionMarginBottom = 1.dp

    val trackHeight = 19.5.dp

    val thumbInnerSize = 16.dp
    val thumbOuterSize = 17.dp

    val thumbRippleRadius = 16.dp

    const val animDuration = 200

    val labelEditWidth = 24.dp

}


/**
 * Contains the colors used for a [ColorPickerSeekbar]
 *
 * @property thumbColor
 * @property thumbOutline
 * @property ripple
 */
data class ColorPickerSeekbarColors(

    val thumbColor: Color,

    val thumbOutline: Color,

    val ripple: Color

)


/**
 * Constructs the default [ColorPickerSeekbarColors]
 *
 * @param thumbColor The color to be used for the thumb
 * @param thumbOutline The color used for the outline of the thumb
 * @param ripple The color used for the ripple effect of he thumb
 * @return
 */
@Composable
fun colorPickerSeekbarColors(
    thumbColor: Color = SeslTheme.colors.colorPickerSeekbarThumb,
    thumbOutline: Color = SeslTheme.colors.colorPickerSeekbarThumbStroke,
    ripple: Color = SeslTheme.colors.seslRippleColor
): ColorPickerSeekbarColors = ColorPickerSeekbarColors(
    thumbColor = thumbColor,
    thumbOutline = thumbOutline,
    ripple = ripple
)