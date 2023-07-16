package org.oneui.compose

import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.oneui.compose.base.Icon
import org.oneui.compose.base.IconView
import org.oneui.compose.layout.toolbar.CollapsingToolbarLayout
import org.oneui.compose.preference.CheckboxPreference
import org.oneui.compose.preference.DropdownPreference
import org.oneui.compose.preference.EditTextPreference
import org.oneui.compose.preference.MultiSelectPreference
import org.oneui.compose.preference.RelativeCard
import org.oneui.compose.preference.SeekbarPreference
import org.oneui.compose.preference.SingleSelectPreference
import org.oneui.compose.preference.SwitchPreference
import org.oneui.compose.preference.SwitchPreferenceScreen
import org.oneui.compose.preference.TipsCardPreference
import org.oneui.compose.preference.internal.RelativeCardLink
import org.oneui.compose.preference.misc.PreferenceCategory
import org.oneui.compose.widgets.SwitchBar
import org.oneui.compose.widgets.text.TextSeparator

@Composable
fun PreferencesScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit
) {
    var state by remember {
        mutableStateOf(PreferenceState())
    }
    val scope = rememberCoroutineScope()
    val ctx = LocalContext.current

    val dropdownItems = (1..4).map { "Item $it" }
    val singleSelectItems = (1..4).map { "Item $it" }
    val multiSelectItems = (1..4).map { "Item $it" }

    CollapsingToolbarLayout(
        modifier = modifier,
        toolbarTitle = "Preferences",
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
        TipsCardPreference(
            title = { Text("TipsCardPreference") },
            buttonLabel = { Text("Done") },
            summary = { Text("This is a test summary. Here you can display information about the preferences.") },
            onButtonClick = { }
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        SwitchBar(
            switched = state.switchBarSwitched,
            onSwitchedChange = {
                state = state.copy(
                    switchBarSwitched = !state.switchBarSwitched
                )
            }
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        PreferenceCategory(
            title = { TextSeparator(text = "Category 1") },
            preferences = listOf(
                {
                    SwitchPreference(
                        title = "SwitchPreference",
                        summary = "Summary",
                        switched = state.switchSwitched,
                        onSwitchedChange = {
                            state = state.copy(
                                switchSwitched = !state.switchSwitched
                            )
                        }
                    )
                },
                {
                    SwitchPreferenceScreen(
                        title = "SwitchPreferenceScreen",
                        summary = "Summary",
                        onClick = {
                            scope.launch {
                                Toast.makeText(ctx, "onClick()", Toast.LENGTH_SHORT).show()
                            }
                        },
                        onSwitch = {
                            state = state.copy(
                                switchScreenSwitched = !state.switchScreenSwitched
                            )
                        },
                        switched = state.switchScreenSwitched
                    )
                },
                {
                    CheckboxPreference(
                        title = "CheckboxPreference",
                        summary = "Summary",
                        checked = state.checkBoxChecked,
                        onCheckedChange = {
                            state = state.copy(
                                checkBoxChecked = !state.checkBoxChecked
                            )
                        }
                    )
                },
                {
                    SeekbarPreference(
                        title = "SeekbarPreference",
                        summary = "Summary",
                        value = state.seekbarValue,
                        onValueChange = {
                            state = state.copy(
                                seekbarValue = it
                            )
                        }
                    )
                }
            )
        )

        PreferenceCategory(
            title = { TextSeparator(text = "Categroy 2") },
            preferences = listOf(
                {
                    EditTextPreference(
                        title = "EditTextPreference",
                        onValueChange = {
                            state = state.copy(
                                editTextValue = it
                            )
                        },
                        value = state.editTextValue
                    )
                },
                {
                    DropdownPreference(
                        title = "DropdownPreference",
                        item = state.dropdownValue,
                        items = dropdownItems,
                        nameFor = { it },
                        onItemSelected = {
                            state = state.copy(
                                dropdownValue = it
                            )
                        }
                    )
                },
                {
                    SingleSelectPreference(
                        title = "SingleSelectPreference",
                        value = state.singleSelectValue,
                        values = singleSelectItems,
                        nameFor = { it },
                        onValueChange = {
                            state = state.copy(
                                singleSelectValue = it
                            )
                        }
                    )
                },
                {
                    MultiSelectPreference(
                        title = "MultiSelectPreference",
                        summary = "Summary",
                        values = multiSelectItems,
                        selectedValues = state.multiSelectValues,
                        nameFor = { it },
                        onValuesChange = {
                            state = state.copy(
                                multiSelectValues = it
                            )
                        }
                    )
                },

                )
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        RelativeCard(
            modifier = Modifier
                .fillMaxWidth(),
            title = { Text("Looking for something else?") }
        ) {
            RelativeCardLink(
                onClick = { }
            ) {
                Text("Option 1")
            }
            RelativeCardLink(
                onClick = { }
            ) {
                Text("Option 2")
            }
            RelativeCardLink(
                onClick = { }
            ) {
                Text("Option 3")
            }
        }
    }
}


data class PreferenceState(

    val switchBarSwitched: Boolean = false,

    val switchSwitched: Boolean = false,

    val switchScreenSwitched: Boolean = false,

    val checkBoxChecked: Boolean = false,

    val seekbarValue: Float = 0.5F,

    val editTextValue: String = "",

    val dropdownValue: String = "Item 1",

    val singleSelectValue: String = "Item 1",

    val multiSelectValues: List<String> = emptyList()
)