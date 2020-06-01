package com.elson.customrxjavalib;

import com.elson.customrxjavalib.funcations.Function;
import com.elson.customrxjavalib.observer.Observer;
import com.elson.customrxjavalib.operator.ObservableCreate;
import com.elson.customrxjavalib.operator.ObservableMap;
import com.elson.customrxjavalib.operator.ObservableObserveOn;
import com.elson.customrxjavalib.operator.ObservableSubscribeOn;
import com.elson.customrxjavalib.scheduler.Scheduler;

public abstract class Observable<T> {

    public static <T> Observable<T> create(OnSubscribe<T> onSubscribe) {
        return new ObservableCreate<T>(onSubscribe);
    }

    public <R> Observable<R> map(Function<? super T, ? extends R> mapper) {
        return (Observable<R>) new ObservableMap<T, R>(this, mapper);
    }

    public final Observable<T> subscribeOn(Scheduler scheduler) {
        return new ObservableSubscribeOn<T>(this, scheduler);
    }

    public final Observable<T> observeOn(Scheduler scheduler) {
        return new ObservableObserveOn<T>(this, scheduler);
    }

    public void subscribe(Observer<? super T> observer) {
        subscribeActual(observer);
    }

    protected abstract void subscribeActual(Observer<? super T> observer);

    public interface OnSubscribe<T> {
        void subscribe(Observer<? super T> observer);
    }
}
