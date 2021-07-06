package com.example.nullcmd.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.nullcmd.R
import com.example.nullcmd.databinding.ItemCategoryBinding
import com.example.nullcmd.models.DrinkModel
import com.example.nullcmd.models.FoodModel

class CategoryAdapter<T> : RecyclerView.Adapter<CategoryViewHolder<T>>() {

    interface Listener<T> {
        fun onItemClick(item: T)
    }

    var listener: Listener<T>? = null
    var itemSelectedPosition = -1

    var itemCategoryList: List<T> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder<T> {
        return CategoryViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder<T>, position: Int) {
        itemCategoryList[position].let {
            holder.bind(it, listener, itemSelectedPosition == position)
        }
    }

    override fun getItemCount(): Int {
        return itemCategoryList.size ?: 0
    }
}

class CategoryViewHolder<T>(private val itemCategoryBinding: ItemCategoryBinding) :
    RecyclerView.ViewHolder(itemCategoryBinding.root) {

    companion object {
        fun <T> create(parent: ViewGroup): CategoryViewHolder<T> {
            val inflater = LayoutInflater.from(parent.context)
            val binding: ItemCategoryBinding =
                DataBindingUtil.inflate(inflater, R.layout.item_category, parent, false)
            return CategoryViewHolder(binding)
        }
    }

    fun bind(model: T, listener: CategoryAdapter.Listener<T>?, isSelected: Boolean) {
        when (model) {
            is FoodModel -> {
                itemCategoryBinding.categoryName = model.strCategory
            }
            is DrinkModel -> {
                itemCategoryBinding.categoryName = model.strCategory
            }
        }

        itemCategoryBinding.color = if (!isSelected) R.color.teal_200 else R.color.purple_200

        itemCategoryBinding.root.setOnClickListener {
            listener?.onItemClick(model)
        }
    }
}