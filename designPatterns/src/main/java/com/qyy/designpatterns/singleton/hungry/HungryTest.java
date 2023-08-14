package com.qyy.designpatterns.singleton.hungry;

/**
 * @author Qyy
 * @date 2023/8/2 21:26
 */
public class HungryTest {
    public static void main(String[] args) {
        Hungry hungry1 = Hungry.newInstance();
        Hungry hungry2 =Hungry.newInstance();
        System.out.println(hungry2==hungry1);

        /**  单例模式就是在一定的作用域内保证只能生成一个实例。为什么要有这样的保证呢？
         *   因为对象的创建和销毁都需要占用内存，消耗资源。频繁的创建销毁对象会影响性能。比如在连接数据库时，数据库连接池对象的创建就比较繁琐，这时候使用单例模式，只创建一个实例，然后通过对象的复用，就可以避免频繁创建对象。
         * 由当前类可知，这样创建出来的hungry1和hungry2都是同一个对象，缺点就是如果创建出来的对象可能不会用到，就会造成资源的浪费。所以就需要另一种单例模式→懒汉方式：在用到对象的时候才去创建对象的实例。
         */
    }
}
