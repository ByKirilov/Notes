package com.bykirilov.domain.internal.rx

import io.reactivex.observers.DisposableSingleObserver

open class EmptySingleObserver<T : Any> : DisposableSingleObserver<T>() {

    override fun onSuccess(t: T) {}

    override fun onError(e: Throwable) {}
}