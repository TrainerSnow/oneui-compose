package org.oneui.compose.util

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import org.oneui.compose.theme.OneUITheme
import org.oneui.compose.theme.color.OneUIColorTheme
import org.oneui.compose.widgets.box.RoundedCornerBox
import org.oneui.compose.widgets.box.RoundedCornerBoxDefaults
import org.oneui.compose.widgets.text.TextSeparator


/**
 * Utility composable to be used in [Preview]s
 * Displays the passed content twice, once for each color theme
 *
 * @param title The string label to display for the composable
 * @param content The composable to preview
 */
@Composable
fun OneUIPreview(
    title: String,
    padding: PaddingValues = RoundedCornerBoxDefaults.padding,
    content: @Composable () -> Unit
) {
    Column(
        verticalArrangement = Arrangement
            .spacedBy(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        OneUITheme(
            colorTheme = OneUIColorTheme.getTheme(false)
        ) {
            TextSeparator(text = title)
            RoundedCornerBox(
                padding = padding
            ) {
                content()
            }
        }
        OneUITheme(
            colorTheme = OneUIColorTheme.getTheme(true)
        ) {
            TextSeparator(text = title)
            RoundedCornerBox(
                padding = padding
            ) {
                content()
            }
        }
    }
}