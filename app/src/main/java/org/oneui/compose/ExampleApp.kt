package org.oneui.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import org.oneui.compose.base.Icon
import org.oneui.compose.base.IconView
import org.oneui.compose.layout.drawer.DrawerItem
import org.oneui.compose.layout.drawer.DrawerLayout
import org.oneui.compose.layout.internal.rememberSlidingDrawerState
import org.oneui.compose.nav.NavDestinations
import org.oneui.compose.ui.NavScreen
import org.oneui.compose.ui.WidgetsScreen

@Composable
fun ExampleApp(
    modifier: Modifier = Modifier
) {
    var selectedDestination by remember {
        mutableStateOf(NavDestinations.Widgets)
    }
    val drawerState = rememberSlidingDrawerState()
    val scope = rememberCoroutineScope()

    DrawerLayout(
        modifier = modifier,
        state = drawerState,
        drawerContent = {
            NavDestinations.values().toList().forEach {
                DrawerItem(
                    icon = {
                        IconView(
                            icon = it.icon
                        )
                    },
                    label = it.title,
                    selected = it == selectedDestination,
                    onClick = {
                        scope.launch {
                            drawerState.closeAnimate()
                            selectedDestination = it
                        }
                    }
                )
            }
        },
        headerIcon = Icon.Vector(Icons.Outlined.Settings)
    ) {
        when (selectedDestination) {
            NavDestinations.Widgets -> {
                WidgetsScreen(
                    modifier = Modifier
                        .fillMaxSize(),
                    onNavigateBack = {
                        scope.launch {
                            drawerState.openAnimate()
                        }
                    }
                )
            }

            NavDestinations.SeekBar -> {}
            NavDestinations.Preferences -> {
                PreferencesScreen(
                    modifier = Modifier
                        .fillMaxSize(),
                    onNavigateBack = {
                        scope.launch {
                            drawerState.openAnimate()
                        }
                    }
                )
            }

            NavDestinations.Navigation -> {
                NavScreen(
                    modifier = Modifier
                        .fillMaxSize(),
                    onNavigateBack = {
                        scope.launch {
                            drawerState.openAnimate()
                        }
                    }
                )
            }
        }
    }
}