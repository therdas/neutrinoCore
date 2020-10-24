package base

import base.context.ReferenceTo

class Base8 (x: Int): BaseN(x, 8){
    constructor(a: BaseN): this(a.value) {
        assert(a.bits == 8) {"Cannot cast non byte BaseN to Base8"}
    }
}

class Base8Reference(private var _value: Base8): ReferenceTo {
    override fun setVal(value: BaseN) {
        _value =  Base8(value)
    }

    override fun getVal(): BaseN = _value
}