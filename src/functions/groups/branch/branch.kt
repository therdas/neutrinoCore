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

fun fCondJump(flags: FlagsRegister,
              pc: RegisterReference,
              addr: Base16,
              cond: Boolean): Boolean {

    if(cond)
        pc.setVal(addr)
    println("PC IS " + pc.getVal().value + " SHOULD BE " + addr.value)

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

fun fCondRet(pc: RegisterReference,
             sp: RegisterReference,
             saveLoc: DoubleMemoryReference,
             addr: Base16,
             cond: Boolean): Boolean {
    if(cond) {
        sp.setVal(sp.getVal() + 2)
        pc.setVal(saveLoc.getVal())
    }

    return true
}