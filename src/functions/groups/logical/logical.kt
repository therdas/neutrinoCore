package functions.groups.logical

import base.context.ReferenceTo
import functors.*
import hardware.FlagReference
import hardware.FlagsRegister

/* Call convention: (flags, [acc, reg])
 */
fun fAnd(flags: FlagsRegister, acc: ReferenceTo, reg: ReferenceTo):Boolean {
    val res    = applyLogical(acc.getVal(), reg.getVal(), ::And)
    acc.setVal(res.value)
    flags.fromContext(res)
    return true
}

/* Call convention: (flags, [acc, reg])
 */
fun fOr(flags: FlagsRegister, acc: ReferenceTo, reg: ReferenceTo):Boolean {
    val res    = applyLogical(acc.getVal(), reg.getVal(), ::Or)
    acc.setVal(res.value)
    flags.fromContext(res)
    return true
}

/* Call convention: (flags, [acc, reg])
 */
fun fXor(flags: FlagsRegister, acc: ReferenceTo, reg: ReferenceTo):Boolean {
    val res    = applyLogical(acc.getVal(), reg.getVal(), ::Xor)
    acc.setVal(res.value)
    flags.fromContext(res)
    return true
}

/* Call convention: (flags, [acc, reg])
 */
fun fNot(acc: ReferenceTo):Boolean {
    val res    = applyLogical(acc.getVal(), Not)
    acc.setVal(res.value)
    return true
}

fun fChangeCarry(flags: FlagsRegister, bin: Boolean): Boolean {
    flags.cy = bin
    return true
}

fun fCompare(flags: FlagsRegister, acc: ReferenceTo, reg: ReferenceTo): Boolean {
    val res = adder(acc.getVal(), reg.getVal().twosComplement(), false)
    res.S = !res.S
    flags.fromContext(res)
    return true
}