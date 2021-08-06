package com.example.mybaseapp.sync.getSMS

import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.viewbinding.ViewBinding
import com.example.mybaseapp.sync.BaseActivityForResult
import com.google.android.gms.auth.api.phone.SmsRetriever

abstract class BaseSmsRetrieverActivity<Binding : ViewBinding> : BaseActivityForResult<Binding>() {

    private val smsBroadcastReceiver by lazy { setupSmsBroadcastListener() }

    private val smsRetrievedListener by lazy { setupSmsRetrievedListener() }

    abstract fun setupSmsRetrievedListener(): SmsRetrievedCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupStartActivityForResultLauncher()
        initSmsRetriever()
    }

    override fun onResult(result: ActivityResult) {
        val data = result.data
        if ((result.resultCode == Activity.RESULT_OK)) {
            val message = data?.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
            message?.let {
                smsRetrievedListener.onRetrieveSmsSuccess(message)
            } ?: run {
                smsRetrievedListener.onRetrieveSmsFailure()
            }
        }
    }

//    override fun onActivityResult(
//        requestCode: Int,
//        resultCode: Int,
//        data: Intent?
//    ) {
//        super.onActivityResult(requestCode, resultCode, data)
//        when (requestCode) {
//            SMS_REQUEST_CODE -> {
//                if ((resultCode == Activity.RESULT_OK) && (data != null)) {
//                    val message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
//                    message?.let {
//                        smsRetrievedListener.onRetrieveSmsSuccess(message)
//                    } ?: run {
//                        smsRetrievedListener.onRetrieveSmsFailure()
//                    }
//                }
//            }
//        }
//    }

    fun initSmsRetriever() {
        SmsRetriever.getClient(applicationContext).also {
            it.startSmsUserConsent(null)//"6505551212") // "ou numero que vai enviar o codigo"
                .addOnSuccessListener {
                    val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
                    this.registerReceiver(smsBroadcastReceiver, intentFilter)
                }
                .addOnFailureListener {

                }
        }
    }

//    <#> FOGAS: ( 486 ) e o seu codigo de validacao. Insira-o no campo indicado no App Granel. Td2ONbxZNs/

    fun setupSmsBroadcastListener(): SMSBroadcastReceiver {
        val smsBroadcastReceiver = SMSBroadcastReceiver()
        smsBroadcastReceiver.listener = object : SMSBroadcastReceiver.SmsListener {
            override fun onSuccess(intent: Intent) {
//                    startActivityForResult(
//                        intent,
//                        SMS_REQUEST_CODE
//                    )
                launchIntent(intent)
            }

            override fun onError() {
                smsRetrievedListener.onRetrieveSmsFailure()
            }
        }
        return smsBroadcastReceiver
    }

    companion object {
        val SMS_REQUEST_CODE: Int = 100
    }
}