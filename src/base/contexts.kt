package base.context
import base.Base16
import base.BaseN

class ArithmeticContext(val value: BaseN,
              val S: Boolean,
              val Z: Boolean,
              val AC: Boolean,
              val P: Boolean,
              val CY:Boolean) {

}

class ShiftContext(val value: BaseN,
                   val serialOut: Boolean) {

}

//May not be used...
enum class Register {
    a, b, c, d, e, h, l
}

enum class RegisterPair {
    psw, bc, de, hl, sp, pc, ir
}

class RegisterContext(val value: BaseN,
                      val register: Register) {

}

class RegisterPairContext(val value: Base16,
                          val registerPair: RegisterPair) {

}