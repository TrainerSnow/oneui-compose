package org.oneui.compose.widgets.text

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import org.oneui.compose.theme.SeslTheme

/**
 * Contains default values for a [TextSeparator]
 */
object TextSeparatorDefaults {

    val padding = PaddingValues(
        top = 11.75.dp,
        bottom = 5.dp,
        start = 18.dp
    )

}


/**
 * Composable for a ouneui-style text-list-separator
 * TODO: Preview image
 *
 * @param modifier The modifier to be applied to the container
 * @param text The text to be displayed
 * @param textStyle The [TextStyle] to be used
 * @param padding The padding to be applied
 */
@Composable
fun TextSeparator(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = SeslTheme.types.textSeparator,
    padding: PaddingValues = TextSeparatorDefaults.padding
) {
    Text(
        modifier = modifier
            .padding(padding),
        text = text,
        style = textStyle
    )
}