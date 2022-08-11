package com.bykirilov.domain.internal.rx

import io.reactivex.observers.DisposableObserver

open class EmptyObserver<T : Any> : DisposableObserver<T>() {

    override fun onNext(t: T) {}

    override fun onError(e: Throwable) {}

    override fun onComplete() {}
}