package functors

import base.BaseN
import base.context.ArithmeticContext

//Pair<Int, Int> represents <result, AC>

fun applyLogical(a: BaseN, b: BaseN, fn: (Int, Int) -> Pair<Int, Int>): ArithmeticContext {
    check(a.bits == b.bits) {"Logical Error, Incompatible number of bits in both arguments"}

    val rfn = fn(a.value, b.value)
    val result = BaseN(rfn.first, a.bits)
    val auxCarry = rfn.second

    return ArithmeticContext(result,
                             result[result.bits - 1],
                             result.value == 0,
                             auxCarry == 0,
                             result.computeParity(),
                             false
                            )
}

fun applyLogical(a: BaseN, not: Int): ArithmeticContext {
    check(not == 0) {"Logical: Unsupported Operation"}
    val res = a.twosComplement()
    return ArithmeticContext(res,
                             res[res.bits - 1],
                             res.value == 0,
                             false,
                             res.computeParity(),
                             false
                            )
}

fun And(a: Int, b: Int) : Pair<Int, Int> = Pair(a and b, 0)
fun Or(a: Int, b: Int) : Pair<Int, Int> = Pair(a or b, 0)
fun Xor(a: Int, b: Int) : Pair<Int, Int> = Pair(a xor b, 0)
val Not = 0

