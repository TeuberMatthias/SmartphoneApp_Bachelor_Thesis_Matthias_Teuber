package com.example.pininterface.values

import com.example.pininterface.logic.Pin

/**
 * PinSets
 * All the different PinSets with their corrosponding Pins that will be used for the User Study
 */
class PinSets {

    //PIN Set 1
    private val pinS1P01 = Pin(arrayListOf(8,0,6,7,))
    private val pinS1P02 = Pin(arrayListOf(0,3,8,9,2,6,))
    private val pinS1P03 = Pin(arrayListOf(8,9,7,0,4,8,6,5,))
    private val pinS1P04 = Pin(arrayListOf(6,6,2,3,4,5,7,8,9,0,9,))

    private val pinSet1 = ArrayList(arrayListOf(
        pinS1P01,
        pinS1P02,
        pinS1P03,
        pinS1P04,
    ).shuffled())

    //PIN Set 2
    private val pinS2P01 = Pin(arrayListOf(8,6,4,0,))
    private val pinS2P02 = Pin(arrayListOf(7,4,8,1,0,9,))
    private val pinS2P03 = Pin(arrayListOf(3,8,4,0,7,9,8,1,))
    private val pinS2P04 = Pin(arrayListOf(6,8,8,9,1,2,4,0,5,5,7,))

    private val pinSet2 = ArrayList(arrayListOf(
        pinS2P01,
        pinS2P02,
        pinS2P03,
        pinS2P04,
    ).shuffled())

    //PIN Set 3
    private val pinS3P01 = Pin(arrayListOf(6,8,0,3,))
    private val pinS3P02 = Pin(arrayListOf(6,8,2,9,1,0,))
    private val pinS3P03 = Pin(arrayListOf(8,7,6,5,0,2,4,8,))
    private val pinS3P04 = Pin(arrayListOf(8,0,9,9,8,3,5,2,6,2,1,))

    private val pinSet3 = ArrayList(arrayListOf(
        pinS3P01,
        pinS3P02,
        pinS3P03,
        pinS3P04,
    ).shuffled())

    //PIN Set 4
    private val pinS4P01 = Pin(arrayListOf(8,6,1,0,))
    private val pinS4P02 = Pin(arrayListOf(2,1,7,0,8,3,))
    private val pinS4P03 = Pin(arrayListOf(8,9,0,1,3,0,5,8,))
    private val pinS4P04 = Pin(arrayListOf(9,6,2,2,5,3,1,0,2,8,4,))

    private val pinSet4 = ArrayList(arrayListOf(
        pinS4P01,
        pinS4P02,
        pinS4P03,
        pinS4P04,
    ).shuffled())

    //All PIN Sets, individual Pin Sets shuffled
    private val pinSets = ArrayList(arrayListOf(
        pinSet1,
        pinSet2,
        pinSet3,
        pinSet4,
    ).shuffled())

    /**
     * Getter PinSets
     * @return the PinSets
     */
    fun getPinSets(): ArrayList<ArrayList<Pin>> {
        return pinSets
    }
}