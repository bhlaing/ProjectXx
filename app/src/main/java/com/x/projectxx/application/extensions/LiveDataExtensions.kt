package com.x.projectxx.application.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * Filter out nulls from liveData.value
 */
fun <T> LifecycleOwner.observeNonNull(liveData: LiveData<T>, f: (T) -> Unit) {
    liveData.observe(this, Observer { t -> t?.let(f) })
}

inline fun <T> LifecycleOwner.observe(liveData: LiveData<T>, crossinline action: (t: T?) -> Unit) {
    liveData.observe(this, Observer { action(it) })
}

inline fun <T> LifecycleOwner.observeEvent(liveData: LiveData<Event<T>>, crossinline unhandledEventContent: (T) -> Unit) {
    liveData.observe(this, EventObserver { it?.let(unhandledEventContent) } )
}

inline fun LifecycleOwner.observeEvent(liveData: LiveData<Event<Unit>>, crossinline unhandledEventContent: () -> Unit) {
    liveData.observe(this, EventObserver { unhandledEventContent() })
}
