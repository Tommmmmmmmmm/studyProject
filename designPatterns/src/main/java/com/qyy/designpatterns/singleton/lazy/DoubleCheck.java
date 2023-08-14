package com.qyy.designpatterns.singleton.lazy;

/**
 * @author Qyy
 * @date 2023/8/3 08:15
 */
public class DoubleCheck {
    private DoubleCheck() {

    }
    private volatile static DoubleCheck doubleCheck;

    public DoubleCheck getInstance(){
        //第一次判断，如果doubleCheck的值不为null，不需要抢占锁，直接返回对象
        if (doubleCheck==null){
            //只有实例没有创建的时候才会获取锁
            synchronized (DoubleCheck.class){
                if(doubleCheck==null){
                    doubleCheck=new DoubleCheck();
                }
            }
        }
        return doubleCheck;
    }

    /**
     * 双重检查锁解决了线程安全、性能问题，但是在类加载过程中，仍然会存在指令重排的问题，类在创建过程中，创建对象，创建引用然后把引用指向内存，此时需要加volatile关键字
     */
}
