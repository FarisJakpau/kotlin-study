package com.example.nullcmd.ui.home

import android.util.Log
import com.example.nullcmd.core.Result
import com.example.nullcmd.core.state.DataLoadingLiveData
import com.example.nullcmd.core.state.DataLoadingViewModel
import com.example.nullcmd.core.state.State
import com.example.nullcmd.models.ApiResponseModel
import com.example.nullcmd.service.primary.DrinkService
import com.example.nullcmd.service.primary.FoodService
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class HomeViewModel(
    private val foodService: FoodService,
    private val drinkService: DrinkService
) :
    DataLoadingViewModel<Any>() {

    init {
        fetchCategories()

        manualRefresh = {
            fetchCategories()
        }
    }

    //val categories: DataLoadingLiveData<ApiResponseModel<List<Any>>> = DataLoadingLiveData()

    private fun fetchCategories() {
        //categories.value = State.Loading

        scope.launch {
            flowOf(foodService.fetchFoodCategory())
                .combine(flowOf(drinkService.fetchDrinkCategory())) { food, drink ->
                    if (food is Result.Success && drink is Result.Success) {
                        val result =
                            ApiResponseModel(meals = food.data.meals, drinks = drink.data.drinks)
                        //categories.loadSuccess(result)
                        Result.Success(result)
                    } else {
                        //categories.loadFailure(Throwable("error"))
                        Result.Failure(Throwable("Error"))
                    }
                }.collect {
                    result = it
                }
        }
    }

    override suspend fun load(): Result<Any>? {
        return null
    }
}