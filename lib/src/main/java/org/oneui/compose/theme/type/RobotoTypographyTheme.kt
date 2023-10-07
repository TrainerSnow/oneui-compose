package org.oneui.compose.theme.type

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.DeviceFontFamilyName
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import org.oneui.compose.theme.color.OneUIColorTheme
import org.oneui.compose.theme.dimensions.IDynamicDimensions


internal class RobotoTypographyTheme private constructor(
    override val button: TextStyle,
    override val drawerItemLabel: TextStyle,
    override val drawerItemLabelSelected: TextStyle,
    override val drawerItemEndLabel: TextStyle,
    override val appbarTitleCollapsed: TextStyle,
    override val appbarTitleExtended: TextStyle,
    override val appbarSubtitle: TextStyle,
    override val textSeparator: TextStyle,
    override val radioLabel: TextStyle,
    override val checkboxLabel: TextStyle,
    override val menuLabel: TextStyle,
    override val menuLabelSelected: TextStyle,
    override val menuLabelDisabled: TextStyle,
    override val preferenceTitle: TextStyle,
    override val preferenceSummary: TextStyle,
    override val dialogBody: TextStyle,
    override val dialogTitle: TextStyle,
    override val dialogButtonLabel: TextStyle,
    override val dialogButtonLabelThreeButtons: TextStyle,
    override val editTextText: TextStyle,
    override val editTextHint: TextStyle,
    override val switchBarLabel: TextStyle,
    override val switchBarLabelOff: TextStyle,
    override val tipsCardPreferenceButtonLabel: TextStyle,
    override val tipsCardPreferenceTitle: TextStyle,
    override val tipsCardPreferenceSummary: TextStyle,
    override val relativeCardTitle: TextStyle,
    override val relativeCardLink: TextStyle,
    override val tabItem: TextStyle,
    override val tabItemSelected: TextStyle,
    override val bnbLabel: TextStyle,
    override val searchEdit: TextStyle,
    override val searchHint: TextStyle,
    override val numberPicker: TextStyle,
    override val stringPicker: TextStyle,
    override val timePicker: TextStyle,
    override val datePickerHeader: TextStyle,
    override val datePickerSunday: TextStyle,
    override val datePickerSaturday: TextStyle,
    override val datePickerNormalDay: TextStyle,
    override val datePickerNumberSaturday: TextStyle,
    override val datePickerNumberSunday: TextStyle,
    override val datePickerNumberNormalDay: TextStyle,
    override val fullscreenDialogButtonLabel: TextStyle,
    override val navigationRailItemLabel: TextStyle,
    override val navigationRailItemLabelSelected: TextStyle,
    override val navigationRailItemEndLabel: TextStyle,
    override val appInfoLayoutButton: TextStyle
) : OneUITypographyTheme {

    companion object {

        private val robotoFamily = FontFamily(
            Font(
                familyName = DeviceFontFamilyName("sec-roboto-light")
            )
        )

        fun create(
            colorTheme: OneUIColorTheme,
            dimens: IDynamicDimensions
        ): OneUITypographyTheme = colorTheme.seslBtnDefaultTextColor.let {
            RobotoTypographyTheme(
                button = TextStyle(
                    color = it,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = robotoFamily
                ),
                drawerItemLabel = TextStyle(
                    fontSize = 18.sp,
                    color = colorTheme.drawerItemLabel,
                    fontFamily = robotoFamily,
                    fontWeight = FontWeight.Normal
                ),
                drawerItemLabelSelected = TextStyle(
                    fontSize = 18.sp,
                    color = colorTheme.drawerItemLabelSelected,
                    fontFamily = robotoFamily,
                    fontWeight = FontWeight.Bold
                ),
                drawerItemEndLabel = TextStyle(
                    fontSize = 13.sp,
                    color = colorTheme.drawerItemEndLabel,
                    fontFamily = robotoFamily,
                    fontWeight = FontWeight.Normal
                ),
                appbarTitleCollapsed = TextStyle(
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorTheme.seslActionBarTextColorTitle,
                    fontFamily = robotoFamily
                ),
                appbarTitleExtended = TextStyle(
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Light,
                    fontFamily = robotoFamily,
                    color = colorTheme.seslActionBarTextColorTitle
                ),
                appbarSubtitle = TextStyle(
                    fontSize = 15.sp,
                    fontFamily = robotoFamily,
                    color = colorTheme.seslActionBarTextColorSubtitle
                ),
                textSeparator = TextStyle(
                    fontSize = 13.sp,
                    fontFamily = robotoFamily,
                    fontWeight = FontWeight.Bold,
                    color = colorTheme.seslListSubheaderTextColor
                ),
                radioLabel = TextStyle(
                    fontFamily = robotoFamily,
                    color = colorTheme.seslPrimaryTextColor
                ),
                checkboxLabel = TextStyle(
                    fontFamily = robotoFamily,
                    color = colorTheme.seslPrimaryTextColor
                ),
                menuLabel = TextStyle(
                    fontFamily = robotoFamily,
                    fontSize = 16.sp,
                    color = colorTheme.seslPopupMenuItemTextColorNormal
                ),
                menuLabelSelected = TextStyle(
                    fontFamily = robotoFamily,
                    fontSize = 16.sp,
                    color = colorTheme.seslPopupMenuItemTextColorChecked
                ),
                menuLabelDisabled = TextStyle(
                    fontFamily = robotoFamily,
                    fontSize = 16.sp,
                    color = colorTheme.seslPopupMenuItemTextColorDisabled
                ),
                preferenceTitle = TextStyle(
                    fontFamily = robotoFamily,
                    fontSize = 17.sp,
                    color = colorTheme.seslPrimaryTextColor
                ),
                preferenceSummary = TextStyle(
                    fontFamily = robotoFamily,
                    fontSize = 13.sp,
                    color = colorTheme.seslSecondaryTextColor
                ),
                dialogBody = TextStyle(
                    fontFamily = robotoFamily,
                    fontSize = 14.sp,
                    color = colorTheme.seslPrimaryTextColor
                ),
                dialogTitle = TextStyle(
                    fontFamily = robotoFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorTheme.seslDialogTitleTextColor
                ),
                dialogButtonLabel = TextStyle(
                    fontFamily = robotoFamily,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorTheme.seslDialogButtonTextColor
                ),
                dialogButtonLabelThreeButtons = TextStyle(
                    fontFamily = robotoFamily,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorTheme.seslDialogButtonTextColor
                ),
                editTextText = TextStyle(
                    fontFamily = robotoFamily,
                    fontSize = 17.sp,
                    color = colorTheme.seslEditTextColor
                ),
                editTextHint = TextStyle(
                    fontFamily = robotoFamily,
                    fontSize = 17.sp,
                    color = colorTheme.seslEditTextHintColor
                ),
                switchBarLabel = TextStyle(
                    fontFamily = robotoFamily,
                    fontSize = dimens.switchBarLabelTextSize,
                    color = colorTheme.seslPrimaryColor,
                    fontWeight = FontWeight.Bold
                ),
                switchBarLabelOff = TextStyle(
                    fontFamily = robotoFamily,
                    fontSize = dimens.switchBarLabelTextSize,
                    color = colorTheme.seslPrimaryTextColor,
                    fontWeight = FontWeight.Bold
                ),
                tipsCardPreferenceButtonLabel = TextStyle(
                    fontSize = 17.sp,
                    color = colorTheme.seslPrimaryTextColor,
                    fontWeight = FontWeight.Bold,
                    fontFamily = robotoFamily
                ),
                tipsCardPreferenceTitle = TextStyle(
                    fontSize = 19.sp,
                    color = colorTheme.seslPrimaryTextColor,
                    fontWeight = FontWeight.Bold,
                    fontFamily = robotoFamily
                ),
                tipsCardPreferenceSummary = TextStyle(
                    fontSize = 15.sp,
                    color = colorTheme.seslPrimaryTextColor,
                    fontFamily = robotoFamily,
                ),
                relativeCardTitle = TextStyle(
                    fontSize = 18.sp,
                    color = colorTheme.seslPrimaryDarkColor,
                    fontFamily = robotoFamily
                ),
                relativeCardLink = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorTheme.seslPrimaryColor,
                    fontFamily = robotoFamily
                ),
                tabItem = TextStyle(
                    color = colorTheme.seslTablayoutTextColorDefault,
                    fontSize = 15.sp,
                    fontFamily = robotoFamily
                ),
                tabItemSelected = TextStyle(
                    color = colorTheme.seslTablayoutTextColorSelected,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    fontFamily = robotoFamily
                ),
                bnbLabel = TextStyle(
                    color = colorTheme.seslNavigationBarText,
                    fontSize = 12.sp,
                    fontFamily = robotoFamily
                ),
                searchEdit = TextStyle(
                    color = colorTheme.seslEditTextColor,
                    fontSize = 21.sp,
                    fontFamily = robotoFamily,
                    fontWeight = FontWeight.Bold
                ),
                searchHint = TextStyle(
                    color = colorTheme.seslEditTextHintColor,
                    fontSize = 21.sp,
                    fontFamily = robotoFamily,
                    fontWeight = FontWeight.Bold
                ),
                numberPicker = TextStyle(
                    fontFamily = robotoFamily,
                    fontSize = 40.sp
                ),
                stringPicker = TextStyle(
                    fontFamily = robotoFamily,
                    fontSize = 40.sp
                ),
                timePicker = TextStyle(
                    fontFamily = robotoFamily,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                ),
                datePickerHeader = TextStyle(
                    fontFamily = robotoFamily,
                    fontSize = 16.sp,
                    color = colorTheme.seslDatePickerHeaderTextColor
                ),
                datePickerSunday = TextStyle(
                    fontFamily = robotoFamily,
                    fontSize = 12.sp,
                    color = colorTheme.seslDatePickerSundayTextColor,
                    textAlign = TextAlign.Center
                ),
                datePickerSaturday = TextStyle(
                    fontFamily = robotoFamily,
                    fontSize = 12.sp,
                    color = colorTheme.seslDatePickerSaturdayTextColor,
                    textAlign = TextAlign.Center
                ),
                datePickerNormalDay = TextStyle(
                    fontFamily = robotoFamily,
                    fontSize = 12.sp,
                    color = colorTheme.seslDatePickerNormalTextColor,
                    textAlign = TextAlign.Center
                ),
                datePickerNumberSunday = TextStyle(
                    fontFamily = robotoFamily,
                    fontSize = 13.sp,
                    color = colorTheme.seslDatePickerSundayNumberTextColor,
                    textAlign = TextAlign.Center
                ),
                datePickerNumberSaturday = TextStyle(
                    fontFamily = robotoFamily,
                    fontSize = 13.sp,
                    color = colorTheme.seslDatePickerSaturdayWeekTextColor,
                    textAlign = TextAlign.Center
                ),
                datePickerNumberNormalDay = TextStyle(
                    fontFamily = robotoFamily,
                    fontSize = 13.sp,
                    color = colorTheme.seslDatePickerNormalDayNumberTextColor,
                    textAlign = TextAlign.Center
                ),
                fullscreenDialogButtonLabel = TextStyle(
                    fontFamily = robotoFamily,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorTheme.seslPrimaryTextColor,
                    textAlign = TextAlign.Center
                ),
                navigationRailItemLabel = TextStyle(
                    fontSize = 18.sp,
                    color = colorTheme.drawerItemLabel,
                    fontFamily = robotoFamily,
                    fontWeight = FontWeight.Normal
                ),
                navigationRailItemLabelSelected = TextStyle(
                    fontSize = 18.sp,
                    color = colorTheme.drawerItemLabelSelected,
                    fontFamily = robotoFamily,
                    fontWeight = FontWeight.Bold
                ),
                navigationRailItemEndLabel = TextStyle(
                    fontSize = 13.sp,
                    color = colorTheme.drawerItemEndLabel,
                    fontFamily = robotoFamily,
                    fontWeight = FontWeight.Normal
                ),
                appInfoLayoutButton = TextStyle(
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorTheme.appInfoLayoutButtonText,
                    fontFamily = robotoFamily
                )
            )
        }
    }
}