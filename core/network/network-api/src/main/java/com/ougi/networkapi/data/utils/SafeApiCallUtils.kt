package com.ougi.networkapi.data.utils

import com.ougi.coreutils.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Call
import retrofit2.awaitResponse


object SafeApiCallUtils {
    suspend fun <T> safeApiCall(
        call: Call<T>,
        errorMessage: String? = null,
        successMessage: String? = null
    ): Result<T?> {
        val errorResult = Result.Error<T>(errorMessage)
        return try {
            call.awaitResponse().let { response ->
                if (response.isSuccessful) Result.Success(response.body(), successMessage)
                else errorResult
            }
        } catch (e: Exception) {
            errorResult
        }
    }

    suspend fun <T> MutableStateFlow<Result<T?>>.safeApiCallStateFlow(
        call: Call<T>,
        errorMessage: String? = null,
        loadingMessage: String? = null,
        successMessage: String? = null
    ) {
        value = Result.Loading(loadingMessage)
        value = safeApiCall(call, errorMessage, successMessage)
    }
}