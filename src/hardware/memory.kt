package hardware
import base.Base16
import base.Base8
import base.BaseN
import base.context.ReferenceTo

class Memory {
    val size: Int = 65536
    private var mem = Array<Short>(65536) { _ -> (0).toShort() }

    fun get(addr: Base16): Base8 {
        return Base8(mem[addr.value].toInt())
    }

    fun set(addr: Base16, data: Base8) {
        mem[addr.value] = data.value.toShort()
    }
}

class MemoryReference(var memory: Memory, val addr: Base16): ReferenceTo(){
    override fun getVal() = memory.get(addr)
    override fun setVal(value: BaseN) {
        memory.set(addr, Base8(value))
    }
}