package io.github.loulangogogo.water.test.tool;

import io.github.loulangogogo.water.interfaces.TreeNode;
import io.github.loulangogogo.water.tool.TreeTool;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * 测试{@link io.github.loulangogogo.water.tool.TreeTool}类的功能。
 */
public class TreeToolTest {

    public static class Category implements TreeNode<Category> {
        private String id;
        private Object pid;
        private String name;
        private List<Category> children;

        public Category() {}

        public Category(String id, Object pid, String name) {
            this.id = id;
            this.pid = pid;
            this.name = name;
        }

        @Override
        public Object getId() { return id; }
        public void setId(String id) { this.id = id; }
        @Override
        public Object getPid() { return pid; }
        public void setPid(Object pid) { this.pid = pid; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        @Override
        public void setChildren(List<Category> children) { this.children = children; }
        public List<Category> getChildren() { return children; }
    }

    /**
     * 测试toTree方法，验证使用Map构建树形结构。
     */
    @Test
    public void testToTree_withMap() {
        List<Category> nodes = new ArrayList<>();
        nodes.add(new Category("1", "-1", "Root"));
        nodes.add(new Category("2", "1", "Child1"));
        nodes.add(new Category("3", "1", "Child2"));
        nodes.add(new Category("4", "2", "GrandChild1"));

        List<Map<String, Object>> tree = TreeTool.toTree(nodes, "id", "pid", "-1");
        assertNotNull(tree);
        assertEquals(1, tree.size());
        Map<String, Object> root = tree.get(0);
        assertEquals("Root", root.get("name"));
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> children = (List<Map<String, Object>>) root.get("children");
        assertNotNull(children);
        assertEquals(2, children.size());
    }

    /**
     * 测试toTree方法，验证使用TreeNode接口构建树形结构。
     */
    @Test
    public void testToTree_withTreeNode() {
        List<Category> nodes = new ArrayList<>();
        nodes.add(new Category("1", "-1", "Root"));
        nodes.add(new Category("2", "1", "Child1"));
        nodes.add(new Category("3", "1", "Child2"));

        List<Category> tree = TreeTool.toTree(nodes, "-1");
        assertNotNull(tree);
        assertEquals(1, tree.size());
        assertEquals("Root", tree.get(0).getName());
        assertNotNull(tree.get(0).getChildren());
        assertEquals(2, tree.get(0).getChildren().size());
    }

    /**
     * 测试parallelToTree方法，验证使用Map并行构建树形结构。
     */
    @Test
    public void testParallelToTree_withMap() {
        List<Category> nodes = new ArrayList<>();
        nodes.add(new Category("1", "-1", "Root"));
        nodes.add(new Category("2", "1", "Child1"));

        List<Map<String, Object>> tree = TreeTool.parallelToTree(nodes, "id", "pid", "-1");
        assertNotNull(tree);
        assertEquals(1, tree.size());
    }

    /**
     * 测试parallelToTree方法，验证使用TreeNode接口并行构建树形结构。
     */
    @Test
    public void testParallelToTree_withTreeNode() {
        List<Category> nodes = new ArrayList<>();
        nodes.add(new Category("1", "-1", "Root"));
        nodes.add(new Category("2", "1", "Child1"));

        List<Category> tree = TreeTool.parallelToTree(nodes, "-1");
        assertNotNull(tree);
        assertEquals(1, tree.size());
    }

    /**
     * 测试toTree方法，验证空列表构建树返回空结果。
     */
    @Test
    public void testToTree_emptyList() {
        List<Map<String, Object>> tree = TreeTool.toTree(new ArrayList<>(), "id", "pid", "-1");
        assertNotNull(tree);
        assertTrue(tree.isEmpty());
    }

    /**
     * 测试toTree方法，验证null列表构建树返回空结果。
     */
    @Test
    public void testToTree_nullList() {
        List<Map<String, Object>> tree = TreeTool.toTree(null, "id", "pid", "-1");
        assertNotNull(tree);
        assertTrue(tree.isEmpty());
    }

    /**
     * 测试toTree方法，验证idName为null时抛出异常。
     */
    @Test(expected = Exception.class)
    public void testToTree_nullIdName() {
        TreeTool.toTree(new ArrayList<>(), null, "pid", "-1");
    }

    /**
     * 测试toTree方法，验证pidName为null时抛出异常。
     */
    @Test(expected = Exception.class)
    public void testToTree_nullPidName() {
        TreeTool.toTree(new ArrayList<>(), "id", null, "-1");
    }

    /**
     * 测试toTree方法，验证pidValue为null时抛出异常。
     */
    @Test(expected = Exception.class)
    public void testToTree_nullPidValue() {
        TreeTool.toTree(new ArrayList<>(), "id", "pid", null);
    }

    /**
     * 测试toTree方法，验证无子节点时children为null。
     */
    @Test
    public void testToTree_noChildren() {
        List<Category> nodes = new ArrayList<>();
        nodes.add(new Category("1", "-1", "Root"));

        List<Category> tree = TreeTool.toTree(nodes, "-1");
        assertEquals(1, tree.size());
        assertNull(tree.get(0).getChildren());
    }

    /**
     * 测试toTree方法，验证多个根节点的树形结构。
     */
    @Test
    public void testToTree_multipleRoots() {
        List<Category> nodes = new ArrayList<>();
        nodes.add(new Category("1", "-1", "Root1"));
        nodes.add(new Category("2", "-1", "Root2"));
        nodes.add(new Category("3", "1", "Child1"));

        List<Category> tree = TreeTool.toTree(nodes, "-1");
        assertEquals(2, tree.size());
    }
}
