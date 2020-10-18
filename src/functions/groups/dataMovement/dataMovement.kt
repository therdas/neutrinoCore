package functions.groups.dataMovement

import base.context.ReferenceTo
import hardware.FlagsRegister
import hardware.FlagReference

/*  Call with (flags, [source, dest])

 */
fun fMov(dest: ReferenceTo, source: ReferenceTo):Boolean {
    dest.setVal(source.getVal())
    println("DEST: " + dest.getVal().value + "TO VALUE" + source.getVal().value)
    return true
}