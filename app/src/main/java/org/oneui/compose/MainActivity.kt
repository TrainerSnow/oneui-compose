package org.oneui.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import org.oneui.compose.base.Icon
import org.oneui.compose.base.IconView
import org.oneui.compose.layout.drawer.DrawerDivider
import org.oneui.compose.layout.drawer.DrawerItem
import org.oneui.compose.layout.drawer.DrawerLayoutCompact
import org.oneui.compose.layout.toolbar.CollapsingToolbarCollapsedState
import org.oneui.compose.layout.toolbar.CollapsingToolbarLayout
import org.oneui.compose.layout.toolbar.rememberCollapsingToolbarState
import org.oneui.compose.theme.OneUITheme
import org.oneui.compose.widgets.buttons.OUIIconButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OneUITheme {
                DrawerLayoutCompact(
                    drawerContent = {
                        DrawerItem(
                            icon = {
                                IconView(
                                    icon = Icon.Vector(Icons.Outlined.Home)
                                )
                            },
                            label = "Home"
                        )
                        DrawerItem(
                            icon = {
                                IconView(
                                    icon = Icon.Vector(Icons.Outlined.Search)
                                )
                            },
                            label = "Search"
                        )
                        DrawerItem(
                            icon = {
                                IconView(
                                    icon = Icon.Vector(Icons.Outlined.Call)
                                )
                            },
                            label = "Call"
                        )
                        DrawerDivider()
                    },
                    headerIcon = Icon.Vector(Icons.Outlined.Info)
                ) {
                    CollapsingToolbarLayout(
                        state = rememberCollapsingToolbarState(
                            initial = CollapsingToolbarCollapsedState.COLLAPSED,
                            velocityThreshold = with(LocalDensity.current) { 100.dp.toPx() }
                        ),
                        toolbarTitle = "Title",
                        toolbarSubtitle = "Subtitle",
                        appbarActions = {
                            OUIIconButton(
                                icon = Icon.Vector(Icons.Outlined.Search)
                            )
                            OUIIconButton(
                                icon = Icon.Vector(Icons.Outlined.Info)
                            )
                        },
                        appbarNavAction = {
                            OUIIconButton(
                                icon = Icon.Vector(Icons.Outlined.ArrowBack)
                            )
                        }
                    ) { }
                }
            }
        }
    }
}