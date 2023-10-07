package org.oneui.compose.layout.app

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.oneui.compose.R
import org.oneui.compose.base.Icon
import org.oneui.compose.layout.app.data.getAppName
import org.oneui.compose.layout.app.data.getAppVersion
import org.oneui.compose.layout.toolbar.CollapsingToolbarCollapsedState
import org.oneui.compose.layout.toolbar.CollapsingToolbarLayout
import org.oneui.compose.layout.toolbar.rememberCollapsingToolbarState
import org.oneui.compose.theme.OneUITheme
import org.oneui.compose.widgets.buttons.IconButton
import dev.oneuiproject.oneui.R as IconR


/**
 * Overload for [AppInfoLayout] that provides more concise and simpler parameters.
 *
 * @param modifier The [Modifier] to apply
 * @param windowInsets The [WindowInsets] to apply
 * @param horizontalLayout Whether the layout should in landscape mode (true) or in portrait mode (false)
 * @param appName The name of the app
 * @param version The current version of the app
 * @param updateState The [UpdateState], received from some repository api
 * @param extraInfo Extra information to be shown on the screen, preferably one or multiple [Text]'s
 * @param buttons Buttons that provide secondary actions related to the app, such as links to a github page or similar. Preferably one or multiple [AppInfoLayoutButton]'s
 * @param onNavigateBackClick The callback invoked when the navigation button is clicked
 * @param onInfoClick The callback invoked when the info button is clicked
 */
@Composable
fun AppInfoLayout(
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = WindowInsets(0, 0, 0, 0),
    horizontalLayout: Boolean = LocalConfiguration.current.orientation == ORIENTATION_LANDSCAPE,
    appName: String = getAppName() ?: "",
    version: String = getAppVersion() ?: "",
    updateState: UpdateState = UpdateState.InstalledLatest,
    extraInfo: (@Composable ColumnScope.() -> Unit)? = null,
    buttons: (@Composable ColumnScope.() -> Unit)? = null,
    onNavigateBackClick: (() -> Unit)? = null,
    onInfoClick: (() -> Unit)? = null
) {
    AppInfoLayout(
        modifier = modifier,
        windowInsets = windowInsets,
        horizontalLayout = horizontalLayout,
        appName = {
            Text(appName)
        },
        versionInfo = {
            Text(
                stringResource(R.string.version_info, version)
            )
        },
        updateInfo = {
            AppInfoLayoutUpdateSection(state = updateState)
        },
        extraInfo = extraInfo,
        buttons = buttons,
        infoButton = {
            onInfoClick?.let {
                IconButton(
                    icon = Icon.Resource(IconR.drawable.ic_oui_info_outline),
                    onClick = it
                )
            }
        },
        navigationButton = {
            onNavigateBackClick?.let {
                IconButton(
                    icon = Icon.Resource(IconR.drawable.ic_oui_back),
                    onClick = it
                )
            }
        }
    )
}


/**
 * Composable for a oneui-style screen which displays meta-infos about the application such as the version.
 *
 * @param modifier The [Modifier] to apply
 * @param windowInsets The [WindowInsets] to apply
 * @param horizontalLayout Whether the layout should in landscape mode (true) or in portrait mode (false)
 * @param appName The composable displaying the app name, preferably a [Text]
 * @param versionInfo The composable displaying information about the installed version, preferably a [Text]
 * @param updateInfo The composable displaying information about whether the app can be updated, preferably a [AppInfoLayoutUpdateSection]
 * @param extraInfo Extra information to be shown on the screen, preferably one or multiple [Text]'s
 * @param buttons Buttons that provide secondary actions related to the app, such as links to a github page or similar. Preferably one or multiple [AppInfoLayoutButton]'s
 * @param infoButton The button to be shown in the appbar, giving further information about the app. Preferably an [IconButton]
 * @param navigationButton The button to be shown at the start of the appbar to navigate back. Preferably an [IconButton]
 */
@Composable
fun AppInfoLayout(
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = WindowInsets(0, 0, 0, 0),
    horizontalLayout: Boolean = LocalConfiguration.current.orientation == ORIENTATION_LANDSCAPE,
    appName: @Composable () -> Unit,
    versionInfo: (@Composable () -> Unit)? = null,
    updateInfo: (@Composable () -> Unit)? = null,
    extraInfo: (@Composable ColumnScope.() -> Unit)? = null,
    buttons: (@Composable ColumnScope.() -> Unit)? = null,
    infoButton: (@Composable () -> Unit)? = null,
    navigationButton: (@Composable () -> Unit)? = null
) {
    CollapsingToolbarLayout(
        modifier = modifier
            .background(OneUITheme.colors.seslRoundAndBgcolor),
        state = rememberCollapsingToolbarState(CollapsingToolbarCollapsedState.COLLAPSED),
        expandable = false,
        windowInsets = windowInsets,
        toolbarTitle = "",
        toolbarSubtitle = "",
        appbarNavAction = navigationButton,
        appbarActions = infoButton
    ) {
        if (horizontalLayout) {
            HorizontalAppInfoLayout(
                Modifier
                    .fillMaxSize(),
                appName, versionInfo, updateInfo, extraInfo, buttons
            )
        } else {
            VerticalAppInfoLayout(
                Modifier
                    .fillMaxSize(),
                appName, versionInfo, updateInfo, extraInfo, buttons
            )
        }
    }
}

