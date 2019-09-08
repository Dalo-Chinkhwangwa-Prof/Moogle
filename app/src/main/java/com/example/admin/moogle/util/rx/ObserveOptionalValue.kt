package com.example.admin.moogle.util.rx

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers

class CustomObserveOptionalValue<T>(private val observeOnScheduler: Scheduler = AndroidSchedulers.mainThread()) : ObservableTransformer<Optional<T>, T>{
    override fun apply(upstream: Observable<Optional<T>>): ObservableSource<T> {
        return upstream
                .observeOn(observeOnScheduler)
                .flatMap { optional ->
                    if(optional is Optional.Some)
                        Observable.just(optional.result)
                    else
                    Observable.empty()
                }
    }
}