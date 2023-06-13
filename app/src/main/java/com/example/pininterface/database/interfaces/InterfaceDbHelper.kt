package com.example.pininterface.database.interfaces

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase

interface InterfaceDbHelper {

    /**
     * Adds a new row to a table with an Integer Primary Key and one unique Text column
     * If the unique Text already is in the DB it will give back the primary key of the already
     * existing row with this unique Text
     * Will return -1 if action is unsuccessful
     * @param pDB database
     * @param pTABLE the table
     * @param pKEY_ID_PRIMARY name of the primary key column (Integer)
     * @param pKEY_UNIQUE_COLUMN the unique text column
     * @param pContent the text field that is supposed to be inserted
     * @return the primary key of the row which includes pContent as float
     */
    @SuppressLint("Range")
    fun getPrimaryKeyFromUniqueColumnStringValue(pDB: SQLiteDatabase, pTABLE: String, pKEY_ID_PRIMARY: String, pKEY_UNIQUE_COLUMN: String, pContent: String): Long {

        var primaryKey: Long

        val contentValues = ContentValues().apply {
            put(pKEY_UNIQUE_COLUMN, pContent)
        }

        try {
            primaryKey = pDB.insertOrThrow(pTABLE, null, contentValues)
        } catch (e: SQLiteConstraintException) {
            val query = "SELECT $pKEY_ID_PRIMARY FROM $pTABLE WHERE $pKEY_UNIQUE_COLUMN = ?"
            val selectionArgs = arrayOf(pContent)

            val cursor = pDB.rawQuery(query, selectionArgs)

            primaryKey = if (cursor.moveToFirst()) {
                cursor.getLong(cursor.getColumnIndex(pKEY_ID_PRIMARY))
            } else {
                -1
            }
            cursor.close()
        }
        pDB.close()
        return primaryKey
    }

    /**
     * counts the Rows of a Table
     * @param pDB database
     * @param pTABLE Table
     * @return number of Rows of Table
     */
    fun countRowsOfTable(pDB: SQLiteDatabase, pTABLE: String): Int {

        val query = "SELECT COUNT(*) FROM $pTABLE"
        val cursor = pDB.rawQuery(query, null)

        var rowCount = 0
        if (cursor.moveToFirst())
            rowCount = cursor.getInt(0)

        cursor.close()
        pDB.close()
        return rowCount
    }

    /**
     * Adds a new Row to a Table
     * @param pDB database
     * @param pTABLE The Table where the row should be added
     * @param pContentValues The values of the row in form of contentValues
     * @return positive Long if successful, -1 when unsuccessful
     */
    fun addRowToTable(pDB: SQLiteDatabase, pTABLE: String, pContentValues: ContentValues): Long {

        val success = pDB.insert(pTABLE, null, pContentValues)
        pDB.close()
        return success
    }
}