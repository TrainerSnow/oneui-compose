package org.oneui.compose.input

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import org.oneui.compose.base.Icon
import org.oneui.compose.base.IconView
import org.oneui.compose.base.iconColors
import org.oneui.compose.theme.OneUITheme
import org.oneui.compose.util.ListPosition
import org.oneui.compose.widgets.EditText
import org.oneui.compose.widgets.box.RoundedCornerBox
import org.oneui.compose.widgets.box.RoundedCornerBoxDefaults
import org.oneui.compose.widgets.box.RoundedCornerListItem
import org.oneui.compose.widgets.buttons.IconButton
import org.oneui.compose.widgets.buttons.iconButtonColors
import dev.oneuiproject.oneui.R as IconR

/**
 * Composable to be used in a form. Allows the user to input string values.
 *
 * @param modifier The [Modifier] to apply
 * @param value The value that is currently entered
 * @param onValueChange The callback invoked when the value is changed
 * @param hint The hint to be shown
 * @param error The error, or null if none is present
 * @param errorMode The way the error message is shown
 * @param leadingIcon The icon shown at the start
 * @param trailingIcon The icon shown at the end
 * @param editText Optional: a custom [EditText] to configure things such as inputtype
 * @param listPosition The [ListPosition]
 */
@Composable
fun InputFormField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String? = null,
    error: String? = null,
    leadingIcon: (@Composable (focus: Boolean) -> Unit)? = null,
    trailingIcon: (@Composable (focus: Boolean) -> Unit)? = null,
    editText: (@Composable () -> Unit)? = null,
    listPosition: ListPosition = ListPosition.Single
) {
    var isFocused by remember {
        mutableStateOf(false)
    }
    var showErrorPopup by remember { mutableStateOf(false) }

    RoundedCornerListItem(
        modifier = modifier.animateContentSize(),
        padding = InputFormFieldDefaults.padding,
        listPosition = listPosition
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(InputFormFieldDefaults.iconTextSpacing)
            ) {
                leadingIcon?.let {
                    it(isFocused)
                }

                Column(
                    modifier = Modifier.weight(1F)
                ) {
                    if (editText != null) {
                        editText()
                    } else {
                        EditText(
                            modifier = Modifier
                                .fillMaxWidth()
                                .onFocusChanged {
                                    isFocused = it.isFocused || it.hasFocus
                                }, value = value, hint = hint ?: "", onValueChange = onValueChange
                        )
                    }
                }

                if (error != null) {
                    IconButton(
                        icon = Icon.Resource(IconR.drawable.ic_oui_error_filled),
                        colors = iconButtonColors(
                            tint = OneUITheme.colors.seslErrorColor
                        ),
                        onClick = {
                            showErrorPopup = !showErrorPopup
                        },
                        padding = PaddingValues()
                    )
                }

                trailingIcon?.let {
                    it(isFocused)
                }
            }
        }

        InputFormErrorPopup(
            visible = showErrorPopup,
            text = error,
            onDismissRequest = {
                showErrorPopup = false
            }
        )
    }
}

/**
 * Overload for [InputFormField]. Takes in raw [Icon]'s.
 *
 * @param modifier The [Modifier] to apply
 * @param value The value that is currently entered
 * @param onValueChange The callback invoked when the value is changed
 * @param hint The hint to be shown
 * @param error The error, or null if none is present
 * @param errorMode The way the error message is shown
 * @param leadingIcon The icon shown at the start
 * @param trailingIcon The icon shown at the end
 * @param listPosition The [ListPosition]
 */
@Composable
fun InputFormField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String? = null,
    error: String? = null,
    leadingIcon: Icon? = null,
    trailingIcon: Icon? = null,
    listPosition: ListPosition = ListPosition.Single
) {
    InputFormField(modifier, value, onValueChange, hint, error, leadingIcon = leadingIcon?.let {
        @Composable { focus ->
            IconView(
                icon = leadingIcon, colors = iconColors(
                    tint = if (focus) OneUITheme.colors.seslPrimaryColor
                    else OneUITheme.colors.seslSecondaryTextColor
                )
            )
        }
    }, trailingIcon = trailingIcon?.let {
        @Composable { focus ->
            IconView(
                icon = trailingIcon, colors = iconColors(
                    tint = if (focus) OneUITheme.colors.seslPrimaryColor
                    else OneUITheme.colors.seslSecondaryTextColor
                )
            )
        }
    }, editText = null, listPosition
    )
}

@Composable
private fun InputFormErrorPopup(
    modifier: Modifier = Modifier,
    visible: Boolean,
    text: String?,
    onDismissRequest: () -> Unit
) {
    val style = TextStyle(
        color = OneUITheme.colors.seslErrorColor,
        fontSize = 15.sp
    )

    val density = LocalDensity.current

    val posProv = object : PopupPositionProvider {

        override fun calculatePosition(
            anchorBounds: IntRect,
            windowSize: IntSize,
            layoutDirection: LayoutDirection,
            popupContentSize: IntSize
        ): IntOffset {
            // Top border of the input form field
            val topBorder = anchorBounds.top - with(density) {
                RoundedCornerBoxDefaults.padding.calculateTopPadding().toPx()
            }.toInt()

            val targetOffset =
                IntOffset(anchorBounds.left, topBorder - popupContentSize.height - 30)

            return if (windowSize.height < targetOffset.y || windowSize.width < targetOffset.x) IntOffset.Zero
            else targetOffset
        }

    }

    if (visible && text != null) {
        Popup(
            onDismissRequest = onDismissRequest,
            popupPositionProvider = posProv
        ) {
            RoundedCornerBox(
                modifier = modifier.shadow(6.dp, RoundedCornerBoxDefaults.shape)
            ) {
                Text(
                    text = text,
                    style = style
                )
            }
        }
    }
}

object InputFormFieldDefaults {

    val iconTextSpacing = 16.dp

    val padding = PaddingValues(
        vertical = 16.dp,
        horizontal = 24.dp
    )

}

@Preview
@Composable
private fun Preview1() {
    InputFormField(
        modifier = Modifier.width(250.dp),
        value = "",
        hint = "E-mail address",
        leadingIcon = Icon.Resource(IconR.drawable.ic_oui_contact),
        onValueChange = { },
    )
}

@Preview
@Composable
private fun Preview2() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        InputFormField(
            modifier = Modifier.width(250.dp),
            value = "asddasasd",
            hint = "E-mail address",
            error = "The value must be a valid email address.",
            leadingIcon = Icon.Resource(IconR.drawable.ic_oui_contact),
            onValueChange = { },
        )
    }
}