# OneUI Library for Jetpack compose 
This is a library for [Jetpack Compose](https://developer.android.com/jetpack/compose) which aims at enabling developers to use [Samsung's OneUI Design](https://developer.android.com/jetpack/compose), that's currently used in Samsung apps like Calculator or Contacts, to design apps in compliance with the OneUI Design guidelines.

## Note
While this library does aim at replicating the look of the OneUI Design, it is to note that this will never be truly achieved, due to the closed-source nature 
of the Samsung apps. Still, by decompiling those, we can scrape core information about the apps, such as color, dimension and translation values, from the apps
`res` folder. While this is tedious and sometimes impossible due to code obfuscation, the team at the [OneUI Library for Android XML](https://github.com/OneUIProject)
has done a big part of this work, making a great contribution towards this project.

## Work in Progress
This library is still, as of July 17 2023, WIP and unfinished. The following table can give a glimpse at the current progress.

- **Alpha**: Component is available with loose colors and dimensions. Certain errors in UX are prevalent and yet to be fixed. These components do not yet comply with the lower mentioned structure.
- **Stable**: Component is available with exact colors and dimensions (as exact as possible). 

| Component                              | Alpha | Stable |
|----------------------------------------|:-----:|:------:|
| Buttons                                |   ✅   |   ❌    |
| Preferences                            |   ✅   |   ❌    |
| Checkbox                               |   ✅   |   ❌    |
| Radio Button                           |   ✅   |   ❌    |
| Spinners                               |   ✅   |   ❌    |
| Menus                                  |   ✅   |   ❌    |
| Searchview                             |   ✅   |   ❌    |
| Drawer Layout                          |   ✅   |   ❌    |
| Collapsing Toolbar Layout              |   ✅   |   ❌    |
| Normal Seekbar                         |   ✅   |   ❌    |
| Expanding Seekbar                      |   ✅   |   ❌    |
| Split Seekbar                          |   ❌   |   ❌    |
| Danger Seekbar                         |   ✅   |   ❌    |
| Haptic Seekbar                         |   ✅   |   ❌    |
| Progress Bar (circular, determinate)   |   ❌   |   ❌    |
| Progress Bar (circular, indeterminate) |   ❌   |   ❌    |
| Progress Bar (normal, determinate)     |   ❌   |   ❌    |
| Progress Bar (normal, indeterminate)   |   ❌   |   ❌    |
| Swipe Refresh Layout                   |   ❌   |   ❌    |
| Navigation Bar                         |   ✅   |   ❌    |
| Navigation Bar Tabs                    |   ✅   |   ❌    |
| Navigation Bar Subtabs                 |   ✅   |   ❌    |
| App Picker View                        |   ❌   |   ❌    |
| Index Scroll                           |   ❌   |   ❌    |
| Number Picker                          |   ❌   |   ❌    |
| Time Picker                            |   ❌   |   ❌    |
| Date Picker                            |   ❌   |   ❌    |
| Spinning Date Picker                   |   ❌   |   ❌    |
| Date Picker Dialog                     |   ❌   |   ❌    |
| Time Picker Dialog                     |   ❌   |   ❌    |
| Start End Time Picker Dialog           |   ❌   |   ❌    |
| Color Picker                           |   ❌   |   ❌    |

## Composable structure
Implementation of smaller components, such as Buttons, Checkboxes etc. will have uniformed implementation.

### File structure
(Same order as in the file itself)
- Core composable: This is the main composable that will construct the component. This is the main composable to be called by library user.
- Core overloads: These are optional overloads for the Core composable, for either a) variations or b) simplicity. Examples are a) Vertical/Horizontal Seekbars, and b)
  multiple overloads for AlertDialog(), taking string values instead of composables for the title and buttons.
- Helper composables/DrawScope functions: These are optional functions to help construct the Core composable. They should be kept `ìnternal`, or preferably `private`.  
- Defaults object: This singleton `object` contains dimension values, such as height, width, padding or margin. These values are hardcoded and obtained
  [as mentioned](#note). Could also contain e.g. animation durations. Values should generally be kept in `dp`, if not possible in`px`.
- Color class: This is a `data class`, containing color fields for all the colors used in the component, such as background, scrim or stroke.
- Color composable: This composable takes all the colors mentioned in the Color class as parameters, and sets the default values as default parameters using the
  `OneUITheme.colors` object. It then returns a constructed instance of the Color class.
- Preview composable: If necessary/wanted, a preview composable can help with developing a single composable.

### Composable structure
Certain parameters are prevalent in all, or most, composables, be they layouts, buttons or dialogs. Such include:

- `modifier: Modifier`, to customize basic attributes like `background`, `border`or `clickable`. Every composable should have and use this parameter.
- `colors: [ColorClass]`, to customize the colors used in the composable. This should be default initialized with the color composable.
- `onClick: () -> Unit` to handle click events.
- `interactionSource: MutableInteractionSource`, to automate interacting with the component. Every composable that's clickable should have and use this parameter.
- `content: @Composable () -> Unit`, when the composable has content to be put inside.

Every composable should use these parameters, if applicable, in the presented order.

## Theme

The theme system was copied/adapted from the material3 library for Jetpack Compose.
To access theme values, your app should be surrounded by the `OneUITheme` composable, with optional configuration:
```kotlin
OneUITheme {
  //Your app here
}
```

This gives you access to the `OneUITheme`singleton object, which contains several fields:

- `colors`: This holds access to an `IColorTheme`, which is by default initialized depending on the dark/light mode setting.
  Some of the fields have been scraped from the `colors.xml` file from the [OneUI Library for Android XML](https://github.com/OneUIProject), some have been manually added.
  In the future, all fields should have been reviewed and manually added.
- `types`: This holds access to an `ITypographyTheme`, which is initialized to a default. It contains fields for all `TextStyle`s, used by different
  composables in the library.
- `dimensions`: This holds access to an `IDynamicDimensions`, which contains dimensions values such as margins, padding, widths and heights, that depend on
  the device's width, height and orientation. These values are stored in the `res`folder, using android resource classifiers, and are parsed and selected at runtime.

The `Theme` composable takes parameters for all of these objects, so they can be customized.

To only have a part of the app customized, you can provide custom values using the according [CompositionLocal](https://developer.android.com/jetpack/compose/compositionlocal)
























