package base

import base.context.ReferenceTo

class Base16 (x: Int): BaseN(x, 16){
    constructor(a: BaseN): this(a.value) {
        assert(a.bits == 16) {"Cannot cast non double BaseN to Base8"}
    }

    constructor(a: Base8, b: Base8): this((a.value shl 8) + b.value)

    var upper
        get() = Base8((value and 0xFF00) shr 8)
        set(x: Base8) {
            value = value and 0x00FF
            value += (x.value shl 8)
        }

    var lower
        get() = Base8(value and 0x00FF)
        set(x: Base8) {
            value = value and 0xFF00
            value += x.value
        }
}

class Base16Reference(private var _value: Base16): ReferenceTo {
    override fun setVal(value: BaseN) {
        _value =  Base16(value)
    }

    override fun getVal(): BaseN = _value
}