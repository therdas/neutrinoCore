package base.context
import base.Base16
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
