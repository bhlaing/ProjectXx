package com.x.projectxx.global.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.observe(owner: LifecycleOwner, f: (T?) -> Unit) {
    this.observe(owner, Observer<T> { t -> f(t) })
}

/**
 * Filter out nulls from liveData.value
 */
fun <T> LiveData<T>.observeNonNull(owner: LifecycleOwner, f: (T) -> Unit) {
    this.observe(owner, Observer<T> { t -> t?.let(f) })
}