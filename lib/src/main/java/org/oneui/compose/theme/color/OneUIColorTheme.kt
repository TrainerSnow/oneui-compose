package org.oneui.compose.theme.color

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

interface OneUIColorTheme {

    val seslActionBarBackgroundColor: Color
    val seslActionBarTextColorMenu: Color
    val seslActionBarTextColorSubtitle: Color
    val seslActionBarTextColorTitle: Color
    val seslActionBarToastTextColor: Color
    val seslBackgroundColor: Color
    val seslBackgroundFloating: Color
    val seslBtnBackgroundColor: Color
    val seslBtnDefaultTextColor: Color
    val seslControlNormalColor: Color
    val seslDescriptionTextColor: Color
    val seslDialogBodyTextColor: Color
    val seslDialogButtonRedText: Color
    val seslDialogButtonTextColor: Color
    val seslDialogListTextColor: Color
    val seslDialogScrollbarHandleTintColorMtrl: Color
    val seslDialogTitleTextColor: Color
    val seslDropdownScrollbarHandleColor: Color
    val seslEdgeEffectDeviceDefault: Color
    val seslEditTextColor: Color
    val seslEditTextColorDisabled: Color
    val seslEditTextHintColor: Color
    val seslEditTextTintColor: Color
    val seslEditTextTintColorDisabled: Color
    val seslEditTextTintColorUnfocused: Color
    val seslFunctionalGreen: Color
    val seslFunctionalOrange: Color
    val seslFunctionalRed: Color
    val seslHighlightedTextColor: Color
    val seslListDividerColor: Color
    val seslListGoToTopArrowBottom: Color
    val seslListGoToTopArrowTop: Color
    val seslListGoToTopInnerRound: Color
    val seslListGoToTopOuterRound: Color
    val seslListGoToTopRipple: Color
    val seslListRippleColor: Color
    val seslPopupMenuDividerColor: Color
    val seslPopupMenuItemTextColorChecked: Color
    val seslPopupMenuItemTextColorDisabled: Color
    val seslPrimaryColor: Color
    val seslPrimaryDarkColor: Color
    val seslPrimaryTextColor: Color
    val seslSecondaryTextColor: Color
    val seslProgressControlColorActivated: Color
    val seslProgressControlColorNormal: Color
    val seslProgressDialogBackgroundColor: Color
    val seslProgressDialogCircleTextColor: Color
    val seslRippleColor: Color
    val seslRoundAndBgcolor: Color
    val seslScrollbarHandleTintColor: Color
    val seslSearchViewBackgroundHintTextColor: Color
    val seslSearchViewBackgroundIconColor: Color
    val seslSearchViewBackgroundTextColor: Color
    val seslSeekbarDisableColorActivated: Color
    val seslSeekbarOverlapColorActivated: Color
    val seslSeekbarOverlapColorDefault: Color
    val seslSpinnerDropdownItemTextColorDisabled: Color
    val seslSpinnerDropdownItemTextColorNormal: Color
    val seslSpinnerIconColorDefault: Color
    val seslSpinnerIconColorDisabled: Color
    val seslSpinnerItemTextColorDisabled: Color
    val seslSpinnerItemTextColorNormal: Color
    val seslStatusBarColor: Color
    val seslSwitchDividerColor: Color
    val seslSwitchbarOffBackgroundColor: Color
    val seslSwitchbarOffTextColor: Color
    val seslSwitchbarOnBackgroundColor: Color
    val seslTooltipBackground: Color
    val seslTooltipForeground: Color
    val seslFragmentBgcolor: Color
    val seslFragmentFgcolor: Color
    val seslIndexBarBackgroundTintColor: Color
    val seslIndexBarTextColor: Color
    val seslDatePickerButtonTintColor: Color
    val seslDatePickerHeaderTextColor: Color
    val seslDatePickerNormalDayNumberTextColor: Color
    val seslDatePickerNormalTextColor: Color
    val seslDatePickerSaturdayTextColor: Color
    val seslDatePickerSaturdayWeekTextColor: Color
    val seslDatePickerSelectedDayNumberTextColor: Color
    val seslDatePickerSundayNumberTextColor: Color
    val seslDatePickerSundayTextColor: Color
    val seslColorPickerSelectedColorItemTextColor: Color
    val seslColorPickerStrokeColor: Color
    val seslColorPickerTabSelectedBackground: Color
    val seslColorPickerTabSelected: Color
    val seslColorPickerUsedColorItemEmptySlotColor: Color
    val seslColorPickerUsedColorTextColor: Color
    val seslSwipeRefreshBackground: Color
    val foregroundMaterial: Color
    val backgroundMaterial: Color
    val backgroundFloatingMaterial: Color
    val primaryMaterial: Color
    val primaryDarkMaterial: Color
    val accentMaterial: Color
    val switchThumbNormalMaterial: Color
    val primaryTextDefaultMaterial: Color
    val secondaryTextDefaultMaterial: Color
    val seslDialogDividerColorMaterial: Color
    val seslDialogSubTextColorMaterial: Color
    val seslDialogWindowBackgroundColorMaterial: Color
    val abcDecorViewStatusGuard: Color
    val seslActionBarRippleColor: Color
    val seslBlack: Color
    val seslWhite: Color
    val seslContextMenuTitleTextColor: Color
    val seslControlActivatedColor: Color
    val seslDialogShowButtonBackgroundColor: Color
    val seslDialogTextBodyColor: Color
    val seslErrorColor: Color
    val seslFastScrollbarBgColor: Color
    val seslInputMethodNavigationGuard: Color
    val seslListSubheaderTextColor: Color
    val seslLoadingProgressColor1: Color
    val seslLoadingProgressColor2: Color
    val seslMenuBadgeBackgroundColor: Color
    val seslMenuBadgeTextColor: Color
    val seslMenuPopupBackgroundColor: Color
    val seslMenuPopupBackgroundStrokeColor: Color
    val seslPopupMenuBlurBackground: Color
    val seslPopupMenuItemTextColorNormal: Color
    val seslProgressControlColorBackground: Color
    val seslProgressDialogHorizontalTextColor: Color
    val seslScrollbarIndexTipColor: Color
    val seslSearchResultSubtitleTextColor: Color
    val seslSearchResultTitleTextColor: Color
    val seslSearchTextfieldColor: Color
    val seslSearchUrlTextDefault: Color
    val seslSearchUrlTextPressed: Color
    val seslSearchUrlTextSelected: Color
    val seslSearchViewHintTextColor: Color
    val seslSearchViewIconColor: Color
    val seslSearchViewTextColor: Color
    val seslSeekbarControlColorActivated: Color
    val seslSeekbarControlColorDefault: Color
    val seslSeekbarControlColorSecondary: Color
    val seslShowButtonShapesColorDefault: Color
    val seslShowButtonShapesColorDisabled: Color
    val seslShowButtonShapesStrokeColorDefault: Color
    val seslShowButtonShapesStrokeColorDisabled: Color
    val seslSpinnerDropdownItemCheckmark: Color
    val seslSwitchThumbOffColor: Color
    val seslSwitchThumbOffDisabledColor: Color
    val seslSwitchThumbOffDisabledStrokeColor: Color
    val seslSwitchThumbOffStrokeColor: Color
    val seslSwitchThumbOnColor: Color
    val seslSwitchThumbOnDisabledColor: Color
    val seslSwitchTrackOffColor: Color
    val seslSwitchTrackOffDisabledColor: Color
    val seslThumbControlColorActivated: Color
    val seslThumbTintColorDisabled: Color
    val seslAppPickerListActionIconTintColor: Color
    val notificationTemplateIconBg: Color
    val notificationTemplateIconLowBg: Color
    val notificationActionColorFilter: Color
    val notificationIconBgColor: Color
    val seslIndexScrollPreviewTextColor: Color
    val seslNumberPickerTextColor: Color
    val seslNumberPickerTextHighlightColor: Color
    val seslDatePickerWeekdayNumberTextColor: Color
    val seslColorPickerCursorStrokeColor: Color
    val seslColorPickerDivider: Color
    val seslColorPickerPercentTextColor: Color
    val seslColorPickerSeekbarCursorBackgroundColor: Color
    val seslColorPickerSeekbarCursorStrokeColor: Color
    val seslColorPickerShadow: Color
    val seslColorPickerStrokeColorSpectrumview: Color
    val seslColorPickerStrokeColorSwatchview: Color
    val seslColorPickerSwatchCursorColor: Color
    val seslColorPickerSwatchCursorColorForWhite: Color
    val seslColorPickerTabUnselected: Color
    val seslColorPickerTransparent: Color
    val preferenceFallbackAccentColor: Color
    val seslSwipeRefreshColor1: Color
    val seslSwipeRefreshColor2: Color
    val materialGrey900: Color
    val materialGrey850: Color
    val materialGrey800: Color
    val materialGrey600: Color
    val materialGrey300: Color
    val materialGrey100: Color
    val materialGrey50: Color
    val materialDeepTeal200: Color
    val materialDeepTeal500: Color
    val materialBlueGrey800: Color
    val materialBlueGrey900: Color
    val materialBlueGrey950: Color
    val seslNavigationBarBackground: Color
    val seslNavigationBarIcon: Color
    val seslNavigationBarRipple: Color
    val seslNavigationBarText: Color
    val seslNavigationBarTextDisable: Color
    val seslNavigationBarTextText: Color
    val seslTablayoutMainTabIndicatorColor: Color
    val seslTablayoutSubtabIndicatorBackground: Color
    val seslTablayoutSubtabSubTextColorDefault: Color
    val seslTablayoutSubtabSubTextColorDisabled: Color
    val seslTablayoutSubtabSubTextColorSelected: Color
    val seslTablayoutSubtabTextColorDefault: Color
    val seslTablayoutSubtabTextColorDisabled: Color
    val seslTablayoutSubtabTextColorSelected: Color
    val seslTablayoutTextColorDefault: Color
    val seslTablayoutTextColorDisabled: Color
    val seslTablayoutTextColorSelected: Color
    val seslTablayoutSubtabBackgroundStrokeColor: Color

    val seslPreferenceRelativeCardBackground: Color
    val ouiTipsCardPreferenceBackground: Color

    /*
    Colors that aren't found in the oui-libs colors.xml file, and therefore 'hardcoded'
     */
    val drawerScrim: Color
    val drawerHeaderIcon: Color
    val drawerItemLabel: Color
    val drawerItemLabelSelected: Color
    val drawerItemEndLabel: Color
    val buttonDefaultBackground: Color
    val simpleColorPickerSelectedStroke: Color
    val navigationRailBackground: Color

    companion object {

        @Composable
        fun getTheme(
            dark: Boolean = false,
            dynamic: Boolean = false,
        ): OneUIColorTheme {
            val canDoDynamicColors = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
            return if(canDoDynamicColors && dynamic && dark) DynamicDarkColorTheme
            else if(canDoDynamicColors && dynamic) DynamicLightColorTheme
            else if(dark) DarkColorTheme
            else LightColorTheme
        }

    }
}