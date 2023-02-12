import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.*;
import java.util.stream.Collectors;

class SmallTool{
    public static  void sleepMills(long mills){
        try{
            Thread.sleep(mills);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public static void printTimeAndThread(String tag) {
        String result = new StringJoiner("\t|\t")
                .add(String.valueOf(System.currentTimeMillis()))
                .add(String.valueOf(Thread.currentThread().getId()))
                .add(Thread.currentThread().getName())
                .add(tag)
                .toString();
        System.out.println(result);
    }
}

@SpringBootTest
class _01_supplyAsync{
    @Test
    public static void main1 (String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = new ThreadPoolExecutor(
                6, 20, 10, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1024),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
        SmallTool.printTimeAndThread("小李进入餐厅");
        SmallTool.printTimeAndThread("效力点了番茄炒蛋");
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(()->{
            SmallTool.printTimeAndThread("厨师开始炒菜");
            SmallTool.sleepMills(200);
            SmallTool.printTimeAndThread("厨师开始打饭");
            SmallTool.sleepMills(200);
            return "饭做好了";
        }, service);
        SmallTool.printTimeAndThread("小李在打王者呢");
        SmallTool.printTimeAndThread(String.format("%s, 小李开吃", cf1.get()));
    }

    @Test
    public static void main2 (String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = new ThreadPoolExecutor(
                6, 20, 10, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1024),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
        SmallTool.printTimeAndThread("小李进入餐厅");
        SmallTool.printTimeAndThread("小李点了番茄炒蛋");
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(()->{
            SmallTool.printTimeAndThread("服务员去下单");
            return "menu";
        }, service).thenCompose(s->CompletableFuture.supplyAsync(()->{
            SmallTool.sleepMills(1000);
            SmallTool.printTimeAndThread(String.format("厨师开始根据%s炒菜",s));
            SmallTool.sleepMills(2000);
            SmallTool.printTimeAndThread("厨师开始打饭");
            SmallTool.sleepMills(2000);
            return s+"上的菜做完了";
        }, service)).thenCompose(s -> CompletableFuture.supplyAsync(()->{
            SmallTool.sleepMills(200);
            SmallTool.printTimeAndThread("服务员开始上菜");
            return "上菜了";
        }, service));
        SmallTool.printTimeAndThread("小李在打王者呢");
        SmallTool.printTimeAndThread(String.format("%s, 小李开吃", cf1.get()));
    }

    @Test
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = new ThreadPoolExecutor(
                6, 20, 10, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1024),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
        SmallTool.printTimeAndThread("小李进入餐厅");
        SmallTool.printTimeAndThread("小李点了番茄炒蛋");
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(()->{
            SmallTool.printTimeAndThread("厨师开始炒菜");
            SmallTool.sleepMills(2000);
            SmallTool.printTimeAndThread("厨师开始打饭");
            SmallTool.sleepMills(200);
            return "饭做好了";
        }, service).thenCombine(CompletableFuture.supplyAsync(()->{
            SmallTool.sleepMills(100);
            SmallTool.printTimeAndThread("服务员开始服务");
            return "服务结束";
        }, service), (s1, s2)->{
            SmallTool.printTimeAndThread(s1+","+s2);
            return "可以开饭了";
        });
        SmallTool.printTimeAndThread("小李在打王者呢");
        SmallTool.printTimeAndThread(String.format("%s, 小李开吃", cf1.get()));
    }
}

@SpringBootTest
public class JavaTest{
    static List<NetMall> mall = Arrays.asList(
            new NetMall("jd"),
            new NetMall("tb"),
            new NetMall("tm"),
            new NetMall("dw")
    );
    // 同步方式
    public static List<String> getPriceByStep(List<NetMall> list, String product){
        return list.stream()
                .map(netMall -> String.format(product+"in %s price is %.2f", netMall.getMallName(), netMall.calcPrice(product)))
                .collect(Collectors.toList());
    }
    // 异步方式
    public static List<String> getPricesByAsync(List<NetMall> list, String product){
        return list.stream()
                .map(netMall -> CompletableFuture.supplyAsync(()->String.format(product+"in %s price is %.2f", netMall.getMallName(), netMall.calcPrice(product))))
                .collect(Collectors.toList())
                .stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        System.out.println("同步方式");
        long startTime = System.currentTimeMillis();
        List<String> list = getPriceByStep(mall, "java");
        for(String el:list) System.out.println(el);
        long endTime = System.currentTimeMillis();
        System.out.println("花费时间为："+(endTime-startTime)+"毫秒");

        System.out.println("异步方式");
        startTime = System.currentTimeMillis();
        list = getPricesByAsync(mall, "java");
        for(String el:list) System.out.println(el);
        endTime = System.currentTimeMillis();
        System.out.println("花费时间为："+(endTime-startTime)+"毫秒");

    }
}


class NetMall {

    private String mallName;

    public NetMall(String mallName){
        this.mallName = mallName;
    }

    public double calcPrice(String productName){
        //模拟检索时间1s
        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
        return ThreadLocalRandom.current().nextDouble() * 2 + productName.charAt(0);
    }

    public String getMallName() { return mallName; }
}
