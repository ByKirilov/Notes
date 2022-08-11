package com.bykirilov.domain.interactor.base

import com.bykirilov.domain.internal.rx.EmptyObserver
import com.bykirilov.domain.internal.rx.EmptySingleObserver
import com.raduga.domain.executor.PostExecutionThread
import com.raduga.domain.executor.ThreadExecutor
import io.reactivex.Single
import io.reactivex.observers.DisposableSingleObserver

abstract class SingleUseCase<TResult : Any, in TParams>(
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : BaseUseCase(threadExecutor, postExecutionThread) {

    abstract fun buildUseCaseSingle(params: TParams): Single<TResult>

    fun execute(
        observer: DisposableSingleObserver<TResult> = EmptySingleObserver(),
        params: TParams
    ) {
        val single = buildUseCaseSingleWithSchedulers(params)
        addDisposable(single.subscribeWith(observer))
    }

    private fun buildUseCaseSingleWithSchedulers(params: TParams): Single<TResult> {
        return buildUseCaseSingle(params)
            .subscribeOn(threadExecutorScheduler)
            .observeOn(postExecutionThreadScheduler)
    }
}