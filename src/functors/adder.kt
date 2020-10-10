package functors
import base.BaseN
import base.ArithmeticContext

fun adder(a: BaseN, b: BaseN, carryIn: Boolean): ArithmeticContext{
    var valA: Int = a.value
    var valB: Int = b.value

    check(a.bits == b.bits) {"Adder Error, Incompatible number of bits in both arguments"}

    //Convert carryIn to Integer, later to BaseN
    var cIn: Int = when(carryIn) {
        true  -> 1
        false -> 0
    }

    //Calculate AC
    /* Logic is as follows:
    ** If the lower nibbles added together are greater than
    ** whatever the maximum value can be stored in a nibble
    ** then there will have to be a carry
     */
    val auxCarry = ((a.value and 0xF) + (b.value and 0xF) + cIn) > 0xF

    //Calculate CY
    /* Name is a bit deceptive, here carry is carry-out-of-
    ** 8-bits, logic is same as above
     */
    val carry = when((a.value and 0xFF + b.value and 0xFF + cIn) and 0x100) {
        0    -> false
        else -> true
    }

    //Calculate Result
    val result = a + b + BaseN(cIn, a.bits)
    return ArithmeticContext(result,
                             result[result.bits - 1],
                          result.value == 0,
                             auxCarry,
                             result.computeParity(),
                             carry)
}