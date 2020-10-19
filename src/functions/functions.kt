package functions

import base.*
import base.context.BooleanReference
import functions.groups.arithmetic.*
import functions.groups.branch.*
import functions.groups.dataMovement.*
import functions.groups.logical.*
import functors.adder
import functors.applyLogical
import hardware.DoubleMemoryReference
import hardware.MemoryReference
import hardware.RegisterPairReference
import hardware.RegisterReference
import sun.java2d.pipe.RegionSpanIterator
import java.lang.management.MemoryType

fun dummy(instr: Base8, a: BaseN) = true

//74 Functions for 74 Instructions
fun iACI(instr: Base8, arg: BaseN): Boolean = fAddWC8(reg.flags, RegisterReference(reg.a), Base8Reference(Base8(arg)))

fun iADC(instr: Base8, arg: BaseN): Boolean {
    val addend = when(instr.value) {
        0x8F -> RegisterReference(reg.a)
        0x88 -> RegisterReference(reg.b)
        0x89 -> RegisterReference(reg.c)
        0x8A -> RegisterReference(reg.d)
        0x8B -> RegisterReference(reg.e)
        0x8C -> RegisterReference(reg.h)
        0x8D -> RegisterReference(reg.l)
        0x8E -> MemoryReference(memory, reg.hl)
        else -> return false
    }

    return fAddWC8(flags, RegisterReference(reg.a), addend)
}

fun iADD(instr: Base8, arg: BaseN): Boolean {
    val addend = when(instr.value) {
        0x87 -> RegisterReference(reg.a)
        0x80 -> RegisterReference(reg.b)
        0x81 -> RegisterReference(reg.c)
        0x82 -> RegisterReference(reg.d)
        0x83 -> RegisterReference(reg.e)
        0x84 -> RegisterReference(reg.h)
        0x85 -> RegisterReference(reg.l)
        0x86 -> MemoryReference(memory, reg.hl)
        else -> return false
    }

    return fAdd8(flags, RegisterReference(reg.a), addend)
}

fun iADI(instr: Base8, arg: BaseN): Boolean = fAdd8(reg.flags, RegisterReference(reg.a), Base8Reference(Base8(arg)))

fun iANA(instr: Base8, arg: BaseN): Boolean {
    val source = when(instr.value) {
        0xA7 -> RegisterReference(reg.a)
        0xA0 -> RegisterReference(reg.b)
        0xA1 -> RegisterReference(reg.c)
        0xA2 -> RegisterReference(reg.d)
        0xA3 -> RegisterReference(reg.e)
        0xA4 -> RegisterReference(reg.h)
        0xA5 -> RegisterReference(reg.l)
        0xA6 -> MemoryReference(memory, reg.hl)
        else -> return false
    }

    return fAnd(flags, RegisterReference(reg.a), source)
}

fun iANI(instr: Base8, arg: BaseN): Boolean = fAnd(flags, RegisterReference(reg.a), Base8Reference(Base8(arg)))

fun iCALLX(instr: Base8, arg: BaseN): Boolean {
    val cond = when(instr.value) {
        0xCD -> true
        0xDC -> flags.cy
        0xD4 -> !flags.cy
        0xFC -> flags.s
        0xF4 -> !flags.s
        0xCC -> flags.z
        0xC4 -> !flags.z
        0xEC -> flags.p
        0xE4 -> !flags.p
        else -> return false
    }

    return fCondCall(
                        RegisterReference(reg.pc), RegisterReference(reg.sp),
                        DoubleMemoryReference(memory, Base16(reg.sp - 2)), Base16(arg),
                        cond
                    )
}

fun iCMA(instr: Base8, arg: BaseN): Boolean = fNot(RegisterReference(reg.a))
fun iCMC(instr: Base8, arg: BaseN): Boolean = fChangeCarry(flags, !flags.cy)

fun iCMP(instr: Base8, arg: BaseN): Boolean {
    val source = when(instr.value) {
        0xBF -> RegisterReference(reg.a)
        0xB8 -> RegisterReference(reg.b)
        0xB9 -> RegisterReference(reg.c)
        0xBA -> RegisterReference(reg.d)
        0xBB -> RegisterReference(reg.e)
        0xBC -> RegisterReference(reg.h)
        0xBD -> RegisterReference(reg.l)
        0xBE -> MemoryReference(memory, reg.hl)
        else -> return false
    }

    return fCompare(flags, RegisterReference(reg.a), source)
}
fun iCMI(instr: Base8, arg: BaseN): Boolean = fCompare(flags, RegisterReference(reg.a), Base8Reference(Base8(arg)))

fun iLXI(instr: Base8, arg: BaseN): Boolean {
    val value = Base16(arg)
    val destination = when(instr.value) {
        0x01 -> RegisterPairReference(reg.b, reg.c)
        0x11 -> RegisterPairReference(reg.d, reg.e)
        0x21 -> RegisterPairReference(reg.h, reg.l)
        0x31 -> RegisterReference(reg.sp)
        else -> return false
    }
    return fMov(destination, Base16Reference(value))
}

