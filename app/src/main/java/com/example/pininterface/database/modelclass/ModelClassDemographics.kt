package com.example.pininterface.database.modelclass

/**
 * Model Class for demographics
 * Entry for the demograpics table DB
 * @param pId id - PRIMARY KEY
 * @param pAge age
 * @param pGender gender
 * @param pDominant_hand dominant hand
 */
class ModelClassDemographics(val pId: Int,
                             val pAge: Int,
                             val pGender: String,
                             val pDominant_hand: String)