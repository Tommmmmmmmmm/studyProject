package com.qyy.reflect;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Qyy
 * @date 2023/8/12 11:15
 */
public class TestUser {
    @Test
    public void test(){
        //通过new关键字来实例化对象,通过构造器来
//        User user = new User();
//        user.setName("覃艳艳");
//        user.setAge(28);
//        System.out.println(user);
    }
    @Test
    public void testReflect() throws Exception {
        //获取到了字节码对象？
        Class<User> clazz = User.class;
        //获取构造方法，通过newInstance()方法来进行实例化，私有的话要设置强吻即强制访问Accessible()为true
        Constructor<User> constructor = clazz.getDeclaredConstructor(String.class,int.class);
        User user = constructor.newInstance("a",2);
        System.out.println(user);
        //获取属性名
        Field[] fields = clazz.getDeclaredFields();
        for (Field field:fields){
            System.out.println(field.getName());
        }
        //获取方法
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (method.getName().equals("speak")){
                method.invoke(user);
            }
        }

    }
}
