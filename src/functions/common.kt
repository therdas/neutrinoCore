package functions

import base.Base8
import base.BaseN
import base.context.ArithmeticContext

fun encodeFlag(a: ArithmeticContext): Base8 = Base8(
    (if( a.S) 1 shl 7 else 0) +
    (if( a.Z) 1 shl 6 else 0) +
    (if(a.AC) 1 shl 4 else 0) +
    (if( a.P) 1 shl 2 else 0) +
    (if( a.Z) 1       else 0)
)

fun checkCarry   (a: BaseN): Boolean = (a.value and 0x01) > 0
fun checkParity  (a: BaseN): Boolean = (a.value and 0x04) > 0
fun checkSign    (a: BaseN): Boolean = (a.value and 0x80) > 0
fun checkZero    (a: BaseN): Boolean = (a.value and 0x40) > 0
fun checkAuxCarry(a: BaseN): Boolean = (a.value and 0x10) > 0

fun toggleCarry   (a: Base8): Base8 = Base8(a.value xor 0x01)
fun toggleParity  (a: Base8): Base8 = Base8(a.value xor 0x04)
fun toggleSign    (a: Base8): Base8 = Base8(a.value xor 0x80)
fun toggleZero    (a: Base8): Base8 = Base8(a.value xor 0x40)
fun toggleAuxCarry(a: Base8): Base8 = Base8(a.value xor 0x10)

fun setCarry   (a: Base8): Base8 = Base8(a.value or 0x01)
fun setParity  (a: Base8): Base8 = Base8(a.value or 0x04)
fun setSign    (a: Base8): Base8 = Base8(a.value or 0x80)
fun setZero    (a: Base8): Base8 = Base8(a.value or 0x40)
fun setAuxCarry(a: Base8): Base8 = Base8(a.value or 0x10)