@Composable
private fun VerticalAppInfoLayout(
    modifier: Modifier = Modifier,
    appName: @Composable () -> Unit,
    versionInfo: (@Composable () -> Unit)? = null,
    updateInfo: (@Composable () -> Unit)? = null,
    extraInfo: (@Composable ColumnScope.() -> Unit)? = null,
    buttons: (@Composable ColumnScope.() -> Unit)? = null
) {
    val topPadding = LocalConfiguration.current.screenHeightDp.dp * 0.12F
    val bottomPadding = LocalConfiguration.current.screenHeightDp.dp * 0.1F

    Column(
        modifier = modifier
            .padding(
                top = topPadding,
                bottom = bottomPadding
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProvideTextStyle(OneUITheme.types.appInfoLayoutAppNameText, appName)
            Spacer(
                modifier = Modifier
                    .height(AppInfoLayoutDefaults.appNameSpacing)
            )

            versionInfo?.let {
                Spacer(
                    modifier = Modifier
                        .height(AppInfoLayoutDefaults.versionInfoSpacingTop)
                )
                ProvideTextStyle(OneUITheme.types.appInfoLayoutVersionText, it)
                Spacer(
                    modifier = Modifier
                        .height(AppInfoLayoutDefaults.versionInfoSpacingBottom)
                )
            }

            extraInfo?.let {
                ProvideTextStyle(OneUITheme.types.appInfoLayoutExtraText) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(AppInfoLayoutDefaults.extraSpacing)
                    ) {
                        it()
                        Spacer(modifier = Modifier)
                    }
                }
            }

            updateInfo?.let {
                ProvideTextStyle(OneUITheme.types.appInfoLayoutExtraText) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(AppInfoLayoutDefaults.updateAppSpacing)
                    ) {
                        it()
                    }
                }
            }
        }

        Spacer(
            modifier = Modifier
                .weight(1F)
        )

        buttons?.let {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                it()
            }
        }
    }
}

@Composable
private fun HorizontalAppInfoLayout(
    modifier: Modifier = Modifier,
    appName: @Composable () -> Unit,
    versionInfo: (@Composable () -> Unit)? = null,
    updateInfo: (@Composable () -> Unit)? = null,
    extraInfo: (@Composable ColumnScope.() -> Unit)? = null,
    buttons: (@Composable ColumnScope.() -> Unit)? = null
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1F)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProvideTextStyle(OneUITheme.types.appInfoLayoutAppNameText, appName)
            Spacer(
                modifier = Modifier
                    .height(AppInfoLayoutDefaults.appNameSpacing)
            )

            versionInfo?.let {
                Spacer(
                    modifier = Modifier
                        .height(AppInfoLayoutDefaults.versionInfoSpacingTop)
                )
                ProvideTextStyle(OneUITheme.types.appInfoLayoutVersionText, it)
                Spacer(
                    modifier = Modifier
                        .height(AppInfoLayoutDefaults.versionInfoSpacingBottom)
                )
            }

            extraInfo?.let {
                ProvideTextStyle(OneUITheme.types.appInfoLayoutExtraText) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(AppInfoLayoutDefaults.extraSpacing)
                    ) {
                        it()
                        Spacer(modifier = Modifier)
                    }
                }
            }

            updateInfo?.let {
                ProvideTextStyle(OneUITheme.types.appInfoLayoutExtraText) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(AppInfoLayoutDefaults.updateAppSpacing)
                    ) {
                        it()
                    }
                }
            }
        }

        buttons?.let {
            Column(
                modifier = Modifier
                    .weight(1F)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                it()
            }
        }
    }
}

/**
 * Contains default values for an [AppInfoLayout]
 */
object AppInfoLayoutDefaults {

    val appNameSpacing = 8.dp

    val versionInfoSpacingTop = 2.dp
    val versionInfoSpacingBottom = 8.dp

    val extraSpacing = 8.dp

    val updateAppSpacing = 10.dp

}

@Preview(device = "spec:width=1280dp,height=800dp,dpi=240")
@Composable
private fun HorizontalAppInfoLayoutPreview() =
    AppInfoLayout(
        modifier = Modifier
            .fillMaxSize(),
        horizontalLayout = true,
        appName = "OneUI Sample App",
        version = "1.0.0",
        updateState = UpdateState.CanUpdate { },
        buttons = {
            AppInfoLayoutButton(
                onClick = { }
            ) {
                Text("Status")
            }
            AppInfoLayoutButton(
                onClick = { }
            ) {
                Text("Github")
            }
        },
        extraInfo = {
            Text("Extra 1")
            Text("Extra 2")
        },
        onNavigateBackClick = { },
        onInfoClick = { }
    )

@Preview(device = "spec:width=411dp,height=891dp")
@Composable
private fun VerticalAppInfoLayoutPreview() =
    AppInfoLayout(
        modifier = Modifier
            .fillMaxSize(),
        horizontalLayout = false,
        appName = "OneUI Sample App",
        version = "1.0.0",
        updateState = UpdateState.CanUpdate { },
        buttons = {
            AppInfoLayoutButton(
                onClick = { }
            ) {
                Text("Status")
            }
            AppInfoLayoutButton(
                onClick = { }
            ) {
                Text("Github")
            }
        },
        extraInfo = {
            Text("Extra 1")
            Text("Extra 2")
        },
        onNavigateBackClick = { },
        onInfoClick = { }
    )