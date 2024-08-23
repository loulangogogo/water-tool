package io.github.loulangogogo.water.test.bean;

import io.github.loulangogogo.water.bean.BeanTool;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/*********************************************************
 ** 对象工具测试类
 **
 ** @author loulan
 ** @since 8
 *********************************************************/
public class BeanToolTest {

    @Test
    public void beanToMap() {
        User user = new User();
        user.setAge(12);
        user.setName("asdfasd");
        Map<String, Object> map = BeanTool.beanToMap(user);
        System.out.println(map);
    }

    @Test
    public void mapToBean() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "asdfas");
        map.put("age", 23);
        map.put("sex", 1);

        User user = new User();
        BeanTool.mapToBean(map, user);
        System.out.println(user);
    }

}


