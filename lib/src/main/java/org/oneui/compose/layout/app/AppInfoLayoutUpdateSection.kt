package org.oneui.compose.layout.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.oneui.compose.R
import org.oneui.compose.progress.CircularProgressIndicatorSize
import org.oneui.compose.progress.ProgressIndicator
import org.oneui.compose.progress.ProgressIndicatorType
import org.oneui.compose.theme.OneUITheme

sealed class UpdateState {

    /**
     * Latest version is installed
     */
    data object InstalledLatest : UpdateState()

    /**
     * Cant check due to internet connection
     */
    data class CantCheck(
        val onRetry: () -> Unit
    ) : UpdateState()

    /**
     * Currently checking for updates
     */
    data object Loading : UpdateState()

    /**
     * Nothing should be shown
     */
    data object Hidden : UpdateState()

    /**
     * App can be updated.
     */
    data class CanUpdate(
        val onUpdateClick: () -> Unit
    ) : UpdateState()

}


/**
 * Composable for a oneui-style update section for an [AppInfoLayout]
 *
 * @param modifier The [Modifier] to apply
 * @param state The [UpdateState]. [UpdateState.Hidden] for nothing.
 */
@Composable
fun AppInfoLayoutUpdateSection(
    modifier: Modifier = Modifier,
    state: UpdateState
) {
    when (state) {
        is UpdateState.CanUpdate -> {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.new_version_is_available),
                    style = OneUITheme.types.appInfoLayoutExtraText
                )
                AppInfoLayoutButton(
                    onClick = { state.onUpdateClick() },
                    colors = appInfoLayoutButtonColors(
                        background = OneUITheme.colors.seslPrimaryColor,
                        labelTextColor = OneUITheme.colors.seslRoundAndBgcolor
                    )
                ) {
                    Text(
                        text = stringResource(R.string.update)
                    )
                }
            }
        }

        is UpdateState.CantCheck -> {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.new_version_is_available),
                    style = OneUITheme.types.appInfoLayoutExtraText
                )
                AppInfoLayoutButton(
                    onClick = { state.onRetry },
                    colors = appInfoLayoutButtonColors(
                        background = OneUITheme.colors.seslPrimaryColor,
                        labelTextColor = OneUITheme.colors.seslRoundAndBgcolor
                    )
                ) {
                    Text(
                        text = stringResource(R.string.retry)
                    )
                }
            }
        }

        UpdateState.Hidden -> {}
        UpdateState.InstalledLatest -> {
            Text(
                modifier = modifier,
                text = stringResource(R.string.latest_version),
                style = OneUITheme.types.appInfoLayoutExtraText
            )
        }

        UpdateState.Loading -> {
            ProgressIndicator(
                modifier = modifier,
                type = ProgressIndicatorType.CircularIndeterminate(
                    size = CircularProgressIndicatorSize.Companion.Medium
                )
            )
        }
    }
}





















