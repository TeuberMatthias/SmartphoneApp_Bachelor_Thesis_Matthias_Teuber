package com.example.pininterface.database.interfaces

import android.content.Context
import android.util.Log
import com.example.pininterface.database.DataBaseHelper
import com.example.pininterface.database.modelclass.ModelClassSuS
import com.example.pininterface.enums.EnumInterfaceTypes

interface InterfaceDbSUS {

    fun dbAddSUS(pId: Int, pInterfaceTyp: EnumInterfaceTypes, pListAnswers: MutableList<Int>, pContext: Context): Long {

        val dataBaseHelper = DataBaseHelper(pContext)
        val modelClassSUS = ModelClassSuS(pId, pInterfaceTyp.toString(),
            pListAnswers[0], pListAnswers[1], pListAnswers[2], pListAnswers[3], pListAnswers[4],
            pListAnswers[5], pListAnswers[6], pListAnswers[7],  pListAnswers[9], pListAnswers[9])

        val success = dataBaseHelper.addSUS(modelClassSUS)
        Log.e("db.add_sus", success.toString())
        return success
    }
}