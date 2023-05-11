package com.example.pininterface.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import com.example.pininterface.R
import com.example.pininterface.database.DataBaseHelper
import com.example.pininterface.database.ModelClassDemographics
import com.example.pininterface.database.ModelClassFeedBack
import com.example.pininterface.database.ModelClassParticipant
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

        var id: Int = 0
        do {
            id = (0..999_999).random()
        } while (!dbCheckIdFree(id))


        val participant = Participant(id, PinSets())
        dbWriteParticipant(participant)


        Log.e("Participant id:", participant.getID().toString())
        dbShowParticipants(dbGetListParticipant())

        button.setOnClickListener { nextInterfaceActivity(participant) }

        //val displayMetrics = resources.displayMetrics
        //val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        //Log.e("dp", "$dpWidth dp")

    }

    private fun dbWriteParticipant(pParticipant: Participant) {
        val id = pParticipant.getID()
        val orderPins = pParticipant.getPinSetsAsString()
        val orderInterfaces = pParticipant.getInterfacesAsString()

        val db = DataBaseHelper(this)
        val participant = ModelClassParticipant(id, 0, orderPins, orderInterfaces)

        Log.e("db_participant",db.addParticipant(participant).toString())
    }

    private fun dbShowParticipants(pListParticipant: MutableList<ModelClassParticipant>) {
        pListParticipant.forEach {
            val string = "id:${it.pId},complete:${it.pComplete},orderPins:${it.pOrderPins},orderInterfaces:${it.pOrderInterfaces}"
            Log.e("participant", string)
        }
    }
    private fun dbGetListParticipant(): MutableList<ModelClassParticipant> {
        val db: DataBaseHelper = DataBaseHelper(this)
        return db.viewParticipant()
    }

    private fun dbCheckIdFree(pId: Int): Boolean {
        val listParticipant = dbGetListParticipant()
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


