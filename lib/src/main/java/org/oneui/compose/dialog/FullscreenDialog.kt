package org.oneui.compose.dialog

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import org.oneui.compose.theme.OneUITheme

/**
 * Different layout types that a [FullscreenDialog] can draw
 */
enum class FullscreenDialogLayout {

    /**
     * Normal mode as shown on mobile phones. Dialog takes the full screen size and has buttons aligned at the bottom
     */
    Normal,

    /**
     * As shown on mobile phones when rotated. Dialog takes the full screen size and has buttons aligned in the top right corner
     */
    Landscape,

    /**
     * As shown on large devices like tablets. Dialog takes a phone-screen sized window and is shown as an actual dialog on top of the application
     */
    Floating

    ;

    companion object {

        fun fromSizeClass(sizeClass: WindowSizeClass): FullscreenDialogLayout =
            when (sizeClass.widthSizeClass) {
                WindowWidthSizeClass.Compact -> Normal
                WindowWidthSizeClass.Medium,
                WindowWidthSizeClass.Expanded -> when (sizeClass.heightSizeClass) {
                    WindowHeightSizeClass.Compact -> Landscape
                    else -> Floating
                }

                else -> Normal
            }

    }

}

typealias FullscreenDialogPositionProvider = PopupPositionProvider


/**
 * Composable for a oneui-style fullscreen dialog. This should be used when an important action that's more than a simple prompt for the user is activated, but should not disrupt the user's current location in the application. This could be e.g. a screen to add an item to a list.
 *
 * @param modifier The [Modifier] to apply to the content of the dialog
 * @param onDismissRequest The callback invoked when the dialog should be removed from the screen
 * @param layout The [FullscreenDialogLayout]. This dictates the way the dialog is shown.
 * @param floatingPositionProvider This is used to calculate the position of the Fullscreen dialog *only* when in [FullscreenDialogLayout.Floating] mode. If none is passed, the dialog aligns to the start of the window
 * @param positiveLabel The label for the positive button of the dialog
 * @param onPositiveClick The callback invoked when the positive button is clicked
 * @param negativeLabel TheThe label for the negative button of the dialog
 * @param onNegativeClick The callback invoked when the negative button is clicked
 * @param content The content to be put into the dialog.
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FullscreenDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    layout: FullscreenDialogLayout = FullscreenDialogLayout.Normal,
    floatingPositionProvider: FullscreenDialogPositionProvider? = null,
    positiveLabel: String,
    onPositiveClick: () -> Unit,
    negativeLabel: String? = null,
    onNegativeClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val dialogContent = @Composable {
        FullscreenDialogContent(
            modifier = modifier,
            layout = layout,
            positiveLabel = positiveLabel,
            onPositiveClick = onPositiveClick,
            negativeLabel = negativeLabel,
            onNegativeClick = onNegativeClick,
            content = content
        )
    }

    if (floatingPositionProvider != null) {
        Popup(
            onDismissRequest = onDismissRequest,
            popupPositionProvider = floatingPositionProvider,
            content = dialogContent,
            properties = PopupProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
                usePlatformDefaultWidth = false,
                focusable = true,
                excludeFromSystemGesture = false
            )
        )
    } else {
        Popup(
            onDismissRequest = onDismissRequest,
            content = dialogContent,
            properties = PopupProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
                usePlatformDefaultWidth = false,
                focusable = true,
                excludeFromSystemGesture = false
            )
        )
    }
}

/**
 * Composable for the content of a fullscreen dialog. This constructs the visuals of a fullscreen dialog but does not display it as a dialog, only as a normal composable
 *
 * @param modifier The [Modifier] to apply to the content
 * @param layout The [FullscreenDialogLayout]. This dictates the way the content is shown.
 * @param positiveLabel The label for the positive button of the dialog
 * @param onPositiveClick The callback invoked when the positive button is clicked
 * @param negativeLabel TheThe label for the negative button of the dialog
 * @param onNegativeClick The callback invoked when the negative button is clicked
 * @param content The content to be put into the dialog.
 */
