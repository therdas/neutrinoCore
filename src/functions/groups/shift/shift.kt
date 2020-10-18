package functions.groups.shift

import base.context.ReferenceTo
import functors.shiftLeft
import functors.shiftRight
import hardware.FlagsRegister

//Convention (flags, [acc, throughCarry])
fun fShiftLeft(flags: FlagsRegister, context: List<ReferenceTo>): Boolean {
    val acc = context[0]
    val throughCarry = context[1].getVal().value > 0

    val res = shiftLeft(acc.getVal(), flags.cy, !throughCarry)
    flags.cy = res.shiftOut

    acc.setVal(res.value)
    return true
}

fun fShiftRight(flags: FlagsRegister, context: List<ReferenceTo>): Boolean {
    val acc = context[0]
    val throughCarry = context[1].getVal().value > 0

    val res = shiftRight(acc.getVal(), flags.cy, !throughCarry)
    flags.cy = res.shiftOut

    acc.setVal(res.value)
    return true
}