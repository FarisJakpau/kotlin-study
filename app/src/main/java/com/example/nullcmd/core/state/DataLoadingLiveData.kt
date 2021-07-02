package com.example.nullcmd.core.state

import androidx.lifecycle.MutableLiveData

class DataLoadingLiveData<T>: MutableLiveData<State<T>>() {

    fun loading() {
        
    }

    fun loadSuccess(data:T) {
        transition(Event.LoadSuccess(data))
    }

    fun loadFailure(error: Throwable) {
        transition(Event.LoadFailure(error))
    }

    fun refresh() {
        transition(Event.Refresh)
    }

    private fun transition(event: Event<T>) {
        when {
            value is State.Loading && event is Event.LoadSuccess -> {
                postValue(State.Success(event.data))
            }

            value is State.Loading && event is Event.LoadFailure -> {
                postValue(State.Failure(event.error))
            }

            value is State.Success && event is Event.Refresh -> {
                postValue(State.Loading)
            }

            value is State.Failure && event is Event.Refresh -> {
                postValue(State.Loading)
            }
        }
    }
}