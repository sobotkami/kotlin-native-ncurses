# kotlin-native-ncurses
Minimal example of Kotlin/Native with NCurses. It demonstrates how to setup the CInterop with the NCurses library.

Tested on Fedora 32. The app take about 600 kiB of RAM in the debug version.

```
import kotlinx.cinterop.CPointer
import ncurses.*
import platform.posix.sleep

fun initWindow() = initscr() ?: throw RuntimeException("Error initialising ncurses.")

fun cleanUp(window: CPointer<WINDOW>) {
    delwin(window)
    endwin()
    refresh()
}

fun showHelloWorld() {
    mvaddstr(13, 33, "Hello, world!")
    refresh()
    sleep(3)
}

fun main() {
    println("starting ncurses ...")

    initWindow().apply {
        try {
            showHelloWorld()
        } finally {
            cleanUp(this)
        }
    }
}
```
