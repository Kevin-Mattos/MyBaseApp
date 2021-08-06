package com.example.mybaseapp.sync.getSMS.fragment

import android.app.PendingIntent
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.viewbinding.ViewBinding
import com.example.mybaseapp.util.base.BaseFragment

abstract class BaseFragmentForResult<Binding: ViewBinding> : BaseFragment<Binding>() {

    val launcherForResult: ActivityResultLauncher<Intent>
            by lazy { setupStartActivityForResultLauncher() }
    val launcherIntentSender: ActivityResultLauncher<IntentSenderRequest>
            by lazy { setupStartIntentSenderForResultLauncher() }

    abstract fun onResult(result: ActivityResult)

    fun setupStartActivityForResultLauncher(): ActivityResultLauncher<Intent> {
        return registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            onResult(result)
        }
    }

    fun setupStartIntentSenderForResultLauncher(): ActivityResultLauncher<IntentSenderRequest> {
        return registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            onResult(result)
        }
    }

    fun launchIntent(intent: Intent) {
        launcherForResult.launch(intent)
    }

    fun launchIntentSender(intent: PendingIntent) {
        val intentSenderRequest = IntentSenderRequest.Builder(intent).build()
        launcherIntentSender.launch(intentSenderRequest)
    }
}