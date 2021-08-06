package com.example.mybaseapp.sync.getSMS

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status

class SMSBroadcastReceiver : BroadcastReceiver() {

    interface SmsListener {
        fun onSuccess(intent: Intent)
        fun onError()
    }

    var listener: SmsListener? = null

    override fun onReceive(
        context: Context?,
        intent: Intent?
    ) {
        if (intent?.action == SmsRetriever.SMS_RETRIEVED_ACTION) {
            val extras = intent.extras
            val smsRetrieverStatus = extras?.get(SmsRetriever.EXTRA_STATUS) as Status
            when (smsRetrieverStatus.statusCode) {
                CommonStatusCodes.SUCCESS -> {
                    val smsIntentExtra: Intent? =
                        extras.getParcelable<Intent>(SmsRetriever.EXTRA_CONSENT_INTENT)
                    smsIntentExtra?.let {
                        listener?.onSuccess(it)
                    } ?: run {
                        listener?.onError()
                    }
                }
                CommonStatusCodes.TIMEOUT -> {
                    //Default timeout is 5 minutes
                    listener?.onError()
                }
            }
        }
    }
}