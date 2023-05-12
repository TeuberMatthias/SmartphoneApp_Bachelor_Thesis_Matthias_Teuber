package com.example.pininterface.values

import com.example.pininterface.enums.EnumInterfaceTypes

/**
 * List Interfaces that will be used
 * to deactivate an Interface just comment line out
 * randomized order
 */
class ListInterfaces() {

    private val listInterfaces = ArrayList(arrayListOf(
        EnumInterfaceTypes.STANDARD,
    //    EnumInterfaceTypes.STANDARD_VIS,
    //    EnumInterfaceTypes.COLUMN,
    //    EnumInterfaceTypes.COLUMN_VIS
    ).shuffled())

    /**
     * Get ListInterfaces
     * @return listInterfaces
     */
    fun getListInterfaces(): MutableList<EnumInterfaceTypes> {

        return listInterfaces
    }
}