package base

import base.context.ReferenceTo
import kotlin.math.pow

open class BaseN(x: Int, _bits: Int) {
    val bits: Int = _bits
    private val upperLimit: Int = (2.0).pow(bits.toDouble()).toInt() - 1
    private val lowerLimit: Int = 0
    
    var value: Int = 0
        set(v) {
            field = when {
                v < lowerLimit -> upperLimit + v + 1
                v > upperLimit -> v - upperLimit - 1
                else -> v
            }
        }

    init {
        value = x
    }

    operator fun plus(increment: Int): BaseN {
        return BaseN(value + increment, bits)
    }

    operator fun minus(decrement: Int): BaseN {
        return BaseN(value + decrement, bits)
    }

    operator fun inc(): BaseN {
        return BaseN(value + 1, bits)
    }

    operator fun dec(): BaseN {
        return BaseN(value - 1, bits)
    }

    operator fun get(index: Int): Boolean {
        return ((value shr index) and 0b1) != 0
    }

    operator fun unaryMinus(): BaseN {
        var res: Int = value
        var temp = 0
        for(i: Int in 0 until bits) {
            temp = temp shl 1
            val currentBit = (res and (0b1 shl bits - 1)) shr bits - 1
            temp += when(currentBit) {
                1 -> 0
                0 -> 1
                else -> 0
            }
            res = res shl 1
        }
        return BaseN(temp, bits)
    }

    fun twosComplement(): BaseN {
        var temp = -BaseN(value, bits)
        return ++temp
    }

    operator fun set(index: Int, setUnset: Boolean): BaseN {
        value = when(setUnset) {
            true  -> value  or (0b1 shl index)
            false -> value and (-BaseN(0b1 shl index, bits)).value
        }
        return this
    }

    fun computeParity(): Boolean {
        var temp:Int = value
        var parity = true      //Assume true parity, as P=1 for AC=0

        for(i: Int in 0 until bits) {
            val currentBit = temp and 0b1           //Get last bit
            parity = when {
                currentBit == 1 -> parity.not()
                else            -> parity
            }
            temp = temp shr 1                       //Go To Next Bit
        }

        return parity
    }
}

class BaseNReference(private var _value: BaseN): ReferenceTo {
    override fun setVal(value: BaseN) {
        _value =  value
    }

    override fun getVal(): BaseN = _value
}