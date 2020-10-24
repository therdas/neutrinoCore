import base.Base16
import base.Base8
import base.context.ReferenceTo

import hardware.*

import functions.*

fun main(args: Array<String>) {
    val reg = RegisterFile()
    val mem = Memory()

    initFunctionsLibrary(reg, mem)
    functionMap[0x01]?.invoke(Base8(0x01), Base16(0xBFCE))
    println(functionMap[0x01])
    println(":::" + reg.c.value)
}