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