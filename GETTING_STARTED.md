# Getting started

## Dependency 
TODO

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
    This can be further customized by padding a custom `IDynamicDimensions` object (not recommended)

Regardless of using the `OneUITheme` composable you can now call the components like in any other library.