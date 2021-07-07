package com.example.nullcmd.api

import com.example.nullcmd.models.ApiResponseModel
import com.example.nullcmd.models.DrinkModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DrinkApi {

    @GET("list.php?c=list")
    suspend fun getDrinkCategory(): Response<ApiResponseModel<List<DrinkModel>>>

    @GET("filter.php?")
    suspend fun getDrinkFromCategory(@Query("c") category: String): Response<ApiResponseModel<List<DrinkModel>>>
}