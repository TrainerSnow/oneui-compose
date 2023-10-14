# Getting started

## Dependency 

This library is hosted at [Jitpack](https://jitpack.io/#TrainerSnow/oneui-compose). Therefore, you need to add the jitpack maven repository to your maven repos:
```kotlin
repositories {
  maven { url 'https://jitpack.io' }
}
```

#### Dependency catalogs
1. Add these entries to your `libs.versions.toml`:
   ```toml
   [Versions]
   oneui-compose = "<latest version>"

   [Libraries]
   oneui-compose = { group = "com.github.TrainerSnow", name = "oneui-compose", version.ref = "oneui-compose" }
   ```
2. Reference this entry in your `build.gradle(.kts)`:
   ```kotlin
    dependencies {
      implementation(libs.oneui.compose)
   }
   ```
#### Legacy gradle
Add the following line to your `build.gradle(.kts)`:
```kotlin
dependencies {
  implementation("com.github.TrainerSnow:oneui-compose:<latest version>")
}
```

## Using the library

Having added the dependency, you start off by calling the `OneUITheme` composable at the root of your application:

```kotlin
OneUITheme {
  //Your app here
}
```

Doing so will automatically take care of several aspects:

- Dark/Light color scheme is automatically adapted depending on the devices setting.  
    This can be further customized by either calling the `OneUIColorTheme.getTheme(...)` or passing a custom `OneUIColorTheme` (not recommended).
- Specific components (namely dialogs) will adjust to the devices width and height.
    This can be further customized by passing a custom `IDynamicDimensions` object (not recommended)

Regardless of using the `OneUITheme` composable you can now call the components like in any other library.

## Basic components
Generally, the basic components are named like their counterpart in the material3 library for jetpack compose. These include, but are not limited to:

- Buttons
- Checkboxes
- Radio Buttons
- Switches
- NavigationDrawer
- NavigationRail

### Special components
- `RoundedCornerBox` acts as a surface in OneUI apps.
- `CollapsingToolbarLayout` is a commonly used screen root.
- `ProgressIndicator` is the composable used for displaying progress. The type can be specified via the `type` parameter.
- `TabItem` and `SubTabItem` are commonly used navigation components in OneUI apps

## Theme
### Colors
All the colors used in OneUI-Design and in the library are contained in the `OneUITheme.colors` object.
Because these were extracted from decompiled OneUI apps, their naming can be confusing.
#### Commonly used colors:
- `seslPrimaryColor` the blue color, e.g. in Checkboxes, Radio buttons or colored buttons.
- `seslPrimaryTextColor` the default text color
- `seslSecondaryTextColor` the secondary text color
- `seslRippleColor` the color used for ripple animations
- `seslRoundAndBgcolor` used as a background on all major screen, like the CollapsingToolbarLayout or DrawerLayout
- `seslBackgroundColor` used as background on all componenty lying **ontop** a background
- `seslWhite` and `seslBlack` the alternatives to using pure white and black color
- `seslFunctional*` raw colorful colors used to indicate error/succes etc.

### Typography
All the text styles used in the library are contained in the `OneUITheme.types` object.
For custom components in your app it is recommended to simply create a new `TextStyle` with the roboto font-family applied.


## Interop with material3
This library is built upon the material3 library of jetpack compose, and uses several components, such as `Text`, `WindowSizeClass` or `ProvideTextStyle`.
Using it in your project is needed, as the oneui-compose library does not provide methods for displaying texts or ripples.

Whenever possible, the oneui-compose library names its components identical to the material3 components. This can lead to confusion and requires developers to check which version of e.g. a `Button` they are importing.

Using material3 components like a `Button` or a `Checkbox` is not recommended, as this can cause issues with a uniform look.
