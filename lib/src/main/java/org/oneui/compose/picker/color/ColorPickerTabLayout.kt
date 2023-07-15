package org.oneui.compose.picker.color

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import org.oneui.compose.R
import org.oneui.compose.theme.SeslTheme

@Composable
fun ColorPickerTabLayout(
    modifier: Modifier = Modifier,
    selected: ColorPickerTabSelection,
    onSelectedChange: (ColorPickerTabSelection) -> Unit,
    colors: ColorPickerTabLayoutColors = colorPickerTabLayoutColors()
) {
    val margin = PaddingValues(
        horizontal = SeslTheme.dimensions.colorPickerTabLayoutMarginHorizontal
    )
    Row(
        modifier = modifier
            .height(ColorPickerTabLayoutDefaults.height)
            .padding(margin),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ColorPickerTab(
            modifier = Modifier
                .weight(1F),
            title = stringResource(R.string.sesl_picker_tab_swatch),
            selected = selected == ColorPickerTabSelection.Swatches,
            background = colors.tabBackground,
            onClick = { onSelectedChange(ColorPickerTabSelection.Swatches) }
        )
        ColorPickerTab(
            modifier = Modifier
                .weight(1F),
            title = stringResource(R.string.sesl_picker_tab_spectrum),
            selected = selected == ColorPickerTabSelection.Spectrum,
            background = colors.tabBackground,
            onClick = { onSelectedChange(ColorPickerTabSelection.Spectrum) }
        )
    }
}

enum class ColorPickerTabSelection {
    Swatches,
    Spectrum
}

@Composable
internal fun ColorPickerTab(
    modifier: Modifier = Modifier,
    title: String,
    selected: Boolean,
    background: Color,
    onClick: () -> Unit
) {
    val selMod = if (selected) Modifier
        .background(
            color = background,
            shape = ColorPickerTabLayoutDefaults.tabShape
        )
        .padding(ColorPickerTabLayoutDefaults.tabPadding)
        .clip(ColorPickerTabLayoutDefaults.tabShape)
    else Modifier

    val style = with(SeslTheme.types) { if (selected) colorPickerTabSelected else colorPickerTab }

    Text(
        modifier = modifier
            .then(selMod)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                role = Role.Tab,
                onClick = onClick
            ),
        text = title,
        style = style
    )
}

object ColorPickerTabLayoutDefaults {

    val height = 55.dp

    val tabPadding = PaddingValues(
        all = 10.dp
    )
    val tabShape = RoundedCornerShape(20.dp)
}

data class ColorPickerTabLayoutColors(

    val tabBackground: Color

)

@Composable
fun colorPickerTabLayoutColors(
    tabBackground: Color = SeslTheme.colors.colorPickerTabBackground
): ColorPickerTabLayoutColors = ColorPickerTabLayoutColors(
    tabBackground = tabBackground
)