package com.example.pininterface.database.interfaces

import android.content.Context
import android.util.Log
import com.example.pininterface.database.DataBaseHelper

interface InterfaceDbGeneral {

    /**
     * Resets the DB
     * @param pContext context
     */
    fun resetDB(pContext: Context) {

        val dataBaseHelper = DataBaseHelper(pContext)
        dataBaseHelper.dropTables()
        Log.e("db.resetDB","resetDB")
    }
}