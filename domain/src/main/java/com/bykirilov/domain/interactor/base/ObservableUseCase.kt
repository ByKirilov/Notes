package com.bykirilov.domain.interactor.base

import com.bykirilov.domain.internal.rx.EmptyObserver
import com.raduga.domain.executor.PostExecutionThread
import com.raduga.domain.executor.ThreadExecutor
import io.reactivex.Observable
import io.reactivex.observers.DisposableObserver

abstract class ObservableUseCase<TResult : Any, in TParams>(
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : BaseUseCase(threadExecutor, postExecutionThread) {

    abstract fun buildUseCaseObservable(params: TParams): Observable<TResult>

    fun execute(observer: DisposableObserver<TResult> = EmptyObserver(), params: TParams) {
        val observable = buildUseUseCaseObservableWithSchedulers(params)
        addDisposable(observable.subscribeWith(observer))
    }

    private fun buildUseUseCaseObservableWithSchedulers(params: TParams): Observable<TResult> {
        return buildUseCaseObservable(params)
            .subscribeOn(threadExecutorScheduler)
            .observeOn(postExecutionThreadScheduler)
    }
}