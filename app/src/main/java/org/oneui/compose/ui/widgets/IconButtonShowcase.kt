package org.oneui.compose.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.oneui.compose.base.Icon
import org.oneui.compose.widgets.box.RoundedCornerBox
import org.oneui.compose.widgets.buttons.OUIIconButton
import org.oneui.compose.widgets.text.TextSeparator

@Composable
fun IconButtonShowcase(
    modifier: Modifier = Modifier
) = Column {
    TextSeparator(text = "Icon buttons")
    RoundedCornerBox(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            /*verticalArrangement = Arrangement
                .spacedBy(8.dp),*/
            modifier = Modifier
                .fillMaxWidth()
        ) {
            OUIIconButton(
                icon = Icon.Vector(
                    Icons.Outlined.AccountCircle
                )
            )
            OUIIconButton(
                icon = Icon.Vector(
                    Icons.Outlined.Create
                )
            )
            OUIIconButton(
                icon = Icon.Vector(
                    Icons.Outlined.DateRange
                )
            )
        }
    }
}