package base

class Base8(x: Int) {
    var value: Int = 0
        set(v) {
            field = when {
                v < 0x00 -> 0xFF + v + 1
                v > 0xFF -> v - 0xFF - 1
                else -> v
            }
        }

    init {
        value = x
    }

    operator fun plus(increment: Base8): Base8 {
        return Base8(value + increment.value)
    }

    operator fun minus(decrement: Base8): Base8 {
        return Base8(value + decrement.value)
    }

    operator fun inc(): Base8 {
        return Base8(value + 1)
    }

    operator fun dec(): Base8 {
        return Base8(value - 1)
    }

    operator fun get(index: Int): Boolean {
        return ((value shr index) and 0x01) != 0
    }

    operator fun unaryMinus(): Base8 {
        var res: Int = value
        var temp: Int = 0
        for(i: Int in 0..7) {
            temp = temp shl 1
            val currentBit = res and 0x80 shr 7
            temp += when(currentBit) {
                1 -> 0
                0 -> 1
                else -> 0
            }
            res = res shl 1
        }
        return Base8(temp)
    }

    operator fun set(index: Int, setUnset: Boolean): Base8 {
        value = when(setUnset) {
            true  -> value  or (0x01 shl index)
            false -> value and (-Base8(0x01 shl index)).value
        }
        return this
    }
}