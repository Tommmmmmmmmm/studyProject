package com.qyy.reflect;

/**
 * @author Qyy
 * @date 2023/8/12 11:13
 */
public class User {
    private String name;
    private int age;
    public void speak(){
        System.out.println("你好");
    }
    public User() {
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

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

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
