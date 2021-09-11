package com.irigel.common.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程实用类
 */
public class ThreadUtils {

    private static AtomicInteger sId = new AtomicInteger(0);

    public static ThreadPoolExecutor sThreadPool;


    /**
     * 在后台执行任务
     *
     * @param executable
     */
    public static void runInBackground(Executable executable) {
        sThreadPool.execute(executable);
    }

    public static void runInBackground(Thread thread) {
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * 阻塞调用线程，等待子线程执行任务
     *
     * @param callable              需要执行的任务，泛型T代表任务执行完成后返回的类型
     * @param timeoutMillis         超时时间，超过该时间不再等待
     * @param mayInterruptIfRunning 是否允许超时后中断子线程
     * @param <T>
     * @return
     */
    public static <T> T runInBackgroundAndBlock(Callable<T> callable, int timeoutMillis, boolean mayInterruptIfRunning) {
        FutureTask<T> futureTask = new FutureTask<>(callable);
        sThreadPool.execute(futureTask);
        T t = null;
        try {
            t = futureTask.get(timeoutMillis, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            futureTask.cancel(mayInterruptIfRunning);
        }
        return t;
    }
}
