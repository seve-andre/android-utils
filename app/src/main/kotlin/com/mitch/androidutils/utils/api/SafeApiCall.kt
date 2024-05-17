package com.mitch.androidutils.utils.api

// import retrofit2.HttpException
// import timber.log.Timber
import java.io.IOException
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

suspend fun <T> safeApiCall(apiCall: suspend () -> T): ApiResult<T> {
    return try {
        ApiResult.Success(apiCall.invoke())
    } catch (e: Exception) {
        when (e) {
            is IOException -> {
                // Timber.e("IOException - ${e.message ?: "empty message"}")
                ApiResult.Error.NoConnectionError
            }

            // is HttpException -> ApiResult.Error.ApiError(code = e.code(), error = e.response()?.message())

            else -> ApiResult.Error.GenericError
        }
    }
}

sealed interface ApiResult<out T> {
    data class Success<out T>(val value: T) : ApiResult<T>
    sealed interface Error {
        data class ApiError(val code: Int, val error: String?) : ApiResult<Nothing>
        data object NoConnectionError : ApiResult<Nothing>
        data object GenericError : ApiResult<Nothing>
    }
}

@OptIn(ExperimentalContracts::class)
fun <T> ApiResult<T>.isSuccess(): Boolean {
    contract { returns(true) implies (this@isSuccess is ApiResult.Success) }
    return this is ApiResult.Success
}
