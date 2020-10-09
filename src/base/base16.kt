package base

class Base16(x: Int) {
    var value: Int = 0
        set(v) {
            field = when {
                v < 0x0000 -> 0xFFFF + v + 1
                v > 0xFFFF -> v - 0xFFFF - 1
                else -> v
            }
        }

    init {
        value = x
    }

    operator fun plus(increment: Base16): Base16 {
        return Base16(value + increment.value)
    }

    operator fun minus(decrement: Base16): Base16 {
        return Base16(value + decrement.value)
    }

    operator fun inc(): Base16 {
        return Base16(value + 1)
    }

    operator fun dec(): Base16 {
        return Base16(value - 1)
    }

    operator fun get(index: Int): Boolean {
        return ((value shr index) and 0x0001) != 0
    }

    operator fun unaryMinus(): Base16 {
        var res: Int = value
        var temp: Int = 0
        for(i: Int in 0..15) {
            temp = temp shl 1
            val currentBit = res and 0x8000 shr 15
            temp += when(currentBit) {
                1 -> 0
                0 -> 1
                else -> 0
            }
            res = res shl 1
        }
        return Base16(temp)
    }

    operator fun set(index: Int, setUnset: Boolean): Base16 {
        value = when(setUnset) {
            true  -> value  or (0x0001 shl index)
            false -> value and (-Base16(0x0001 shl index)).value
        }
        return this
    }
}