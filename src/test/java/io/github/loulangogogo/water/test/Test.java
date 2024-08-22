package io.github.loulangogogo.water.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/*********************************************************
 ** 测试类
 **
 ** @author loulan
 ** @since 8
 *********************************************************/
public class Test {

    private static final Logger LOG = LoggerFactory.getLogger(Test.class);

    /**
     * 单纯的测试
     * @author     :loulan
     * */
    @org.junit.Test
    public void test01() {
        LOG.debug("测试日志");
        LOG.info("测试日志");
        LOG.warn("测试日志");
        LOG.error("测试日志");
        LOG.trace("测试日志");
    }
}
