package io.github.loulangogogo.water.stream;

import io.github.loulangogogo.water.collection.CollTool;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/*********************************************************
 ** Stream的收集器工具
 ** 
 ** @author 楼兰
 ** @since 8
 *********************************************************/
public class CollectorTool {

    /**
     * 将流数据转换为指定类型的数组
     *
     * @param <T>   泛型
     * @param supplierArray 函数式方法，主要是用来提供数组对象，{@code （size）-> new String[size]},方法参数为提供的数组的大小【注意提供数组类型必须和流类型一致】
     * @return Array收集器
     * @author :loulan
     */
    public static <T> Collector<T, ?, T[]> toArray(Function<Integer, T[]> supplierArray) {
        return Collector.of(
                () -> CollTool.list(),
                (l, t) -> l.add(t),
                (l1, l2) -> {
                    l1.addAll(l2);
                    return l1;
                },
                (l) -> l.toArray(supplierArray.apply(l.size())),
                Collector.Characteristics.CONCURRENT
        );
    }

    /**
     * 将流数据转换为Collection集合收集器
     *
     * @param <T>   泛型
     * @param <C>   继承实现{@link Collection}的泛型
     * @param collectionFactory 集合对象提供。集合对象必须是继承了Collection的
     * @return Collection集合收集器
     * @author :loulan
     */
    public static <T, C extends Collection<T>> Collector<T, ?, C> toCollection(Supplier<C> collectionFactory) {
        return Collectors.toCollection(collectionFactory);
    }

    /**
     * list集合收集器。{@link Collectors#toList()}
     *
     * @param <T>   泛型
     * @return list集合收集器
     * @author :loulan
     */
    public static <T> Collector<T, ?, List<T>> toList() {
        return Collectors.toList();
    }

    /**
     * set集合收集器。{@link Collectors#toSet()}
     *
     * @param <T>   泛型
     * @return set集合收集器
     * @author :loulan
     */
    public static <T> Collector<T, ?, Set<T>> toSet() {
        return Collectors.toSet();
    }

    /**
     * 将流数据转换为一个字符串的收集器{@link Collectors#joining()}
     *
     * @return 字符串收集器
     * @author :loulan
     */
    public static Collector<CharSequence, ?, String> toStr() {
        return Collectors.joining();
    }

    /**
     * 将流数据转换为一个字符串的收集器(包含分隔符){@link Collectors#joining(CharSequence)}
     *
     * @param delimiter 分隔符
     * @return 字符串收集器
     * @author :loulan
     */
    public static Collector<CharSequence, ?, String> toStr(CharSequence delimiter) {
        return Collectors.joining(delimiter);
    }

    /**
     * 将流数据转换为一个字符串的收集器(包含分隔符，前缀，后缀){@link Collectors#joining(CharSequence, CharSequence, CharSequence)}
     *
     * @param delimiter 分隔符
     * @param prefix    前缀
     * @param suffix    后缀
     * @return 字符串收集器
     * @author :loulan
     */
    public static Collector<CharSequence, ?, String> toStr(CharSequence delimiter, CharSequence prefix, CharSequence suffix) {
        return Collectors.joining(delimiter, prefix, suffix);
    }

