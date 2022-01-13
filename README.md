# kotlin-native-ncurses
Minimal example of Kotlin/Native with NCurses. It demonstrates how to set up the CInterop with the NCurses library.

Tested on Fedora 35. The app takes about 294 kB of RAM in the debug version.

The `ncurses.def` file. Note the `--allow-shlib-undefined` (see https://linux.die.net/man/1/ld for more information) which resolves mainly the problem with different glibc versions while linking like `/usr/lib/x86_64-linux-gnu/libtinfo.so: error: undefined reference to 'stat', version 'GLIBC_2.33'`.
```kotlin
headers = curses.h
headerFilter = curses.h
compilerOpts = -I/usr/include -I/usr/include/ncurses
linkerOpts.linux = -L/usr/lib64 -L/usr/lib/x86_64-linux-gnu -lncurses --allow-shlib-undefined
```

The `main.kt` file
```kotlin
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
    sleep(5)
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
