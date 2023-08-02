package org.oneui.compose.dialog

import android.view.Gravity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import org.oneui.compose.theme.OneUITheme
import org.oneui.compose.theme.locals.ProvideBackgroundColor


/**
 * Composable for a oneui-style dialog. This is only the frame and background, and should only rarely
 *     be used standalone.
 *
 * @param modifier The [Modifier] applied to the container
 * @param properties The [DialogProperties] to apply
 * @param onDismissRequest The callback invoked when the dialog should be closed
 * @param colors The [BaseDialogColors] to apply
 * @param content The content to be put inside the dialog
 */
@Composable
fun BaseDialog(
    modifier: Modifier = Modifier,
    colors: BaseDialogColors = baseDialogColors(),
    properties: DialogProperties = DialogProperties(
        usePlatformDefaultWidth = false
    ),
    onDismissRequest: () -> Unit,
    padding: PaddingValues = BaseDialogDefaults.padding,
    width: Dp = (OneUITheme.dimensions.dialogMinWidth * LocalConfiguration.current.screenWidthDp).dp,
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        (LocalView.current.parent as? DialogWindowProvider)?.window?.run {
            setDimAmount(
                BaseDialogDefaults.dimAmount
            )
            setGravity(Gravity.BOTTOM)
        }
        Box(
            modifier = modifier
                .padding(BaseDialogDefaults.margin)
        ) {
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .clip(
                        BaseDialogDefaults.shape
                    )
                    .shadow(
                        elevation = BaseDialogDefaults.elevation
                    )
                    .background(
                        color = colors.background,
                        shape = BaseDialogDefaults.shape
                    )
                    .padding(padding)
                    .wrapContentHeight()
                    .verticalScroll(rememberScrollState())
            ) {
                ProvideBackgroundColor(colors.background) {
                    content()
                }
            }
        }
    }
}


/**
 * Contains the colors for a [BaseDialog]
 */
data class BaseDialogColors(

    val background: Color

)


/**
 * Constructs the default [BaseDialogColors]
 *
 * @param background The background color of the dialog
 */
@Composable
fun baseDialogColors(
    background: Color = OneUITheme.colors.seslDialogWindowBackgroundColorMaterial
): BaseDialogColors = BaseDialogColors(
    background = background
)

/**
 * Contains default values for a [BaseDialog]
 */
object BaseDialogDefaults {

    val padding = PaddingValues(
        all = 24.dp
    )

    val margin = PaddingValues(
        bottom = 12.dp,
        start = 12.dp,
        end = 12.dp
    )

    val shape = RoundedCornerShape(
        size = 26.dp
    )

    const val dimAmount = 0.2F

    val elevation = 1.dp

    const val animDuration = 150

}