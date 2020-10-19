package functions.groups.arithmetic

import base.Base8
import base.context.ArithmeticContext
import base.context.ReferenceTo
import functors.adder
import hardware.FlagsRegister
import hardware.RegisterReference

//context = (accumulator, register/memory)
fun fAdd8(flags: FlagsRegister, augend: ReferenceTo, addend: ReferenceTo): Boolean {
    val result = adder(addend.getVal(), augend.getVal(), false)
    augend.setVal(result.value)
    flags.fromContext(result)

    return true
}

//context = (accumulator, register/memory)
fun fAddWC8(flags: FlagsRegister, augend: ReferenceTo, addend: ReferenceTo): Boolean {
    val result = adder(addend.getVal(), augend.getVal(), flags.cy)
    augend.setVal(result.value)
    flags.fromContext(result)

    return true
}

//context = (accumulator, register/memory)
fun fSub8(flags: FlagsRegister, minuend: ReferenceTo, subtrahend: ReferenceTo): Boolean {
    val result = adder(minuend.getVal(), -(subtrahend.getVal()), true)
    minuend.setVal(result.value)
    result.S = !result.S
    flags.fromContext(result)

    return true
}

//context = (accumulator, register/memory)
fun fSubWB8(flags: FlagsRegister,  minuend: ReferenceTo, subtrahend: ReferenceTo): Boolean {
    val result = adder(minuend.getVal(), -(subtrahend.getVal()), !flags.cy)
    minuend.setVal(result.value)
    result.S = !result.S
    flags.fromContext(result)

    return true
}

//context = (accumulator, register/memory)
fun fIdr(flags: FlagsRegister, context: List<ReferenceTo>): Boolean {
    if(context.size != 2)
        return false

    val operand = context[0]
    var factor  = context[1]
    val result = adder(operand.getVal(), factor.getVal(), false)

    result.CY = flags.cy

    operand.setVal(result.value)
    flags.fromContext(result)
    return true
}

//context = (accumulator, register/memory)
fun fIdx(flags: FlagsRegister, context: List<ReferenceTo>): Boolean {
    if(context.size != 2)
        return false

    val operand = context[0]
    val factor  = context[1]
    val result = adder(operand.getVal(), factor.getVal(), false)

    result.CY = flags.cy

    operand.setVal(result.value)
    flags.fromContext(result)
    return true
}

fun fAdd16(flags: FlagsRegister, context: List<ReferenceTo>): Boolean {
    if(context.size != 2)
        return false

    val augend = context[0]
    val addend = context[1]
    val result = adder(augend.getVal(), addend.getVal(), false)

    augend.setVal(result.value)
    flags.cy = result.CY

    return true
}

fun fDecimaladjust(flags: FlagsRegister, target: RegisterReference): Boolean {

    /* Intel Documentation:
    IF 64-Bit Mode
	    THEN
	        #UD;
	    ELSE
	        old_AL ← AL;
	        old_CF ← CF;
	        CF ← 0;
	        IF (((AL AND 0FH) > 9) or AF = 1)
	                THEN
	                    AL ← AL + 6;
	                    CF ← old_CF or (Carry from AL ← AL + 6);
	                    AF ← 1;
	                ELSE
	                    AF ← 0;
	        FI;
	        IF ((old_AL > 99H) or (old_CF = 1))
	            THEN
	                    AL ← AL + 60H;
	                    CF ← 1;
	            ELSE
	                    CF ← 0;
	        FI;
	FI;
     */
    val oldData = target.getVal().value
    val oldAC = flags.ac
    val oldCY = flags.cy

    flags.cy = false
    var res = ArithmeticContext(Base8(0x00), false, false, false, false, false)
    var modified: Boolean = false

    if((oldData and 0x0F) > 0x09 || oldAC) {
        res = adder(target.getVal(), Base8(0x06), false)
        target.setVal(res.value)
        flags.cy = if(oldCY) true else res.CY
        flags.ac = true
        modified = true
    } else {
        flags.ac = false
    }

    if((oldData > 0x99) || oldCY) {
        res = adder(target.getVal(), Base8(0x60), false)
        target.setVal(res.value)
        flags.cy = true
        modified = true
    } else {
        flags.cy = false
    }

    if(modified) {
        flags.z = res.Z
        flags.p = res.P
        flags.s = res.S
    }

    return true
}