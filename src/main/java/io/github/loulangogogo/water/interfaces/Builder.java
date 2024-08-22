package io.github.loulangogogo.water.interfaces;

import java.io.Serializable;

/*********************************************************
 ** 建造者模式的接口定义
 ** 
 ** @author loulan
 ** @since JDK-1.8
 *********************************************************/
public interface Builder<T> extends Serializable {

    /**
     * 构建对象
     *
     * @return 构建出来的对象
     * @author :loulan
     */
    T build();
}
