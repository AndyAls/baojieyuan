package com.qlckh.purifier.common;

import android.os.Handler;
import android.support.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Andy
 * @date 2018/4/19 14:17
 * Des:   线程池管理类
 */
public class ThreadPool {
    private static volatile ThreadPool pool;
    private ExecutorService service;
    private ScheduledExecutorService delayService;
    private ScheduledExecutorService timerService;

    public static ThreadPool getInstance() {
        if (pool == null) {
            synchronized (ThreadPool.class) {
                if (pool == null) {
                    pool = new ThreadPool();
                }
            }
        }
        return pool;
    }

    /**
     * 开启线程池执行耗时操作
     *
     * @param threadName 线程名称
     * @param runnable   run
     */
    public void execute(final String threadName, @NonNull Runnable runnable) {

        ThreadFactory factory = new ThreadFactory() {
            @Override
            public Thread newThread(@NonNull Runnable runnable) {
                Thread thread = new Thread(runnable);
                thread.setPriority(Thread.MAX_PRIORITY);
                return new Thread(runnable, threadName);
            }
        };
        service = new ThreadPoolExecutor(5, 200, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), factory);
        service.execute(runnable);
        service.shutdown();
    }

    /**
     * 开启线程池执行延迟任务
     * @param runnable  run
     * @param delayTime 延迟时间,单位毫秒
     */
    public void executeDelayed(Runnable runnable, Long delayTime) {

        ThreadFactory factory = new ThreadFactory() {
            @Override
            public Thread newThread(@NonNull Runnable runnable) {
                Thread thread = new Thread(runnable);
                thread.setPriority(Thread.MAX_PRIORITY);
                return new Thread(runnable);
            }
        };
        delayService = new ScheduledThreadPoolExecutor(5,
                factory);
        delayService.schedule(runnable, delayTime, TimeUnit.MILLISECONDS);
    }


    /**
     * 开启线程池,执行周期任务
     * 延迟多少毫秒,每个一段时间执行某个任务
     *
     * @param runnable     run
     * @param initialDelay 延迟时间
     * @param period       周期时间
     */
    public void executeTimer(Runnable runnable, long initialDelay, long period) {

        ThreadFactory factory = new ThreadFactory() {
            @Override
            public Thread newThread(@NonNull Runnable runnable) {
                Thread thread = new Thread(runnable);
                thread.setPriority(Thread.MAX_PRIORITY);
                return new Thread(runnable);
            }
        };
        timerService = new ScheduledThreadPoolExecutor(5,
                factory);
        timerService.scheduleAtFixedRate(runnable, initialDelay, period, TimeUnit.MILLISECONDS);
    }

    /**
     * 主动释放pool资源
     */
    public void release(){

        if (service!=null){
            service.shutdown();
            service=null;
        }
        if (delayService!=null){
            delayService.shutdown();
            delayService=null;
        }
        if (timerService!=null){
            timerService.shutdown();
            timerService=null;
        }
        pool=null;
    }

}
