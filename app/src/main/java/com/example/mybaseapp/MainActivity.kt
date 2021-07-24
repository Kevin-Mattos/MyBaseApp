package com.example.mybaseapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.mybaseapp.databinding.ActivityMainBinding
import com.example.mybaseapp.test.Intent
import com.example.mybaseapp.test.State
import com.example.mybaseapp.test.mVM
import com.example.mybaseapp.util.base.BaseActivity
import com.example.mybaseapp.util.base.recyclerview.ExampleAdapter
import kotlinx.coroutines.flow.collect

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel by viewModels<mVM>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.handle(Intent.GetCep)
        val adapter = ExampleAdapter(this)
        binding.recyclerViewExample.adapter = adapter
        adapter.list = mutableListOf(1,2,3,4,5,6,7,9,8,565465498)
    }

    override fun setupBinding(): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)

    override fun setObservers() = lifecycleScope.launchWhenStarted  {
        viewModel.state.collect {
            when (it) {
                is State.IsLoading -> binding.tv.text = it.loading.toString()
                is State.ShowCep -> binding.tv.text = it.cep.toString()
                is State.ShowError -> binding.tv.text = it.error.toString()
            }
        }
    }
}