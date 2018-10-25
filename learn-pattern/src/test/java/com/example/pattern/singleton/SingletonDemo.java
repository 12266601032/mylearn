package com.example.pattern.singleton;

/**
 * 单例模式即保证只创建一个类的实例来使用。
 * 关于单例模式有很多种实现方式
 * 1、使用枚举
 * 2、静态成员内部类
 * 3、饿汉式
 * 4、懒汉式 方法同步
 * 5、懒汉式 双重检查同步
 */
public class SingletonDemo {

    /**
     * 这种实现是线程安全的，不过如果要添加一些方法之后是否还安全，是需要开发者来保障了。
     */
    public enum EnumIvoryTower {
        INSTANCE;
        @Override
        public String toString() {
            return getDeclaringClass().getCanonicalName() + "@" + hashCode();
        }
    }
    /**
     * 内部类方式-线程安全
     * 这种也是懒加载的方式，而且在所有已知的java版本中都可以使用
     * 而且这种方式是不需要通过任何的同步、volatile来保证，而天然线程安全的。
     */
    public final static class InitializingOnDemandHolderIdiom {
        /**
         * 构造私有化
         */
        private InitializingOnDemandHolderIdiom() {}
        /**
         * @return 单例对象
         */
        public static InitializingOnDemandHolderIdiom getInstance() {
            return HelperHolder.INSTANCE;
        }
        /**
         * Provides the lazy-loaded Singleton instance.
         */
        private static class HelperHolder {
            private static final InitializingOnDemandHolderIdiom INSTANCE =
                    new InitializingOnDemandHolderIdiom();
        }
    }
    /**
     * 饿汉式 - 线程安全
     * 属于是立即加载。
     */
    public final static class IvoryTower {
        /**
         * 私有化构造
         */
        private IvoryTower() {}
        /**
         * 提供静态成员实例对象
         */
        private static final IvoryTower INSTANCE = new IvoryTower();

        /**
         * 提供获取单例对象的方式
         * @return instance of the singleton.
         */
        public static IvoryTower getInstance() {
            return INSTANCE;
        }
    }
    /**
     * 双重检查懒加载实现方式-线程安全
     * 比直接对getInstance方法进行同步 性能要高
     */
    public final static class ThreadSafeDoubleCheckLocking {
        //注意这里使用volatile修饰 是为了避免重排序导致的变量不为null但是对象尚未初始化完成导致的NPE
        private static volatile ThreadSafeDoubleCheckLocking instance;
        /**
         * 私有化构造，防止外部创建该类对象
         */
        private ThreadSafeDoubleCheckLocking() {
            //防止反射调用
            if (instance != null) {
                throw new IllegalStateException("Already initialized.");
            }
        }
        /**
         * 公共方法访问单例对象
         * @return an instance of the clazz.
         */
        public static ThreadSafeDoubleCheckLocking getInstance() {
            // local variable increases performance by 25 percent
            // Joshua Bloch "Effective Java, Second Edition", p. 283-284
            ThreadSafeDoubleCheckLocking result = instance;
            // Check if singleton instance is initialized. If it is initialized then we can return the instance.
            //检查是否已经实例化
            if (result == null) {
                // It is not initialized but we cannot be sure because some other thread might have initialized it
                // in the meanwhile. So to make sure we need to lock on an object to get mutual exclusion.
                //需要实例化 - 那么先上个锁 保证并发
                synchronized (ThreadSafeDoubleCheckLocking.class) {
                    // Again assign the instance to local variable to check if it was initialized by some other thread
                    // while current thread was blocked to enter the locked zone. If it was initialized then we can
                    // return the previously created instance just like the previous null check.
                    //再次检查 防止竞争锁的过程中 已经被其他线程实例化
                    result = instance;
                    if (result == null) {
                        // The instance is still not initialized so we can safely (no other thread can enter this zone)
                        // create an instance and make it our singleton instance.
                        //创建单例对象
                        instance = result = new ThreadSafeDoubleCheckLocking();
                    }
                }
            }
            return result;
        }
    }

    /**
     * 方法同步式懒加载 - 线程安全
     */
    public final static class ThreadSafeLazyLoadedIvoryTower {
        private static ThreadSafeLazyLoadedIvoryTower instance;
        private ThreadSafeLazyLoadedIvoryTower() {
            // protect against instantiation via reflection
            if (instance == null) {
                instance = this;
            } else {
                throw new IllegalStateException("Already initialized.");
            }
        }
        /**
         * The instance gets created only when it is called for first time. Lazy-loading
         * 只有第一次调用getInstance时 才会创建对象
         */
        public static synchronized ThreadSafeLazyLoadedIvoryTower getInstance() {
            if (instance == null) {
                instance = new ThreadSafeLazyLoadedIvoryTower();
            }
            return instance;
        }
    }
}
