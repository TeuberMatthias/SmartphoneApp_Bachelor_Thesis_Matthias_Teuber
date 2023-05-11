package com.example.pininterface.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "thesis_database"
        private const val DATABASE_VERSION = 1
        private const val TABLE_DEMOGRAPHICS = "participant"
        private const val TABLE_FEEDBACK = "feedback"
        private const val TABLE_SUS = "sus"
        private const val TABLE_SUBMISSIONS = "submissions"

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

    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $TABLE_DEMOGRAPHICS($KEY_ID INTEGER PRIMARY KEY,$KEY_PARTICIPANT_ID INTEGER,$KEY_AGE INTEGER,$KEY_GENDER TEXT,$KEY_DOMINANT_HAND TEXT)")
        db?.execSQL("CREATE TABLE $TABLE_FEEDBACK($KEY_ID INTEGER PRIMARY KEY,$KEY_PARTICIPANT_ID INTEGER,$KEY_FEEDBACK TEXT)")
        db?.execSQL("CREATE TABLE $TABLE_SUS($KEY_ID INTEGER PRIMARY KEY,$KEY_PARTICIPANT_ID INTEGER,$KEY_INTERFACE TEXT," +
                "$KEY_Q0 INTEGER,$KEY_Q1 INTEGER,$KEY_Q2 INTEGER,$KEY_Q3 INTEGER,$KEY_Q4 INTEGER," +
                "$KEY_Q5 INTEGER,$KEY_Q6 INTEGER,$KEY_Q7 INTEGER,$KEY_Q8 INTEGER,$KEY_Q9 INTEGER)")
        db?.execSQL("CREATE TABLE $TABLE_SUBMISSIONS($KEY_ID INTEGER PRIMARY KEY,$KEY_PARTICIPANT_ID INTEGER,$KEY_INTERFACE, $KEY_PIN TEXT,$KEY_SUBMISSION TEXT,$KEY_TIME INTEGER)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_DEMOGRAPHICS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_FEEDBACK")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SUS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SUBMISSIONS")
        onCreate(db)
    }

    fun addSubmission(submission: ModelClassInterActionSubmission): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues().apply {
            put(KEY_PARTICIPANT_ID, submission.pId)
            put(KEY_INTERFACE, submission.pInterface)
            put(KEY_PIN, submission.pPin)
            put(KEY_SUBMISSION, submission.pSubmission)
            put(KEY_TIME, submission.pTime)
        }

        val success = db.insert(TABLE_SUBMISSIONS, null, contentValues)
        db.close()
        return success
    }


    fun addFeedBack(feedback: ModelClassFeedBack): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues().apply {
            put(KEY_PARTICIPANT_ID, feedback.pId)
            put(KEY_FEEDBACK, feedback.pFeedBack)
        }

        val success = db.insert(TABLE_FEEDBACK, null, contentValues)
        db.close()
        return success
    }

    fun addDemographics(demographics: ModelClassDemographics): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_PARTICIPANT_ID, demographics.id)
        contentValues.put(KEY_AGE, demographics.age)
        contentValues.put(KEY_GENDER, demographics.gender)
        contentValues.put(KEY_DOMINANT_HAND, demographics.dominant_hand)

        val success = db.insert(TABLE_DEMOGRAPHICS, null, contentValues)

        db.close()
        return success
    }

    fun addSUS(sus: ModelClassSuS): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues().apply {
            put(KEY_PARTICIPANT_ID, sus.pId)
            put(KEY_INTERFACE, sus.pInterfaceTyp)
            put(KEY_Q0, sus.pQ0)
            put(KEY_Q1, sus.pQ1)
            put(KEY_Q2, sus.pQ2)
            put(KEY_Q3, sus.pQ3)
            put(KEY_Q4, sus.pQ4)
            put(KEY_Q5, sus.pQ5)
            put(KEY_Q6, sus.pQ6)
            put(KEY_Q7, sus.pQ7)
            put(KEY_Q8, sus.pQ8)
            put(KEY_Q9, sus.pQ9)
        }

        val success = db.insert(TABLE_SUS, null, contentValues)
        db.close()
        return success
    }
}