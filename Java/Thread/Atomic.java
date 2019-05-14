import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class IntegerTest{
    private Integer syncInteger;
    private final AtomicInteger atomicInteger;
    public IntegerTest(){
        syncInteger = 0;
        atomicInteger = new AtomicInteger(0);
    }
    synchronized public void addSyncInteger(){
        syncInteger++; //syncInteger変数の値をインクリメント
    }
    public void addAtomicInteger(){
        atomicInteger.getAndIncrement();
        //AtomicIntegerオブジェクトが保持するint値をアトミックにインクリメントする
    }
    public void showData(){
        System.out.println("syncInt  : "+syncInteger);
        System.out.println("atomicInt : "+atomicInteger.get());
    }
}

public class Atomic {
    public static void main(String[] args) throws Exception {
        // java.util.concurrent.atomic
        // マルチスレッド環境下においてsynchronizedメソッドが行っている操作は「分割不可能な操作」
        // このような分割不可能な操作のことを一般にアトミック（atomic)操作と呼ぶ

        // java.util.concurrent.atomicパッケージの主なクラス
        // - AtomicBoolean	アトミックに操作するboolean型の値を扱うクラス
        // - AtomicInteger	アトミックに操作するint型の値を扱うクラス
        // - AtomicLong	アトミックに操作するlong型の値を扱うクラス
        // - AtomicReference  アトミックに操作する参照型のデータを扱うクラス

        // AtomicIntegerクラスの主なメソッド
        // final int addAndGet(int delta)	アトミックに指定された値を現在の値に追加する。戻り値は増分後の値

        // final boolean compareAndSet(int expect,int update)
        // 現在の値が第1引数と等しい場合、アトミックに第2引数で指定された値に更新する

        // final int incrementAndGet()	アトミックにインクリメントし、更新値を返す

        // final int get() 	現在の値を取得する

        // final int getAndIncrement		アトミックにインクリメントし、更新前の値を返す

        IntegerTest obj = new IntegerTest();
        exec(() -> obj.addSyncInteger());
        exec(() -> obj.addAtomicInteger());
        obj.showData();
    }

    private static void exec(Runnable task) throws InterruptedException{
        ExecutorService service = null;
        try{
            service = Executors.newFixedThreadPool(100); //100のスレッド
            service.submit(() -> {
                for(int i = 0;i < 10000;i++){
                    task.run();
                }
            });
            service.awaitTermination(10, TimeUnit.SECONDS);
        }finally{
            if(service != null) service.shutdown();
        }
    }
}
