package com.elson.customrxjavalib.scheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NewThreadScheduler implements Scheduler {

    ExecutorService service;

    public NewThreadScheduler() {
        service = Executors.newCachedThreadPool(new RxThreadFactory("-thread-new-"));
    }

    @Override
    public void schedule(Runnable runnable) {
        service.submit(runnable);
    }
}
