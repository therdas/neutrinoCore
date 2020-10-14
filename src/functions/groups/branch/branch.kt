package functions.groups.branch

import base.Base16
import base.context.ReferenceTo
import hardware.FlagsRegister
import jdk.nashorn.internal.ir.Flags
import sun.plugin2.os.windows.FLASHWINFO

/*
** Call convention, (flags, [pc, sp, addr, cond]);
*  Strictly speaking flags is not required. However, this helps maintain
*  a uniform call convention for all functions, which Do require flags
 */

fun fCondJump(flags: FlagsRegister, context: List<ReferenceTo>): Boolean {
    if(context.size != 3)
        return false;
    val pc = context[0]
    val addr = context[2]
    val cond = context[3].getVal().value > 0
    if(cond)
        pc.setVal(addr.getVal())

    return true
}


/*
** Call convention, (flags, [pc, sp, addr, cond]);
*  Strictly speaking flags is not required. However, this helps maintain
*  a uniform call convention for all functions, which Do require flags
 */
fun fCondCall(flags: FlagsRegister, context: List<ReferenceTo>): Boolean {
    if(context.size != 3)
        return false;
    val pc = context[0]
    val sp = context[1]
    val addr = Base16(context[2].getVal())
    val cond = context[3].getVal().value > 0
    if(cond)
        pc.setVal(addr)

    return true
}