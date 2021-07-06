package com.example.nullcmd.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.nullcmd.R
import com.example.nullcmd.core.state.State
import com.example.nullcmd.models.ApiResponseModel
import com.example.nullcmd.models.DrinkModel
import com.example.nullcmd.models.FoodModel
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment: Fragment(R.layout.fragment_home) {
    private val viewModel: HomeViewModel by viewModel()
    private val foodCategoryAdapter = CategoryAdapter<FoodModel>()
    private val drinkCategoryAdapter = CategoryAdapter<DrinkModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        placeholderView.retryClick = {
            viewModel.refresh()
        }

        rv_food_category.adapter = foodCategoryAdapter
        rv_drink_category.adapter = drinkCategoryAdapter

        viewModel.state.observe(viewLifecycleOwner, Observer { state ->

            placeholderView.bindState(state)

            when (state) {
                is State.Success -> {
                    val data = state.data as ApiResponseModel<List<Any>>? ?: return@Observer
                    data.let {
                        val foodCategory = it.meals as List<FoodModel>
                        val drinkCategory = it.drinks as List<DrinkModel>

                        foodCategoryAdapter.itemCategoryList = foodCategory
                        drinkCategoryAdapter.itemCategoryList = drinkCategory
                    }

                }
                is State.Failure -> {
                    //showToast("Error while fetching data : error(${state.error})")
                }
            }
        })
    }
}