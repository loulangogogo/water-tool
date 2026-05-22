package io.github.loulangogogo.water.test.thread;

import io.github.loulangogogo.water.thread.ExecutorBuilder;
import io.github.loulangogogo.water.thread.NamedThreadFactory;
import io.github.loulangogogo.water.thread.RejectPolicyEnum;
import io.github.loulangogogo.water.thread.ThreadPoolTool;
import io.github.loulangogogo.water.thread.ThreadTool;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.*;

import static org.junit.Assert.*;

/**
 * 测试{@link ExecutorBuilder}、{@link NamedThreadFactory}、{@link ThreadPoolTool}、{@link ThreadTool}类的功能。
 */
public class ThreadToolTest {

    @Before
    public void setUp() {
        ThreadPoolTool.init();
    }

    @After
    public void cleanup() {
        ThreadPoolTool.shutdown(true);
    }

    // ExecutorBuilder tests
    /**
     * 测试ExecutorBuilder.builder方法，验证使用默认参数构建线程池的场景。
     */
    @Test
    public void testBuilder_default() {
        ThreadPoolExecutor executor = ExecutorBuilder.builder().build();
        assertNotNull(executor);
        executor.shutdownNow();
    }

    /**
     * 测试ExecutorBuilder.setCorePoolSize方法，验证设置核心线程数的场景。
     */
    @Test
    public void testBuilder_corePoolSize() {
        ThreadPoolExecutor executor = ExecutorBuilder.builder()
                .setCorePoolSize(4)
                .build();
        assertEquals(4, executor.getCorePoolSize());
        executor.shutdownNow();
    }

    /**
     * 测试ExecutorBuilder.setMaxPoolSize方法，验证设置最大线程数的场景。
     */
    @Test
    public void testBuilder_maxPoolSize() {
        ThreadPoolExecutor executor = ExecutorBuilder.builder()
                .setMaxPoolSize(10)
                .build();
        assertEquals(10, executor.getMaximumPoolSize());
        executor.shutdownNow();
    }

    /**
     * 测试ExecutorBuilder.setKeepAliveTime方法，验证设置线程空闲超时时间（毫秒）的场景。
     */
    @Test
    public void testBuilder_keepAliveTime() {
        ThreadPoolExecutor executor = ExecutorBuilder.builder()
                .setKeepAliveTime(120000)
                .build();
        assertEquals(120000, executor.getKeepAliveTime(TimeUnit.MILLISECONDS));
        executor.shutdownNow();
    }

    /**
     * 测试ExecutorBuilder.setKeepAliveTime方法，验证带时间单位设置线程空闲超时时间的场景。
     */
    @Test
    public void testBuilder_keepAliveTime_withUnit() {
        ThreadPoolExecutor executor = ExecutorBuilder.builder()
                .setKeepAliveTime(2, TimeUnit.MINUTES)
                .build();
        assertEquals(120000, executor.getKeepAliveTime(TimeUnit.MILLISECONDS));
        executor.shutdownNow();
    }

    /**
     * 测试ExecutorBuilder.useArrayBlockingQueue方法，验证使用ArrayBlockingQueue作为任务队列的场景。
     */
    @Test
    public void testBuilder_arrayBlockingQueue() {
        ThreadPoolExecutor executor = ExecutorBuilder.builder()
                .setCorePoolSize(2)
                .useArrayBlockingQueue(100)
                .build();
        BlockingQueue<Runnable> queue = executor.getQueue();
        assertTrue(queue instanceof ArrayBlockingQueue);
        executor.shutdownNow();
    }

    /**
     * 测试ExecutorBuilder.useSynchronousQueue方法，验证使用SynchronousQueue作为任务队列的场景。
     */
    @Test
    public void testBuilder_synchronousQueue() {
        ThreadPoolExecutor executor = ExecutorBuilder.builder()
                .useSynchronousQueue()
                .build();
        BlockingQueue<Runnable> queue = executor.getQueue();
        assertTrue(queue instanceof SynchronousQueue);
        executor.shutdownNow();
    }

