package com.example.mybaseapp.sync.retrieveUserNumber

import android.app.Activity
import android.app.PendingIntent
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.mybaseapp.R
import com.example.mybaseapp.databinding.ActivityRetrieveUserNumberBinding
import com.example.mybaseapp.sync.BaseActivityForResult
import com.google.android.gms.auth.api.credentials.Credential
import com.google.android.gms.auth.api.credentials.Credentials
import com.google.android.gms.auth.api.credentials.HintRequest
import kotlinx.coroutines.flow.collect


class RetrieveUserCredentialActivity : BaseActivityForResult<ActivityRetrieveUserNumberBinding>() {

    val viewModel by viewModels<RetrieveUserCredentialViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupStartIntentSenderForResultLauncher()
        setListenter()
        viewModel.handle(RetrieveCredentialIntent.InitArgs(intent.extras))
    }

    override fun setupBinding() = ActivityRetrieveUserNumberBinding.inflate(layoutInflater)

    override fun setObservers() = lifecycleScope.launchWhenStarted  {
        viewModel.state.collect {
            when (it) {
                is RetrieveCredentialState.RetrieveUserCredential -> requestCredential(it.isPhoneCredential)
                is RetrieveCredentialState.ShowUserCredential -> showCredential(it.phone)
                is RetrieveCredentialState.SetView -> setView(it.isPhoneCredential)
            }
        }
    }

    override fun onResult(result: ActivityResult) {
        if(result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val credential: Credential? = data
                ?.getParcelableExtra(Credential.EXTRA_KEY)
            credential?.let {
                viewModel.handle(
                    RetrieveCredentialIntent.UserCredential(
                        it.id
                    )
                )
            }
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if(requestCode == PHONE_HINT_CODE){
//            val credential: Credential? = data?.getParcelableExtra(Credential.EXTRA_KEY)
//            credential?.let {
//                viewModel.handle(RetrieveCredentialIntent.UserCredential(it.id))
//            }
//        }
//    }

    private fun showCredential(credential: String) = binding.run {
        userCredential.text = credential
    }

    private fun requestCredential(phoneCredential: Boolean) {
        val hintRequest = HintRequest.Builder()
            .setPhoneNumberIdentifierSupported(phoneCredential)
            .setEmailAddressIdentifierSupported(!phoneCredential)
            .build()

        val intent: PendingIntent = Credentials.getClient(this)
            .getHintPickerIntent(hintRequest)

//        startIntentSenderForResult(
//            intent.intentSender,
//            PHONE_HINT_CODE, null, 0, 0, 0
//        )
       launchIntentSender(intent)
    }

    private fun setView(phoneCredential: Boolean) = binding.run {
        getPhoneNumber.text = if(phoneCredential) getString(R.string.credentials_get_phones) else getString(R.string.credencials_get_email)
    }

    private fun setListenter() = binding.run {
        getPhoneNumber.setOnClickListener {
            viewModel.handle(RetrieveCredentialIntent.GetCredentialClick)
        }
    }

    companion object {
        val PHONE_HINT_CODE = 456
    }
}