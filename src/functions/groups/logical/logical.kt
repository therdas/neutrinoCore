package functions.groups.logical

import base.context.ReferenceTo
import functors.*
import hardware.FlagReference
import hardware.FlagsRegister

/* Call convention: (flags, [source, dest])
 */
fun fAnd(flags: FlagsRegister, dest: ReferenceTo, source: ReferenceTo):Boolean {
    val res    = applyLogical(source.getVal(), dest.getVal(), ::And)
    source.setVal(res.value)
    flags.fromContext(res)
    return true
}

/* Call convention: (flags, [source, dest])
 */
fun fOr(flags: FlagsRegister, dest: ReferenceTo, source: ReferenceTo):Boolean {
    val res    = applyLogical(source.getVal(), dest.getVal(), ::Or)
    source.setVal(res.value)
    flags.fromContext(res)
    return true
}

/* Call convention: (flags, [source, dest])
 */
fun fXor(flags: FlagsRegister, dest: ReferenceTo, source: ReferenceTo):Boolean {
    val res    = applyLogical(source.getVal(), dest.getVal(), ::Xor)
    source.setVal(res.value)
    flags.fromContext(res)
    return true
}

/* Call convention: (flags, [source, dest])
 */
fun fNot(source: ReferenceTo):Boolean {
    val res    = applyLogical(source.getVal(), Not)
    source.setVal(res.value)
    return true
}

fun fChangeCarry(flags: FlagsRegister, bin: Boolean): Boolean {
    flags.cy = bin
    return true
}

fun fCompare(flags: FlagsRegister, dest: ReferenceTo, source: ReferenceTo): Boolean {
    val res = adder(dest.getVal(), source.getVal().twosComplement(), false)
    res.S = !res.S
    flags.fromContext(res)
    return true
}