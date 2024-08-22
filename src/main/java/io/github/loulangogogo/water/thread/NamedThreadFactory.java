package io.github.loulangogogo.water.thread;

import io.github.loulangogogo.water.tool.StrTool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/*********************************************************
 ** 命名线程工厂类
 ** 
 ** @author loulan
 ** @since 8
 *********************************************************/
public class NamedThreadFactory implements ThreadFactory {
    //  线程的前缀名称
    private final String prefix;
    // 原子类对象，主要是用来标记各个线程的
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    // 是否是守护线程
    private final boolean isDaemon;

    /**
     * 构造器，默认使用的是非守护线程
     *
     * @param prefix 线程名称的前缀
     * @author :loulan
     */
    public NamedThreadFactory(String prefix) {
        this(prefix, false);
    }

    /**
     * 构造器
     *
     * @param   prefix 线程名称的前缀(如果为null或者空，这使用默认的名称)
     * @param   isDaemon 线程是否是守护线程
     * @author :loulan
     */
    public NamedThreadFactory(String prefix, boolean isDaemon) {
        if (StrTool.isEmpty(prefix)) {
            prefix = "owner";
        }
        this.prefix = prefix;
        this.isDaemon = isDaemon;
    }


    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r, prefix + "-" + threadNumber.getAndIncrement());

        if (thread.isDaemon()) {
            if (!isDaemon) {
                // 本身是守护线程，但是要求不是守护线程，所以这里进行设置
                thread.setDaemon(false);
            }
        }else {
            if (isDaemon) {
                // 本身不是守护线程，但是要求作为守护线程，所以这里进行设置
                thread.setDaemon(true);
            }
        }

        //优先级
        if (Thread.NORM_PRIORITY != thread.getPriority()) {
            // 标准优先级
            thread.setPriority(Thread.NORM_PRIORITY);
        }

        return thread;
    }
}
