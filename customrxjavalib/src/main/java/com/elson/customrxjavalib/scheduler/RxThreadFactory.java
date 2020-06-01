package com.elson.customrxjavalib.scheduler;

import java.util.concurrent.ThreadFactory;

public class RxThreadFactory implements ThreadFactory {

    private String mThreadName;

    public RxThreadFactory(String threadName) {
        this.mThreadName = threadName;
    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, mThreadName);
    }
}
