package base.context
import base.Base16
import base.Base8
import base.BaseN

class ArithmeticContext(val value: BaseN,
                        var S: Boolean,
                        var Z: Boolean,
                        var AC: Boolean,
                        var P: Boolean,
                        var CY:Boolean)

class ShiftContext(val value: BaseN,
                   val serialOut: Boolean) {

}
/*
** A reference to a given object, wether atomic or part of a
** nother representation. getVal and setVal MUST modify it in
** whereever it was declared.
 */
interface ReferenceTo {
    fun getVal(): BaseN
    fun setVal(value: BaseN): Unit
}

//Used for CALL JMP and RET funcs as a easy method of invoking condition
//Also provides for a way to convert a Boolean to BaseN:
// BooleanReference(true).getVal() -> 0x01
class BooleanReference(private var cond: Boolean): ReferenceTo {
    override fun getVal() = Base8(if (cond) 0 else 1)
    override fun setVal(value: BaseN) {
        cond = when {
            value.value > 0 -> true
            else            -> false
        }
    }
}