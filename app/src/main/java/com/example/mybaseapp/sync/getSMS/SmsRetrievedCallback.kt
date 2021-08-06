package com.example.mybaseapp.sync.getSMS

interface SmsRetrievedCallback {
    fun onRetrieveSmsSuccess(msg: String)
    fun onRetrieveSmsFailure()
}