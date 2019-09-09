package com.example.admin.moogle.util.rx

import android.util.Log

object ErrorLog{
    fun logError(throwable: Throwable){
        Log.e("TAG_Error", "Error: $throwable")
    }
}