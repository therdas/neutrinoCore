package functors
import base.BaseN
import base.context.ShiftContext
import kotlin.math.pow

fun shiftLeft(a: BaseN, shiftIn: Boolean = false, circular: Boolean = false): ShiftContext {
    var value = (a.value shl 1) and ((2.0).pow(a.bits).toInt() - 1)

    value += if(circular) {
        when(a[a.bits - 1]) {
            true -> 1
            false -> 0
        }
    } else {
        when(shiftIn) {
            true -> 1
            false -> 0
        }
    }

    return ShiftContext(BaseN(value, a.bits), a[a.bits - 1])
}

fun shiftRight(a: BaseN, shiftIn: Boolean = false, circular: Boolean = false): ShiftContext {
    var value = a.value shr 1       //No need to mask, right shifted can NEVER be bigger.

    value += if(circular) {
        when(a[0]) {
            true -> 1
            false -> 0
        }
    } else {
        when(shiftIn) {
            true -> 1
            false -> 0
        }
    } shl (a.bits - 1)

    return ShiftContext(BaseN(value, a.bits), a[a.bits - 1])
}
