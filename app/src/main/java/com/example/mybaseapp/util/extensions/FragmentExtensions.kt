package com.example.mybaseapp.util.extensions

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.displayToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), message, duration).show()
}