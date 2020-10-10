import base.Base8
import base.Base16
import functors.adder
import functors.*

fun main(args: Array<String>) {
    var a = Base8(0x01)
    for (i: Int in 0 until 25) {
        val res = shiftRight(a, false, true)
        println(" " + res.value.value + ", " + res.serialOut)
        a = Base8(res.value)
    }
}