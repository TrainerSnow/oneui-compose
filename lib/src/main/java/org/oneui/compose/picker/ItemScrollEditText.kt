package org.oneui.compose.picker

import android.widget.Toast
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import org.oneui.compose.widgets.EditText

/**
 * Composable that configures a normal [EditText] to match the characteristics of an EditText used in an [ItemScroll]
 *
 * @param modifier The [Modifier] to apply to the container
 * @param value The currently typed value
 * @param onValueChange The callback invoked when the user applies its input, so far it matches [predicate]
 * @param style The [TextStyle] to apply to the text
 * @param predicate The check whether the input matches the given criteria
 * @param keyboardOptions The [KeyboardOptions] to apply to the [EditText]
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ItemScrollEditText(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    style: TextStyle,
    predicate: (String) -> Boolean,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    val ctx = LocalContext.current
    val kbc = LocalSoftwareKeyboardController.current
    val focusRequester = remember {
        FocusRequester()
    }

    var buffer by remember {
        mutableStateOf(
            TextFieldValue(
                text = value,
                selection = TextRange(
                    start = 0,
                    end = 0
                )
            )
        )
    }
    val action: KeyboardActionScope.() -> Unit = {
        if (predicate(buffer.text)) {
            onValueChange(buffer.text)
        } else {
            Toast.makeText(ctx, "Invalid value entered", Toast.LENGTH_SHORT).show()
            focusRequester.freeFocus()
            onValueChange(value)
        }
        kbc?.hide()
    }

    val customTextSelectionColors = TextSelectionColors(
        handleColor = Color.Transparent,
        backgroundColor = Color.Transparent
    )

    CompositionLocalProvider(
        LocalTextSelectionColors provides customTextSelectionColors
    ) {
        EditText(
            modifier = modifier
                .onFocusChanged {
                    if (it.isFocused || it.hasFocus) {
                        buffer = buffer.copy(
                            selection = TextRange(
                                start = 0,
                                end = buffer.text.length
                            )
                        )
                    }
                }
                .focusRequester(focusRequester)
                .width(IntrinsicSize.Min),
            value = buffer,
            onValueChange = { buffer = it },
            maxLines = 1,
            style = style,
            keyboardActions = KeyboardActions(
                onDone = action,
            ),
            keyboardOptions = keyboardOptions
        )
    }
}