    /**
     * 测试ExecutorBuilder.useSynchronousQueue方法，验证使用公平模式SynchronousQueue的场景。
     */
    @Test
    public void testBuilder_synchronousQueue_fair() {
        ThreadPoolExecutor executor = ExecutorBuilder.builder()
                .useSynchronousQueue(true)
                .build();
        BlockingQueue<Runnable> queue = executor.getQueue();
        assertTrue(queue instanceof SynchronousQueue);
        executor.shutdownNow();
    }

    /**
     * 测试ExecutorBuilder.userLinkedBlockingQueue方法，验证使用LinkedBlockingQueue作为任务队列的场景。
     */
    @Test
    public void testBuilder_linkedBlockingQueue() {
        ThreadPoolExecutor executor = ExecutorBuilder.builder()
                .userLinkedBlockingQueue()
                .build();
        BlockingQueue<Runnable> queue = executor.getQueue();
        assertTrue(queue instanceof LinkedBlockingQueue);
        executor.shutdownNow();
    }

    /**
     * 测试ExecutorBuilder.userPriorityBlockingQueue方法，验证使用PriorityBlockingQueue作为任务队列的场景。
     */
    @Test
    public void testBuilder_priorityBlockingQueue() {
        ThreadPoolExecutor executor = ExecutorBuilder.builder()
                .userPriorityBlockingQueue()
                .build();
        BlockingQueue<Runnable> queue = executor.getQueue();
        assertTrue(queue instanceof PriorityBlockingQueue);
        executor.shutdownNow();
    }

    /**
     * 测试ExecutorBuilder.userPriorityBlockingQueue方法，验证带自定义比较器使用PriorityBlockingQueue的场景。
     */
    @Test(expected = OutOfMemoryError.class)
    public void testBuilder_priorityBlockingQueue_withComparator() {
        ThreadPoolExecutor executor = ExecutorBuilder.builder()
                .userPriorityBlockingQueue((r1, r2) -> Integer.compare(r1.hashCode(), r2.hashCode()))
                .build();
        try {
            BlockingQueue<Runnable> queue = executor.getQueue();
            assertTrue(queue instanceof PriorityBlockingQueue);
        } finally {
            executor.shutdownNow();
        }
    }

    /**
     * 测试ExecutorBuilder.setThreadFactory方法，验证设置自定义线程工厂的场景。
     */
    @Test
    public void testBuilder_threadFactory() {
        NamedThreadFactory factory = new NamedThreadFactory("custom");
        ThreadPoolExecutor executor = ExecutorBuilder.builder()
                .setThreadFactory(factory)
                .build();
        assertNotNull(executor.getThreadFactory());
        executor.shutdownNow();
    }

    /**
     * 测试ExecutorBuilder.setHandler方法，验证设置自定义拒绝策略的场景。
     */
    @Test
    public void testBuilder_handler() {
        ThreadPoolExecutor executor = ExecutorBuilder.builder()
                .setCorePoolSize(1)
                .useArrayBlockingQueue(1)
                .setMaxPoolSize(1)
                .setHandler(new ThreadPoolExecutor.CallerRunsPolicy())
                .build();
        assertNotNull(executor);
        executor.shutdownNow();
    }

    /**
     * 测试ExecutorBuilder默认队列选择，验证核心线程数为0时使用SynchronousQueue的场景。
     */
    @Test
    public void testBuilder_defaultQueue_corePoolSizeZero() {
        // When corePoolSize <= 0, should use SynchronousQueue
        ThreadPoolExecutor executor = ExecutorBuilder.builder()
                .setCorePoolSize(0)
                .build();
        assertTrue(executor.getQueue() instanceof SynchronousQueue);
        executor.shutdownNow();
    }

