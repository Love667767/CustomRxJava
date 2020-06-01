package com.elson.customrxjavademo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.elson.customrxjavalib.Observable;
import com.elson.customrxjavalib.funcations.Function;
import com.elson.customrxjavalib.observer.Observer;
import com.elson.customrxjavalib.scheduler.Scheduler;
import com.elson.customrxjavalib.scheduler.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.normalTextView).setOnClickListener(this);
        findViewById(R.id.mapTextView).setOnClickListener(this);
        findViewById(R.id.flatmapTextView).setOnClickListener(this);
        findViewById(R.id.subscribeOnTextView).setOnClickListener(this);
        findViewById(R.id.observeOnTextView).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.normalTextView:
                clickNormal();
                break;
            case R.id.mapTextView:
                clickMap();
                break;
            case R.id.flatmapTextView:
                break;
            case R.id.subscribeOnTextView:
                clickSubscribeOn();
                break;
            case R.id.observeOnTextView:
                clickObserveOn();
                break;
        }
    }

    private void clickNormal() {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void subscribe(Observer<? super String> observer) {
                observer.onNext("11");
                observer.onNext("22");
                observer.onNext("33");
                observer.onCompleted();
            }
        }).subscribe(this.<String>getObserver());
    }

    private void clickMap() {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void subscribe(Observer<? super String> observer) {
                observer.onNext("11");
                observer.onNext("22");
                observer.onNext("33");
                observer.onCompleted();
            }
        }).map(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return Integer.parseInt(s);
            }
        }).subscribe(this.<Integer>getObserver());
    }

    private void clickSubscribeOn() {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void subscribe(Observer<? super String> observer) {
                observer.onNext("11");
                observer.onNext("22");
                observer.onNext("33");
                observer.onCompleted();
            }
        }).map(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                Log.d(TAG, "map.apply 所在线程 = " + Thread.currentThread().getName());
                return Integer.parseInt(s);
            }
        }).subscribeOn(Schedulers.newThread())
        .subscribe(this.<Integer>getObserver());
    }

    private void clickObserveOn() {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void subscribe(Observer<? super String> observer) {
                observer.onNext("11");
                observer.onNext("22");
                observer.onNext("33");
                observer.onCompleted();
            }
        }).map(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                Log.d(TAG, "map.apply 所在线程 = " + Thread.currentThread().getName());
                return Integer.parseInt(s);
            }
        }).subscribeOn(Schedulers.newThread())
        .observeOn(Schedulers.mainThread())
        .subscribe(this.<Integer>getObserver());
    }

    private <T> Observer<T> getObserver() {
        return new Observer<T>() {
            @Override
            public void onNext(T s) {
                Log.d(TAG, "onNext = " + s
                        + "; Type = " + s.getClass().getSimpleName()
                        + "; Current Thread = " + Thread.currentThread().getName());
            }

            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable t) {

            }
        };
    }
}
