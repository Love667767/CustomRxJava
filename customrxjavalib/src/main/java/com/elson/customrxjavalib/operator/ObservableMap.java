package com.elson.customrxjavalib.operator;

import com.elson.customrxjavalib.Observable;
import com.elson.customrxjavalib.funcations.Function;
import com.elson.customrxjavalib.observer.Observer;

public class ObservableMap<T, R> extends Observable<T> {

    Observable<T> source;
    Function<? super T, ? extends R> mapper;

    public ObservableMap(Observable<T> source, Function<? super T, ? extends R> mapper) {
        this.source = source;
        this.mapper = mapper;
    }

    @Override
    protected void subscribeActual(Observer<? super T> observer) {
        MapObserver<T, R> parent = new MapObserver<>(observer, mapper);
        source.subscribe(parent);
    }

    static class MapObserver<T, R> implements Observer<T> {

        Observer downStream;
        Function<? super T, ? extends R> mapper;

        public MapObserver(Observer<? super T> observer, Function<? super T, ? extends R> mapper) {
            this.downStream = observer;
            this.mapper = mapper;
        }

        @Override
        public void onNext(T t) {
            R r = mapper.apply(t);
            downStream.onNext(r);
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
