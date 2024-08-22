package io.github.loulangogogo.water.thread;

import io.github.loulangogogo.water.interfaces.Builder;
import io.github.loulangogogo.water.tool.ObjectTool;

import java.util.Comparator;
import java.util.concurrent.*;

/*********************************************************
 ** {@link ThreadPoolExecutor} 建造者
 ** 
 ** <pre>
 **     1.如果有空闲线程，则直接执行该任务；
 **     2.如果没有空闲线程，且当前运行的线程数少于corePoolSize，则创建新的线程执行该任务；
 **     3.如果没有空闲线程，且当前的线程数等于corePoolSize，同时阻塞队列未满，则将任务入队列，而不添加新的线程；
 **     4.如果没有空闲线程，且阻塞队列已满，同时池中的线程数小于maximumPoolSize ，则创建新的线程执行任务；(大于corePoolSize的线程空闲下来之后经过keepAliveTime之后会被释放掉)
 **     5.如果没有空闲线程，且阻塞队列已满，同时池中的线程数等于maximumPoolSize ，则根据构造函数中的 handler 指定的策略来拒绝新的任务;
 ** </pre>
 ** @author loulan
 ** @since JDK-1.8
 *********************************************************/
public class ExecutorBuilder implements Builder<ThreadPoolExecutor> {
    private static final long serialVersionUID = 1L;
    // 默认阻塞队列的容量（有界队列）
    public static final int DEFAULT_QUEUE_CAPACITY = 1024;

    // 核心线程数
    private int corePoolSize;
    // 最大线程数
    private int maxPoolSize = Integer.MAX_VALUE;
    // 存活时间
    private long keepAliveTime = TimeUnit.SECONDS.toSeconds(60);
    // 阻塞队列
    private BlockingQueue<Runnable> queue;
    // 线程工厂
    private ThreadFactory threadFactory;
    // 处理器
    private RejectedExecutionHandler handler;

    /**
     * 私有化构造器
     *
     * @author :loulan
     */
    private ExecutorBuilder() {

    }

    /**
     * 构建建造者
     *
     * @return {@link ThreadPoolExecutor} 建造者
     * @author :loulan
     */
    public static ExecutorBuilder builder() {
        return new ExecutorBuilder();
    }

