package io.github.loulangogogo.water.thread;

import io.github.loulangogogo.water.tool.ObjectTool;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/*********************************************************
 ** 线程池工具
 * 当前线程池工具采用的同步阻塞队列，无限最大线程
 **
 ** @author loulan
 ** @since 8
 *********************************************************/
public class ThreadPoolTool {
    private static ThreadPoolExecutor executor;

    static {
        init();
    }

    /**
     * 私有化构造器
     *
     * @author :loulan
     */
    private ThreadPoolTool() {
    }

    /**
     * 对工具使用进行初始化
     * 使用同步阻塞队列，其它使用默认配置
     * 0核心线程
     * 无限最大线程数
     *
     * @author :loulan
     */
    synchronized public static void init() {
        if (null != executor) {
            executor.shutdownNow();
        }
        executor = ExecutorBuilder.builder().useSynchronousQueue().build();
    }

    /**
     * 关闭公共线程池
     *
     * @param isNow 是否立马就关闭
     * @author :loulan
     */
    synchronized public static void shutdown(boolean isNow) {
        if (ObjectTool.isNotNull(executor)) {
            if (isNow) {
                executor.shutdownNow();
            } else {
                executor.shutdown();
            }
        }
    }

    /**
     * 在公共线程池中执行线程
     *
     * @param runnable 可运行的对象
     * @author :loulan
     */
    public static void execute(Runnable runnable) {
        executor.execute(runnable);
    }

    /**
     * 执行有返回值的异步方法
     *
     * @param <T>  泛型
     * @param task 任务线程
     * @return {@link Future}对象
     * @author :loulan
     */
    public static <T> Future<T> submit(Callable<T> task) {
        return executor.submit(task);
    }

    /**
     * 执行有返回值的异步方法
     *
     * @param runnable 可运行的对象
     * @return {@link Future}对象
     * @author :loulan
     */
    public static Future<?> submit(Runnable runnable) {
        return executor.submit(runnable);
    }
}
