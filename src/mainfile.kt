import base.Base8
import base.Base16
import base.context.ReferenceTo
import functors.adder

import hardware.*

import functors.*
import functions.*
import functions.groups.arithmetic.f_add8
import functions.groups.arithmetic.f_addWC8

fun main(args: Array<String>) {
    var reg: RegisterFile = RegisterFile()
    var mem: Memory = Memory()



    var a:List<(FlagsRegister, List<ReferenceTo>)-> Boolean> = listOf<(FlagsRegister, List<ReferenceTo>)-> Boolean>(::f_add8, ::f_addWC8)
}