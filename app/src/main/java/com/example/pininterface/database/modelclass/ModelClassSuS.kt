package com.example.pininterface.database.modelclass

/**
 * Model Class for System Usability Score (SUS)
 * Entry for the sus table DB
 * PRIMARY KEY: pID + pInterfaceTyp
 * @param pId id
 * @param pInterfaceTyp interface typ
 * @param pQ0 answer value sus question 0
 * @param pQ1 answer value sus question 1
 * @param pQ2 answer value sus question 2
 * @param pQ3 answer value sus question 3
 * @param pQ4 answer value sus question 4
 * @param pQ5 answer value sus question 5
 * @param pQ6 answer value sus question 6
 * @param pQ7 answer value sus question 7
 * @param pQ8 answer value sus question 8
 * @param pQ9 answer value sus question 9
 */
class ModelClassSuS(val pId: Int,
                    val pInterfaceTyp: String,
                    val pQ0: Int,
                    val pQ1: Int,
                    val pQ2: Int,
                    val pQ3: Int,
                    val pQ4: Int,
                    val pQ5: Int,
                    val pQ6: Int,
                    val pQ7: Int,
                    val pQ8: Int,
                    val pQ9: Int)