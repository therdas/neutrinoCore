package functions.groups.branch

import base.Base16
import base.Base16Reference
import base.context.ReferenceTo
import hardware.DoubleMemoryReference
import hardware.FlagsRegister
import hardware.RegisterReference
import jdk.nashorn.internal.ir.Flags
import sun.plugin2.os.windows.FLASHWINFO

/*
** Call convention, (flags, [pc, sp, saveLoc, addr, cond]);
*  Strictly speaking flags is not required. However, this helps maintain
*  a uniform call convention for all functions, which Do require flags
 */

fun fCondJump(flags: FlagsRegister, context: List<ReferenceTo>): Boolean {
    if(context.size != 3)
        return false;
    val pc = context[0]
    val addr = context[3]
    val cond = context[4].getVal().value > 0
    if(cond)
        pc.setVal(addr.getVal())

    return true
}


/*
** Call convention, (flags, [pc, sp, saveLoc, addr, cond]);
*  SaveLoc is a double reference to location pointed to by stack pointer
 */
fun fCondCall(pc: RegisterReference,
              sp: RegisterReference,
              saveLoc: DoubleMemoryReference,
              addr: Base16,
              cond: Boolean): Boolean {
    val c = cond

    if(c) {
        sp.setVal(sp.getVal() - 2)
        saveLoc.setVal(pc.getVal())
        pc.setVal(addr)
    }

    return true
}

fun fCondRet(flags: FlagsRegister, context: List<ReferenceTo>): Boolean {
    if(context.size != 3)
        return false;
    val pc = context[0]
    val sp = context[1]
    val saveLoc = context[2]
    val cond = context[4].getVal().value > 0

    if(cond) {
        sp.setVal(sp.getVal() + 2)
        pc.setVal(saveLoc.getVal())
    }

    return true
}