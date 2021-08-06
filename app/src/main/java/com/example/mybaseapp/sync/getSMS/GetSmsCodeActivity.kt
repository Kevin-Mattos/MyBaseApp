package com.example.mybaseapp.sync.getSMS

import android.os.Bundle
import com.example.mybaseapp.R
import com.example.mybaseapp.databinding.ActivityGetSmsCodeBinding

class GetSmsCodeActivity : BaseSmsRetrieverActivity<ActivityGetSmsCodeBinding>(), SmsRetrievedCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setupBinding() = ActivityGetSmsCodeBinding.inflate(layoutInflater)

    override fun setObservers() = Unit

    override fun onRetrieveSmsSuccess(msg: String) {
        binding.smstv.text = msg
    }

    override fun onRetrieveSmsFailure() {
        binding.smstv.text = getString(R.string.on_retrieve_sms_error)
    }

    override fun setupSmsRetrievedListener() = this
}