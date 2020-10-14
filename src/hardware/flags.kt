package hardware

import base.Base8
import base.BaseN
import base.context.ArithmeticContext
import base.context.ReferenceTo

class FlagsRegister {
    var s = false
    var z = false
    var ac = false
    var p = false
    var cy = false

    var value
        get() = toBase8().value
        set(i: Int) {
            println("/::::::: $i")
            fromBase8(Base8(i))
            println("::::::::" + Base8(i).value)
        }

    fun toBase8() = Base8(
        (if( s) 1 shl 7 else 0) +
                (if( z) 1 shl 6 else 0) +
                (if(ac) 1 shl 4 else 0) +
                (if( p) 1 shl 2 else 0) +
                (if( z) 1       else 0)
    )

    fun fromBase8(a: Base8) {
        s  = (a.value and 0x80) > 0
        z  = (a.value and 0x40) > 0
        println("><::::: $z")
        ac = (a.value and 0x10) > 0
        p  = (a.value and 0x04) > 0
        cy  = (a.value and 0x01) > 0
    }

    fun fromContext(a: ArithmeticContext) {
        s  = a.S
        z  = a.Z
        ac = a.AC
        p  = a.P
        cy = a.CY
    }
}

class FlagReference(var flag: FlagsRegister) : ReferenceTo {
    override fun getVal() = flag.toBase8()
    override fun setVal(value: BaseN): Unit {
        flag.fromBase8(Base8(value))
    }
}