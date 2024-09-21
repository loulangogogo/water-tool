package io.github.loulangogogo.water.test.tool;

import io.github.loulangogogo.water.enums.MaskingDataTypeEnum;
import io.github.loulangogogo.water.tool.StrTool;
import org.junit.Test;

/*********************************************************
 ** 测试字符串工具类
 **
 ** @author loulan
 ** @since 8
 *********************************************************/
public class StrToolTest {

    @Test
    public void dataMaskingTest() {
        String name = "我是中国人";
        System.out.println(StrTool.dataMasking(name, MaskingDataTypeEnum.NAME));
    }
}
