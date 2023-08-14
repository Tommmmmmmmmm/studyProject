package com.qyy.designpatterns.proxy.jdkProxy;

/**
 * @author Qyy
 * @date 2023/8/2 19:35
 */
public class Zfb implements Pay{

    @Override
    public void pay() {
        System.out.println("使用支付宝支付");
    }
}
