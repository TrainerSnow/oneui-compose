# OneUI Library for Jetpack compose 
This is a library for [Jetpack Compose](https://developer.android.com/jetpack/compose) which aims at enabling developers to use [Samsung's OneUI Design](https://developer.android.com/jetpack/compose).

## Note
While this library does aim at replicating the look of the OneUI Design, it is to note that this will never be truly achieved, due to the closed-source nature 
of the Samsung apps. Still, by decompiling those, we can scrape core information about the apps, such as color, dimension and translation values, from the apps
`res` folder. While this is tedious and sometimes impossible due to code obfuscation, the team at the [OneUI Library for Android XML](https://github.com/OneUIProject)
has done a big part of this work, making a great contribution towards this project.

## Work in Progress
This library is still, as of October 14 2023, work in progress.

Future aims include but are not limited to:

- Markup documentation for every component with preview images
- Support for dynamic theming (OneUI-capable devices only)
- Color Picker dialog

Further problems are noted as TODO-comments at the core composable of a component.

## Contributing
Please refer to the [contributing-guide](CONTRIBUTING.md)

## Getting started
Please refer to [getting started](GETTING_STARTED.md)

## Design Library

This library is **only** a design library, meaning only visuals, no implementation is provided. Meaning for example, the technical implementation for the preference 
components of storing a users preferences must be implemented by each app itself. This may change in the future, although currently this is no big priority.

## Credits
- [Samsung](https://www.samsung.com/) for their awesome OneUI Design.
- [Yanndroid](https://github.com/Yanndroid) and [BlackMesa123](https://github.com/BlackMesa123) for their work at the [OneUI Library for Android XML](https://github.com/OneUIProject)




















