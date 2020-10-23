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
import sun.java2d.pipe.RegionIterator
import sun.java2d.pipe.RegionSpanIterator
import sun.net.RegisteredDomain
import java.lang.management.MemoryType

fun dummyFunction(instr: Base8, a: BaseN) = true
infix fun Int.between (range: Pair<Int, Int>) = (this >= range.first) && (this <= range.second)

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
fun iDAA(instr: Base8, arg: BaseN): Boolean = fDecimaladjust(flags, RegisterReference(reg.a))

fun iDAD(instr: Base8, arg: BaseN): Boolean {
    val addend = when(instr.value) {
        0x09 -> RegisterPairReference(reg.b, reg.c)
        0x19 -> RegisterPairReference(reg.d, reg.e)
        0x29 -> RegisterPairReference(reg.h, reg.l)
        0x39 -> RegisterReference(reg.sp)
        else -> return false
    }
    return fAdd16(flags, RegisterPairReference(reg.h, reg.l), addend)
}

fun iDCR(instr: Base8, arg: BaseN): Boolean {
    val target = when(instr.value) {
        0x3D -> RegisterReference(reg.a)
        0x05 -> RegisterReference(reg.b)
        0x0D -> RegisterReference(reg.c)
        0x15 -> RegisterReference(reg.d)
        0x1D -> RegisterReference(reg.e)
        0x25 -> RegisterReference(reg.h)
        0x2D -> RegisterReference(reg.l)
        0x35 -> MemoryReference(memory, reg.hl)
        else -> return false
    }
    return fIdr(flags, target, Base8(0x01).twosComplement())
}

fun iDCX(instr: Base8, arg: BaseN): Boolean {
    val target = when(instr.value) {
        0x0B -> RegisterPairReference(reg.b, reg.c)
        0x1B -> RegisterPairReference(reg.d, reg.e)
        0x2B -> RegisterPairReference(reg.h, reg.l)
        0x3B -> RegisterReference(reg.sp)
        else -> return false
    }
    return fIdx(flags, target, Base16(0x0001).twosComplement())
}

fun iINR(instr: Base8, arg: BaseN): Boolean {
    val target = when(instr.value) {
        0x3C -> RegisterReference(reg.a)
        0x04 -> RegisterReference(reg.b)
        0x0C -> RegisterReference(reg.c)
        0x14 -> RegisterReference(reg.d)
        0x1C -> RegisterReference(reg.e)
        0x24 -> RegisterReference(reg.h)
        0x2C -> RegisterReference(reg.l)
        0x34 -> MemoryReference(memory, reg.hl)
        else -> return false
    }
    return fIdr(flags, target, Base8(0x01))
}

fun iINX(instr: Base8, arg: BaseN): Boolean {
    val target = when(instr.value) {
        0x03 -> RegisterPairReference(reg.b, reg.c)
        0x13 -> RegisterPairReference(reg.d, reg.e)
        0x23 -> RegisterPairReference(reg.h, reg.l)
        0x33 -> RegisterReference(reg.sp)
        else -> return false
    }
    return fIdx(flags, target, Base16(0x0001))
}

fun iJMP(instr: Base8, arg: BaseN): Boolean {
    val cond = when(instr.value) {
        0xC2 -> !flags.z
        0xC3 -> true
        0xCA -> flags.z
        0xD2 -> !flags.cy
        0xDA -> flags.cy
        0xE2 -> !flags.p
        0xEA -> flags.p
        0xF2 -> !flags.s
        0xFA -> flags.s
        else -> return false
    }

    return fCondJump(flags, RegisterReference(reg.pc), Base16(arg), cond)
}

fun iLDA(instr: Base8, arg: BaseN): Boolean = fMov(RegisterReference(reg.a), MemoryReference(memory, Base16(arg)))
fun iLDAX(instr: Base8, arg: BaseN): Boolean {
    val source = when(instr.value) {
        0x0A -> RegisterPairReference(reg.b, reg.c)
        0x1A -> RegisterPairReference(reg.d, reg.e)
        else -> return false
    }

    return fMov(RegisterReference(reg.a), MemoryReference(memory, Base16(source.getVal())))
}
fun iLHLD(instr: Base8, arg: BaseN): Boolean = fMov(RegisterPairReference(reg.h, reg.l), Base16Reference(Base16(arg)))
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
fun iMOV(instr: Base8, arg: BaseN): Boolean {
    val lower = instr.value and 0x0F
    val source = when(instr.value and 0x0F) {
        0xF, 0x7 -> RegisterReference(reg.a)
        0x8, 0x0 -> RegisterReference(reg.b)
        0x9, 0x1 -> RegisterReference(reg.c)
        0xA, 0x2 -> RegisterReference(reg.d)
        0xB, 0x3 -> RegisterReference(reg.e)
        0xC, 0x4 -> RegisterReference(reg.h)
        0xD, 0x5 -> RegisterReference(reg.l)
        0xF, 0x6 -> MemoryReference(memory, reg.hl)
        else     -> return false
    }

    val dest = when(instr.value and 0xF0) {
        0x70 -> if(lower between Pair(0x0, 0x7)) MemoryReference(memory, reg.hl) else RegisterReference(reg.a)
        0x60 -> if(lower between Pair(0x0, 0x7)) RegisterReference(reg.h)        else RegisterReference(reg.l)
        0x50 -> if(lower between Pair(0x0, 0x7)) RegisterReference(reg.d)        else RegisterReference(reg.e)
        0x40 -> if(lower between Pair(0x0, 0x7)) RegisterReference(reg.b)        else RegisterReference(reg.c)
        else -> return false
    }

    return fMov(dest, source)
}

fun iMVI(instr: Base8, arg: BaseN): Boolean {
    val dest  = when(instr.value) {
        0x3E -> RegisterReference(reg.a)
        0x06 -> RegisterReference(reg.b)
        0x0E -> RegisterReference(reg.c)
        0x16 -> RegisterReference(reg.d)
        0x1E -> RegisterReference(reg.e)
        0x26 -> RegisterReference(reg.h)
        0x2E -> RegisterReference(reg.l)
        0x36 -> MemoryReference(memory, reg.hl)
        else -> return false
    }

    return fMov(dest, Base8Reference(Base8(arg)))
}

fun iORA(instr: Base8, arg: BaseN): Boolean {
    val operand = when(instr.value) {
        0xB7 -> RegisterReference(reg.a)
        0xB0 -> RegisterReference(reg.b)
        0xB1 -> RegisterReference(reg.c)
        0xB2 -> RegisterReference(reg.d)
        0xB3 -> RegisterReference(reg.e)
        0xB4 -> RegisterReference(reg.h)
        0xB5 -> RegisterReference(reg.l)
        0xB6 -> MemoryReference(memory, reg.hl)
        else -> return false
    }

    return fOr(flags, RegisterReference(reg.a), operand)
}

fun iORI(instr: Base8, arg: BaseN): Boolean = fOr(flags, RegisterReference(reg.a), Base8Reference(Base8(arg)))