    /**
     * 设置核心线程数
     *
     * @param corePoolSize 核心线程池大小
     * @return {@link ThreadPoolExecutor} 建造者
     * @author :loulan
     */
    public ExecutorBuilder setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
        return this;
    }

    /**
     * 设置最大线程数
     *
     * @param maxPoolSize 最大线程池大小
     * @return {@link ThreadPoolExecutor} 建造者
     * @author :loulan
     */
    public ExecutorBuilder setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
        return this;
    }

    /**
     * 设置超出核心线程的存活时间
     * （当线程数大于核心线程数的时候，没经过keepAliveTime时间都会检查是否有线程空闲，如果有那么停止释放掉这个线程）
     *
     * @param keepAliveTime 存活时间
     * @return {@link ThreadPoolExecutor} 建造者
     * @author :loulan
     */
    public ExecutorBuilder setKeepAliveTime(long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
        return this;
    }

    /**
     * 设置超出核心线程的存活时间(默认的时间单位是毫秒值)
     * （当线程数大于核心线程数的时候，没经过keepAliveTime时间都会检查是否有线程空闲，如果有那么停止释放掉这个线程）
     *
     * @param keepAliveTime 存活时间
     * @param unit          时间单位
     * @return {@link ThreadPoolExecutor} 建造者
     * @author :loulan
     */
    public ExecutorBuilder setKeepAliveTime(long keepAliveTime, TimeUnit unit) {
        this.keepAliveTime = unit.toMillis(keepAliveTime);
        return this;
    }

    /**
     * 设置阻塞队列
     *
     * @param queue 阻塞队列的对象
     * @return {@link ThreadPoolExecutor} 建造者
     * @author :loulan
     */
    public ExecutorBuilder setQueue(BlockingQueue<Runnable> queue) {
        this.queue = queue;
        return this;
    }

    /**
     * 线程工厂
     *
     * @param threadFactory 线程工厂对象
     * @return {@link ThreadPoolExecutor} 建造者
     * @author :loulan
     */
    public ExecutorBuilder setThreadFactory(ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
        return this;
    }

    /**
     * 设置线程的拒绝策略
     *
     * @param handler 策略对象
     * @return {@link ThreadPoolExecutor} 建造者
     * @author :loulan
     */
    public ExecutorBuilder setHandler(RejectedExecutionHandler handler) {
        this.handler = handler;
        return this;
    }


    /**
     * 数组（有界）阻塞队列
     * 一个由数组支持的有界阻塞队列。此队列按 FIFO（先进先出）原则对元素进行排序。
     * 队列的头部 是在队列中存在时间最长的元素。队列的尾部 是在队列中存在时间最短的元素。
     * 新元素插入到队列的尾部，队列获取操作则是从队列头部开始获得元素。
     * <p>
     * 默认采用公平锁。
     *
     * @param capacity 初始化队列容量
     * @return {@link ThreadPoolExecutor} 建造者
     * @author :loulan
     */
    public ExecutorBuilder useArrayBlockingQueue(int capacity) {
        return setQueue(new ArrayBlockingQueue<>(capacity));
    }

    /**
     * 使用同步阻塞（有界）队列，
     * 这是一个很有意思的阻塞队列，其中每个插入操作必须等待另一个线程的移除操作，
     * 同样任何一个移除操作都等待另一个线程的插入操作。
     * 因此此队列内部其 实没有任何一个元素，或者说容量是0，严格说并不是一种容器。
     * 由于队列没有容量，因此不能调用peek操作，因为只有移除元素时才有元素
     * 默认该同步阻塞队列为采用非公平锁模式
     *
     * @return {@link ThreadPoolExecutor} 建造者
     * @author :loulan
     */
    public ExecutorBuilder useSynchronousQueue() {
        return useSynchronousQueue(false);
    }

    /**
     * 使用同步阻塞（有界）队列，
     * 这是一个很有意思的阻塞队列，其中每个插入操作必须等待另一个线程的移除操作，
     * 同样任何一个移除操作都等待另一个线程的插入操作。
     * 因此此队列内部其 实没有任何一个元素，或者说容量是0，严格说并不是一种容器。
     * 由于队列没有容量，因此不能调用peek操作，因为只有移除元素时才有元素
     * 默认该同步阻塞队列为采用非公平锁模式
     *
     * @param fair 是否采用公平锁的方式（先到先得）
     * @return {@link ThreadPoolExecutor} 建造者
     * @author :loulan
     */
    public ExecutorBuilder useSynchronousQueue(boolean fair) {
        return setQueue(new SynchronousQueue<>(fair));
    }

    /**
     * 使用链表（无界）阻塞队列。
     * 这个说是无界队列，其实也是有界的，它的大小是Integer.MAX_VALUE，所以也就默认无限了。
     * 而且这个队列可以设置初始化值为有界队列，但是使用有界队列不使用链表结构的，数组结构的效率更高。
     *
     * @return {@link ThreadPoolExecutor} 建造者
     * @author :loulan
     */
    public ExecutorBuilder userLinkedBlockingQueue() {
        return setQueue(new LinkedBlockingQueue<>());
    }

    /**
     * 使用优先级比较（无界）阻塞队列。（也可以设置为有界，但是有界一般不用这个队列）
     * 是一个按照优先级进行内部元素排序的无界阻塞队列。队列中的元素必须实现 {@link Comparable} 接口，
     * 这样才能通过实现{@link Comparable#compareTo(Object)}方法进行排序。优先级最高的元素将始终排在队列的头部;
     * <p>
     * 默认采用自然排序（接口强行对实现它的每个类的对象进行整体排序。这种排序被称为类的自然排序）
     *
     * @return {@link ThreadPoolExecutor} 建造者
     * @author :loulan
     */
    public ExecutorBuilder userPriorityBlockingQueue() {
        return setQueue(new PriorityBlockingQueue<>());
    }

    /**
     * 使用优先级比较（无界）阻塞队列。（也可以设置为有界，但是有界一般不用这个队列）
     * 是一个按照优先级进行内部元素排序的无界阻塞队列。队列中的元素必须实现 {@link Comparable} 接口，
     * 这样才能通过实现{@link Comparable#compareTo(Object)}方法进行排序。优先级最高的元素将始终排在队列的头部;
     * <p>
     *
     * @param comparator 排序方式的比较对象
     * @return {@link ThreadPoolExecutor} 建造者
     * @author :loulan
     */
    public ExecutorBuilder userPriorityBlockingQueue(Comparator<? super Runnable> comparator) {
        return setQueue(new PriorityBlockingQueue<>(Integer.MAX_VALUE, comparator));
    }


    /**
     * 构建{@link ThreadPoolExecutor}对象
     *
     * @return {@link ThreadPoolExecutor}对象
     * @author :loulan
     */
    public ThreadPoolExecutor build() {
        return build(this);
    }

    /**
     * 构建{@link ThreadPoolExecutor}对象
     *
     * @param builder {@link ThreadPoolExecutor}建造者
     * @return {@link ThreadPoolExecutor}对象
     * @author :loulan
     */
    private static ThreadPoolExecutor build(ExecutorBuilder builder) {
        int corePoolSize = builder.corePoolSize;
        int maxPoolSize = builder.maxPoolSize;
        long keepAliveTime = builder.keepAliveTime;

        BlockingQueue<Runnable> queue = ObjectTool.isNull(builder.queue) ? (corePoolSize <= 0) ? new SynchronousQueue<>() : new ArrayBlockingQueue<>(DEFAULT_QUEUE_CAPACITY) : builder.queue;
        ThreadFactory threadFactory = ObjectTool.isNull(builder.threadFactory) ? new NamedThreadFactory("") : builder().threadFactory;
        RejectedExecutionHandler handler = ObjectTool.isNull(builder.handler) ? RejectPolicyEnum.ABORT.getValue() : builder.handler;

        return new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                keepAliveTime,
                TimeUnit.MILLISECONDS,
                queue,
                threadFactory,
                handler
        );
    }
}
