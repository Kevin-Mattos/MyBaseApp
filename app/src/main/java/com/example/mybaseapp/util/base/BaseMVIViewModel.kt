package com.example.mybaseapp.util.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseMVIViewModel<Intent, State> : ViewModel() {

    protected val disposable = CompositeDisposable()//RXJava

    abstract val initialState: State
    protected val _state by lazy { MutableStateFlow(initialState) }
    val state = _state.asStateFlow()

    abstract fun handle(intent: Intent)

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}