package org.oneui.compose.layout.app.data

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
internal fun getAppName(): String? = LocalContext
    .current
    .run {
        try {
            applicationInfo
                .labelRes
                .let {
                    if (it == 0) applicationInfo.nonLocalizedLabel.toString()
                    else resources.getString(it)
                }
        } catch (_: Exception) {
            null
        }
    }

@Composable
internal fun getAppVersion(): String? = LocalContext
    .current
    .run {
        try {
            packageManager
                .getPackageInfo(
                    packageName,
                    0
                ).versionName
        } catch (_: Exception) {
            null
        }
    }