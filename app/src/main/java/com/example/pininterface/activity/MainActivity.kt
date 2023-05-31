package com.example.pininterface.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.pininterface.database.interfaces.InterfaceDbParticipant
import com.example.pininterface.R
import com.example.pininterface.databinding.ActivityMainBinding
import com.example.pininterface.activity.helper.SuperActivityNavigation

/**
 * MainActivity
 * once participant is finished, loops back to MainActivity
 */
class MainActivity : SuperActivityNavigation(), InterfaceDbParticipant {

    private lateinit var binding: ActivityMainBinding

    private lateinit var editTextFieldPhoneID: EditText

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var phoneID: String

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

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val buttonStart = binding.buttonMainStart
        val buttonApplyPhoneID = binding.buttonApplyPhoneId
        editTextFieldPhoneID = binding.editTextPhoneId

        updateEditTextPhoneID()

        buttonStart.setOnClickListener { start() }
        buttonApplyPhoneID.setOnClickListener { applyPhoneId() }
    }


    private fun start() {

        val intent = Intent(this, StartActivity::class.java)
        startActivity(intent)
    }

    private fun applyPhoneId() {

        val newPhoneID = editTextFieldPhoneID.text.toString().toIntOrNull() ?: -1
        if (newPhoneID == -1)
            Toast.makeText(this,"invalid Phone ID. Use only positive, whole numbers!", Toast.LENGTH_SHORT).show()
        else
            phoneID = newPhoneID.toString()

        editor.putString("phoneID", phoneID)
        editor.apply()
        updateEditTextPhoneID()
    }

    private fun updateEditTextPhoneID() {

        editTextFieldPhoneID.hint = getString(R.string.phone_id, phoneID)
    }



}


