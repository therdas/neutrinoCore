package functions.groups.stack

import base.context.ReferenceTo
import hardware.FlagsRegister

//Call convention (flags, [source, sp, memoryAt]
fun fPush(context: List<ReferenceTo>): Boolean {
    val source = context[0]
    val sp = context[1]
    val mem = context[2]

    sp.setVal(sp.getVal() - 2)
    mem.setVal(source.getVal())
    return true
}

//Call convention (flags, [dest, sp, memoryAt]
fun fPop(context: List<ReferenceTo>): Boolean {
    val dest = context[0]
    val sp = context[1]
    val mem = context[2]

    sp.setVal(sp.getVal() + 2)
    dest.setVal(mem.getVal())
    return true
}

fun fSwap(context: List<ReferenceTo>):Boolean {
    val dest = context[0]
    val mem = context[2]
    val temp = dest.getVal()
    dest.setVal(mem.getVal())
    mem.setVal(temp)

    return true
}