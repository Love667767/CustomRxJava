package com.elson.customrxjavalib.operator;

import com.elson.customrxjavalib.Observable;
import com.elson.customrxjavalib.observer.Observer;
import com.elson.customrxjavalib.scheduler.Scheduler;

public class ObservableObserveOn<T> extends Observable<T> {

    Observable<T> source;
    Scheduler scheduler;

    public ObservableObserveOn(Observable<T> source, Scheduler scheduler) {
        this.source = source;
        this.scheduler = scheduler;
    }

    @Override
    protected void subscribeActual(Observer<? super T> observer) {
        source.subscribe(new ObserveOnObserver<T>(scheduler, observer));
    }

    static final class ObserveOnObserver<T> implements Observer<T>, Runnable {

        Scheduler scheduler;
        Observer downStream;
        T value;

        public ObserveOnObserver(Scheduler scheduler, Observer<? super T> observer) {
            this.scheduler = scheduler;
            this.downStream = observer;
        }

        @Override
        public void onNext(T t) {
            value = t;
            // 通过调度器切换线程。
            scheduler.schedule(this);
        }

        @Override
        public void onCompleted() {
            downStream.onCompleted();
        }

        @Override
        public void onError(Throwable t) {
            downStream.onError(t);
        }

        @Override
        public void run() {
            downStream.onNext(value);
        }
    }
}
