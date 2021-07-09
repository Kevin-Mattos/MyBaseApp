package com.example.mybaseapp.util.extensions

import android.util.Log
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.observers.DisposableCompletableObserver
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subscribers.DisposableSubscriber


fun <T> Single<T>.singleSubscribe(
    onLoading: ((Boolean) -> Unit)? = null,
    onError: ((Throwable?) -> Unit)? = null,
    onSuccess: ((T) -> Unit)? = null,
//    calculate: ((T) -> Any)? = null,
    subscribeOnScheduler: Scheduler? = Schedulers.io(),
    observeOnScheduler: Scheduler? = AndroidSchedulers.mainThread(),
//    calculateOnScheduler: Scheduler? = Schedulers.computation()
) = subscribeOn(subscribeOnScheduler)
//    .observeOn(calculateOnScheduler)
//    .map { calculate?.invoke(it) }
    .observeOn(observeOnScheduler)
    .doOnSubscribe { onLoading?.invoke(true) }
    .subscribeWith(object : DisposableSingleObserver<T>() {
        override fun onSuccess(value: T) {
            onLoading?.invoke(false)
            onSuccess?.invoke(value)
        }

        override fun onError(e: Throwable) {
            onLoading?.invoke(false)
            onError?.invoke(e)
        }
    })

fun <T> Observable<T>.observableSubscribe(
    onLoading: ((Boolean) -> Unit)? = null,
    onComplete: (() -> Unit)? = null,
    onNext: ((T) -> Unit)? = null,
    onError: ((Throwable?) -> Unit)? = null,
    subscribeOnScheduler: Scheduler? = Schedulers.io(),
    observeOnScheduler: Scheduler? = AndroidSchedulers.mainThread()
) = subscribeOn(subscribeOnScheduler)
    .observeOn(observeOnScheduler)
    .doOnSubscribe { onLoading?.invoke(true) }
    .subscribeWith(object : DisposableObserver<T>() {
        override fun onNext(t: T) {
            onLoading?.invoke(false)
            onNext?.invoke(t)
        }

        override fun onComplete() {
            onLoading?.invoke(false)
            onComplete?.invoke()
        }

        override fun onError(e: Throwable?) {
            onError?.invoke(e)
        }
    })


fun Completable.completableSubscribe(
    onLoading: ((Boolean) -> Unit)? = null,
    onError: ((Throwable?) -> Unit)? = null,
    onComplete: (() -> Unit)? = null,
    subscribeOnScheduler: Scheduler? = Schedulers.io(),
    observeOnScheduler: Scheduler? = AndroidSchedulers.mainThread()
) = subscribeOn(subscribeOnScheduler)
    .observeOn(observeOnScheduler)
    .doOnSubscribe { onLoading?.invoke(true) }
    .subscribeWith(object : DisposableCompletableObserver() {
        override fun onComplete() {
            onLoading?.invoke(false)
            onComplete?.invoke()
        }

        override fun onError(e: Throwable) {
            onLoading?.invoke(false)
            onError?.invoke(e)
        }
    })


fun <T> Flowable<T>.flowableSubscribe(
    onLoading: ((Boolean) -> Unit)? = null,
    onError: ((Throwable?) -> Unit)? = null,
    onComplete: (() -> Unit)? = null,
    onNext: ((T) -> Unit)? = null,
    subscribeOnScheduler: Scheduler = Schedulers.io(),
    observeOnScheduler: Scheduler = AndroidSchedulers.mainThread()
) = subscribeOn(subscribeOnScheduler)
    .observeOn(observeOnScheduler)
    .doOnSubscribe { onLoading?.invoke(true) }
    .subscribeWith(object : DisposableSubscriber<T>() {
        override fun onComplete() {
            onLoading?.invoke(false)
            onComplete?.invoke()
        }

        override fun onError(e: Throwable) {
            onLoading?.invoke(false)
            onError?.invoke(e)
        }

        override fun onNext(t: T) {
            onLoading?.invoke(false)
            onNext?.invoke(t)
        }
    })