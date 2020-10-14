package functions.groups.arithmetic
import base.Base16
import base.Base8
import base.context.ArithmeticContext
import base.context.ReferenceTo
import base.context.ShiftContext
import functions.checkCarry
import functions.encodeFlag
import functors.adder
import hardware.FlagReference
import hardware.FlagsRegister

//context = (accumulator, register/memory)
fun f_add8(flags: FlagsRegister, context: List<ReferenceTo>): Boolean {
    if(context.size != 3)
        return false

    val augend = context[1]
    val addend = context[2]
    val result = adder(addend.getVal(), augend.getVal(), false)
    augend.setVal(result.value)
    flags.fromContext(result)

    return true
}

//context = (accumulator, register/memory)
fun f_addWC8(flags: FlagsRegister, context: List<ReferenceTo>): Boolean {
    if(context.size != 3)
        return false

    val augend = context[1]
    val addend = context[2]
    val result = adder(addend.getVal(), augend.getVal(), !flags.cy)
    augend.setVal(result.value)
    flags.fromContext(result)

    return true
}

//context = (accumulator, register/memory)
fun f_sub8(flags: FlagsRegister, context: List<ReferenceTo>): Boolean {
    if(context.size != 3)
        return false

    val minuend = context[1]
    val subtrahend = context[2]
    val result = adder(minuend.getVal(), -(subtrahend.getVal()), true)
    minuend.setVal(result.value)
    result.S = !result.S
    flags.fromContext(result)

    return true
}

//context = (accumulator, register/memory)
fun f_subWB8(flags: FlagsRegister, context: List<ReferenceTo>): Boolean {
    if(context.size != 3)
        return false

    val minuend = context[1]
    val subtrahend = context[2]
    val result = adder(minuend.getVal(), -(subtrahend.getVal()), !flags.cy)
    minuend.setVal(result.value)
    result.S = !result.S
    flags.fromContext(result)

    return true
}

//context = (accumulator, register/memory)
fun f_idr(flags: FlagsRegister, context: List<ReferenceTo>): Boolean {
    if(context.size != 2)
        return false;

    val operand = context[0]
    var factor  = context[1]
    val result = adder(operand.getVal(), factor.getVal(), false)

    result.CY = flags.cy

    operand.setVal(result.value)
    flags.fromContext(result)
    return true
}

//context = (accumulator, register/memory)
fun f_idx(flags: FlagsRegister, context: List<ReferenceTo>): Boolean {
    if(context.size != 2)
        return false;

    val operand = context[0]
    var factor  = context[1]
    val result = adder(operand.getVal(), factor.getVal(), false)

    result.CY = flags.cy

    operand.setVal(result.value)
    flags.fromContext(result)
    return true
}


