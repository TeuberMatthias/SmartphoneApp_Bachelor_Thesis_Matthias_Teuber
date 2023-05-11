package com.example.pininterface.activity

import android.os.Bundle
import android.util.Log
import com.example.pininterface.R
import com.example.pininterface.databinding.ActivityResultPageBinding
import com.example.pininterface.helper.SuperActivityNavigation
import com.example.pininterface.logic.Participant
import com.google.gson.Gson

class ResultPageActivity : SuperActivityNavigation() {
    private lateinit var participant: Participant
    private lateinit var binding: ActivityResultPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_page)

        binding = ActivityResultPageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val intent = intent
        val gson = Gson()
        participant = gson.fromJson<Participant>(intent.getStringExtra("participant"), Participant::class.java)

        val results = createResultString(participant)
        Log.e("Results","\n" + results)
        binding.results.text = ""

        binding.resultsButtonContinue.buttonContinue.setOnClickListener { nextInterfaceActivity(participant) }
    }
}