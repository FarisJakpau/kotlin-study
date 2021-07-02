package com.example.nullcmd.api

import com.example.nullcmd.models.ApiResponseModel
import com.example.nullcmd.models.FoodModel
import retrofit2.Response
import retrofit2.http.GET

interface FoodApi {

    @GET("list.php?c=list")
    suspend fun getMealCategory(): Response<ApiResponseModel<List<FoodModel>>>

}