package io.github.loulangogogo.water.thread;

import io.github.loulangogogo.water.exception.ThreadException;
import org.apache.commons.lang3.ThreadUtils;
import org.apache.commons.lang3.time.DurationUtils;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/*********************************************************
 ** 线程的工具类
 **
 ** @author loulan
 ** @since 8
 *********************************************************/
public class ThreadTool {

    /**
     * 在公共线程池中执行线程
     *
     * @param runnable 可运行的对象
     * @author :loulan
     */
    public static void execute(Runnable runnable) {
        ThreadPoolTool.execute(runnable);
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
        return ThreadPoolTool.submit(task);
    }

    /**
     * 执行有返回值的异步方法
     *
     * @param runnable 可运行的对象
     * @return {@link Future}对象
     * @author :loulan
     */
    public static Future<?> submit(Runnable runnable) {
        return ThreadPoolTool.submit(runnable);
    }

    /**
     * 线程休眠。
     * 时间单位默认是毫秒
     *
     * @param millis 休眠毫秒值
     * @author :loulan
     */
    public static void sleep(long millis) {
        sleep(millis, TimeUnit.MILLISECONDS);
    }

    /**
     * 线程休眠。
     *
     * @param amount   休眠时间
     * @param timeUnit 时间单位
     * @author :loulan
     */
    public static void sleep(long amount, TimeUnit timeUnit) {
        try {
            ThreadUtils.sleep(DurationUtils.toDuration(amount, timeUnit));
        } catch (InterruptedException e) {
            throw new ThreadException(e);
        }
    }
}
