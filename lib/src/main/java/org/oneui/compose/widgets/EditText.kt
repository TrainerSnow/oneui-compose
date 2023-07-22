package org.oneui.compose.widgets

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import org.oneui.compose.theme.OneUITheme
import org.oneui.compose.util.allIn

@Composable
fun EditText(
    modifier: Modifier = Modifier,
    value: String = "",
    hint: String = "",
    onValueChange: (String) -> Unit,
    colors: EditTextColors = editTextColors(),
    enabled: Boolean = true,
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    style: TextStyle = OneUITheme.types.editTextText,
    hintStyle: TextStyle = OneUITheme.types.editTextHint,
    maxCharacters: Long? = null,
    characters: CharArray? = null,
    decorationBox: @Composable (innerTextField: @Composable () -> Unit) -> Unit =
        @Composable { innerTextField -> innerTextField() }
) {
    val showHint = value.isEmpty()

    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomStart
    ) {
        if (showHint) {
            Text(
                text = hint,
                style = hintStyle
            )
        }
        BasicTextField(
            value,
            onValueChange = {
                val nonValid = (maxCharacters != null && it.length > maxCharacters) ||
                        (characters != null && !it.toCharArray().toList()
                            .allIn(characters.toList()))
                if (!nonValid) onValueChange(it)
            },
            modifier = modifier
                .fillMaxWidth(),
            enabled,
            readOnly,
            textStyle = style,
            keyboardOptions,
            keyboardActions,
            singleLine,
            maxLines,
            minLines,
            visualTransformation,
            onTextLayout,
            interactionSource,
            cursorBrush = SolidColor(colors.cursor),
            decorationBox
        )
    }
}

@Composable
fun UnderlinedEditText(
    modifier: Modifier = Modifier,
    value: String = "",
    hint: String = "",
    onValueChange: (String) -> Unit,
    colors: EditTextColors = editTextColors(),
    enabled: Boolean = true,
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    var focused by remember {
        mutableStateOf(false)
    }

    EditText(
        modifier
            .onFocusChanged {
                focused = it.isFocused
            },
        value,
        hint,
        onValueChange,
        colors,
        enabled,
        readOnly,
        keyboardOptions,
        keyboardActions,
        singleLine,
        maxLines,
        minLines,
        visualTransformation,
        onTextLayout,
        interactionSource,
        decorationBox = {
            val strokeWidth =
                if (focused) EditTextDefaults.underlineThicknessFocused else EditTextDefaults.underlineThickness
            val strokeColor = if (focused) colors.underlineFocused else colors.underline

            Column(
                verticalArrangement = Arrangement
                    .spacedBy(EditTextDefaults.underlineSpacing)
            ) {
                it()

                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    val yCenter = size.height / 2

                    drawLine(
                        color = strokeColor,
                        strokeWidth = strokeWidth,
                        start = Offset(
                            x = 0F,
                            y = yCenter
                        ),
                        end = Offset(
                            x = size.width,
                            y = yCenter
                        )
                    )
                }
            }
        }
    )
}

@Composable
fun NumberEditText(
    modifier: Modifier = Modifier,
    value: Int,
    hint: String = "",
    onValueChange: (Int) -> Unit,
    colors: EditTextColors = editTextColors(),
    enabled: Boolean = true,
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    style: TextStyle = OneUITheme.types.editTextText,
    hintStyle: TextStyle = OneUITheme.types.editTextHint,
    maxCharacters: Long? = null,
    characters: CharArray? = null,
    maxValue: Int = Int.MAX_VALUE,
    minValue: Int = Int.MIN_VALUE,
    decorationBox: @Composable (innerTextField: @Composable () -> Unit) -> Unit =
        @Composable { innerTextField -> innerTextField() }
) {
    EditText(
        modifier,
        value = value.toString(),
        hint,
        onValueChange = {
            val proc = it
                .replace("0", "")
                .replace(",", "")
                .replace(".", "")
                .replace("-", "")
            val number = if (proc.isEmpty()) 0 else proc.toInt()
            onValueChange(
                number.coerceIn(
                    minimumValue = minValue,
                    maximumValue = maxValue
                )
            )
        },
        colors,
        enabled,
        readOnly,
        keyboardOptions = keyboardOptions.copy(
            keyboardType = KeyboardType.Number
        ),
        keyboardActions,
        singleLine,
        maxLines,
        minLines,
        visualTransformation,
        onTextLayout,
        interactionSource,
        style,
        hintStyle,
        maxCharacters,
        characters,
        decorationBox
    )
}

object EditTextDefaults {

    const val underlineThicknessFocused = 5F

    const val underlineThickness = 2F

    val underlineSpacing = 6.dp

}

data class EditTextColors(

    val cursor: Color,

    val underlineFocused: Color,

    val underline: Color

)

@Composable
fun editTextColors(
    cursor: Color = OneUITheme.colors.seslPrimaryColor,
    underlineFocused: Color = OneUITheme.colors.seslEditTextColor,
    underline: Color = OneUITheme.colors.seslEditTextHintColor
): EditTextColors = EditTextColors(
    cursor = cursor,
    underlineFocused = underlineFocused,
    underline = underline
)