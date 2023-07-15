package org.oneui.compose.ui.widgets

import android.util.Log.d
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.oneui.compose.widgets.SwitchBar
import org.oneui.compose.widgets.text.TextSeparator

@Composable
fun SwitchBarShowcase(
    modifier: Modifier = Modifier
) = Column {
    TextSeparator(text = "SwitchBar")
    var switched by remember {
        mutableStateOf(false)
    }
    SwitchBar(
        modifier = modifier,
        switched = switched,
        onSwitchedChange = {
            d("SwitchBar", "Changed to $it")
            switched = it
        }
    )
}
