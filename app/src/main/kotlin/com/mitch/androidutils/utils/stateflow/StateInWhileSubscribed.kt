package com.mitch.androidutils.utils.stateflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

context(ViewModel)
fun <T> Flow<T>.stateInWhileSubscribed(initialValue: T): StateFlow<T> {
    return stateIn(
        scope = viewModelScope,
        // 5 seconds as described here https://developer.android.com/topic/performance/vitals/anr
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = initialValue,
    )
}
