package com.example.pininterface.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import com.example.pininterface.R
import com.example.pininterface.database.DataBaseHelper
import com.example.pininterface.database.ModelClassDemographics
import com.example.pininterface.database.ModelClassFeedBack
import com.example.pininterface.databinding.ActivityMainBinding
import com.example.pininterface.helper.SuperActivityNavigation
import com.example.pininterface.logic.Participant
import com.example.pininterface.logic.PinSets


class MainActivity : SuperActivityNavigation() {
    private lateinit var binding: ActivityMainBinding
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val button = binding.buttonMainStart
        //TODO(Check that participant ID is unique)
        val participant = Participant((0..10000).random(), PinSets())


        button.setOnClickListener { nextInterfaceActivity(participant) }
        //button.setOnClickListener { test() }

        val displayMetrics = resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density

        Log.e("dp", "$dpWidth dp")
    }

    private fun test() {
        val id = 11
        val feedback = "test"

        val age = 29
        val hand = "rechts"
        val gender = "male"


        val dataBaseHelper = DataBaseHelper(this)

        Log.e("dataBase_demographics", dataBaseHelper.addDemographics(ModelClassDemographics(id, age, gender, hand)).toString())
        Log.e("dataBase_feedback", dataBaseHelper.addFeedBack(ModelClassFeedBack(id, feedback)).toString())
    }

}


