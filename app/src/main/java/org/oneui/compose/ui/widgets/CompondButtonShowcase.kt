package org.oneui.compose.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.oneui.compose.widgets.box.RoundedCornerBox
import org.oneui.compose.widgets.buttons.Checkbox
import org.oneui.compose.widgets.buttons.Switch
import org.oneui.compose.widgets.buttons.radio.VerticalRadioGroup
import org.oneui.compose.widgets.text.TextSeparator

@Composable
fun RadioButtonShowcase(
    modifier: Modifier = Modifier
) = Column {
    TextSeparator(text = "Radio buttons")
    RoundedCornerBox(
        modifier = modifier
            .fillMaxWidth()
    ) {
        val values = listOf("One", "Two", "Three", "Four")
        var groupValue by remember {
            mutableStateOf(values.first())
        }

        Column {
            VerticalRadioGroup(
                modifier = Modifier
                    .fillMaxWidth(),
                groupValue = groupValue,
                values = values,
                labels = values,
                onClick = { value, _ ->
                    groupValue = value
                }
            )
        }
    }
}

@Composable
fun CheckboxShowcase(
    modifier: Modifier = Modifier
) = Column {
    TextSeparator(text = "Checkboxes")
    RoundedCornerBox(
        modifier = modifier
            .fillMaxWidth()
    ) {
        val values = listOf("One", "Two", "Three", "Four")
        val selectedValues = remember {
            mutableStateListOf<String>()
        }

        Column(
            verticalArrangement = Arrangement
                .spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            values.forEach {
                Checkbox(
                    checked = it in selectedValues,
                    onCheckedChange = { checked ->
                        if (checked)
                            selectedValues.add(it)
                        else
                            selectedValues.remove(it)
                    }
                ) {
                    Text(
                        text = it
                    )
                }
            }
        }
    }
}

@Composable
fun SwitchShowcase(
    modifier: Modifier = Modifier
) = Column {
    TextSeparator(text = "Switches")
    RoundedCornerBox(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement
                .spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            var switched1 by remember {
                mutableStateOf(false)
            }
            Switch(
                switched = switched1,
                onSwitchedChange = { switched1 = it }
            )
            var switched2 by remember {
                mutableStateOf(false)
            }
            Switch(
                switched = switched2,
                onSwitchedChange = { switched2 = it }
            )
            var switched3 by remember {
                mutableStateOf(false)
            }
            Switch(
                switched = switched3,
                onSwitchedChange = { switched3 = it }
            )
        }
    }
}

