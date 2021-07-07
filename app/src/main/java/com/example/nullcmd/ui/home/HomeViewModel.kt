package com.example.nullcmd.ui.home

import com.example.nullcmd.core.Result
import com.example.nullcmd.core.state.StateLiveData
import com.example.nullcmd.core.state.DataLoadingViewModel
import com.example.nullcmd.models.ApiResponseModel
import com.example.nullcmd.models.DrinkModel
import com.example.nullcmd.models.FoodModel
import com.example.nullcmd.service.primary.DrinkService
import com.example.nullcmd.service.primary.FoodService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val foodService: FoodService,
    private val drinkService: DrinkService
) :
    DataLoadingViewModel<Any>() {

    val randomFood: StateLiveData<FoodModel> =
        StateLiveData()
    val randomDrink: StateLiveData<DrinkModel> =
        StateLiveData()

    val foodCategory: MutableStateFlow<FoodModel> = MutableStateFlow(FoodModel())
    val drinkCategory: MutableStateFlow<DrinkModel> = MutableStateFlow(DrinkModel())

    init {
        fetchCategories()

        manualRefresh = {
            fetchCategories()
        }
    }

    private fun fetchCategories() {
        scope.launch {
            flowOf(foodService.fetchFoodCategory())
                .combine(flowOf(drinkService.fetchDrinkCategory())) { food, drink ->
                    if (food is Result.Success && drink is Result.Success) {
                        val result =
                            ApiResponseModel(meals = food.data.meals, drinks = drink.data.drinks)
                        Result.Success(result)
                    } else {
                        Result.Failure(Throwable("Error"))
                    }
                }.collect {
                    result = it
                }
        }
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    fun getFoodFromCategory() {
        randomFood.loading()

        scope.launch {
            foodCategory.debounce(500)
                .distinctUntilChanged()
                .flatMapLatest {
                    flowOf(foodService.getFoodFromCategory(it.strCategory.toString()))
                }.collect { result ->
                    when (result) {
                        is Result.Failure -> {
                            randomFood.loadFailure(Throwable("Error fetch food"))
                            FoodModel()
                        }
                        is Result.Success -> {
                            result.data.meals?.random()?.let {
                                randomFood.loadSuccess(it)
                            }
                        }
                    }
                }
        }
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    fun getDrinkFromCategory() {
        randomDrink.loading()

        scope.launch {
            drinkCategory.debounce(500)
                .distinctUntilChanged()
                .flatMapLatest {
                    flowOf(drinkService.getDrinkFromCategory(it.strCategory.toString()))
                }.collect { result ->
                    when (result) {
                        is Result.Failure -> {
                            randomDrink.loadFailure(Throwable("Error fetch drink"))
                            DrinkModel()
                        }
                        is Result.Success -> {
                            result.data.drinks?.random()?.let {
                                randomDrink.loadSuccess(it)
                            }
                        }
                    }
                }
        }
    }

    override suspend fun load(): Result<Any>? {
        return null
    }
}