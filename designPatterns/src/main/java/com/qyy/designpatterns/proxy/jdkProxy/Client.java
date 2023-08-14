package com.qyy.designpatterns.proxy.jdkProxy;

/**
 * @author Qyy
 * @date 2023/8/2 19:43
 */
public class Client {
    public static void main(String[] args) {
        ProxyFactory proxyFactory = new ProxyFactory(new Zfb());
        Pay pay = (Pay) proxyFactory.getProxyObject();
        pay.pay();
    }
}