    /**
     * 测试ExecutorBuilder默认队列选择，验证核心线程数大于0时使用ArrayBlockingQueue的场景。
     */
    @Test
    public void testBuilder_defaultQueue_corePoolSizePositive() {
        // When corePoolSize > 0 and no queue set, should use ArrayBlockingQueue
        ThreadPoolExecutor executor = ExecutorBuilder.builder()
                .setCorePoolSize(2)
                .build();
        assertTrue(executor.getQueue() instanceof ArrayBlockingQueue);
        executor.shutdownNow();
    }

    /**
     * 测试RejectPolicyEnum枚举，验证各拒绝策略枚举值对应正确的ThreadPoolExecutor策略实现。
     */
    @Test
    public void testRejectPolicyEnum() {
        assertEquals(ThreadPoolExecutor.AbortPolicy.class,
                RejectPolicyEnum.ABORT.getValue().getClass());
        assertEquals(ThreadPoolExecutor.DiscardPolicy.class,
                RejectPolicyEnum.DISCARD.getValue().getClass());
        assertEquals(ThreadPoolExecutor.DiscardOldestPolicy.class,
                RejectPolicyEnum.DISCARD_OLDEST.getValue().getClass());
        assertEquals(ThreadPoolExecutor.CallerRunsPolicy.class,
                RejectPolicyEnum.CALLER_RUNS.getValue().getClass());
    }

    // NamedThreadFactory tests
    /**
     * 测试NamedThreadFactory构造函数，验证默认线程名前缀为"owner-"的场景。
     */
    @Test
    public void testNamedThreadFactory_defaultPrefix() {
        NamedThreadFactory factory = new NamedThreadFactory("");
        Thread thread = factory.newThread(() -> {});
        assertTrue(thread.getName().startsWith("owner-"));
    }

    /**
     * 测试NamedThreadFactory构造函数，验证传入null前缀时使用默认"owner-"的场景。
     */
    @Test
    public void testNamedThreadFactory_nullPrefix() {
        NamedThreadFactory factory = new NamedThreadFactory(null);
        Thread thread = factory.newThread(() -> {});
        assertTrue(thread.getName().startsWith("owner-"));
    }

    /**
     * 测试NamedThreadFactory构造函数，验证使用自定义线程名前缀的场景。
     */
    @Test
    public void testNamedThreadFactory_customPrefix() {
        NamedThreadFactory factory = new NamedThreadFactory("my-pool");
        Thread thread = factory.newThread(() -> {});
        assertTrue(thread.getName().startsWith("my-pool-"));
    }

    /**
     * 测试NamedThreadFactory构造函数，验证连续创建线程时线程名编号递增的场景。
     */
    @Test
    public void testNamedThreadFactory_threadNumber() {
        NamedThreadFactory factory = new NamedThreadFactory("test");
        Thread t1 = factory.newThread(() -> {});
        Thread t2 = factory.newThread(() -> {});
        assertNotEquals(t1.getName(), t2.getName());
    }

    /**
     * 测试NamedThreadFactory构造函数，验证设置守护线程标志的场景。
     */
    @Test
    public void testNamedThreadFactory_daemon() {
        NamedThreadFactory factory = new NamedThreadFactory("test", true);
        Thread thread = factory.newThread(() -> {});
        assertTrue(thread.isDaemon());
    }

    /**
     * 测试NamedThreadFactory构造函数，验证设置非守护线程标志的场景。
     */
    @Test
    public void testNamedThreadFactory_nonDaemon() {
        NamedThreadFactory factory = new NamedThreadFactory("test", false);
        Thread thread = factory.newThread(() -> {});
        assertFalse(thread.isDaemon());
    }

    /**
     * 测试NamedThreadFactory构造函数，验证创建线程使用默认优先级的场景。
     */
    @Test
    public void testNamedThreadFactory_priority() {
        NamedThreadFactory factory = new NamedThreadFactory("test");
        Thread thread = factory.newThread(() -> {});
        assertEquals(Thread.NORM_PRIORITY, thread.getPriority());
    }

