package com.elson.customrxjavalib.operator;

import com.elson.customrxjavalib.Observable;
import com.elson.customrxjavalib.observer.Observer;
import com.elson.customrxjavalib.scheduler.Scheduler;

public class ObservableSubscribeOn<T> extends Observable<T> {

    Observable<T> source;
    Scheduler scheduler;

    public ObservableSubscribeOn(Observable<T> source, Scheduler scheduler) {
        this.source = source;
        this.scheduler = scheduler;
    }

    @Override
    protected void subscribeActual(Observer<? super T> observer) {
        SubscribeOnObserver parent = new SubscribeOnObserver<>(observer);
        scheduler.schedule(new SubscribeTask(parent));
    }

    static class SubscribeOnObserver<T> implements Observer<T> {

        Observer downStream;

        public SubscribeOnObserver(Observer<? super T> observer) {
            this.downStream = observer;
        }

        @Override
        public void onNext(T t) {
            downStream.onNext(t);
        }

        @Override
        public void onCompleted() {
            downStream.onCompleted();
        }

        @Override
        public void onError(Throwable t) {
            downStream.onError(t);
        }
    }


    final class SubscribeTask implements Runnable {

        private final SubscribeOnObserver<T> parent;

        SubscribeTask(SubscribeOnObserver<T> parent) {
            this.parent = parent;
        }

        @Override
        public void run() {
            source.subscribe(parent);
        }
    }
}
