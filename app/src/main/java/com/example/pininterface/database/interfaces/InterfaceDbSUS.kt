package com.example.pininterface.database.interfaces

import android.content.Context
import android.util.Log
import com.example.pininterface.database.DataBaseHelper
import com.example.pininterface.database.modelclass.ModelClassSuS
import com.example.pininterface.enums.EnumInterfaceTypes

/**
 * Interface for DB SUS actions
 */
interface InterfaceDbSUS {

    /**
     * Add a new row to sus table of DB
     * @param pId id
     * @param pInterfaceTyp interface typ
     * @param pListAnswers list of the sus Answers (10 entries)
     * @param pContext context ("this" in activity)
     * @return positive Long if successful, -1 when unsuccessful
     */
    fun dbAddSUS(pPhoneID: Int, pId: Int, pInterfaceTyp: EnumInterfaceTypes, pListAnswers: MutableList<Int>, pContext: Context): Long {

        val dataBaseHelper = DataBaseHelper(pContext)
        val modelClassSUS = ModelClassSuS(pPhoneID, pId, pInterfaceTyp.toString(),
            pListAnswers[0], pListAnswers[1], pListAnswers[2], pListAnswers[3], pListAnswers[4],
            pListAnswers[5], pListAnswers[6], pListAnswers[7],  pListAnswers[9], pListAnswers[9])

        val success = dataBaseHelper.addSUS(modelClassSUS)
        Log.e("db.add_sus", success.toString())
        return success
    }
}