@Composable
fun FullscreenDialogContent(
    modifier: Modifier = Modifier,
    layout: FullscreenDialogLayout = FullscreenDialogLayout.Normal,
    positiveLabel: String,
    onPositiveClick: () -> Unit,
    negativeLabel: String? = null,
    onNegativeClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val mod = modifier
        .then(
            if (layout == FullscreenDialogLayout.Floating)
                Modifier
                    .size(FullscreenDialogDefaults.floatingSize)
                    .clip(FullscreenDialogDefaults.floatingShape)
                    .border(
                        width = FullscreenDialogDefaults.floatingBorderWidth,
                        color = OneUITheme.colors.seslListDividerColor,
                        shape = FullscreenDialogDefaults.floatingShape
                    )
            else Modifier.fillMaxSize()
        )

    Box(
        modifier = mod
            .background(OneUITheme.colors.seslBackgroundColor)
    ) {
        FullscreenDialogLayout(
            modifier = Modifier
                .fillMaxSize(),
            horizontalMode = layout == FullscreenDialogLayout.Landscape,
            positiveLabel, onPositiveClick, negativeLabel, onNegativeClick, content
        )
    }
}


@Composable
private fun FullscreenDialogLayout(
    modifier: Modifier = Modifier,
    horizontalMode: Boolean = false,
    positiveLabel: String,
    onPositiveClick: () -> Unit,
    negativeLabel: String? = null,
    onNegativeClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        if (horizontalMode) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(FullscreenDialogDefaults.buttonRowPadding),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                negativeLabel?.let { label ->
                    FullscreenDialogButton(
                        label = label,
                        onClick = { onNegativeClick?.let { it() } }
                    )
                }
                FullscreenDialogButton(
                    label = positiveLabel,
                    onClick = onPositiveClick
                )
            }
        }
        Box(
            modifier = Modifier.weight(1F)
        ) {
            content()
        }
        if (!horizontalMode) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(FullscreenDialogDefaults.buttonRowPadding),
                verticalAlignment = Alignment.CenterVertically
            ) {
                negativeLabel?.let { label ->
                    FullscreenDialogButton(
                        modifier = Modifier
                            .weight(1F),
                        label = label,
                        onClick = { onNegativeClick?.let { it() } }
                    )
                }
                FullscreenDialogButton(
                    modifier = Modifier
                        .weight(1F),
                    label = positiveLabel,
                    onClick = onPositiveClick
                )
            }
        }
    }
}

@Composable
private fun FullscreenDialogButton(
    modifier: Modifier = Modifier,
    label: String,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    color = OneUITheme.colors.seslRippleColor
                ),
                onClick = onClick
            )
            .padding(FullscreenDialogDefaults.buttonPadding),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            style = OneUITheme.types.fullscreenDialogButtonLabel
        )
    }
}

/**
 * Contains default values for a [FullscreenDialog]
 */
object FullscreenDialogDefaults {

    val buttonPadding = PaddingValues(
        vertical = 12.dp,
        horizontal = 8.dp
    )
    val buttonRowPadding = PaddingValues(
        horizontal = 16.dp,
        vertical = 4.dp
    )

    val floatingSize = DpSize(360.dp, 570.dp)
    val floatingShape = RoundedCornerShape(26.dp)
    val floatingBorderWidth = 0.5.dp

}

@Preview(device = "spec:width=411dp,height=891dp")
@Composable
private fun FullscreenDialogPreview() {
    FullscreenDialog(
        onDismissRequest = { },
        positiveLabel = "Save",
        negativeLabel = "Cancel",
        onPositiveClick = { },
        onNegativeClick = { },
        layout = FullscreenDialogLayout.Normal
    ) {
        Column {
            Text("Test text")
        }
    }
}

@Preview(device = "spec:width=891dp,height=411dp")
@Composable
private fun FullscreenDialogPreviewHorizontal() {
    FullscreenDialog(
        onDismissRequest = { },
        positiveLabel = "Save",
        negativeLabel = "Cancel",
        onPositiveClick = { },
        onNegativeClick = { },
        layout = FullscreenDialogLayout.Landscape
    ) {
        Column {
            Text("Test text")
        }
    }
}

@Preview(device = "spec:width=1280dp,height=800dp,dpi=240")
@Composable
private fun FullscreenDialogPreviewFloating() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(OneUITheme.colors.seslBackgroundColor)
    ) {
        FullscreenDialog(
            onDismissRequest = { },
            positiveLabel = "Save",
            negativeLabel = "Cancel",
            onPositiveClick = { },
            onNegativeClick = { },
            layout = FullscreenDialogLayout.Floating
        ) {
            Column {
                Text("Test text")
            }
        }
    }
}