package com.example.nullcmd.models

data class ApiResponseModel<T>(
    val meals: T? = null,
    val drinks: T? = null
)