package com.example.pininterface.database.interfaces

import android.content.Context
import android.util.Log
import com.example.pininterface.database.DataBaseHelper
import com.example.pininterface.database.modelclass.ModelClassOrderInterfaces

/**
 * Interface for DB order_interfaces actions
 */
interface InterfaceDbOrderInterfaces {

    /**
     * Checks if an order of interfaces is already in the DB
     * If no it will add it to the table order_interface and give back the new auto generated id as Int
     * If an order of interfaces is already in use it will return the id if the already existing row
     * @param pModelClassOrderInterfaces ModelClass OrderInterfaces / represents a row of the table model_interfaces
     * @param pContext context ("this" in activity)
     * @return id of the orderInterfaces
     */
    fun dbGetIdOrderInterfaces(pModelClassOrderInterfaces: ModelClassOrderInterfaces, pContext: Context): Int {

        val dataBaseHelper = DataBaseHelper(pContext)

        val success = dataBaseHelper.addOrderInterfaces(pModelClassOrderInterfaces)
        Log.e("db.getOrderInterfacesID", success.toString())
        return success.toInt()
    }
}