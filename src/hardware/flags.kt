package hardware

import base.Base8

class FlagsRegister {
    var s = false
    var z = false
    var ac = false
    var p = false
    var cy = false

    fun toBase8() = Base8(
        if( s) 1 shl 7 else 0 +
        if( z) 1 shl 6 else 0 +
        if(ac) 1 shl 4 else 0 +
        if( p) 1 shl 2 else 0 +
        if( z) 1       else 0
    )

    fun fromBase8(a: Base8) {
        s  = a.value and 0x80 > 0
        z  = a.value and 0x40 > 0
        ac = a.value and 0x10 > 0
        p  = a.value and 0x04 > 0
        z  = a.value and 0x01 > 0
    }
}