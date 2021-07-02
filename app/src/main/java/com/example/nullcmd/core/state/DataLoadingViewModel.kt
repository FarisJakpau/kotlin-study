package com.example.nullcmd.core.state

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nullcmd.core.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class DataLoadingViewModel<T>: ViewModel() {

    var state = MutableLiveData<State<T>>(State.Loading)

    private var isManualTransaction = true

    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    protected val scope = CoroutineScope(coroutineContext)

    abstract suspend fun load(): Result<T>?

    protected var manualRefresh:(() -> Unit)? = null

    var result: Result<T>? = null
        set(value) {
            field = value
            initTransition()
        }

    private fun initTransition() {
        result?.let {
            when(it) {
                is Result.Success -> {
                    transition(Event.LoadSuccess(it.data))
                }

                is Result.Failure -> {
                    transition(Event.LoadFailure(it.error))
                }
            }
        }
    }

    fun proceedToLoad() = viewModelScope.launch {
        isManualTransaction = false
        result = load()
    }

    fun refresh() {
        transition(Event.Refresh)
        if (!isManualTransaction) proceedToLoad() else manualRefresh?.invoke()
    }

    private fun transition(event: Event<T>) {
        when {
            state.value is State.Loading && event is Event.LoadSuccess -> {
                state.postValue(State.Success(event.data))
            }

            state.value is State.Loading && event is Event.LoadFailure -> {
                state.postValue(State.Failure(event.error))
            }

            state.value is State.Success && event is Event.Refresh -> {
                state.postValue(State.Loading)
            }

            state.value is State.Failure && event is Event.Refresh -> {
                state.postValue(State.Loading)
            }
        }
    }
}