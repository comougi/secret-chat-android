package com.ougi.networkapi.data.utils

import android.util.Log
import com.ougi.coreutils.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.awaitResponse


object SafeApiCallUtils {

    val badResponseCodes = arrayOf(400, 401, 404)

    @Suppress("BlockingMethodInNonBlockingContext")
    suspend inline fun <reified T> safeApiCall(
        call: Call<T>,
        errorMessage: Any? = null,
        successMessage: Any? = null,
    ): Result<T?> {
        return try {
            call.awaitResponse().let { response ->
                val body = response.body()
                if (response.isSuccessful && !badResponseCodes.contains(response.code())) {
                    Log.d("DATA", "HERE1")
                    Result.Success(body, successMessage)
                } else {
                    Log.d("DATA", "HERE2")
                    val errorBody = withContext(Dispatchers.IO) { response.errorBody()?.string() }
                    Log.d("DATA", errorBody ?: "errorBody")
                    Log.d("DATA", response.message())
                    val message =
                        if (errorBody.isNullOrBlank() || errorBody.isNullOrEmpty()) errorMessage else errorBody
                    Result.Error(message)
                }
            }
        } catch (e: Exception) {
            Log.d("DATA", "HERE3")
            Log.d("DATA", e.stackTraceToString())
            Result.Error(errorMessage)
        }
    }


    suspend inline fun <reified T> MutableStateFlow<Result<T?>>.safeApiCallStateFlow(
        call: Call<T>,
        errorMessage: Any? = null,
        loadingMessage: Any? = null,
        successMessage: Any? = null
    ) {
        value = Result.Loading(loadingMessage)
        value = safeApiCall(call, errorMessage, successMessage)
    }
}