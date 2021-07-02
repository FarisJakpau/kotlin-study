package com.example.nullcmd.api

import com.example.nullcmd.models.ApiResponseModel
import com.example.nullcmd.models.DrinkModel
import retrofit2.Response
import retrofit2.http.GET

interface DrinkApi {

    @GET("list.php?c=list")
    suspend fun getDrinkCategory(): Response<ApiResponseModel<List<DrinkModel>>>

}