package com.example.pininterface.logic

import com.example.pininterface.enums.EnumInterfaceTypes

class SuSResults (pInterfaceTyp: EnumInterfaceTypes, pSuSResults: MutableList<Int>) {
    private val interfaceTyp = pInterfaceTyp
    private val suSResults = pSuSResults

    fun getInterfaceTyp(): EnumInterfaceTypes {
        return interfaceTyp
    }

    fun getInterfaceTypAsString(): String {
        return interfaceTyp.value.toString()
    }

    fun getSuSResults(): MutableList<Int> {
        return suSResults
    }

    fun getSuSResultsAsString(): String {
        var string = ""
        suSResults.forEach {
            string += "$it-"
        }
        return string
    }

    fun checkSuSComplete(): Boolean {
        suSResults.forEach {
            if (it < 0)
                return false
        }
        return true
    }
}