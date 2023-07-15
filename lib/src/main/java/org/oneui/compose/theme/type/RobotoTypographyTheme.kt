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
    override val colorPickerHexSectionEdit: TextStyle,
    override val colorPickerHexSectionTitle: TextStyle,
    override val colorPickerRgbSectionTitle: TextStyle,
    override val colorPickerRgbSectionEdit: TextStyle,
    override val coloPickerSeekbarTitle: TextStyle,
    override val colorPickerSeekbarProgressEdit: TextStyle,
    override val colorPickerSeekbarProgressLabel: TextStyle,
    override val colorPickerTab: TextStyle,
    override val colorPickerTabSelected: TextStyle
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
                    color = colorTheme.drawerItemLabelColor,
                    fontFamily = robotoFamily,
                    fontWeight = FontWeight.Normal
                ),
                drawerItemLabelSelected = TextStyle(
                    fontSize = 18.sp,
                    color = colorTheme.drawerItemLabelColor,
                    fontFamily = robotoFamily,
                    fontWeight = FontWeight.Bold
                ),
                drawerItemEndLabel = TextStyle(
                    fontSize = 13.sp,
                    color = colorTheme.drawerItemLabelEndColor,
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
                    color = colorTheme.textSeparatorText
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
                    color = colorTheme.relativeCardTitleText,
                    fontFamily = robotoFamily
                ),
                relativeCardLink = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorTheme.seslPrimaryColor,
                    fontFamily = robotoFamily
                ),
                colorPickerHexSectionEdit = TextStyle(
                    fontSize = 14.sp,
                    color = colorTheme.seslPrimaryTextColor,
                    fontFamily = robotoFamily,
                    textAlign = TextAlign.Center
                ),
                colorPickerHexSectionTitle = TextStyle(
                    fontSize = 12.sp,
                    color = colorTheme.seslSecondaryTextColor,
                    fontWeight = FontWeight.Bold,
                    fontFamily = robotoFamily,
                    textAlign = TextAlign.Center
                ),
                colorPickerRgbSectionEdit = TextStyle(
                    fontSize = 14.sp,
                    color = colorTheme.seslPrimaryTextColor,
                    fontFamily = robotoFamily,
                    textAlign = TextAlign.Center
                ),
                colorPickerRgbSectionTitle = TextStyle(
                    fontSize = 12.sp,
                    color = colorTheme.seslSecondaryTextColor,
                    fontWeight = FontWeight.Bold,
                    fontFamily = robotoFamily,
                    textAlign = TextAlign.Center
                ),
                coloPickerSeekbarTitle = TextStyle(
                    fontSize = 14.sp,
                    color = colorTheme.seslSecondaryTextColor,
                    fontWeight = FontWeight.Bold,
                    fontFamily = robotoFamily
                ),
                //Font size is 14 dp, but we cant declare this here. Conversion from dp -> sp needs @Composable scope. To be declared on-site
                colorPickerSeekbarProgressEdit = TextStyle(
                    color = colorTheme.seslPrimaryTextColor,
                    fontFamily = robotoFamily,
                    textAlign = TextAlign.End
                ),
                //Font size is 14 dp, but we cant declare this here. Conversion from dp -> sp needs @Composable scope. To be declared on-site
                colorPickerSeekbarProgressLabel = TextStyle(
                    color = colorTheme.seslSecondaryTextColor,
                    fontFamily = robotoFamily,
                    textAlign = TextAlign.End
                ),
                //Font size is 15 dp, but we cant declare this here. Conversion from dp -> sp needs @Composable scope. To be declared on-site
                colorPickerTab = TextStyle(
                    color = colorTheme.seslSecondaryTextColor,
                    textAlign = TextAlign.Center,
                    fontFamily = robotoFamily
                ),
                colorPickerTabSelected = TextStyle(
                    color = colorTheme.seslDialogBodyTextColor,
                    textAlign = TextAlign.Center,
                    fontFamily = robotoFamily,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}