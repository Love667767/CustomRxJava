package com.elson.customrxjavalib.scheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IOScheduler implements Scheduler {

    ExecutorService service;

    public IOScheduler() {
        service = Executors.newCachedThreadPool(new RxThreadFactory("-thread-io-"));
    }

    @Override
    public void schedule(Runnable runnable) {
        service.submit(runnable);
    }
}
