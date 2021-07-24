package com.example.mybaseapp.util.base.recyclerview

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<Item, Binding: ViewBinding>(protected val binding: Binding) :
    RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(item: Item): Any
}
