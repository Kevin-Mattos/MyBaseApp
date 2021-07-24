package com.example.mybaseapp.util.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<Binding : ViewBinding> : AppCompatActivity() {

    protected val binding: Binding by lazy { setupBinding() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setObservers()
    }

    abstract fun setupBinding(): Binding
    abstract fun setObservers(): Any
}