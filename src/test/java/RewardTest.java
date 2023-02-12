public class RewardTest {

}
// 饿汉式,类加载过程中创建对象,存在内存浪费
class Singleton{
    // 关键点1：构造器私有化
    private Singleton(){

    }
    //关键点2：在类的内部直接构建对象
    private static Singleton instance = new Singleton();
    //关键点3：提供一个static方法返回对象
    public static Singleton getInstance(){
        return instance;
    }
}

//双重锁
class Singleton1{
    // 关键点1：构造函数私有化
    private Singleton1(){}

    private static Singleton1 instance;

    public static Singleton1 getInstance(){
        // 第一次判断，如果instance不为null，不进入锁
        if(instance == null){
            // 保证只有一个线程创建锁
            synchronized (Singleton1.class){
                // 保证被锁阻拦线程不会重复创建对象
                if(instance == null){
                    return new Singleton1();
                }
            }
        }
        return instance;
    }
}

class Singleton2{
    private Singleton2(){};

    private static class SingletonHolder{
        private static Singleton2 instance = new Singleton2();
    }

    public static Singleton2 getInstance(){
        return new SingletonHolder().instance;
    }
}