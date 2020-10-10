import base.Base8
import base.Base16
import functors.adder

fun main(args: Array<String>) {
    var a = Base16(244)
    var result = adder(Base8(7), Base16(8), true)
    println(result.value.value)
    println(result.S)
    println(result.Z)
    println(result.AC)
    println(result.P)
    println(result.CY)
}