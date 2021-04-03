package packrle

import java.lang.IllegalArgumentException

fun main(arg: Array<String>) {
    try {
        Runner().main(arg)
    } catch (e: IllegalArgumentException) {
        println(e.message)
    }
}