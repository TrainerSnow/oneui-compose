package org.oneui.compose.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import dev.oneuiproject.oneui.R
import org.oneui.compose.base.Icon
import org.oneui.compose.base.IconView
import org.oneui.compose.layout.toolbar.CollapsingToolbarCollapsedState
import org.oneui.compose.layout.toolbar.CollapsingToolbarLayout
import org.oneui.compose.layout.toolbar.rememberCollapsingToolbarState
import org.oneui.compose.picker.NumberPicker
import org.oneui.compose.progress.CircularProgressIndicatorSize
import org.oneui.compose.progress.ProgressIndicator
import org.oneui.compose.progress.ProgressIndicatorType
import org.oneui.compose.widgets.box.RoundedCornerBox
import org.oneui.compose.widgets.text.TextSeparator

@Composable
fun ProgressIndicatorScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit
) {
    CollapsingToolbarLayout(
        modifier = modifier,
        toolbarTitle = "Progress",
        expandable = false,
        state = rememberCollapsingToolbarState(
            initial = CollapsingToolbarCollapsedState.COLLAPSED,
            velocityThreshold = with(LocalDensity.current) { 100.dp.toPx() }
        ),
        appbarNavAction = {
            IconButton(
                onClick = onNavigateBack
            ) {
                IconView(
                    icon = Icon.Resource(R.drawable.ic_oui_drawer)
                )
            }
        }
    ) {
        TextSeparator(text = "Indeterminate")
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
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    CircularProgressIndicatorSize.values().forEach { size ->
                        ProgressIndicator(
                            type = ProgressIndicatorType.CircularIndeterminate(
                                size = size
                            )
                        )
                    }
                }
                ProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth(),
                    type = ProgressIndicatorType.HorizontalIndeterminate
                )
            }
        }

        TextSeparator(text = "NumberPicker")
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
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    CircularProgressIndicatorSize.values().forEach { size ->
                        ProgressIndicator(
                            type = ProgressIndicatorType.CircularDeterminate(
                                size = size,
                                progress = 0.4F
                            )
                        )
                    }
                }
                ProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth(),
                    type = ProgressIndicatorType.HorizontalDeterminate(
                        progress = 0.4F
                    )
                )
            }
        }

        /*TextSeparator(text = "TimePicker")
        TimePicker(
            config = timePickerConfig(
                timeFormat = TimeFormat.Military
            )
        )*/
        var value by remember {
            mutableIntStateOf(1)
        }
        TextSeparator(text = "NumberPicker ($value)")
        NumberPicker(
            values = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19),
            onValueChange = { value = it }
        )
    }
}