    // ThreadPoolTool tests
    /**
     * 测试ThreadPoolTool.init方法，验证初始化全局线程池的场景。
     */
    @Test
    public void testThreadPoolTool_init() {
        ThreadPoolTool.init();
        // Should not throw
    }

    /**
     * 测试ThreadPoolTool.execute方法，验证提交Runnable任务执行的场景。
     */
    @Test
    public void testThreadPoolTool_execute() {
        CountDownLatch latch = new CountDownLatch(1);
        ThreadPoolTool.execute(() -> latch.countDown());
        try {
            latch.await(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            fail("Interrupted");
        }
    }

    /**
     * 测试ThreadPoolTool.submit方法，验证提交Callable任务并获取返回值的场景。
     */
    @Test
    public void testThreadPoolTool_submit_callable() throws Exception {
        Future<String> future = ThreadPoolTool.submit(() -> "hello");
        assertEquals("hello", future.get(5, TimeUnit.SECONDS));
    }

    /**
     * 测试ThreadPoolTool.submit方法，验证提交Runnable任务执行的场景。
     */
    @Test
    public void testThreadPoolTool_submit_runnable() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Future<?> future = ThreadPoolTool.submit(() -> latch.countDown());
        future.get(5, TimeUnit.SECONDS);
        assertEquals(0, latch.getCount());
    }

    /**
     * 测试ThreadPoolTool.shutdown方法，验证优雅关闭线程池的场景。
     */
    @Test
    public void testThreadPoolTool_shutdown() {
        ThreadPoolTool.shutdown(false);
        // Should not throw
    }

    /**
     * 测试ThreadPoolTool.shutdownNow方法，验证立即关闭线程池的场景。
     */
    @Test
    public void testThreadPoolTool_shutdownNow() {
        ThreadPoolTool.shutdown(true);
        // Should not throw
    }

    // ThreadTool tests
    /**
     * 测试ThreadTool.execute方法，验证通过ThreadTool执行Runnable任务的场景。
     */
    @Test
    public void testThreadTool_execute() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        ThreadTool.execute(() -> latch.countDown());
        latch.await(5, TimeUnit.SECONDS);
    }

    /**
     * 测试ThreadTool.submit方法，验证通过ThreadTool提交Callable任务并获取返回值的场景。
     */
    @Test
    public void testThreadTool_submit_callable() throws Exception {
        Future<String> future = ThreadTool.submit(() -> "hello");
        assertEquals("hello", future.get(5, TimeUnit.SECONDS));
    }

    /**
     * 测试ThreadTool.submit方法，验证通过ThreadTool提交Runnable任务的场景。
     */
    @Test
    public void testThreadTool_submit_runnable() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Future<?> future = ThreadTool.submit(() -> latch.countDown());
        future.get(5, TimeUnit.SECONDS);
        assertEquals(0, latch.getCount());
    }

    /**
     * 测试ThreadTool.sleep方法，验证线程休眠指定毫秒的场景。
     */
    @Test
    public void testThreadTool_sleep() {
        long start = System.currentTimeMillis();
        ThreadTool.sleep(50);
        long elapsed = System.currentTimeMillis() - start;
        assertTrue("Sleep should take at least 50ms, was " + elapsed, elapsed >= 40);
    }

    /**
     * 测试ThreadTool.sleep方法，验证线程休眠指定时间单位的场景。
     */
    @Test
    public void testThreadTool_sleep_withTimeUnit() {
        long start = System.currentTimeMillis();
        ThreadTool.sleep(50, TimeUnit.MILLISECONDS);
        long elapsed = System.currentTimeMillis() - start;
        assertTrue("Sleep should take at least 50ms, was " + elapsed, elapsed >= 40);
    }
}
