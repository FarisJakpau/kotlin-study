package com.example.nullcmd.service.primary

import com.example.nullcmd.api.FoodApi
import com.example.nullcmd.core.Result
import com.example.nullcmd.models.ApiResponseModel
import com.example.nullcmd.models.FoodModel
import com.example.nullcmd.service.NetworkRequestManager

class FoodService(
    private val foodApi: FoodApi,
    private val networkRequestManager: NetworkRequestManager) {

    suspend fun fetchFoodCategory(): Result<ApiResponseModel<List<FoodModel>>> {
        return networkRequestManager.apiRequest {
            foodApi.getMealCategory()
        }
    }

    suspend fun getFoodFromCategory(category: String) : Result<ApiResponseModel<List<FoodModel>>> {
        return networkRequestManager.apiRequest {
            foodApi.getFoodFromCategory(category)
        }
    }
}