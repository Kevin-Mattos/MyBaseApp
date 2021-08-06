package com.example.mybaseapp.sync

import android.app.PendingIntent
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.viewbinding.ViewBinding
import com.example.mybaseapp.util.base.BaseActivity

abstract class BaseActivityForResult<Binding : ViewBinding> : BaseActivity<Binding>() {

    private lateinit var launcherForResult: ActivityResultLauncher<Intent>
    private lateinit var launcherIntentSender: ActivityResultLauncher<IntentSenderRequest>

    abstract fun onResult(result: ActivityResult)

    protected fun setupStartActivityForResultLauncher() {
        launcherForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), ::onResult)
    }

    protected fun setupStartIntentSenderForResultLauncher() {
        launcherIntentSender = registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) {
            onResult(it)
        }
    }

    protected fun launchIntent(intent: Intent) {
        launcherForResult.launch(intent)
    }

    protected fun launchIntentSender(intent: PendingIntent) {
        val intentSenderRequest = IntentSenderRequest.Builder(intent).build()
        launcherIntentSender.launch(intentSenderRequest)
    }
}