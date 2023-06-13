package com.example.pininterface.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.example.pininterface.database.interfaces.InterfaceDbParticipant
import com.example.pininterface.R
import com.example.pininterface.databinding.ActivityMainBinding
import com.example.pininterface.activity.helper.SuperActivityNavigation
import com.example.pininterface.database.interfaces.InterfaceDbGeneral

/**
 * MainActivity
 * once participant is finished, loops back to MainActivity
 */
class MainActivity : SuperActivityNavigation(), InterfaceDbParticipant, InterfaceDbGeneral {

    private lateinit var binding: ActivityMainBinding

    private lateinit var editTextFieldPhoneID: EditText
    private lateinit var editTextFieldResetDB: EditText

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var phoneID: String

    private val password = 2023

    /**
     * onCreate
     */
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("userStudyTeuberSharedPreferences", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        phoneID = sharedPreferences.getString("phoneID", "1").toString()
        if (phoneID == "1") {
            editor.putString("phoneID", "1")
            editor.apply()
        }

        Log.e("phoneId: ", phoneID)


        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val buttonStart = binding.buttonMainStart
        val buttonApplyPhoneID = binding.buttonApplyPhoneId
        editTextFieldPhoneID = binding.editTextPhoneId
        editTextFieldResetDB = binding.editTextResetDb
        val buttonResetDB = binding.buttonResetDb
        val buttonExportDB = binding.buttonExportDb

        updateEditTextPhoneID()

        if (!checkSizeDB(500_000)) {
            Toast.makeText(this, "DataBase is Full!", Toast.LENGTH_LONG).show()
            buttonStart.isEnabled = false
        }

        buttonStart.setOnClickListener { start() }
        buttonApplyPhoneID.setOnClickListener { applyPhoneId() }
        buttonResetDB.setOnClickListener { resetDB() }
        buttonExportDB.setOnClickListener { exportDB() }
    }

    /**
     * Export DB
     */
    private fun exportDB() {

        //TODO("write")
    }

    /**
     * Resets DB
     */
    private fun resetDB() {

        val pwResetDb = editTextFieldResetDB.text.toString().toIntOrNull() ?: -1
        if (pwResetDb == -1 || pwResetDb != password) {
            Toast.makeText(this, "invalid PW", Toast.LENGTH_SHORT).show()
            return
        }
        resetDB(this)
        Toast.makeText(this, "DB reset", Toast.LENGTH_SHORT).show()
    }

    /**
     * Checks if the Database is full
     * Counts the rows of the Participant table and compares it to pSize
     * @param pSize Integer number
     * @return false if number Rows > pSize, true if number Rows < pSize
     */
    private fun checkSizeDB(pSize: Int): Boolean {

        val dbSize = countRowsParticipant(this)
        return dbSize < pSize
    }

    /**
     * Starts the User Study
     */
    private fun start() {

        val intent = Intent(this, StartActivity::class.java)
        startActivity(intent)
    }

    /**
     * applys the Phone ID
     */
    private fun applyPhoneId() {

        val newPhoneID = editTextFieldPhoneID.text.toString().toIntOrNull() ?: -1
        if (newPhoneID == -1)
            Toast.makeText(this,"invalid Phone ID. Use only positive, whole numbers!", Toast.LENGTH_SHORT).show()
        else {
            phoneID = newPhoneID.toString()
            Toast.makeText(this, "PhoneID: $phoneID", Toast.LENGTH_SHORT).show()
        }

        editor.putString("phoneID", phoneID)
        editor.apply()
        updateEditTextPhoneID()
    }

    /**
     * updates the hint of the editTextView phone ID
     */
    private fun updateEditTextPhoneID() {

        editTextFieldPhoneID.hint = getString(R.string.phone_id, phoneID)
    }



}


