package functions

import base.Base16
import base.Base8
import base.BaseN

fun consumeFromMemory(init: BaseN, until: Int): Boolean {
    var addr = init
    while(true) {
        val instr = memory.get(Base16(addr))

        //Check for exit condition (Either until or RST1
        if(instr.value == 0xCF || instr.value == until)
            return true

        val size = SizeOf[instr.value]
        val arg: BaseN = if(size == 2) {
            memory.get(Base16(++addr))
        } else if(size == 3) {
            val lower = memory.get(Base16(++addr))
            val upper = memory.get(Base16(++addr))
            Base16(upper, lower)
        } else {
            Base8(0)
        }

        val ret = DispatchFunction(instr, arg)

        if(!ret)
            return false
    }
}