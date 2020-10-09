import base.Base8
import base.Base16

fun main(args: Array<String>) {
    var a = Base16(277)
    println("" + a.value + a[4])
    a[4] = false
    println("" + a.value + a[4])
    println("" + (-a).value + a[4])
}