package com.example.mybaseapp.util.extensions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart

suspend fun <T> Flow<T>.onCollect(
    onCollect: ((T) -> Unit)? = null,
    onError: ((Throwable) -> Unit)? = null,
    onLoading: ((Boolean) -> Unit)? = null
) =
    onStart { onLoading?.invoke(true) }
        .catch { throwable ->
            onLoading?.invoke(false)
            onError?.invoke(throwable)
        }
        .collect {
            onLoading?.invoke(false)
            onCollect?.invoke(it)
        }