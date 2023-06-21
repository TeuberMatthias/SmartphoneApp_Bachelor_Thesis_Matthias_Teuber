package com.example.pininterface.activity

import android.content.Intent
import android.os.Bundle
import com.example.pininterface.R
import com.example.pininterface.activity.helper.SuperActivityNavigation
import com.example.pininterface.databinding.ActivityEndBinding

/**
 * EndActivity, End Screen
 */
class EndActivity: SuperActivityNavigation() {

    private var counter = 0
    private var oldTime = System.currentTimeMillis()

    private lateinit var binding: ActivityEndBinding


    /**
     * onCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end)

        binding = ActivityEndBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    /**
     * Loops back to start activity after 5 short presses of the back button
     */
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {

        val newTime = System.currentTimeMillis()

        if (newTime - oldTime < 1000)
            counter++
        else
            counter = 0

        oldTime = newTime

        if (counter >= 5) {
            val intent = Intent(this, StartActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
}