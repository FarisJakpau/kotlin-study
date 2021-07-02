package com.example.nullcmd.di

import com.example.nullcmd.api.DrinkApi
import com.example.nullcmd.api.FoodApi
import com.example.nullcmd.service.EmptyBodyConverterFactory
import com.example.nullcmd.service.NetworkRequestManager
import com.example.nullcmd.service.primary.DrinkService
import com.example.nullcmd.service.primary.FoodService
import com.example.nullcmd.ui.home.HomeViewModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val RETROFIT_FOOD_API = "RETROFIT_FOOD_API"
private const val RETROFIT_DRINK_API = "RETROFIT_DRINK_API"

val networkModule = module {

    fun provideRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .build()
            )
            .addConverterFactory(EmptyBodyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    fun provideNetworkRequestManager(): NetworkRequestManager {
        return NetworkRequestManager()
    }

    single(named(RETROFIT_FOOD_API)) {
        val baseUrl = "https://www.themealdb.com/api/json/v1/1/"
        provideRetrofit(baseUrl)
    }

    single(named(RETROFIT_DRINK_API)) {
        val baseUrl = "https://www.thecocktaildb.com/api/json/v1/1/"
        provideRetrofit(baseUrl)
    }

    single { provideNetworkRequestManager() }
}

val apiModule = module {

    fun provideFoodApi(retrofit: Retrofit): FoodApi {
        return retrofit.create(FoodApi::class.java)
    }

    fun provideDrinkApi(retrofit: Retrofit): DrinkApi {
        return retrofit.create(DrinkApi::class.java)
    }

    single { provideFoodApi(get(named(RETROFIT_FOOD_API))) }
    single { provideDrinkApi(get(named(RETROFIT_DRINK_API))) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get()) }
}

val serviceModule = module {
    single { FoodService(get(), get()) }
    single { DrinkService(get(), get()) }
}