    /**
     * 将流数据转换为指定key和value的map收集器。（如果key出现重复，那么会抛出异常，因为使用map的merge方法，重复的key设置为抛出异常）
     *
     * @param <T>   泛型
     * @param <K>   泛型
     * @param <U>   泛型
     * @param keyMapper   key的{@link Function}函数式接口
     * @param valueMapper value的{@link Function}函数式接口
     * @return 流数据的map收集器
     * @author :loulan
     */
    public static <T, K, U> Collector<T, ?, Map<K, U>> toMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper) {
        return Collectors.toMap(keyMapper, valueMapper);
    }

    /**
     * 将流数据转换为指定key和value的map收集器。（如果说{@link CollectorTool#toMap(Function, Function)}会抛出异常，那么这个方法的最后一个参数就是异常合并处理的参数）
     *
     * @param <T>   泛型
     * @param <K>   泛型
     * @param <U>   泛型
     * @param keyMapper     key的{@link Function}函数式接口
     * @param valueMapper   value的{@link Function}函数式接口
     * @param mergeFunction 合并的函数方法。{@link CollectorTool#toMap(Function, Function)}会抛出异常就是这个参数执行抛出异常导致的。
     * @return 流数据的map收集器
     * @author :loulan
     */
    public static <T, K, U> Collector<T, ?, Map<K, U>> toMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper, BinaryOperator<U> mergeFunction) {
        return Collectors.toMap(keyMapper, valueMapper, mergeFunction);
    }

    /**
     * 将流数据转换为指定key和value的map收集器。（如果说{@link CollectorTool#toMap(Function, Function)}会抛出异常，那么这个方法的最后一个参数就是异常合并处理的参数）
     *
     * @param <T>   泛型
     * @param <K>   泛型
     * @param <U>   泛型
     * @param <M>   继承{@link Map}的泛型
     * @param keyMapper     key的{@link Function}函数式接口
     * @param valueMapper   value的{@link Function}函数式接口
     * @param mergeFunction 合并的函数方法。{@link CollectorTool#toMap(Function, Function)}会抛出异常就是这个参数执行抛出异常导致的。
     * @param mapSupplier   这个可以指定使用的返回的map的类型
     * @return 流数据的map收集器
     * @author :loulan
     */
    public static <T, K, U, M extends Map<K, U>> Collector<T, ?, M> toMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper, BinaryOperator<U> mergeFunction, Supplier<M> mapSupplier) {
        return Collectors.toMap(keyMapper, valueMapper, mergeFunction, mapSupplier);
    }

    /**
     * 将流数据转换为指定key和value的ConcurrentMap收集器。（如果key出现重复，那么会抛出异常，因为使用map的merge方法，重复的key设置为抛出异常）
     *
     * @param <T>   泛型
     * @param <K>   泛型
     * @param <U>   泛型
     * @param keyMapper   key的{@link Function}函数式接口
     * @param valueMapper value的{@link Function}函数式接口
     * @return 流数据的ConcurrentMap收集器
     * @author :loulan
     */
    static <T, K, U> Collector<T, ?, ConcurrentMap<K, U>> toConcurrentMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper) {
        return Collectors.toConcurrentMap(keyMapper, valueMapper);
    }

    /**
     * 将流数据转换为指定key和value的ConcurrentMap收集器。（如果说{@link CollectorTool#toConcurrentMap(Function, Function)}会抛出异常，那么这个方法的最后一个参数就是异常合并处理的参数）
     *
     * @param <T>   泛型
     * @param <K>   泛型
     * @param <U>   泛型
     * @param keyMapper     key的{@link Function}函数式接口
     * @param valueMapper   value的{@link Function}函数式接口
     * @param mergeFunction 合并的函数方法。{@link CollectorTool#toConcurrentMap(Function, Function)}会抛出异常就是这个参数执行抛出异常导致的。
     * @return 流数据的ConcurrentMap收集器
     * @author :loulan
     */
    public static <T, K, U> Collector<T, ?, ConcurrentMap<K, U>> toConcurrentMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper, BinaryOperator<U> mergeFunction) {
        return Collectors.toConcurrentMap(keyMapper, valueMapper, mergeFunction);
    }

    /**
     * 将流数据转换为指定key和value的ConcurrentMap收集器。（如果说{@link CollectorTool#toConcurrentMap(Function, Function)}会抛出异常，那么这个方法的最后一个参数就是异常合并处理的参数）
     *
     * @param <T>   泛型
     * @param <K>   泛型
     * @param <U>   泛型
     * @param <M>   继承{@link ConcurrentMap}的泛型
     * @param keyMapper     key的{@link Function}函数式接口
     * @param valueMapper   value的{@link Function}函数式接口
     * @param mergeFunction 合并的函数方法。{@link CollectorTool#toConcurrentMap(Function, Function)}会抛出异常就是这个参数执行抛出异常导致的。
     * @param mapSupplier   这个可以指定使用的返回的ConcurrentMap的类型
     * @return 流数据的ConcurrentMap收集器
     * @author :loulan
     */
    public static <T, K, U, M extends ConcurrentMap<K, U>> Collector<T, ?, M> toConcurrentMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper, BinaryOperator<U> mergeFunction, Supplier<M> mapSupplier) {
        return Collectors.toConcurrentMap(keyMapper, valueMapper, mergeFunction, mapSupplier);
    }

    /**
     * 将流中的元素按照指定的key进行分组(分组用List集合表示)存储到Map集合中的收集器
     *
     * @param <T>   泛型
     * @param <K>   泛型
     * @param classifier 对元素进行分组的函数式方法（函数式方法）
     * @return 将流中的元素按照指定的key进行分组存储到Map集合中的收集器
     * @author :loulan
     */
    public static <T, K> Collector<T, ?, Map<K, List<T>>> groupingBy(Function<? super T, ? extends K> classifier) {
        return Collectors.groupingBy(classifier);
    }

    /**
     * 将流中的元素按照指定的key进行分组(分组用用指定的收集器收集)存储到Map集合中的收集器
     *
     * @param <T>   泛型
     * @param <K>   泛型
     * @param <A>   泛型
     * @param <D>   泛型
     * @param classifier 对元素进行分组的函数式方法（函数式方法）
     * @param downstream 分组使用的收集器，获取收集方法（就是将相同的key下的元素收集起来的收集器）
     * @return 将流中的元素按照指定的key进行分组存储到Map集合中的收集器
     * @author :loulan
     */
    public static <T, K, A, D> Collector<T, ?, Map<K, D>> groupingBy(Function<? super T, ? extends K> classifier, Collector<? super T, A, D> downstream) {
        return Collectors.groupingBy(classifier, downstream);
    }

    /**
     * 将流中的元素按照指定的key进行分组(分组用用指定的收集器收集)存储mapFactory提供的对象中
     *
     * @param <T>   泛型
     * @param <K>   泛型
     * @param <A>   泛型
     * @param <D>   泛型
     * @param <M>   继承{@link Map}的泛型
     * @param classifier 对元素进行分组的函数式方法（函数式方法）
     * @param mapFactory 提供这些分组数据的存储对象（该对象一定是继承了Map集合的）
     * @param downstream 分组使用的收集器，获取收集方法（就是将相同的key下的元素收集起来的收集器）
     * @return 将流中的元素按照指定的key进行分组存储到提供的对象中的收集器
     * @author :loulan
     */
    public static <T, K, D, A, M extends Map<K, D>> Collector<T, ?, M> groupingBy(Function<? super T, ? extends K> classifier, Supplier<M> mapFactory, Collector<? super T, A, D> downstream) {
        return Collectors.groupingBy(classifier, mapFactory, downstream);
    }

    /**
     * 将流中的元素按照指定的key进行分组(分组用List集合表示)存储到ConcurrentMap集合中的收集器。
     * 与{@link CollectorTool#groupingBy(Function)}区别就是返回的集合是线程安全的。
     *
     * @param <T>   泛型
     * @param <K>   泛型
     * @param classifier 对元素进行分组的函数式方法（函数式方法）
     * @return 将流中的元素按照指定的key进行分组存储到ConcurrentMap集合中的收集器
     * @author :loulan
     */
    public static <T, K> Collector<T, ?, ConcurrentMap<K, List<T>>> groupingByConcurrent(Function<? super T, ? extends K> classifier) {
        return Collectors.groupingByConcurrent(classifier);
    }

    /**
     * 将流中的元素按照指定的key进行分组(分组用用指定的收集器收集)存储到ConcurrentMap集合中的收集器。
     * 与{@link CollectorTool#groupingBy(Function, Collector)}区别就是返回的集合是线程安全的。
     *
     * @param <T>   泛型
     * @param <K>   泛型
     * @param <A>   泛型
     * @param <D>   泛型
     * @param classifier 对元素进行分组的函数式方法（函数式方法）
     * @param downstream 分组使用的收集器，获取收集方法（就是将相同的key下的元素收集起来的收集器）
     * @return 将流中的元素按照指定的key进行分组存储到ConcurrentMap集合中的收集器
     * @author :loulan
     */
    public static <T, K, A, D> Collector<T, ?, ConcurrentMap<K, D>> groupingByConcurrent(Function<? super T, ? extends K> classifier, Collector<? super T, A, D> downstream) {
        return Collectors.groupingByConcurrent(classifier, downstream);
    }

    /**
     * 将流中的元素按照指定的key进行分组(分组用用指定的收集器收集)存储mapFactory提供的对象中。
     * 与{@link CollectorTool#groupingBy(Function, Supplier, Collector)}区别就是返回的集合是线程安全的。
     *
     * @param <T>   泛型
     * @param <K>   泛型
     * @param <A>   泛型
     * @param <D>   泛型
     * @param <M>   继承{@link ConcurrentMap}的泛型
     * @param classifier 对元素进行分组的函数式方法（函数式方法）
     * @param mapFactory 提供这些分组数据的存储对象（该对象一定是继承了ConcurrentMap集合的）
     * @param downstream 分组使用的收集器，获取收集方法（就是将相同的key下的元素收集起来的收集器）
     * @return 将流中的元素按照指定的key进行分组存储到提供的对象中的收集器
     * @author :loulan
     */
    public static <T, K, A, D, M extends ConcurrentMap<K, D>> Collector<T, ?, M> groupingByConcurrent(Function<? super T, ? extends K> classifier, Supplier<M> mapFactory, Collector<? super T, A, D> downstream) {
        return Collectors.groupingByConcurrent(classifier, mapFactory, downstream);
    }

    /**
     * 将流进行维度转换(我更喜欢叫降维)收集的收集器.(类似{@link java.util.stream.Stream#map(Function)}方法)
     *
     * @param <T>   泛型
     * @param <U>   泛型
     * @param <A>   泛型
     * @param <R>   泛型
     * @param mapper     维度转换方法，其实就是一个函数式方法，将输进去的流元素返回为另一种元素作为流元素
     * @param downstream 对转换维度之后的流元素进行收集的收集器
     * @return 将流进行维度转换(我更喜欢叫降维)收集的收集器
     * @author :loulan
     */
    public static <T, U, A, R> Collector<T, ?, R> mapping(Function<? super T, ? extends U> mapper, Collector<? super U, A, R> downstream) {
        return Collectors.mapping(mapper, downstream);
    }

    /**
     * 将流中数据进行比较产生最大元素的收集器
     *
     * @param <T>   泛型
     * @param comparator 比较器
     * @return 将流中数据进行比较产生最大元素的收集器
     * @author :loulan
     */
    public static <T> Collector<T, ?, Optional<T>> maxBy(Comparator<? super T> comparator) {
        return Collectors.maxBy(comparator);
    }

    /**
     * 将流中数据进行比较产生最小元素的收集器
     *
     * @param <T>   泛型
     * @param comparator 比较器
     * @return 将流中数据进行比较产生最小元素的收集器
     * @author :loulan
     */
    public static <T> Collector<T, ?, Optional<T>> minBy(Comparator<? super T> comparator) {
        return Collectors.minBy(comparator);
    }

    /**
     * 将流元素按照Boolean分为两组（组为List集合）的收集器。
     * （完全可以使用{@link CollectorTool#groupingBy(Function)}来代替）
     *
     * @param <T>   泛型
     * @param predicate 进行Boolean判断的函数式方法
     * @return 将流元素按照Boolean分为两组的收集器
     * @author :loulan
     */
    public static <T> Collector<T, ?, Map<Boolean, List<T>>> partitioningBy(Predicate<? super T> predicate) {
        return Collectors.partitioningBy(predicate);
    }

    /**
     * 将流元素按照Boolean分为两组（组为指定的收集器收集）的收集器。
     * （完全可以使用{@link CollectorTool#groupingBy(Function, Collector)}来代替）
     *
     * @param <T>   泛型
     * @param <D>   泛型
     * @param <A>   泛型
     * @param predicate  进行Boolean判断的函数式方法
     * @param downstream 分组使用的收集器，获取收集方法（就是将相同的Boolean下的元素收集起来的收集器）
     * @return 将流元素按照Boolean分为两组的收集器
     * @author :loulan
     */
    public static <T, D, A> Collector<T, ?, Map<Boolean, D>> partitioningBy(Predicate<? super T> predicate, Collector<? super T, A, D> downstream) {
        return Collectors.partitioningBy(predicate, downstream);
    }

    /**
     * 对收集器进行加工转换的收集器
     *
     * @param <T>   泛型
     * @param <A>   泛型
     * @param <R>   泛型
     * @param <RR>   泛型
     * @param downstream 内部试用的收集器
     * @param finisher   收集完的数据要进行处理的函数式方法
     * @return 收集器进行加工转换的收集器
     * @author :loulan
     */
    public static <T, A, R, RR> Collector<T, A, RR> collectingAndThen(Collector<T, A, R> downstream, Function<R, RR> finisher) {
        return Collectors.collectingAndThen(downstream, finisher);
    }

    /**
     * 进行累积操操作的收集器。（类似于{@link java.util.stream.Stream#reduce(BinaryOperator)}）
     *
     * @param <T>   泛型
     * @param op 进行累积操作的函数式方法
     * @return 累积操操作的收集器
     * @author :loulan
     */
    public static <T> Collector<T, ?, Optional<T>> reducing(BinaryOperator<T> op) {
        return Collectors.reducing(op);
    }

    /**
     * 给定指定类型初始累积变量进行累积操操作的收集器。（类似于{@link java.util.stream.Stream#reduce(Object, BinaryOperator)}）
     *
     * @param <T>   泛型
     * @param identity 进行累积操作给定的初始变量（指定类型，也是流数据类型）
     * @param op       进行累积操作的函数式方法
     * @return 累积操操作的收集器
     * @author :loulan
     */
    public static <T> Collector<T, ?, T> reducing(T identity, BinaryOperator<T> op) {
        return Collectors.reducing(identity, op);
    }

    /**
     * 给定任意类型初始累积变量进行累积操操作的收集器。
     *
     * @param <T>   泛型
     * @param <U>   泛型
     * @param identity 进行累积操作给定的初始变量（任意类型）
     * @param mapper   由流数据类型转换为任意数据类型的映射方法
     * @param op       进行累积操作的函数式方法
     * @return 累积操操作的收集器
     * @author :loulan
     */
    public static <T, U> Collector<T, ?, U> reducing(U identity, Function<? super T, ? extends U> mapper, BinaryOperator<U> op) {
        return Collectors.reducing(identity, mapper, op);
    }

    /**
     * double类型的计算器收集器
     *
     * @param <T>   泛型
     * @param mapper 流数据到double类型的映射函数
     * @return double类型的计算器收集器
     * @author :loulan
     */
    public static <T> Collector<T, ?, DoubleSummaryStatistics> summarizingDouble(ToDoubleFunction<? super T> mapper) {
        return Collectors.summarizingDouble(mapper);
    }

    /**
     * Integer类型的计算器收集器
     *
     * @param <T>   泛型
     * @param mapper 流数据到Integer类型的映射函数
     * @return Integer类型的计算器收集器
     * @author :loulan
     */
    public static <T> Collector<T, ?, IntSummaryStatistics> summarizingInt(ToIntFunction<? super T> mapper) {
        return Collectors.summarizingInt(mapper);
    }

    /**
     * Long类型的计算器收集器
     *
     * @param <T>   泛型
     * @param mapper 流数据到Long类型的映射函数
     * @return Long类型的计算器收集器
     * @author :loulan
     */
    public static <T> Collector<T, ?, LongSummaryStatistics> summarizingLong(ToLongFunction<? super T> mapper) {
        return Collectors.summarizingLong(mapper);
    }

    /**
     * Double类型的求和收集器
     *
     * @param <T>   泛型
     * @param mapper 流数据到Double类型的映射函数
     * @return Double类型的求和收集器
     * @author :loulan
     */
    public static <T> Collector<T, ?, Double> summingDouble(ToDoubleFunction<? super T> mapper) {
        return Collectors.summingDouble(mapper);
    }

    /**
     * Integer类型的求和收集器
     *
     * @param <T>   泛型
     * @param mapper 流数据到Integer类型的映射函数
     * @return Integer类型的求和收集器
     * @author :loulan
     */
    public static <T> Collector<T, ?, Integer> summingInt(ToIntFunction<? super T> mapper) {
        return Collectors.summingInt(mapper);
    }

    /**
     * Long类型的求和收集器
     *
     * @param <T>   泛型
     * @param mapper 流数据到Long类型的映射函数
     * @return Long类型的求和收集器
     * @author :loulan
     */
    public static <T> Collector<T, ?, Long> summingLong(ToLongFunction<? super T> mapper) {
        return Collectors.summingLong(mapper);
    }

    /**
     * 将流数据转换为double类型平均值的收集器
     *
     * @param <T>   泛型
     * @param mapper 转换为double的函数式方法（函数式方法）
     * @return 流数据的double收集器
     * @author :loulan
     */
    public static <T> Collector<T, ?, Double> averagingDouble(ToDoubleFunction<? super T> mapper) {
        return Collectors.averagingDouble(mapper);
    }

    /**
     * 将流数据转换为Int类型平均值的收集器
     *
     * @param <T>   泛型
     * @param mapper 转换为int的函数式方法（函数式方法）
     * @return 流数据的int收集器
     * @author :loulan
     */
    public static <T> Collector<T, ?, Double> averagingInt(ToIntFunction<? super T> mapper) {
        return Collectors.averagingInt(mapper);
    }

    /**
     * 将流数据转换为Long类型平局之的收集器
     *
     * @param <T>   泛型
     * @param mapper 转换为Loing的函数式方法（函数式方法）
     * @return 流数据的Long收集器
     * @author :loulan
     */
    public static <T> Collector<T, ?, Double> averagingLong(ToLongFunction<? super T> mapper) {
        return Collectors.averagingLong(mapper);
    }

    /**
     * 统计流中元素的个数
     *
     * @param <T>   泛型
     * @return 流中元素个数
     * @author :loulan
     */
    public static <T> Collector<T, ?, Long> counting() {
        return Collectors.counting();
    }
}
