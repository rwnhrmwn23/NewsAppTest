package com.onedev.newsapptest.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.net.ssl.SSLPeerUnverifiedException

sealed class Resource<out T> {
    data object Loading: Resource<Nothing>()
    data class Success<T>(val data: T): Resource<T>()
    data class Error(val message: String): Resource<Nothing>()
}

fun <T> safeApiFlow(apiCall: suspend () -> T): Flow<Resource<T>> = flow {
    emit(Resource.Loading)
    try {
        val result = apiCall()
        emit(Resource.Success(result))
    } catch (e: SSLPeerUnverifiedException) {
        emit(Resource.Error("Unsecure connection detected (SSL pinning failed)."))
    } catch (e: Exception) {
        emit(Resource.Error("An error occurred: ${e.localizedMessage ?: "Unknown error"}"))
    }
}


