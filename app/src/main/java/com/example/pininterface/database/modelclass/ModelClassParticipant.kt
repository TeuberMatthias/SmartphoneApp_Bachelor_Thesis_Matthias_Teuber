package com.example.pininterface.database.modelclass

/**
 * Model Class for Participant
 * Entry for participant table DB
 * @param pId (unique) id - PRIMARY KEY
 * @param pComplete complete 0 if not complete, 1 if participant finished his trial
 * @param pOrderPins order of the Pins for this participant TODO(maybe later in a seperate DB)
 * @param pOrderInterfaces order of the Interfaces for this participant TODO(same as above)
 */
class ModelClassParticipant (val pId: Int,
                             val pComplete: Int = 0,
                             val pOrderPins: String = "",
                             val pOrderInterfaces: Int = -1)