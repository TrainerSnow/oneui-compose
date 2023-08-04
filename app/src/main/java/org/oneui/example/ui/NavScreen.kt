package org.oneui.example.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.IconButton
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
import org.oneui.compose.navigation.BottomNavigationBar
import org.oneui.compose.navigation.BottomNavigationBarItem
import org.oneui.compose.navigation.CustomTabItem
import org.oneui.compose.navigation.SubTabItem
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
                    icon = Icon.Resource(dev.oneuiproject.oneui.R.drawable.ic_oui_drawer)
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
                                icon = Icon.Resource(dev.oneuiproject.oneui.R.drawable.ic_oui_drawer)
                            )
                        }
                    }
                }
            }
        }

        val subTabs = (1..3).map { "Tab $it" }
        var selectedSubTab by remember {
            mutableStateOf(subTabs.first())
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
                TextSeparator(text = "Sub Tabs")
                RoundedCornerBox(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Tabs(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        subTabs.forEach {
                            SubTabItem(
                                modifier = Modifier
                                    .weight(1F),
                                text = it,
                                selected = it == selectedSubTab,
                                onClick = { selectedSubTab = it }
                            )
                        }
                    }
                }
            }
        }

        val navItems = (1..4).map { "Item $it" }
        val icons = listOf(
            dev.oneuiproject.oneui.R.drawable.ic_oui_share_outline,
            dev.oneuiproject.oneui.R.drawable.ic_oui_delete_outline,
            dev.oneuiproject.oneui.R.drawable.ic_oui_edit_outline,
            dev.oneuiproject.oneui.R.drawable.ic_oui_location_outline,
        )
        var selectedNavItem by remember {
            mutableStateOf(navItems.first())
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
                TextSeparator(text = "Bottom Navigation Bar")
                RoundedCornerBox(
                    modifier = Modifier
                        .fillMaxWidth(),
                    padding = PaddingValues(4.dp)
                ) {
                    BottomNavigationBar(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        navItems.forEach {
                            BottomNavigationBarItem(
                                modifier = Modifier
                                    .weight(1F),
                                label = it,
                                onClick = { selectedNavItem = it },
                                icon = Icon.Resource(icons[navItems.indexOf(it)])
                            )
                        }
                    }
                }
            }
        }
    }
}