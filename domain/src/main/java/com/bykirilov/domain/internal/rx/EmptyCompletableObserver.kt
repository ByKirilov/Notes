package com.bykirilov.domain.internal.rx

import io.reactivex.observers.DisposableCompletableObserver

open class EmptyCompletableObserver : DisposableCompletableObserver() {

    override fun onComplete() {}

    override fun onError(e: Throwable) {}
}