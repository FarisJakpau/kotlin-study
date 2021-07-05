package com.example.nullcmd.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.nullcmd.R
import com.example.nullcmd.core.state.State
import com.example.nullcmd.models.ApiResponseModel
import com.example.nullcmd.models.DrinkModel
import com.example.nullcmd.models.FoodModel
import com.example.nullcmd.ui.home.CategoryAdapter
import com.example.nullcmd.ui.home.HomeViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModel()
    private val foodCategoryAdapter = CategoryAdapter<FoodModel>()
    private val drinkCategoryAdapter = CategoryAdapter<DrinkModel>()

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        placeholderView.retryClick = {
            viewModel.refresh()
        }

        rv_food_category.adapter = foodCategoryAdapter
        rv_drink_category.adapter = drinkCategoryAdapter

//        viewModel.categories.observe(this, Observer {  state ->
//            placeholderView.bindState(state)
//
//            when (state) {
//                is State.Success -> {
//                    val data = state.data as ApiResponseModel<List<Any>>? ?: return@Observer
//                    data.let {
//                        val foodCategory = it.meals as List<FoodModel>
//                        val drinkCategory = it.drinks as List<DrinkModel>
//
//                        foodCategoryAdapter.itemCategoryList = foodCategory
//                        drinkCategoryAdapter.itemCategoryList = drinkCategory
//                    }
//
//                }
//                is State.Failure -> {
//                    showToast("Error while fetching data : error(${state.error})")
//                }
//            }
//        })

        viewModel.state.observe(this, Observer { state ->

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
                    showToast("Error while fetching data : error(${state.error})")
                }
            }
        })
    }
}