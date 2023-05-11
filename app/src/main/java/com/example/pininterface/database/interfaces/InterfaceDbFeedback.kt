package com.example.pininterface.database.interfaces

import android.content.Context
import android.util.Log
import com.example.pininterface.database.DataBaseHelper
import com.example.pininterface.database.modelclass.ModelClassFeedBack

interface InterfaceDbFeedback {

    fun dbAddFeedback(pId: Int, pFeedback: String, pContext: Context): Long {

        val dataBaseHelper = DataBaseHelper(pContext)

        val success = dataBaseHelper.addFeedBack(ModelClassFeedBack(pId, pFeedback))
        Log.e("db.add_feedback", success.toString())
        return success
    }
}