package base
import base.BaseN

class Base16 (x: Int): BaseN(x, 16){
    constructor(a: BaseN): this(a.value) {
        assert(a.bits == 16) {"Cannot cast non double BaseN to Base8"}
    }

    constructor(a: Base8, b: Base8): this((a.value shl 8) + b.value)
}