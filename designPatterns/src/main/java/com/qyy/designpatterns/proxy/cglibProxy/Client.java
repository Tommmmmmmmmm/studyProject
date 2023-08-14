package com.qyy.designpatterns.proxy.cglibProxy;

/**
 * @author Qyy
 * @date 2023/8/2 18:05
 */
public class Client {
    public static void main(String[] args) {
//        ProxyFactory proxyFactory = new ProxyFactory();
//        HouseOwner proxyObject = proxyFactory.getProxyObject();
//        proxyObject.rent();
        CGLibInterceptor cgLibInterceptor = new CGLibInterceptor(new HouseOwner());
        HouseOwner houseOwner = (HouseOwner)cgLibInterceptor.getProxyObject();
        houseOwner.rent();

    }
}
