package org.oneui.compose.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.oneui.compose.base.Icon
import org.oneui.compose.base.IconView
import org.oneui.compose.layout.toolbar.CollapsingToolbarLayout
import org.oneui.compose.widgets.EditText
import org.oneui.compose.widgets.SwitchBar
import org.oneui.compose.widgets.box.RoundedCornerBox
import org.oneui.compose.widgets.buttons.Button
import org.oneui.compose.widgets.buttons.Checkbox
import org.oneui.compose.widgets.buttons.ColoredButton
import org.oneui.compose.widgets.buttons.Switch
import org.oneui.compose.widgets.buttons.TransparentButton
import org.oneui.compose.widgets.buttons.radio.VerticalRadioGroup
import org.oneui.compose.widgets.text.TextSeparator

@Composable
fun WidgetsScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit
) {
    CollapsingToolbarLayout(
        modifier = modifier,
        toolbarTitle = "Widgets",
        appbarNavAction = {
            IconButton(
                onClick = onNavigateBack
            ) {
                IconView(
                    icon = Icon.Vector(Icons.Outlined.Menu)
                )
            }
        }
    ) {
        var state by remember {
            mutableStateOf(WidgetsState())
        }
        val radioButtonValues = (1..3).map { "Option $it" }

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            SwitchBar(
                switched = state.switchBarSwitched,
                onSwitchedChange = {
                    state = state.copy(
                        switchBarSwitched = !state.switchBarSwitched
                    )
                }
            )

            TextSeparator(text = "Compound Buttons")
            RoundedCornerBox(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement
                        .spacedBy(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Switch"
                        )
                        Switch(
                            switched = state.switchSwitched,
                            onSwitchedChange = {
                                state = state.copy(
                                    switchSwitched = !state.switchSwitched
                                )
                            }
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        state.checkBoxesChecked.forEachIndexed { index, value ->
                            Checkbox(
                                checked = value,
                                onCheckedChange = {
                                    state = state.copy(
                                        checkBoxesChecked = state
                                            .checkBoxesChecked
                                            .toMutableList()
                                            .let {
                                                it[index] = !value
                                                it
                                            }
                                    )
                                }
                            ) {
                                Text(text = "CheckBox")
                            }
                        }
                    }

                    VerticalRadioGroup(
                        modifier = Modifier
                            .fillMaxWidth(),
                        groupValue = state.radioButtonSelected,
                        values = radioButtonValues,
                        labels = radioButtonValues,
                        onClick = { value, _ ->
                            state = state.copy(
                                radioButtonSelected = value
                            )
                        }
                    )
                }
            }

            TextSeparator(text = "Buttons")
            RoundedCornerBox(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement
                        .spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        label = "Button"
                    )
                    ColoredButton(
                        label = "Button"
                    )
                    TransparentButton(
                        label = "Button"
                    )
                }
            }

            TextSeparator(text = "EditText")
            RoundedCornerBox(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                EditText(
                    value = state.editTextValue,
                    hint = "Type something",
                    onValueChange = {
                        state = state.copy(
                            editTextValue = it
                        )
                    }
                )
            }
        }
    }
}

data class WidgetsState(

    val switchBarSwitched: Boolean = false,

    val switchSwitched: Boolean = false,

    val checkBoxesChecked: List<Boolean> = (0 until 2).map { false },

    val radioButtonSelected: String = "",

    val editTextValue: String = ""

)