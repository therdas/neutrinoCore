package hardware
import base.Base16
import base.Base8
import base.BaseN
import base.context.ReferenceTo

class Memory(private val size: Int = 65536) {
    private var mem = Array<Short>(size) { _ -> (0).toShort() }

    fun get(addr: Base16): Base8 {
        return Base8(mem[addr.value].toInt())
    }

    fun set(addr: Base16, data: Base8) {
        mem[addr.value] = data.value.toShort()
    }
}

class MemoryReference(var memory: Memory, val addr: Base16): ReferenceTo{
    override fun getVal() = memory.get(addr)
    override fun setVal(value: BaseN) {
        memory.set(addr, Base8(value))
    }
}

class DoubleMemoryReference(var memory: Memory, val addr: Base16): ReferenceTo {
    override fun getVal(): BaseN = Base16( memory.get(Base16(addr + 1)), memory.get(addr) )
    override fun setVal(value: BaseN) {
        val dword = Base16(value)
        memory.set(addr, dword.lower)
        memory.set(Base16(addr + 1), dword.upper)
    }
}

