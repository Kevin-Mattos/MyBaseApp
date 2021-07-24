package com.example.mybaseapp.util.base.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.mybaseapp.databinding.ActivityMainBinding
import com.example.mybaseapp.databinding.RecyclerExampleItemBinding

abstract class BaseRecyclerViewAdapter<Item, Binding: ViewBinding>(
    protected val context: Context,
    itens: MutableList<Item> = mutableListOf()
) : RecyclerView.Adapter<BaseViewHolder<Item, Binding>>() {

    var list: MutableList<Item> = itens
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    fun addItems(itens: List<Item>) {
        list.addAll(itens)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Item, Binding>, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = list.size
}