import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
    public static void main(String[] args) throws Exception {
        // スレッドプール
        // 複数のスレッドを用意し（プールしておき）タスクを順次実施させるスレッドプールを利用した
        // ExecutorServiceを利用することも可能

        // 1 static ExecutorService newCachedThreadPool()
        // 新規スレッドを作成するスレッドプールを作成するが、以前に作成されたスレッドが利用可能であれば再利用する

        // 2 static ExecutorService newFixedThreadPool(int nThreads)
        // 固定数のスレッドを再利用するスレッドプールを作成する。
        // 引数で指定した数のスレッドがすべてアクティヴ（タスクを実行中）であると、
        //　それらのタスクはスレッドが使用可能になるまで待機する

        // 3 static ScheduledExecutorService newScheduledThreadPool(int corePoolSize)
        // 構文2と同様に引数て指定したプールサイズのスレッドプールを作成する。
        // ただし、定期的なコマンド実行のスケジュールが可能。

        ExecutorService service = null;
        try{
            service = Executors.newCachedThreadPool();
            //service = Executors.newFixedThreadPool(2);
            Runnable task = () -> {
                String name = Thread.currentThread().getName();
                System.out.println(name + " : start"); //スレッド名とstart文字列を出力
                try{
                    Thread.sleep(3000); //3秒間スリープ
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println(name + " : end");
            };
            for(int i = 0;i < 5;i++){
                service.execute(task);
            }
        }finally{
            if(service != null) service.shutdown();
        }
    }
}
