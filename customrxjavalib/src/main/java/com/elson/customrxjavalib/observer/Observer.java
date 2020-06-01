package com.elson.customrxjavalib.observer;

public interface Observer<T> {

    void onNext(T t);

    void onCompleted();

    void onError(Throwable t);
}
