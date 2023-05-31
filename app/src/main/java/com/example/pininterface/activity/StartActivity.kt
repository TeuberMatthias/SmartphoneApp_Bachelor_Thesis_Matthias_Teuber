package com.example.pininterface.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import com.example.pininterface.database.interfaces.InterfaceDbParticipant
import com.example.pininterface.R
import com.example.pininterface.database.modelclass.ModelClassParticipant
import com.example.pininterface.databinding.ActivityStartBinding
import com.example.pininterface.activity.helper.SuperActivityNavigation
import com.example.pininterface.logic.Participant
import com.example.pininterface.values.ListInterfaces
import com.example.pininterface.values.PinSets

/**
 * MainActivity
 * once participant is finished, loops back to MainActivity
 */
class StartActivity : SuperActivityNavigation(), InterfaceDbParticipant {

    private lateinit var binding: ActivityStartBinding

    /**
     * onCreate
     */
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityStartBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val buttonStart = binding.buttonStart

        buttonStart.setOnClickListener { start() }
    }

    /**
     * Button start pressed
     * Generates a participant and goes to the first intermediate page
     */
    private fun start() {

        val participant = createNewParticipant()
        startNewActivity(participant, IntermediatePageActivity::class.java)
    }

    /**
     * creates a new Participant with unique ID
     * @return the new Participant
     */
    private fun createNewParticipant(): Participant {

        var id: Int
        do {
            id = (100_000..999_999).random()
        } while (!checkIdFree(id))

        val participant = Participant(id, PinSets(), ListInterfaces())
        dbAddParticipant(participant, this)

        return participant
    }

    /**
     * displays the participant table in Log
     * @param pListParticipant the participant table as MutableList<ModelClassParticipant>
     */
    private fun showParticipants(pListParticipant: MutableList<ModelClassParticipant>) {

        pListParticipant.forEach {
            val string = "id:${it.pId},complete:${it.pComplete},orderPins:${it.pIdOrderPins},orderInterfaces:${it.pIdOrderInterfaces}"
            Log.e("participant", string)
        }
    }

    /**
     * Checks if an Id is already in DB
     * @param pId id
     * @return true if id is free/not in DB, false if id was already used
     */
    private fun checkIdFree(pId: Int): Boolean {
        val listParticipant = dbViewListParticipant(this)
        listParticipant.forEach {
            if (it.pId == pId) {
                Log.e("id_used", "Id: $pId already in use")
                return false
            }
        }
        Log.e("id_used", "Id: $pId not in use")
        return true
    }

}


