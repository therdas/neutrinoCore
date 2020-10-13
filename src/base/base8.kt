package base

class Base8 (x: Int): BaseN(x, 8){
    constructor(a: BaseN): this(a.value) {
        assert(a.bits == 8) {"Cannot cast non byte BaseN to Base8"}
    }
}