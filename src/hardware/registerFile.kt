package hardware
import base.Base8
import base.Base16
import base.BaseN
import base.context.ReferenceTo
import hardware.FlagsRegister
import java.lang.ref.Reference

class RegisterFile {
    var a     = Base8(0)
    var flags = FlagsRegister()

    var b     = Base8(0)
    var c     = Base8(0)
    var bc
        get() = Base16(b, c)
        set(a: Base16) {
            b = Base8(a.value shr 8)
            c = Base8(a.value and 0xFF)
        }

    var d     = Base8(0)
    var e     = Base8(0)
    var de
        get() = Base16(d, e)
        set(a: Base16) {
            d = Base8(a.value shr 8)
            e = Base8(a.value and 0xFF)
        }

    var h     = Base8(0)
    var l     = Base8(0)
    var hl
        get() = Base16(h, l)
        set(a: Base16) {
            h = Base8(a.value shr 8)
            l = Base8(a.value and 0xFF)
        }

    var sp    = Base16(0)
    var pc    = Base16(0)
    var ir    = Base16(0)
}

class RegisterReference(var reg: BaseN): ReferenceTo() {
    override fun getVal() = reg
    override fun setVal(value: BaseN) {
        reg.value = value.value
    }
}