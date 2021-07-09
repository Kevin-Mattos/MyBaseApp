package com.example.mybaseapp.util.extensions

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.annotation.AnimRes
import com.example.mybaseapp.R

fun Activity.displayToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Activity.startActivitySlide(intent: Intent, requestCode: Int? = null) {
    startActivityTransition(intent, R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim)
}

fun Activity.startActivityTransition(intent: Intent, @AnimRes idAninIn :Int, @AnimRes idAninOut: Int, requestCode: Int? = null) {
    if(requestCode == null)
        startActivity(intent)
    else
        startActivityForResult(intent, requestCode)

    overridePendingTransition(idAninIn, idAninOut)
}