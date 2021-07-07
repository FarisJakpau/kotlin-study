package com.example.nullcmd.service.primary

import com.example.nullcmd.api.DrinkApi
import com.example.nullcmd.service.NetworkRequestManager

class DrinkService(
    private val drinkApi: DrinkApi,
    private val networkRequestManager: NetworkRequestManager
) {

    suspend fun fetchDrinkCategory() = networkRequestManager.apiRequest {
        drinkApi.getDrinkCategory()
    }

    suspend fun getDrinkFromCategory(category: String) = networkRequestManager.apiRequest {
        drinkApi.getDrinkFromCategory(category)
    }
}