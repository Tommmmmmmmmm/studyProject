package com.qyy.designpatterns.singleton.lazy;

/**
 * @author Qyy
 * @date 2023/8/2 21:37
 */
public class LazySimple {
    private LazySimple() {
    }
    private static LazySimple lazySimple=null;

    public static LazySimple newInstance(){
        if(lazySimple==null){
            lazySimple=new LazySimple();
        }
        return lazySimple;
    }

    public static void main(String[] args) {
        LazySimple lazySimple1 = LazySimple.newInstance();
        LazySimple lazySimple2 = LazySimple.newInstance();
        System.out.println(lazySimple1==lazySimple2);


        /**
         *  这样会存在线程安全问题，当线程A进入到if判断，还没执行下一步，此时线程B也进到判断语句中，此时对象还没被创建出来，因此线程A、B所创建出来的对象不是同一个。
         * 在方法加锁关键字synchronised可以解锁这个问题，但当对象已经存在时，每次获取仍然要获取锁。也需要消耗资源，影响性能。因此需要改变锁的粒度→双重检查锁
         */
    }
}
