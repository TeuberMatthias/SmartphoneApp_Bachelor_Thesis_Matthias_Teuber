package com.example.pininterface.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import com.example.pininterface.database.interfaces.InterfaceDbParticipant
import com.example.pininterface.R
import com.example.pininterface.database.modelclass.ModelClassParticipant
import com.example.pininterface.databinding.ActivityMainBinding
import com.example.pininterface.activity.helper.SuperActivityNavigation
import com.example.pininterface.logic.Participant
import com.example.pininterface.values.ListInterfaces
import com.example.pininterface.values.PinSets

/**
 * MainActivity
 * once participant is finished, loops back to MainActivity
 */
class MainActivity : SuperActivityNavigation(), InterfaceDbParticipant {

    private lateinit var binding: ActivityMainBinding

    /**
     * onCreate
     */
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val button = binding.buttonMainStart

        var id: Int
        do {
            id = (100_000..999_999).random()
        } while (!checkIdFree(id))


        val participant = Participant(id, PinSets(), ListInterfaces())
        dbAddParticipant(participant, this)

        showParticipants(dbViewListParticipant(this))

        button.setOnClickListener { startNewActivity(participant, IntermediatePageActivity::class.java) }

        //val displayMetrics = resources.displayMetrics
        //val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        //Log.e("dp", "$dpWidth dp")
    }

    /**
     * displays the participant table in Log
     * @param pListParticipant the participant table as MutableList<ModelClassParticipant>
     */
    private fun showParticipants(pListParticipant: MutableList<ModelClassParticipant>) {

        pListParticipant.forEach {
            val string = "id:${it.pId},complete:${it.pComplete},orderPins:${it.pOrderPins},orderInterfaces:${it.pOrderInterfaces}"
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


