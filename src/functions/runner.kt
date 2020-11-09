package functions

import base.Base16
import base.Base8
import base.BaseN

fun consumeFromMemory(init: BaseN, until: Array<Int>): Boolean {
    reg.pc = Base16(init)
    while(true) {
        println("AT " + reg.pc.value.toString(16))
        val instr = memory.get(reg.pc)
        reg.pc = Base16(reg.pc + 1)

        //Check for exit condition (Either until or RST1
        if(until.contains(instr.value))
            return true


        val size = SizeOf[instr.value]
        var arg: BaseN = BaseN(0,0)
        if(size == 2) {
            arg = memory.get(reg.pc)
            reg.pc = Base16(reg.pc + 1)

        } else if(size == 3) {
            val lower = memory.get(reg.pc)
            reg.pc = Base16(reg.pc + 1)

            val upper = memory.get(reg.pc)
            reg.pc = Base16(reg.pc + 1)

            arg = Base16(upper, lower)
        } else {
            Base8(0)
        }

        println("" + instr.value.toString(16) + " " + arg.value.toString(16))

        val ret = DispatchFunction(instr, arg)

        if(!ret)
            return false
    }
}