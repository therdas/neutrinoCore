import base.Base16
import base.Base8
import base.context.ReferenceTo

import hardware.*

import functions.*
val preCompiledExampleOne = arrayOf(Pair(0xF000, 0x3E),
        Pair(0xF001, 0x05),
        Pair(0xF002, 0x06),
        Pair(0xF003, 0x07),
        Pair(0xF004, 0x80),
        Pair(0xF005, 0x0E),
        Pair(0xF006, 0x0A),
        Pair(0xF007, 0x76))

fun main(args: Array<String>) {
    val reg = RegisterFile()
    val mem = Memory()
    reg.hl = Base16(0x3412)
    initFunctionsLibrary(reg, mem)
    functionMap[0x22]?.invoke(Base8(0x22), Base16(0xBFCE))
    println(":::" + mem.get(Base16(0xBFCE)).value + mem.get(Base16(0xBFCF)).value)
}