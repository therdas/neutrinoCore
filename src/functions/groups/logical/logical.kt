package functions.groups.logical

import base.context.ReferenceTo
import functors.*
import hardware.FlagReference
import hardware.FlagsRegister

/* Call convention: (flags, [source, dest])
 */
fun fAnd(flags: FlagsRegister, context: List<ReferenceTo>):Boolean {
    val source = context[0]
    val dest   = context[1]

    val res    = applyLogical(source.getVal(), dest.getVal(), ::And)
    source.setVal(res.value)
    flags.fromContext(res)
    return true
}

/* Call convention: (flags, [source, dest])
 */
fun fOr(flags: FlagsRegister, context: List<ReferenceTo>):Boolean {
    val source = context[0]
    val dest   = context[1]

    val res    = applyLogical(source.getVal(), dest.getVal(), ::Or)
    source.setVal(res.value)
    flags.fromContext(res)
    return true
}

/* Call convention: (flags, [source, dest])
 */
fun fXor(flags: FlagsRegister, context: List<ReferenceTo>):Boolean {
    val source = context[0]
    val dest   = context[1]

    val res    = applyLogical(source.getVal(), dest.getVal(), ::Xor)
    source.setVal(res.value)
    flags.fromContext(res)
    return true
}

/* Call convention: (flags, [source, dest])
 */
fun fNot(flags: FlagsRegister, context: List<ReferenceTo>):Boolean {
    val source = context[0]

    val res    = applyLogical(source.getVal(), Not)
    source.setVal(res.value)
    return true
}

fun fChangeCarry(flags: FlagsRegister, context: List<ReferenceTo>): Boolean {
    flags.cy = context[0].getVal().value > 0
    return true
}

fun fCompare(flags: FlagsRegister, context: List<ReferenceTo>): Boolean {
    val res = adder(context[0].getVal(), context[1].getVal().twosComplement(), false)
    res.S = !res.S
    flags.fromContext(res)
    return true
}