import base.Base16
import base.Base8
import base.context.ReferenceTo

import hardware.*

import functions.groups.arithmetic.*

fun main(args: Array<String>) {
    val reg = RegisterFile()
    val mem = Memory()
    mem.set(Base16(0x00FF), Base8(0x0F))
    mem.set(Base16(0x000E), Base8(0x01))
    reg.a.value = 0x01
    reg.b.value = 0x01
    println("" + reg.a.value + ", " + reg.b.value)

    val a = listOf<(FlagsRegister, MutableList<ReferenceTo>)-> Boolean>(::fAdd8, ::fAddWC8)
    for (i in a) {
        val lst = mutableListOf<ReferenceTo>(MemoryReference(mem, Base16(0x0E)), MemoryReference(mem, Base16(0x00FF)))
        i(reg.flags, lst)
        println("" + mem.get(Base16(0x00FF)).value + ", " + mem.get(Base16(0x000E)).value + ", " + reg.flags.cy)
    }
    println(":::" + mem.get(Base16(0x000E)).value)
}