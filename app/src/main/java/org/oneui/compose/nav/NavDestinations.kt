package org.oneui.compose.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Settings
import org.oneui.compose.base.Icon

enum class NavDestinations(
    val title: String,
    val icon: Icon
) {

    Widgets(
        title = "Widgets",
        icon = Icon.Vector(Icons.Outlined.List)
    ),

    SeekBar(
        title = "Seekbar",
        icon = Icon.Vector(Icons.Outlined.Create)
    ),

    Preferences(
        title = "Preferences",
        icon = Icon.Vector(Icons.Outlined.Settings)
    ),

    Navigation(
        title = "Navigation",
        icon = Icon.Vector(Icons.Outlined.MoreVert)
    )

}