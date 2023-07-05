package com.example.pininterface.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.pininterface.database.interfaces.InterfaceDbHelper
import com.example.pininterface.database.modelclass.ModelClassDemographics
import com.example.pininterface.database.modelclass.ModelClassFeedBack
import com.example.pininterface.database.modelclass.ModelClassInterActionSubmission
import com.example.pininterface.database.modelclass.ModelClassOrderInterfaces
import com.example.pininterface.database.modelclass.ModelClassOrderPins
import com.example.pininterface.database.modelclass.ModelClassParticipant
import com.example.pininterface.database.modelclass.ModelClassSuS

/**
 * Class to manage the DataBank
 */
class DataBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION),
    InterfaceDbHelper {

    /**
     * Companion object with the table and key/column names of the DataBank
     */
    companion object {

        private const val DATABASE_NAME = "thesis_database"
        private const val DATABASE_VERSION = 10

        private const val TABLE_DEMOGRAPHICS = "demographics"
        private const val TABLE_FEEDBACK = "feedback"
        private const val TABLE_SUS = "sus"
        private const val TABLE_SUBMISSIONS = "submissions"
        private const val TABLE_PARTICIPANT = "participant"
        private const val TABLE_ORDER_INTERFACES = "order_interfaces"
        private const val TABLE_ORDER_PINS = "order_pins"

        private const val PK_TABLE_SUS = "pk_table_sus"

        private const val KEY_ID_PHONE = "id_phone"
        private const val KEY_ID_SUBMISSION = "id_submission"
        private const val KEY_ID_PARTICIPANT = "id_participant"
        private const val KEY_ID_ORDER_INTERFACES = "id_order_interface"
        private const val KEY_ID_ORDER_PINS = "id_order_pins"

        private const val KEY_AGE = "age"
        private const val KEY_GENDER = "gender"
        private const val KEY_DOMINANT_HAND = "dominant_hand"
        private const val KEY_HAND_USED = "hand_used"
        private const val KEY_HAND_USED_NORMALLY = "hand_used_normally"
        private const val KEY_FEEDBACK = "feedback_text"
        private const val KEY_INTERFACE = "interface"
        private const val KEY_PIN = "pin"
        private const val KEY_SUBMISSION = "submission"
        private const val KEY_TIME = "time_ms"
        private const val KEY_Q0 = "question_0"
        private const val KEY_Q1 = "question_1"
        private const val KEY_Q2 = "question_2"
        private const val KEY_Q3 = "question_3"
        private const val KEY_Q4 = "question_4"
        private const val KEY_Q5 = "question_5"
        private const val KEY_Q6 = "question_6"
        private const val KEY_Q7 = "question_7"
        private const val KEY_Q8 = "question_8"
        private const val KEY_Q9 = "question_9"
        private const val KEY_COMPLETE = "complete"
        private const val KEY_ORDER_PINS = "order_pin"
        private const val KEY_ORDER_INTERFACES = "order_interface"
    }

    /**
     * Creates the DataBank Tables
     * @param db SQLiteDataBase
     */
    override fun onCreate(db: SQLiteDatabase?) {

        db?.execSQL("CREATE TABLE $TABLE_PARTICIPANT(" +
                "$KEY_ID_PHONE INTEGER," +
                "$KEY_ID_PARTICIPANT INTEGER," +
                "$KEY_COMPLETE INTEGER," +
                "$KEY_ID_ORDER_PINS INTEGER," +
                "$KEY_ID_ORDER_INTERFACES INTEGER," +
                "PRIMARY KEY ($KEY_ID_PHONE, $KEY_ID_PARTICIPANT))"
        )

        db?.execSQL("CREATE TABLE $TABLE_DEMOGRAPHICS(" +
                "$KEY_ID_PHONE INTEGER," +
                "$KEY_ID_PARTICIPANT INTEGER," +
                "$KEY_AGE INTEGER," +
                "$KEY_GENDER TEXT," +
                "$KEY_DOMINANT_HAND TEXT," +
                "$KEY_HAND_USED TEXT," +
                "$KEY_HAND_USED_NORMALLY TEXT," +
                "PRIMARY KEY ($KEY_ID_PHONE, $KEY_ID_PARTICIPANT))"
        )

        db?.execSQL("CREATE TABLE $TABLE_FEEDBACK(" +
                "$KEY_ID_PHONE INTEGER," +
                "$KEY_ID_PARTICIPANT INTEGER," +
                "$KEY_FEEDBACK TEXT," +
                "PRIMARY KEY ($KEY_ID_PHONE, $KEY_ID_PARTICIPANT))"
        )

        db?.execSQL("CREATE TABLE $TABLE_SUS(" +
                "$KEY_ID_PHONE INTEGER," +
                "$KEY_ID_PARTICIPANT INTEGER NOT NULL," +
                "$KEY_INTERFACE TEXT NOT NULL," +
                "$KEY_Q0 INTEGER,$KEY_Q1 INTEGER,$KEY_Q2 INTEGER,$KEY_Q3 INTEGER,$KEY_Q4 INTEGER," +
                "$KEY_Q5 INTEGER,$KEY_Q6 INTEGER,$KEY_Q7 INTEGER,$KEY_Q8 INTEGER,$KEY_Q9 INTEGER," +
                "CONSTRAINT $PK_TABLE_SUS PRIMARY KEY ($KEY_ID_PHONE, $KEY_ID_PARTICIPANT, $KEY_INTERFACE))"
        )

        db?.execSQL("CREATE TABLE $TABLE_SUBMISSIONS(" +
                "$KEY_ID_SUBMISSION INTEGER," +
                "$KEY_ID_PHONE INTEGER," +
                "$KEY_ID_PARTICIPANT INTEGER," +
                "$KEY_INTERFACE TEXT," +
                "$KEY_PIN TEXT," +
                "$KEY_SUBMISSION TEXT," +
                "$KEY_TIME INTEGER," +
                "PRIMARY KEY ($KEY_ID_PHONE, $KEY_ID_SUBMISSION, $KEY_ID_PARTICIPANT))"
        )

        db?.execSQL("CREATE TABLE $TABLE_ORDER_INTERFACES(" +
                "$KEY_ID_ORDER_INTERFACES INTEGER PRIMARY KEY," +
                "$KEY_ORDER_INTERFACES TEXT UNIQUE)"
        )

        db?.execSQL("CREATE TABLE $TABLE_ORDER_PINS(" +
                "$KEY_ID_ORDER_PINS INTEGER PRIMARY KEY," +
                "$KEY_ORDER_PINS TEXT UNIQUE)"
        )
    }

    /**
     * Checks on Upgrade of DB if a Table already exists
     * @param db SQLiteDatabase
     * @param oldVersion old version of the DB
     * @param newVersion new version of the DB
     */
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_PARTICIPANT")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_DEMOGRAPHICS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_FEEDBACK")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SUS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SUBMISSIONS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ORDER_INTERFACES")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ORDER_PINS")

        onCreate(db)
    }

    /**
     * Drop all Tables in DB and recreates it new
     */
    fun dropTables() {

        val db = this.writableDatabase

        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_PARTICIPANT")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_DEMOGRAPHICS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_FEEDBACK")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SUS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SUBMISSIONS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ORDER_INTERFACES")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ORDER_PINS")

        onCreate(db)
    }

    /**
     * Counts number of Rows of Table Participant
     * @return number of rows
     */
    fun countRowsParticipant(): Int {

        val db = this.writableDatabase
        return countRowsOfTable(db, TABLE_PARTICIPANT)
    }

    /**
     * Adds a new Row to the order_pin Table
     * If the order of interfaces already exist it will return the id of the existing row
     * @param pOrderPins order_interfaces ModelClass
     * @return the id of the row, -1 if action unsuccessful
     */
    fun addOrderPins(pOrderPins: ModelClassOrderPins): Long {

        val db = this.writableDatabase
        val orderPins = pOrderPins.pOrderPins
        return getPrimaryKeyFromUniqueColumnStringValue(db, TABLE_ORDER_PINS, KEY_ID_ORDER_PINS, KEY_ORDER_PINS, orderPins)
    }

    /**
     * Adds a new Row to the order_interfaces Table
     * If the order of interfaces already exist it will return the id of the existing row
     * @param pOrderInterfaces order_interfaces ModelClass
     * @return the id of the row, -1 if action unsuccessful
     */
    fun addOrderInterfaces(pOrderInterfaces: ModelClassOrderInterfaces): Long {

        val db = this.writableDatabase
        val orderInterfaces = pOrderInterfaces.pOrderInterfaces
        return getPrimaryKeyFromUniqueColumnStringValue(db, TABLE_ORDER_INTERFACES, KEY_ID_ORDER_INTERFACES, KEY_ORDER_INTERFACES, orderInterfaces)
    }

    /**
     * Adds a new Row to the participant Table
     * @param pParticipant participant ModelClass
     * @return positive Long if successful, -1 when unsuccessful
     */
    fun addParticipant(pParticipant: ModelClassParticipant): Long {

        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(KEY_ID_PHONE, pParticipant.pPhoneID)
            put(KEY_ID_PARTICIPANT, pParticipant.pId)
            put(KEY_COMPLETE, pParticipant.pComplete)
            put(KEY_ID_ORDER_PINS, pParticipant.pIdOrderPins)
            put(KEY_ID_ORDER_INTERFACES, pParticipant.pIdOrderInterfaces)
        }

        return addRowToTable(db, TABLE_PARTICIPANT, contentValues)
    }

    /**
     * Gets the entire participant table
     * @return participant table as MutableList<ModelClassParticipant>
     */
    @SuppressLint("Range", "Recycle")
    fun viewParticipant(): MutableList<ModelClassParticipant> {

        val listParticipant: MutableList<ModelClassParticipant> = mutableListOf<ModelClassParticipant>()
        val querySelect = "SELECT * FROM $TABLE_PARTICIPANT"
        val db = this.readableDatabase
        val cursor: Cursor?

        try {
            cursor = db.rawQuery(querySelect, null)
        } catch (e: SQLiteException) {
            db.execSQL(querySelect)

            return mutableListOf()
        }

        var phoneID: Int
        var id: Int
        var complete: Int
        var orderPinsID: Int
        var orderInterfacesID: Int

        if (cursor.moveToFirst()) {
            do {
                phoneID = cursor.getInt(cursor.getColumnIndex(KEY_ID_PHONE))
                id = cursor.getInt(cursor.getColumnIndex(KEY_ID_PARTICIPANT))
                complete = cursor.getInt(cursor.getColumnIndex(KEY_COMPLETE))
                orderPinsID = cursor.getInt(cursor.getColumnIndex(KEY_ID_ORDER_PINS))
                orderInterfacesID = cursor.getInt(cursor.getColumnIndex(KEY_ID_ORDER_INTERFACES))

                val participant = ModelClassParticipant(phoneID, id, complete, orderPinsID, orderInterfacesID)
                listParticipant.add(participant)
            } while (cursor.moveToNext())
        }
        return listParticipant
    }

    /**
     * Updates the complete field for one entry in the participant table
     * @param pParticipant participant as ModelClassParticipant, only the Id and complete attributes matter
     * @return positive Int when successful, -1 if unsuccessful
     */
    fun updateParticipantComplete(pParticipant: ModelClassParticipant): Int {

        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(KEY_ID_PHONE, pParticipant.pPhoneID)
            put(KEY_ID_PARTICIPANT, pParticipant.pId)
            put(KEY_COMPLETE, pParticipant.pComplete)
        }

        val success = db.update(TABLE_PARTICIPANT, contentValues, "$KEY_ID_PARTICIPANT=${pParticipant.pId} AND $KEY_ID_PHONE=${pParticipant.pPhoneID}", null)
        db.close()
        return success
    }

    /**
     * Adds a new Row to the submission Table
     * @param pSubmission Submission ModelClass
     * @return positive Long if successful, -1 when unsuccessful
     */
    fun addSubmission(pSubmission: ModelClassInterActionSubmission): Long {

        val db = writableDatabase

        val query = "SELECT MAX($KEY_ID_SUBMISSION) " +
                "FROM $TABLE_SUBMISSIONS " +
                "WHERE $KEY_ID_PARTICIPANT = ${pSubmission.pId} AND $KEY_ID_PHONE = ${pSubmission.pPhoneID}"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()
        val idSubmission = cursor.getInt(0) + 1

        cursor.close()
        //db.close()

        val contentValues = ContentValues().apply {
            put(KEY_ID_SUBMISSION, idSubmission)
            put(KEY_ID_PHONE, pSubmission.pPhoneID)
            put(KEY_ID_PARTICIPANT, pSubmission.pId)
            put(KEY_INTERFACE, pSubmission.pInterface)
            put(KEY_PIN, pSubmission.pPin)
            put(KEY_SUBMISSION, pSubmission.pSubmission)
            put(KEY_TIME, pSubmission.pTime)
        }

        return addRowToTable(db, TABLE_SUBMISSIONS, contentValues)
    }

    /**
     * Adds a new Row to the feedback Table
     * @param pFeedback Feedback ModelClass
     * @return positive Long if successful, -1 when unsuccessful
     */
    fun addFeedBack(pFeedback: ModelClassFeedBack): Long {

        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(KEY_ID_PHONE, pFeedback.pPhoneID)
            put(KEY_ID_PARTICIPANT, pFeedback.pId)
            put(KEY_FEEDBACK, pFeedback.pFeedBack)
        }

        return addRowToTable(db, TABLE_FEEDBACK, contentValues)
    }

    /**
     * Adds a new Row to the demographics Table
     * @param pDemographics Demographics ModelClass
     * @return positive Long if successful, -1 when unsuccessful
     */
    fun addDemographics(pDemographics: ModelClassDemographics): Long {

        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(KEY_ID_PHONE, pDemographics.pPhoneID)
            put(KEY_ID_PARTICIPANT, pDemographics.pId)
            put(KEY_AGE, pDemographics.pAge)
            put(KEY_GENDER, pDemographics.pGender)
            put(KEY_DOMINANT_HAND, pDemographics.pDominant_hand)
            put(KEY_HAND_USED, pDemographics.pHandUsed)
            put(KEY_HAND_USED_NORMALLY, pDemographics.pHandUsedNormally)
        }

        return addRowToTable(db, TABLE_DEMOGRAPHICS, contentValues)
    }

    /**
     * Adds a new Row to the sus Table
     * @param pSUS Demograpics ModelClass
     * @return positive Long if successful, -1 when unsuccessful
     */
    fun addSUS(pSUS: ModelClassSuS): Long {

        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(KEY_ID_PHONE, pSUS.pPhoneID)
            put(KEY_ID_PARTICIPANT, pSUS.pId)
            put(KEY_INTERFACE, pSUS.pInterfaceTyp)
            put(KEY_Q0, pSUS.pQ0)
            put(KEY_Q1, pSUS.pQ1)
            put(KEY_Q2, pSUS.pQ2)
            put(KEY_Q3, pSUS.pQ3)
            put(KEY_Q4, pSUS.pQ4)
            put(KEY_Q5, pSUS.pQ5)
            put(KEY_Q6, pSUS.pQ6)
            put(KEY_Q7, pSUS.pQ7)
            put(KEY_Q8, pSUS.pQ8)
            put(KEY_Q9, pSUS.pQ9)
        }

        return addRowToTable(db, TABLE_SUS, contentValues)
    }

}