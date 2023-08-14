package com.qyy.designpatterns.proxy.jdkProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Qyy
 * @date 2023/8/2 19:37
 */
public class ProxyFactory implements InvocationHandler {
    private Object target;

    public ProxyFactory(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("代理对象增强");
        Object obj = method.invoke(target, args);
        return obj;
    }

    public Object getProxyObject(){
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }
}
