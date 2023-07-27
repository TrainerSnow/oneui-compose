package org.oneui.compose.picker.time

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.oneui.compose.R
import org.oneui.compose.theme.OneUITheme
import org.oneui.compose.util.OneUIPreview


@Composable
internal fun DatePickerHeader(
    modifier: Modifier = Modifier,
    onPrevClick: () -> Unit,
    onNextClick: () -> Unit,
    colors: DatePickerColors = datePickerColors(),
    label: @Composable () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(
                    start = DatePickerDefaults.margin
                )
        ) {
            DatePickerIconButton(
                painter = painterResource(R.drawable.sesl_date_picker_prev),
                onClick = onPrevClick,
                colors = colors
            )
        }

        ProvideTextStyle(OneUITheme.types.datePickerHeader) {
            Box(
                modifier = Modifier
                    .padding(DatePickerDefaults.headerTextPadding)
            ) {
                label()
            }
        }

        Box(
            modifier = Modifier
                .padding(
                    start = DatePickerDefaults.margin
                )
        ) {
            DatePickerIconButton(
                painter = painterResource(R.drawable.sesl_date_picker_next),
                onClick = onNextClick,
                colors = colors
            )
        }
    }
}

@Composable
private fun DatePickerIconButton(
    modifier: Modifier = Modifier,
    painter: Painter,
    onClick: () -> Unit,
    colors: DatePickerColors
) {
    Box(
        modifier = modifier
            .size(OneUITheme.dimensions.datePickerHeaderHeight)
            .clip(CircleShape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    color = colors.ripple,
                    radius = 24.dp
                ),
                role = Role.Button,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painter,
            contentDescription = null
        )
    }
}

object DatePickerDefaults {

    val margin = 15.dp

    val headerTextPadding = PaddingValues(
        horizontal = 8.dp
    )

}

data class DatePickerColors(

    val ripple: Color

)

@Composable
fun datePickerColors(
    ripple: Color = OneUITheme.colors.seslRippleColor
): DatePickerColors = DatePickerColors(
    ripple = ripple
)

@Preview
@Composable
fun DatePickerHeaderPreview() = OneUIPreview(title = "DatePickerHeaderPreview") {
    DatePickerHeader(
        modifier = Modifier
            .height(OneUITheme.dimensions.datePickerHeaderHeight)
            .fillMaxWidth(),
        onPrevClick = { },
        onNextClick = { }
    ) {
        Text(text = "July 2023")
    }
}