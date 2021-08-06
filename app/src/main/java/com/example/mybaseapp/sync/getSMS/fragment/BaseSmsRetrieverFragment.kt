package com.example.mybaseapp.sync.getSMS.fragment

import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.viewbinding.ViewBinding
import com.example.mybaseapp.sync.getSMS.SmsRetrievedCallback
import com.example.mybaseapp.sync.getSMS.SMSBroadcastReceiver
import com.example.mybaseapp.util.base.BaseFragment
import com.google.android.gms.auth.api.phone.SmsRetriever

abstract class BaseSmsRetrieverFragment<Binding : ViewBinding> : BaseFragment<Binding>(),
    SmsRetrievedCallback {

    private val SMSBroadcastReceiver by lazy { setupSmsBroadcastListener() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSmsRetriever()
    }

    fun initSmsRetriever() {
        SmsRetriever.getClient(requireActivity()).also {
            it.startSmsRetriever()
            it.startSmsUserConsent(null)
                .addOnSuccessListener {
                    val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
                    requireActivity().registerReceiver(SMSBroadcastReceiver, intentFilter)
                }
                .addOnFailureListener {

                }
        }
    }

    fun setupSmsBroadcastListener(): SMSBroadcastReceiver {
        val SMSBroadcastReceiver = SMSBroadcastReceiver()
        SMSBroadcastReceiver?.listener = object : SMSBroadcastReceiver.SmsListener {
            override fun onSuccess(intent: Intent) {
                intent?.let { context ->
                    startActivityForResult(
                        context,
                        SMS_REQUEST_CODE
                    )
                }
            }

            override fun onError() {
                onError()
            }
        }
        return SMSBroadcastReceiver
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            SMS_REQUEST_CODE -> {
                if ((resultCode == Activity.RESULT_OK) && (data != null)) {
                    val message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
                    message?.let {
                        //TODO treat received message
                        onRetrieveSmsSuccess(message)
                    } ?: run {
                        onRetrieveSmsFailure()
                    }
                }
            }
        }
    }

    companion object {
        val SMS_REQUEST_CODE: Int = 100
    }

}