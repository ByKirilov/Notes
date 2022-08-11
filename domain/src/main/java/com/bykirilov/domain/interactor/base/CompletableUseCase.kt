package com.bykirilov.domain.interactor.base

import com.bykirilov.domain.internal.rx.EmptyCompletableObserver
import com.raduga.domain.executor.PostExecutionThread
import com.raduga.domain.executor.ThreadExecutor
import io.reactivex.Completable
import io.reactivex.observers.DisposableCompletableObserver

abstract class CompletableUseCase<in TParams>(
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : BaseUseCase(threadExecutor, postExecutionThread) {

    abstract fun buildUseCaseCompletable(params: TParams): Completable

    fun execute(
        observer: DisposableCompletableObserver = EmptyCompletableObserver(),
        params: TParams
    ) {
        val completable = buildUseCaseCompletableWithSchedulers(params)
        addDisposable(completable.subscribeWith(observer))
    }

    private fun buildUseCaseCompletableWithSchedulers(params: TParams): Completable {
        return buildUseCaseCompletable(params)
            .subscribeOn(threadExecutorScheduler)
            .observeOn(postExecutionThreadScheduler)
    }
}