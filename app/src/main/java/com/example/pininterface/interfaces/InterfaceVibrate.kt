package com.example.pininterface.interfaces

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

interface InterfaceVibrate {

    fun vibrate(pContext: Context, pDuration: Long) {

        val apiVersion = Build.VERSION.SDK_INT
        val vibrator = pContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        if (apiVersion >= Build.VERSION_CODES.S) {
            vibrator.vibrate(VibrationEffect.createOneShot(pDuration, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(pDuration)
        }
    }
}