package com.example.mybaseapp.example.mvvmActivity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mybaseapp.MainActivity
import com.example.mybaseapp.databinding.ActivityMainBinding

class MyExampleActivity: AppCompatActivity() {

    private val viewModel by viewModels<ExampleViewModel>()
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        setupObservers()
        viewModel.teste()
    }

    private fun setupObservers() {
        viewModel.cepLiveData.observe(this, {
            binding.tv.text = it
        })
    }

}