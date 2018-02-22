package com.th;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class UpdateRunner {
    private static final int FPS = 60;
    private static ScheduledExecutorService exe;

    private final static Runnable NULL = new Runnable () {
        public void run () {
            // Do nothing
        }
    };

    public static void run(Runnable r){
        stop();
        exe = Executors.newSingleThreadScheduledExecutor();
        exe.scheduleAtFixedRate(r, 0 , 1000/FPS, TimeUnit.MILLISECONDS);
    }
    public static void stop(){
        if(exe != null) exe.shutdown();
    }
    public static long getTime(){
        return System.nanoTime();
    }
    public static long getElapsedTime(long start){
        return getTime() - start;
    }
}
