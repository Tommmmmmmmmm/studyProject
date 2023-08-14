package com.qyy.designpatterns.proxy.staticProxy;

/**
 * @author Qyy
 * @date 2023/8/2 17:58
 */
public class Client {
    public static void main(String[] args) {
        HouseProxy houseProxy = new HouseProxy(new House());
    }
}
