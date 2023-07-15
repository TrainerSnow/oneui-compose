package org.oneui.compose.ui.widgets

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import org.oneui.compose.widgets.text.TextSeparator

@Composable
fun PreferenceShowcase(
    modifier: Modifier = Modifier
) = Column(
    modifier = modifier
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val editTextPref = @Composable {
        var text by remember {
            mutableStateOf("[No text set]")
        }

        EditTextPreference(
            title = "EditTextPreference",
            onValueChange = { text = it },
            value = text
        )
    }

    val singleSelectPreference = @Composable {
        val options = (1..3).map { "Option $it" }
        var option by remember {
            mutableStateOf(options.first())
        }

        SingleSelectPreference(
            title = "SingleSelectPreference",
            onValueChange = { option = it },
            value = option,
            values = options
        )
    }


    val multiSelectPref = @Composable {
        val options = (1..3).map { "Option $it" }
        var selectedOptions by remember {
            mutableStateOf(listOf(*options.take(2).toTypedArray()))
        }

        MultiSelectPreference(
            title = "MultiSelectPreference",
            values = options,
            selectedValues = selectedOptions,
            onValuesChange = {
                selectedOptions = it
            },
            summary = "summary"
        )
    }

    val switchPref = @Composable {
        var switched by remember {
            mutableStateOf(true)
        }

        SwitchPreference(
            title = "SwitchPreference",
            switched = switched,
            onSwitchedChange = { switched = it },
            summary = "summary"
        )
    }

    val checkboxPreference = @Composable {
        var checked by remember {
            mutableStateOf(true)
        }

        CheckboxPreference(
            title = "CheckboxPreference",
            checked = checked,
            onCheckedChange = { checked = it },
            summary = "summary"
        )
    }

    val seekbarPreference = @Composable {
        var value by remember {
            mutableStateOf(0.75F)
        }

        SeekbarPreference(
            title = "SeekbarPreference",
            value = value,
            onValueChange = { value = it },
            summary = "Summary"
        )
    }

    val dropdownPreference = @Composable {
        val options = (1..3).map { "Option $it" }
        var selectedOption by remember {
            mutableStateOf(options.first())
        }

        DropdownPreference(
            title = "DropdownPreference",
            item = selectedOption,
            items = options,
            nameFor = { it },
            onItemSelected = { selectedOption = it }
        )
    }

    val switchScreenPreference = @Composable {
        var switched by remember {
            mutableStateOf(false)
        }

        SwitchPreferenceScreen(
            title = "SwitchPreferenceScreen",
            onClick = {
                scope.launch {
                    Toast.makeText(context, "onClick()", Toast.LENGTH_SHORT).show()
                }
            },
            onSwitch = { switched = it },
            summary = "Summary",
            switched = switched
        )
    }

    TipsCardPreference(
        modifier = Modifier
            .fillMaxWidth(),
        title = {
            Text(
                text = "TipsCardPreference"
            )
        },
        summary = {
            Text(
                text = "This is an example summary for a TipsCardPreference. This should spark the users eye and give them valuable information about the application."
            )
        },
        buttonLabel = {
            Text(
                text = "Button"
            )
        },
        onButtonClick = { }
    )

    PreferenceCategory(
        title = { TextSeparator(text = "Preferences") },
        preferences = listOf(
            editTextPref,
            singleSelectPreference,
            multiSelectPref,
            switchPref,
            checkboxPreference,
            seekbarPreference,
            dropdownPreference,
            switchScreenPreference
        )
    )

    Spacer(
        modifier = Modifier
            .height(16.dp)
    )
    RelativeCard(
        modifier = Modifier
            .fillMaxWidth(),
        title = {
            Text(
                text = "Looking for something else?"
            )
        }
    ) {
        (1..3).forEach {
            RelativeCardLink(
                onClick = { }
            ) {
                Text(
                    text = "Option $it"
                )
            }
        }
    }
}
