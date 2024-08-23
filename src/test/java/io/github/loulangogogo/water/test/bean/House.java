package io.github.loulangogogo.water.test.bean;

import java.io.Serializable;
import java.util.List;

/*********************************************************
 ** 用户对象测试类
 **
 ** @author loulan
 ** @since 8
 *********************************************************/
class House implements Serializable {
    private String name;
    private int age;

    private List<User> users;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
