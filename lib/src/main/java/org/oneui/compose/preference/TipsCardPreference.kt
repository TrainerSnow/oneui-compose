package org.oneui.compose.preference

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.oneui.compose.preference.internal.TipsCardPreferenceButton
import org.oneui.compose.theme.SeslTheme
import org.oneui.compose.widgets.box.RoundedCornerBox
import org.oneui.compose.widgets.box.roundedCornerBoxColors

@Composable
fun TipsCardPreference(
    modifier: Modifier = Modifier,
    colors: TipsCardPreferenceColors = tipsCardPreferenceColors(),
    title: @Composable () -> Unit,
    summary: (@Composable () -> Unit)? = null,
    buttonLabel: @Composable () -> Unit,
    closeIcon: (@Composable () -> Unit)? = null,
    onButtonClick: () -> Unit
) {
    RoundedCornerBox(
        modifier = modifier,
        cornerRadius = TipsCardPreferenceDefaults.cornerRadius,
        colors = roundedCornerBoxColors(
            background = colors.background
        ),
        padding = TipsCardPreferenceDefaults.padding
    ) {
        Column {
            Row(
                modifier = Modifier
                    .padding(TipsCardPreferenceDefaults.titleBoxMargin)
            ) {
                ProvideTextStyle(SeslTheme.types.tipsCardPreferenceTitle) {
                    title()
                }
                Spacer(
                    modifier = Modifier
                        .width(TipsCardPreferenceDefaults.titleCloseSpacing)
                )
                Spacer(
                    modifier = Modifier
                        .weight(1F)
                )
                closeIcon?.let { it() }
            }
            Box(
                modifier = Modifier
                    .padding(TipsCardPreferenceDefaults.summaryMargin)
            ) {
                ProvideTextStyle(SeslTheme.types.tipsCardPreferenceSummary) {
                    summary?.let { it() }
                }
            }
            Spacer(
                modifier = Modifier
                    .height(TipsCardPreferenceDefaults.summaryButtonSpacing)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(TipsCardPreferenceDefaults.buttonSectionMargin),
                horizontalArrangement = Arrangement.End
            ) {
                TipsCardPreferenceButton(
                    onClick = onButtonClick
                ) {
                    buttonLabel()
                }
            }
        }
    }
}

object TipsCardPreferenceDefaults {

    val padding = PaddingValues(
        top = 24.dp
    )

    val titleBoxMargin = PaddingValues(
        start = 24.dp,
        end = 20.dp,
        bottom = 8.dp
    )

    val summaryMargin = PaddingValues(
        start = 24.dp,
        end = 24.dp,
        bottom = 8.dp
    )

    val titleCloseSpacing = 22.dp

    val summaryButtonSpacing = 16.dp

    val cornerRadius = 26.dp

    val buttonSectionMargin = PaddingValues(
        bottom = 14.dp,
        start = 12.dp,
        end = 12.dp,
        top = 4.dp
    )

}

data class TipsCardPreferenceColors(

    val background: Color

)

@Composable
fun tipsCardPreferenceColors(
    background: Color = SeslTheme.colors.tipsCardPreference
): TipsCardPreferenceColors = TipsCardPreferenceColors(
    background = background
)