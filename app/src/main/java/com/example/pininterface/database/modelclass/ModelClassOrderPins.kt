package com.example.pininterface.database.modelclass

/**
 * Model Class for order_pins
 * Entry for order_pins table DB
 * @param pId the id of the row - PRIMARY KEY
 * @param pOrderPins the order of pins as string. Not atomar but not needed for this DB
 */
class ModelClassOrderPins (val pId: Int = -1, val pOrderPins: String)