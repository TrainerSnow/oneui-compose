package org.oneui.compose.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.oneui.compose.base.Icon
import org.oneui.compose.base.IconView
import org.oneui.compose.layout.toolbar.CollapsingToolbarLayout
import org.oneui.compose.navigation.CustomTabItem
import org.oneui.compose.navigation.TabItem
import org.oneui.compose.navigation.Tabs
import org.oneui.compose.widgets.box.RoundedCornerBox
import org.oneui.compose.widgets.text.TextSeparator

@Composable
fun NavScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit
) {
    CollapsingToolbarLayout(
        modifier = modifier,
        toolbarTitle = "Navigation",
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
        val tabs = (1..3).map { "Tab $it" }
        var selectedTab by remember {
            mutableStateOf(tabs.first())
        }

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                TextSeparator(text = "Tabs")
                RoundedCornerBox(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Tabs(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        tabs.forEach {
                            TabItem(
                                modifier = Modifier
                                    .weight(1F),
                                text = it,
                                selected = it == selectedTab,
                                onClick = { selectedTab = it }
                            )
                        }
                        CustomTabItem(
                            modifier = Modifier
                                .weight(1F),
                            onClick = { }
                        ) {
                            IconView(
                                icon = Icon.Vector(Icons.Outlined.Menu)
                            )
                        }
                    }
                }
            }
        }
    }
}