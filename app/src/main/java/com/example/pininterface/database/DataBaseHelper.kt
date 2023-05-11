package com.example.pininterface.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.pininterface.database.modelclass.ModelClassDemographics
import com.example.pininterface.database.modelclass.ModelClassFeedBack
import com.example.pininterface.database.modelclass.ModelClassInterActionSubmission
import com.example.pininterface.database.modelclass.ModelClassParticipant
import com.example.pininterface.database.modelclass.ModelClassSuS

/**
 * Class to manage the DataBank
 */
class DataBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    /**
     * Companion object with the table and key/column names of the DataBank
     */
    companion object {
        private const val DATABASE_NAME = "thesis_database"
        private const val DATABASE_VERSION = 1
        private const val TABLE_DEMOGRAPHICS = "demographics"
        private const val TABLE_FEEDBACK = "feedback"
        private const val TABLE_SUS = "sus"
        private const val TABLE_SUBMISSIONS = "submissions"
        private const val TABLE_PARTICIPANT = "participant"

        private const val PK_TABLE_SUS = "pk_table_sus"

        private const val KEY_ID = "_id"
        private const val KEY_PARTICIPANT_ID = "p_id"
        private const val KEY_AGE = "age"
        private const val KEY_GENDER = "gender"
        private const val KEY_DOMINANT_HAND = "dominant_hand"

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
                "$KEY_PARTICIPANT_ID INTEGER PRIMARY KEY," +
                "$KEY_COMPLETE INTEGER," +
                "$KEY_ORDER_PINS TEXT," +
                "$KEY_ORDER_INTERFACES TEXT)")

        db?.execSQL("CREATE TABLE $TABLE_DEMOGRAPHICS(" +
                "$KEY_PARTICIPANT_ID INTEGER PRIMARY KEY," +
                "$KEY_AGE INTEGER," +
                "$KEY_GENDER TEXT," +
                "$KEY_DOMINANT_HAND TEXT)")

        db?.execSQL("CREATE TABLE $TABLE_FEEDBACK(" +
                "$KEY_PARTICIPANT_ID INTEGER PRIMARY KEY," +
                "$KEY_FEEDBACK TEXT)")

        db?.execSQL("CREATE TABLE $TABLE_SUS(" +
                "$KEY_PARTICIPANT_ID INTEGER NOT NULL," +
                "$KEY_INTERFACE TEXT NOT NULL," +
                "$KEY_Q0 INTEGER,$KEY_Q1 INTEGER,$KEY_Q2 INTEGER,$KEY_Q3 INTEGER,$KEY_Q4 INTEGER," +
                "$KEY_Q5 INTEGER,$KEY_Q6 INTEGER,$KEY_Q7 INTEGER,$KEY_Q8 INTEGER,$KEY_Q9 INTEGER," +
                "CONSTRAINT $PK_TABLE_SUS PRIMARY KEY ($KEY_PARTICIPANT_ID, $KEY_INTERFACE))")

        db?.execSQL("CREATE TABLE $TABLE_SUBMISSIONS(" +
                "$KEY_ID INTEGER PRIMARY KEY," +
                "$KEY_PARTICIPANT_ID INTEGER," +
                "$KEY_INTERFACE TEXT," +
                "$KEY_PIN TEXT," +
                "$KEY_SUBMISSION TEXT," +
                "$KEY_TIME INTEGER)")
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
        onCreate(db)
    }

    /**
     * Adds a new Row to the participant Table
     * @param pParticipant participant ModelClass
     * @return positive Long if successful, -1 when unsuccessful
     */
    fun addParticipant(pParticipant: ModelClassParticipant): Long {

        val db = this.writableDatabase

        val contentValues = ContentValues().apply {
            put(KEY_PARTICIPANT_ID, pParticipant.pId)
            put(KEY_COMPLETE, pParticipant.pComplete)
            put(KEY_ORDER_PINS, pParticipant.pOrderPins)
            put(KEY_ORDER_INTERFACES, pParticipant.pOrderInterfaces)
        }

        val success = db.insert(TABLE_PARTICIPANT, null, contentValues)
        db.close()
        return success
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
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(querySelect, null)
        } catch (e: SQLiteException) {
            db.execSQL(querySelect)

            return mutableListOf()
        }

        var id: Int
        var complete: Int
        var orderPins: String
        var orderInterfaces: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(KEY_PARTICIPANT_ID))
                complete = cursor.getInt(cursor.getColumnIndex(KEY_COMPLETE))
                orderPins = cursor.getString(cursor.getColumnIndex(KEY_ORDER_PINS))
                orderInterfaces = cursor.getString(cursor.getColumnIndex(KEY_ORDER_INTERFACES))

                val participant = ModelClassParticipant(id, complete, orderPins, orderInterfaces)
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
            put(KEY_PARTICIPANT_ID, pParticipant.pId)
            put(KEY_COMPLETE, pParticipant.pComplete)
            //put(KEY_ORDER_PINS, participant.pOrderPins)
            //put(KEY_ORDER_INTERFACES, participant.pOrderInterfaces)
        }

        val success = db.update(TABLE_PARTICIPANT, contentValues, "$KEY_PARTICIPANT_ID=${pParticipant.pId}", null)
        db.close()
        return success
    }

    /**
     * Adds a new Row to the submission Table
     * @param pSubmission Submission ModelClass
     * @return positive Long if successful, -1 when unsuccessful
     */
    fun addSubmission(pSubmission: ModelClassInterActionSubmission): Long {

        val db = this.writableDatabase

        val contentValues = ContentValues().apply {
            put(KEY_PARTICIPANT_ID, pSubmission.pId)
            put(KEY_INTERFACE, pSubmission.pInterface)
            put(KEY_PIN, pSubmission.pPin)
            put(KEY_SUBMISSION, pSubmission.pSubmission)
            put(KEY_TIME, pSubmission.pTime)
        }

        val success = db.insert(TABLE_SUBMISSIONS, null, contentValues)
        db.close()
        return success
    }

    /**
     * Adds a new Row to the feedback Table
     * @param pFeedback Feedback ModelClass
     * @return positive Long if successful, -1 when unsuccessful
     */
    fun addFeedBack(pFeedback: ModelClassFeedBack): Long {

        val db = this.writableDatabase

        val contentValues = ContentValues().apply {
            put(KEY_PARTICIPANT_ID, pFeedback.pId)
            put(KEY_FEEDBACK, pFeedback.pFeedBack)
        }

        val success = db.insert(TABLE_FEEDBACK, null, contentValues)
        db.close()
        return success
    }

    /**
     * Adds a new Row to the demographics Table
     * @param pDemographics Demographics ModelClass
     * @return positive Long if successful, -1 when unsuccessful
     */
    fun addDemographics(pDemographics: ModelClassDemographics): Long {

        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_PARTICIPANT_ID, pDemographics.id)
        contentValues.put(KEY_AGE, pDemographics.age)
        contentValues.put(KEY_GENDER, pDemographics.gender)
        contentValues.put(KEY_DOMINANT_HAND, pDemographics.dominant_hand)

        val success = db.insert(TABLE_DEMOGRAPHICS, null, contentValues)

        db.close()
        return success
    }

    /**
     * Adds a new Row to the sus Table
     * @param pSUS Demograpics ModelClass
     * @return positive Long if successful, -1 when unsuccessful
     */
    fun addSUS(pSUS: ModelClassSuS): Long {

        val db = this.writableDatabase

        val contentValues = ContentValues().apply {
            put(KEY_PARTICIPANT_ID, pSUS.pId)
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

        val success = db.insert(TABLE_SUS, null, contentValues)
        db.close()
        return success
    }
}