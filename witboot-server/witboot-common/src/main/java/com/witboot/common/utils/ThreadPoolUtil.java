package com.witboot.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池工具类
 *
 * @author sunxiaozhi
 */
@Slf4j
public class ThreadPoolUtil {
    /**
     * 线程池的工具类
     * 用于进行线程的管理，防止重复创建、杀死线程。
     * 多线程运行期间，如果系统不断的创建、杀死新线程，会产生过度消耗系统资源，以及过度切换线程的问题，甚至可能导致系统资源的崩溃。
     * 因此需要线程池，对线程进行管理。
     */
    private final String TAG = getClass().getName();

    private static volatile ThreadPoolUtil instance;

    /**
     * 核心线程池的数量，同时能够执行的线程数量
     */
    private final int corePoolSize;

    /**
     * 最大线程池数量，表示当缓冲队列满的时候能继续容纳的等待任务的数量
     */
    private final int maxPoolSize;

    /**
     * 存活时间
     */
    private final long keepAliveTime = 1;

    private ThreadPoolExecutor threadPoolExecutor;

    private ThreadPoolUtil() {
        //给corePoolSize赋值：当前设备可用处理器核心数*2 + 1,能够让cpu的效率得到最大程度执行（有研究论证的）
        corePoolSize = Runtime.getRuntime().availableProcessors() * 2 + 1;
        log.info("核心线程数：{}", corePoolSize);

        maxPoolSize = corePoolSize;
        log.info("最大线程数：{}", corePoolSize);

        threadPoolExecutor = new ThreadPoolExecutor(
                //当某个核心任务执行完毕，会依次从缓冲队列中取出等待任务
                corePoolSize,

                // 然后new LinkedBlockingQueue<Runnable>(),然后maximumPoolSize,但是它的数量是包含了corePoolSize的
                maxPoolSize,

                //表示的是 maximumPoolSize 当中等待任务的存活时间
                keepAliveTime,

                TimeUnit.HOURS,

                //缓冲队列，用于存放等待任务，Linked的先进先出
                new LinkedBlockingQueue<>(),

                new DefaultThreadFactory(Thread.NORM_PRIORITY, "thread-pool-"),

                new ThreadPoolExecutor.AbortPolicy()
        );
    }

    /**
     * 线程池初始化
     *
     * @return 线程池
     */
    public static ThreadPoolUtil getInstance() {
        if (instance == null) {
            log.info("线程池未存在，创建线程池");
            synchronized (ThreadPoolUtil.class) {
                if (instance == null) {
                    instance = new ThreadPoolUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 执行任务
     *
     * @param runnable runnable
     */
    public void execute(Runnable runnable) {
        if (threadPoolExecutor == null) {
            threadPoolExecutor = new ThreadPoolExecutor(
                    corePoolSize,
                    maxPoolSize,
                    keepAliveTime,
                    TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>(),
                    new DefaultThreadFactory(Thread.NORM_PRIORITY, "thread-pool-"),
                    new ThreadPoolExecutor.AbortPolicy());
        }

        if (runnable != null) {
            threadPoolExecutor.execute(runnable);
        }
    }

    /**
     * 移除任务
     *
     * @param runnable runnable
     */
    public void remove(Runnable runnable) {
        if (runnable != null) {
            threadPoolExecutor.remove(runnable);
        }
    }

    private static class DefaultThreadFactory implements ThreadFactory {
        //线程池的计数
        private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);

        //线程的计数
        private final AtomicInteger threadNumber = new AtomicInteger(1);

        private final ThreadGroup group;

        private final String namePrefix;

        private final int threadPriority;

        DefaultThreadFactory(int threadPriority, String threadNamePrefix) {
            this.threadPriority = threadPriority;
            this.group = Thread.currentThread().getThreadGroup();
            this.namePrefix = threadNamePrefix + POOL_NUMBER.getAndIncrement() + "-thread-";
        }

        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(group, runnable, namePrefix + threadNumber.getAndIncrement(), 0);

            // 返回True该线程就是守护线程
            // 守护线程应该永远不去访问固有资源，如：数据库、文件等。因为它会在任何时候甚至在一个操作的中间发生中断。
            if (thread.isDaemon()) {
                thread.setDaemon(false);
            }

            thread.setPriority(threadPriority);

            return thread;
        }
    }
}
