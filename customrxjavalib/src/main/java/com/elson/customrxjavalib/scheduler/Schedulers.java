package com.elson.customrxjavalib.scheduler;

public class Schedulers {

    static final Scheduler IO;

    static final Scheduler MAIN;

    static final Scheduler NEW_THREAD;

    static {
        IO = new IOScheduler();
        MAIN = new MainScheduler();
        NEW_THREAD = new NewThreadScheduler();
    }

    public static Scheduler io() {
        return IO;
    }

    public static Scheduler mainThread() {
        return MAIN;
    }

    public static Scheduler newThread() {
        return NEW_THREAD;
    }
}
