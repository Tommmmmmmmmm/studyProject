package com.qyy.designpatterns.proxy.jdkProxy;

/**
 * @author Qyy
 * @date 2023/8/2 19:36
 */
public class Wx implements Pay{
    @Override
    public void pay() {
        System.out.println("微信支付！");
    }
}
