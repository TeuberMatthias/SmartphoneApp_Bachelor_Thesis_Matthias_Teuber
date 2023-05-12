package com.example.pininterface.database.interfaces

import android.content.Context
import android.util.Log
import com.example.pininterface.database.DataBaseHelper
import com.example.pininterface.database.modelclass.ModelClassOrderPins

/**
 * Interface for DB order_pin actions
 */
interface InterfaceDbOrderPins {

    /**
     * Checks if an order of pins is already in the DB
     * If no it will add it to the table order_pins and give back the new auto generated id as Int
     * If an order of pins is already in use it will return the id if the already existing row
     * @param pModelClassOrderPins ModelClass OrderPins / represents a row of the table model_pins
     * @param pContext context ("this" in activity)
     * @return id of the orderInterfaces
     */
    fun dbGetIdOrderPins(pModelClassOrderPins: ModelClassOrderPins, pContext: Context): Int {

        val dataBaseHelper = DataBaseHelper(pContext)

        val success = dataBaseHelper.addOrderPins(pModelClassOrderPins)
        Log.e("db.getOrderPinsID", success.toString())
        return success.toInt()
    }
}