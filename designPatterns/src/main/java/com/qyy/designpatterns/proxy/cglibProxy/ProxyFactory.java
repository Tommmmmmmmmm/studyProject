package com.qyy.designpatterns.proxy.cglibProxy;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Qyy
 * @date 2023/8/2 18:02
 */
public class ProxyFactory implements MethodInterceptor {
    private HouseOwner houseOwner=new HouseOwner();

    public HouseOwner getProxyObject(){
        Enhancer enhancer = new Enhancer();
        //设置父类的字节码对象。指定父类？
        enhancer.setSuperclass(HouseOwner.class);
        enhancer.setCallback(this);
        HouseOwner proxyObject = (HouseOwner)enhancer.create();
        return proxyObject;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("cglib代理！");
        Object obj = method.invoke(houseOwner, objects);
        return obj;
    }
}
