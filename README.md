## WearML Wrapper for Android

### A lightweight library that provides programmatic access to WearML funcitonality for Realwear HMT devices.
#### (Please note this project is not officially associated with Realwear Inc)


## Code Example

In your activity, include the following line in your `OnCreate` method:
``` kotlin
WearML.init(this)
```

To register a command use:
``` kotlin
WearML.register("Hello World") {
    Log.d("WearML", "Hello World") // Prints: WearML: Hello World
}
```

To register a command with multiple activation words use:
``` kotlin
WearML.register("Hello", "Hi", "Hey", "Howdy") {
    Log.d("WearML", "Hello World") // Prints: WearML: Hello World
}
```

To access commands within the called functions use:
``` kotlin
WearML.register("Hello", "Hi", "Hey", "Howdy") { words ->
    val hey = words[2]
    Log.d("WearML", hey) // Prints: WearML: Hey
}
```

To maintain a reference to the registered command use:
``` kotlin
val helloCommand = WearML.register("Hello World") {
    Log.d("WearML", "Hello World") // Prints: WearML: Hello World
}
```

To unregister a command use:
``` kotlin
WearML.unregister(helloCommand)
```

To unregister all commands use:
``` kotlin
WearML.unregisterAll()
```

To register a command with extra directives use:

```kotlin
val directives = mutableListOf<WearML.Directives>().apply {
    add(WearML.Directives.NO_GLOBAL_COMMANDS)
    add(WearML.Directives.NO_PTT_HOME)
}

WearML.register("Hello", extraDirectives= directives) {
    Log.d("WearML", "Hello World") // Prints: WearML: Hello World
}
```

Note: Directives that have an instant effect will be applied immediately, not when the command is invoked.
Further information about directives can be found [here](https://realwear.com/knowledge-center/developer/wearml/wearml-embedded/wearml-embedded-api/).

## Further information
You can also register commands within a fragment, although it is advised that you unregister those commands when the fragment is destroyed, or else those commands will persist to other fragments.\
This would cause your commands to be available in parts of your application that you don't intend, and invoking them could cause a crash. Furthermore, revisitng the fragment would duplicate the command.\
It is therefore recommended that you keep a list of registered commands in your fragment, and unregister each command in your fragment's `onDestroy` method.\
If you don't register any commands in your activity and do so exclusively in your fragments, you can use `unregisterAll` in your `onDestroy` method as no other commands are at risk of being removed.

## License
MIT License

Copyright (c) 2021 Sam Stenner

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.