package org.oneui.compose.nav

import org.oneui.compose.base.Icon

enum class NavDestinations(
    val title: String,
    val icon: Icon
) {

    Widgets(
        title = "Widgets",
        icon = Icon.Resource(dev.oneuiproject.oneui.R.drawable.ic_oui_game_launcher)
    ),

    SeekBar(
        title = "Seekbar",
        icon = Icon.Resource(dev.oneuiproject.oneui.R.drawable.ic_oui_color_adjustment)
    ),

    Preferences(
        title = "Preferences",
        icon = Icon.Resource(dev.oneuiproject.oneui.R.drawable.ic_oui_settings_outline)
    ),

    Navigation(
        title = "Navigation",
        icon = Icon.Resource(dev.oneuiproject.oneui.R.drawable.ic_oui_navigationbar)
    )

}