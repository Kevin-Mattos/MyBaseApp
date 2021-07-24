package com.example.mybaseapp.util.base.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.mybaseapp.databinding.RecyclerExampleItemBinding

class ExampleAdapter(context: Context): BaseRecyclerViewAdapter<Int, RecyclerExampleItemBinding>(context) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Int, RecyclerExampleItemBinding> {
        return Holder(RecyclerExampleItemBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    inner class Holder(a: RecyclerExampleItemBinding): BaseViewHolder<Int, RecyclerExampleItemBinding>(a) {
        override fun bind(item: Int) = binding.run {
            tv.text = item.toString()
        }
    }
}