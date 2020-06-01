package com.elson.customrxjavalib.operator;

import com.elson.customrxjavalib.Observable;
import com.elson.customrxjavalib.observer.Observer;

public class ObservableCreate<T> extends Observable<T> {

    OnSubscribe source;
    public ObservableCreate(OnSubscribe<T> source) {
        this.source = source;
    }

    @Override
    protected void subscribeActual(Observer<? super T> observer) {
        CreateObserver<T> parent = new CreateObserver<>(observer);
        source.subscribe(parent);
    }

    static class CreateObserver<T> implements Observer<T> {

        Observer downStream;

        public CreateObserver(Observer<? super T> observer) {
            downStream = observer;
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
}
