package com.example.nullcmd.core.state

sealed class State<out R> {
    object Loading: State<Nothing>()
    class Success<out T>(val data: T): State<T>()
    class Failure(val error: Throwable): State<Nothing>()
}

sealed class Event<out R> {
    object Refresh: Event<Nothing>()
    class LoadSuccess<out T>(val data: T): Event<T>()
    class LoadFailure(val error: Throwable): Event<Nothing>()
}