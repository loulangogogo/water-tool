package io.github.loulangogogo.water.tool;

import java.util.UUID;

/*********************************************************
 ** Id的通用工具
 ** 
 ** @author 楼兰
 ** @since JDK-1.8
 *********************************************************/
public class IdTool {

    /**
     * 生成UUID,不带横线的
     *
     * @return 不带横线的UUID
     * @author :loulan
     */
    public static String simpleUUID() {
        return randomUUID().replace("-", "");
    }

    /**
     * 生成UUID,带横线的
     *
     * @return UUID
     * @author :loulan
     */
    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }
}
