package com.example.nullcmd.service

import retrofit2.Response
import com.example.nullcmd.core.Result

class NetworkRequestManager {

    suspend inline fun <reified T: Any> apiRequest(crossinline apiCall: suspend () -> Response<T>): Result<T> {
        val response = apiCall.invoke()

        return try {
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) Result.Success(body)
                else Result.Failure(Throwable("Empty response"))
            } else {
                Result.Failure(Throwable(response.errorBody().toString()))
            }
        } catch (exception: Exception) {
            Result.Failure(Throwable(response.errorBody().toString()))
        }
    }
}