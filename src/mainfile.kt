import base.Base8
import base.Base16
import base.context.ReferenceTo
import functors.adder

import hardware.*

import functors.*

fun main(args: Array<String>) {
    var reg: RegisterFile = RegisterFile()
    var mem: Memory = Memory()

    val arr = mutableListOf<ReferenceTo>()
    arr.add(0, RegisterReference(reg.d))
    arr.add(1, MemoryReference(mem, Base16(0xFF00)))
    arr.add(2, FlagReference(reg.flags))


}