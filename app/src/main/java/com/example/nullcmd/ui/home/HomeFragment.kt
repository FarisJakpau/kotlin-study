package com.example.nullcmd.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.nullcmd.R
import com.example.nullcmd.core.state.State
import com.example.nullcmd.models.ApiResponseModel
import com.example.nullcmd.models.DrinkModel
import com.example.nullcmd.models.FoodModel
import com.example.nullcmd.ui.custom.RandomMealPlaceholderView
import com.example.nullcmd.util.showToast
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.consume
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.coroutines.coroutineContext

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val viewModel: HomeViewModel by viewModel()
    private val foodCategoryAdapter = CategoryAdapter<FoodModel>()
    private val drinkCategoryAdapter = CategoryAdapter<DrinkModel>()
    private val selectedMealCategoryChannel = MutableLiveData<ApiResponseModel<Any>>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_food_category.adapter = foodCategoryAdapter
        rv_drink_category.adapter = drinkCategoryAdapter

        placeholderView.retryClick = {
            viewModel.refresh()
        }

        selectedMealCategoryChannel.observe(viewLifecycleOwner, {
            if (it.meals != null){
                println("selected food -> ${(it.meals as FoodModel).strCategory}")
            }
            if (it.drinks  != null)
                println("selected drink -> ${(it?.drinks as DrinkModel).strCategory}")
        })

        foodCategoryAdapter.listener = object : CategoryAdapter.Listener<FoodModel> {
            override fun onItemClick(item: FoodModel) {
                viewModel.foodCategory.value = item
                viewModel.getFoodFromCategory()
                updateAdapter(item, foodCategoryAdapter)
            }
        }

        drinkCategoryAdapter.listener = object : CategoryAdapter.Listener<DrinkModel> {
            override fun onItemClick(item: DrinkModel) {
                viewModel.drinkCategory.value = item
                viewModel.getDrinkFromCategory()
                updateAdapter(item, drinkCategoryAdapter)
            }
        }

        viewModel.state.observe(viewLifecycleOwner, Observer { state ->

            placeholderView.bindState(state)

            when (state) {
                is State.Success -> {
                    val data = state.data as ApiResponseModel<*>? ?: return@Observer
                    data.let {
                        val foodCategory = it.meals as List<FoodModel>
                        val drinkCategory = it.drinks as List<DrinkModel>

                        foodCategoryAdapter.itemCategoryList = foodCategory
                        drinkCategoryAdapter.itemCategoryList = drinkCategory
                    }

                }
                is State.Failure -> {
                    showToast(requireContext(), "Error while fetching data : error(${state.error})")
                }
            }
        })

        viewModel.randomFood.observe(viewLifecycleOwner, { state ->
            food_placeholder.bindState(state, RandomMealPlaceholderView.MealType.FOOD)
            ll_meal_selection.isVisible = true

//            when(state) {
//                is State.Failure -> {
//                    showToast(requireContext(), "Failed")
//                }
//                State.Loading -> {
//                    showToast(requireContext(), "Loading")
//                }
//                is State.Success -> {
//                    showToast(requireContext(), "Success")
//                }
//            }
        })

        viewModel.randomDrink.observe(viewLifecycleOwner, {
            drink_placeholder.bindState(it, RandomMealPlaceholderView.MealType.DRINK)
            ll_meal_selection.isVisible = true
        })
    }

    private fun <T> updateAdapter(item: T, adapter: CategoryAdapter<T>) {
        lifecycleScope.launch {
            val position = adapter.itemCategoryList.indexOf(item)
            if (position > -1) {
                adapter.notifyItemChanged(adapter.itemSelectedPosition)
                adapter.itemSelectedPosition = position
                adapter.notifyItemChanged(adapter.itemSelectedPosition)
            }
        }
    }
}