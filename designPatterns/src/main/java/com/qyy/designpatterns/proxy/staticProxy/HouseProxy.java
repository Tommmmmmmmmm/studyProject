package com.qyy.designpatterns.proxy.staticProxy;

/**
 * @author Qyy
 * @date 2023/8/2 17:55
 */
public class HouseProxy {
    private House house=new House();

    public HouseProxy(House house) {
        System.out.println("我是二房东！我要收手续费");
        house.rent();
    }
}
