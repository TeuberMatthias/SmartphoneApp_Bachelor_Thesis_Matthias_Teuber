package com.example.pininterface.Interface

import android.content.Context
import android.util.Log
import com.example.pininterface.database.DataBaseHelper
import com.example.pininterface.database.ModelClassFeedBack

interface InterfaceDbFeedback {

    fun dbAddRecordFeedBack(pId: Int, pFeedback: String, pContext: Context): Long {

        val dataBaseHelper = DataBaseHelper(pContext)

        val success = dataBaseHelper.addFeedBack(ModelClassFeedBack(pId, pFeedback))
        Log.e("dataBase", success.toString())
        return success
    }
}