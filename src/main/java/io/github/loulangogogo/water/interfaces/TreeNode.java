package io.github.loulangogogo.water.interfaces;

import io.github.loulangogogo.water.tool.TreeTool;

import java.util.List;

/*********************************************************
 ** 使用{@link TreeTool}工具需要实现的接口
 ** 
 ** @author loulan
 ** @since JDK-1.8
 *********************************************************/
public interface TreeNode<T> {

    /**
     * 树形判断使用的id
     *
     * @return id值
     * @author :loulan
     */
    Object getId();

    /**
     * 树形判断使用的pid
     *
     * @return pid值
     * @author :loulan
     */
    Object getPid();

    /**
     * 用来添加字迹的方法
     *
     * @param children 子集
     * @author :loulan
     */
    void setChildren(List<T> children);
}
