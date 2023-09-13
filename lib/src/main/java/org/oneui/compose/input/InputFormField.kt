package org.oneui.compose.input

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp
import org.oneui.compose.base.Icon
import org.oneui.compose.base.IconView
import org.oneui.compose.base.iconColors
import org.oneui.compose.theme.OneUITheme
import org.oneui.compose.util.ListPosition
import org.oneui.compose.widgets.EditText
import org.oneui.compose.widgets.box.RoundedCornerListItem

/**
 * Composable to be used in a form. Allows the user to input string values.
 *
 * @param modifier The [Modifier] to apply
 * @param value The value that is currently entered
 * @param onValueChange The callback invoked when the value is changed
 * @param hint The hint to be shown
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
    leadingIcon: (@Composable (focus: Boolean) -> Unit)? = null,
    trailingIcon: (@Composable (focus: Boolean) -> Unit)? = null,
    editText: (@Composable () -> Unit)? = null,
    listPosition: ListPosition = ListPosition.Single
) {
    var isFocused by remember {
        mutableStateOf(false)
    }

    RoundedCornerListItem(
        modifier = modifier
            .animateContentSize(),
        padding = InputFormFieldDefaults.padding,
        listPosition = listPosition
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement
                .spacedBy(InputFormFieldDefaults.iconTextSpacing)
        ) {
            leadingIcon?.let {
                it(isFocused)
                //TODO: this does not automatically change the icons color when the edittext is selected. Do some shenanigans with LocalContentColor here.
            }

            Box(
                modifier = Modifier
                    .weight(1F)
            ) {
                if (editText != null) {
                    editText()
                } else {
                    EditText(
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged {
                                isFocused = it.isFocused || it.hasFocus
                            },
                        value = value,
                        hint = hint ?: "",
                        onValueChange = onValueChange
                    )
                }
            }

            trailingIcon?.let {
                it(isFocused)
            }
        }
    }
}

/**
 * Overload for [InputFormField]. Takes in raw [Icon]'s.
 *
 * @param modifier The [Modifier] to apply
 * @param value The value that is currently entered
 * @param onValueChange The callback invoked when the value is changed
 * @param hint The hint to be shown
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
    leadingIcon: Icon? = null,
    trailingIcon: Icon? = null,
    listPosition: ListPosition = ListPosition.Single
) {
    InputFormField(
        modifier, value, onValueChange, hint,
        leadingIcon = leadingIcon?.let {
            @Composable { focus ->
                IconView(
                    icon = leadingIcon,
                    colors = iconColors(
                        tint = if (focus)
                            OneUITheme.colors.seslPrimaryColor
                        else
                            OneUITheme.colors.seslSecondaryTextColor
                    )
                )
            }
        },
        trailingIcon = trailingIcon?.let {
            @Composable { focus ->
                IconView(
                    icon = trailingIcon,
                    colors = iconColors(
                        tint = if (focus)
                            OneUITheme.colors.seslPrimaryColor
                        else
                            OneUITheme.colors.seslSecondaryTextColor
                    )
                )
            }
        },
        editText = null,
        listPosition
    )
}

object InputFormFieldDefaults {

    val iconTextSpacing = 16.dp

    val padding = PaddingValues(
        vertical = 16.dp,
        horizontal = 24.dp
    )

}