package com.example.pininterface.database.modelclass

/**
 * Model Class for Participant
 * Entry for participant table DB
 * @param pId (unique) id - PRIMARY KEY
 * @param pComplete complete 0 if not complete, 1 if participant finished his trial
 * @param pIdOrderPins order of the Pins for this participant
 * @param pIdOrderInterfaces order of the Interfaces for this participant
 */
class ModelClassParticipant (val pId: Int,
                             val pComplete: Int = 0,
                             val pIdOrderPins: Int = -1,
                             val pIdOrderInterfaces: Int = -1)