package com.ougi.networkapi.data.utils

import com.ougi.coreutils.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Call
import retrofit2.awaitResponse


object SafeApiCallUtils {

    private val badResponseCodes = arrayOf(400, 401, 404)
    suspend fun <T> safeApiCall(
        call: Call<T>,
        errorMessage: Any? = null,
        successMessage: Any? = null
    ): Result<T?> {
        return try {
            call.awaitResponse().let { response ->
                if (response.isSuccessful && !badResponseCodes.contains(response.code())) {
                    Result.Success(response.body(), successMessage)
                } else {
                    Result.Error(response.body() ?: errorMessage)
                }
            }
        } catch (e: Exception) {
            Result.Error<T>(errorMessage).also {
            }
        }
    }


    suspend fun <T> MutableStateFlow<Result<T?>>.safeApiCallStateFlow(
        call: Call<T>,
        errorMessage: Any? = null,
        loadingMessage: Any? = null,
        successMessage: Any? = null
    ) {
        value = Result.Loading(loadingMessage)
        value = safeApiCall(call, errorMessage, successMessage)
    }
}