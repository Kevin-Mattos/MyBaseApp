package com.example.mybaseapp.sync

import android.content.Intent
import android.os.Bundle
import com.example.mybaseapp.databinding.ActivityInitialSyncBinding
import com.example.mybaseapp.sync.getSMS.GetSmsCodeActivity
import com.example.mybaseapp.sync.retrieveUserNumber.RetrieveUserCredentialActivity
import com.example.mybaseapp.util.base.BaseActivity
import com.example.mybaseapp.util.extensions.startActivitySlide
const val IS_PHONE_KEY = "IS_PHONE_KEY"
class InitialSyncActivity: BaseActivity<ActivityInitialSyncBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setView()
    }

    override fun setupBinding() = ActivityInitialSyncBinding.inflate(layoutInflater)

    override fun setObservers() = Unit

    private fun setView() = binding.run {
        openGetPhone.setOnClickListener {
            val intent = Intent(this@InitialSyncActivity, RetrieveUserCredentialActivity::class.java)
            intent.putExtra(IS_PHONE_KEY, true)
            startActivitySlide(intent)
        }
        openGetSms.setOnClickListener {
            startActivitySlide(Intent(this@InitialSyncActivity, GetSmsCodeActivity::class.java))
        }

        openGetEmail.setOnClickListener {
            val intent = Intent(this@InitialSyncActivity, RetrieveUserCredentialActivity::class.java)
            intent.putExtra(IS_PHONE_KEY, false)
            startActivitySlide(intent)
        }
    }
}