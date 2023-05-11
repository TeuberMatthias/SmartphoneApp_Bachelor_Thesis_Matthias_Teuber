package com.example.pininterface.logic

class Demographics (pAge: Int, pDominantHand: String, pGender: String) {
    val age = pAge
    val dominantHand = pDominantHand
    val gender = pGender

    fun getDemographicsString(): String {
        return "($gender,$dominantHand,${age})"
    }
}