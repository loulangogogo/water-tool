package io.github.loulangogogo.water.test.tool;

import io.github.loulangogogo.water.tool.AssertTool;
import org.junit.Test;

/*********************************************************
 ** 断言工具的测试类
 **
 ** @author loulan
 ** @since 17
 *********************************************************/
public class AssertToolTest {

    @Test
    public void notEmpty1(){
        String[] a = {"1","2"};
        AssertTool.notEmpty(a, "滚蛋");
    }
}
