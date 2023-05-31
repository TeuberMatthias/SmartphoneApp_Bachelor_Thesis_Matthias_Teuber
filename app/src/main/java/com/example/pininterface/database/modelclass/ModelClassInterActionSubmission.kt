package com.example.pininterface.database.modelclass

/**
 * Model Class for submission
 * Entry for the submission table of DB
 * Auto generated PRIMARY KEY
 * @param pId id
 * @param pInterface interface
 * @param pPin pin
 * @param pSubmission submission
 * @param pTime time in msec since last submission
 */
class ModelClassInterActionSubmission(val pPhoneID: Int,
                                      val pId: Int,
                                      val pInterface: String,
                                      val pPin: String,
                                      val pSubmission: String,
                                      val pTime: Int)

//TODO(possible to get the order of participants through the order of submissions, maybe change later)