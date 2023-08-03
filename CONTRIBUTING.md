# How to contribute
Simply fork the project, make some commits and open a pull request like usually.

## Guidelines
While its great to keep the official [kotlin style conventions](https://kotlinlang.org/docs/coding-conventions.html#horizontal-whitespace) in mind, these are not mandatory.
Just try to keep your code clean and readable.

## Component structure
When implementing a simple to medium sized component, please try to follow the unified structure as best as possible:

### File structure
(Same order as in the file itself)
- Core composable: This is the main composable that will construct the component. This is the main composable to be called by library user.
- Core overloads: These are optional overloads for the Core composable, for either a) variations or b) simplicity. Examples are a) Vertical/Horizontal Seekbars, and b)
  multiple overloads for AlertDialog(), taking string values instead of composables for the title and buttons.
- Helper composables/DrawScope functions: These are optional functions to help construct the Core composable. They should be kept `ìnternal`, or preferably `private`.
- Defaults object: This singleton `object` contains dimension values, such as height, width, padding or margin. These values are hardcoded. 
   Could also contain e.g. animation durations. Values should generally be kept as `dp`, if not possible as a `Float` (denoting pixel values).
- Color class: This is a `data class`, containing color fields for all the colors used in the component, such as background, scrim or stroke.
- Color composable: This composable takes all the colors mentioned in the Color class as parameters, and sets the default values as default parameters using the
  `OneUITheme.colors` object. It then returns a constructed instance of the Color class.
- Preview composable: If necessary/wanted, a preview composable can help with developing a single composable.
### Composable structure
Certain parameters are prevalent in all, or most, composables, be they layouts, buttons or dialogs. Such include:

- `modifier: Modifier` to customize basic attributes like `background`, `border`or `clickable`. Every composable should have and use this parameter.
- `colors: [ColorClass]` to customize the colors used in the composable. This should be default initialized with the color composable.
- `onClick: () -> Unit` to handle click events.
- `enabled: Boolean` to set the component enabled/disabled.
- [Component specific parameters]
- `interactionSource: MutableInteractionSource` to programmatically interact with this component. Every composable that's clickable should have and use this parameter.
- `content: @Composable () -> Unit` when the composable has content to be put inside.

Every composable should use these parameters, if applicable, in the presented order.