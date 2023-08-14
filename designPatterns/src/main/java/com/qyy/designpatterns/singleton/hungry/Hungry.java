package com.qyy.designpatterns.singleton.hungry;

/** 单例模式之饿汉
 * @author Qyy
 * @date 2023/8/2 21:22
 */
public class Hungry {
    private Hungry() {
    }
    private static Hungry hungry=new Hungry();
    public static Hungry newInstance(){
        return hungry;
    }
}
