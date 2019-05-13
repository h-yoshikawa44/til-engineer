import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Executor {
    public static void main(String[] args) throws Exception {
    	// 3. Executorフレームワーク
    	// これまでのマルチスレッドプログラミングでは、ThreadクラスやRunnableインタフェースを使用してきた
    	// java.util.concurrentパッケージで提供されているExecutorフレームワークを使用すると
    	// スレッドの再利用やスケジューリングを行うスレッドコードを簡単に実装できる

    	// Executorフレームワークの主なインタフェースとクラス
    	// - Executorインタフェース	送信されたRunnableタスク（一つの処理）を実行するオブジェクト
    	// - ExecutorServiceインタフェース
    	//   終了を管理するメソッド、および非同期タスクの進行状況を追跡するFutureを生成するメソッドを提供する
        // - Futureインタフェース
    	//   非同期計算の結果を表す。計算が完了したかどうかのチェック、完了までの待機、
        //   計算結果の取得などを行うためのメソッドを提供。
        // - Callableインタフェース	タスクを行うクラス。結果を返し、例外をスローすることがある
        // - Executorsクラス
    	//   Executor、ExecutorService、ScheduledExecutorService、ThreadFactory、Callableオブジェクト用の
        //   ファクトリ及びユーティリティメソッドを提供

        // Executorクラスの主なメソッド
    	// ・static ExecutorService newSingleThreadExecutor()
    	// 一つのスレッドでタスクの処理するExecutorServiceオブジェクトを返す

    	// static ExecutorService newFixedThreadPool(int nThreads)
    	// 固定数のスレッドを再利用するスレッドプールを提供するExecutorServiceオブジェクトを返す

    	// static ExecutorService newCachedThreadPool()
    	// 新規スレッドが作成するスレッドプールを作成するが、利用可能な場合には
    	// 以前に構築されたスレッドを再利用するExecutorServiceオブジェクトを返す

    	// static ScheduledExecutorService newSingleThreadScheduledExecutor)
    	// 指定された遅延時間後、または周期的にコマンドの実行をスケジュールできる、
    	// 一つのスレッドでタスクの処理するExecutorServiceオブジェクトを返す

    	// static ScheduledExecutorService newScheduledThreadPool(int corePoolSize)
    	// 指定された遅延時間後、または周期的にコマンドを実行をスケジュールできる、
    	// スレッドプールを作成するScheduledExecutorServiceオブジェクトを返す

    	// static Callable<Object> callable(Runnable task)
    	// 呼び出し時に、指定されたタスクを実行し、nullを返すCallableオブジェクトを返す

    	// static<T>Callable<T>callable(Runnable,T result)
    	// 呼び出し時に、指定されたタスクを実行し、指定された結果を返すCallableオブジェクトを返す

        ExecutorService service = null;
        try{
            service = Executors.newSingleThreadExecutor(); //ExecutorServiceオブジェクトを取得
            System.out.println("service.execute()");
            String s = "*";
            for(int i = 0;i < 3;i++){
                service.execute(() -> { //execute()の引数はRunnable型であるためラムダ式で実装
                    //ExecutorServiceインタフェースはExecutorを継承しているためexecute()でタスクの実行可能
                    System.out.print("thread task ");
                    for(int a = 0;a < 5;a++){
                        try{
                            Thread.sleep(500);
                            System.out.print(" * "); //500ミリ秒スリープしながら＊を表示
                        }catch(InterruptedException e){ e.printStackTrace();}
                    }
                    System.out.println();
                });
            }
        }finally{
            service.shutdown(); //ExecutorServiceの終了
            //新しいタスクの受け入れは行わないが、既に実行中のタスクや待機しているタスクがあれば実行される
            System.out.println("ex.shutdown()");
		}
        /* service.execute()
         * thread task ex.shutdown()
         * *  *  *  *  *
         * thread task *  *  *  *  *
         * thread task *  *  *  *  *
         */

        // ExecutorServiceインタフェースの主なメソッド
        // boolean awaitTermination(long timeout,TimeUnit unit)throws InterruptedException
    	// シャットダウン要求後にすべてのタスクが実行を完了していたか、タイムアウトが発生するか、
        // 現在のスレッドで割り込みが発生するか、そのいずれかが最初に発生するまでブロックする

        // boolean isShutdown()
        // このExecutorがシャットダウンしていた場合、trueを返す

        // boolean isTerminated()
        // シャットダウンに続いてすべてのタスクが完了していた場合、trueを返す

        // void shutdown()
        // 順序正しくシャットダウンをする。以前に送信されたタスクは実行されるが、
        // 新規タスクは受け入れられない

        // List<Runnable> shutdownNow()
        // 実行中のアクティブなタスクおよび、待機中のタスクの処理を停止し、
        // 実行を待機していたタスクのリストを返す

        // <T>Future<T>submit(Callable<T>task)
        // 値を返す実行用タスクを送信して、保留状態のタスク結果を表すFutureオブジェクトを返す

        // Future<?>submit(Runnable task)
        // 実行中のRunnableタスクを送信して、そのタスクを表すFutureを返す

        // void execute(Runnable command)　　※Executorインタフェースのメソッド
        // 指定されたタスクを実行する

        ExecutorService service2 = null;
        try{
            service2 = Executors.newSingleThreadExecutor();
            Future<?> result1 =
                    service2.submit(() -> System.out.println("hello"));
            System.out.println(result1.get());
            //タスクの完了結果を取得　null
            //Runnableオブジェクトを引数にsubmit()を実行した場合、戻り値としてFutureオブジェクトを返す
            //そして正常にタスクが完了した場合、Futureのget()メソッドはnullを返す
            Future<Boolean> result2 =
                    service2.submit(() -> System.out.println("hello"),true);
            System.out.println(result2.get());
            //タスクの完了結果を取得　true
            //Runnableオブジェクトと第2引数を指定した場合、
            //正常にタスクが完了すると第2引数の値（この例はtrue)を返す
        }catch(InterruptedException | ExecutionException e){
            e.printStackTrace();
        }finally{
            if(service2 != null) service2.shutdown();
        }
        // hello
        // null
        // hello
        // true

        // Futureインタフェースの主なメソッド
        // boolean cancel(boolean mayInterruptIfRunning)
        // このタスク実行の取り消しを試みる

        // boolean isCancelled()
        // このタスクが正常に完了する前に取り消された場合はtrueを返す

        // boolean isDone()
        // このタスクが完了した場合はtrueを返す

        // V get() throws InterruptedException,ExecutionExceotion
        // 必要に応じてタスクが完了するまで待機し、その後、タスク結果を取得する

        // V get(long timeout,TimeUnit unit)throws InterruptedException,ExeutionExeption,TimeoutException
        // 必要に応じて、最大で指定された時間及び計算が完了するまで待機し、その後タスク結果を取得する


        //  タスクのスケジュール
        // execute()やsubmit()では、タスクが即座に実行されてきたが、スケジューリングすることも可能
        // （指定時間後の実行や、定期的な実行などの制御）
        // そのためにはExecutorServiceインタフェースを継承したScheduledExecutorServiceインタフェースを使用する

        // ScheduledExcutorServiceインタフェースの主なメソッド
        // <V>ScheduledFuture<V> schedule(Callable<V>callable,long delay,TimeInit unit)
        // 指定された遅延時間後に有効になる単発的なアクションを作成してCallableを実行する

        // ScheduledFuture<?>schedule(RUnnable command,long delay,TimeUnit unit)
        // 指定された遅延時間後に有効になる単発的なアクションを作成してRunnableを実行する

        // ScheduledFuture<?> scheduleAtFixedRate(Runnable command,long initialDelay,long period,TimeUnit unit)
        // 指定された初期遅延の経過後に初めて有効になり、その後は指定された期間ごとに有効になる
        // 定期的なアクションを作成して実行する

        // SchedledFuture<?>scheduleWithFixedDelay(Runnable command,long initialDelay,long delay,TimeUnit unit)
        // 指定された初期遅延の経過後に初めて有効になり、その後は実行の終了後から次の開始までの指定の
        // 遅延時間ごとに有効になる定期的なアクションを作成して実行する

		ScheduledExecutorService service3 = null;
		try{
			service3 = Executors.newSingleThreadScheduledExecutor();
			 //ScheduedExecutorServiceオブジェクトを取得後、タスク処理を行うオブジェクトを準備
			Runnable task1 = () -> System.out.println("task1");
			 //Runnableの実装としてtask1文字列の出力
			Callable<Date> task2 = () -> new Date();
			 //Callableの実装としてDateオブジェクトを戻り値として返す
			ScheduledFuture<?> result1 =
					service3.schedule(task1, 3, TimeUnit.SECONDS); //3秒後に実行
			ScheduledFuture<Date> result2 =
					service3.schedule(task2, 1, TimeUnit.MILLISECONDS); //1ミリ秒後に実行
		}finally{
			if(service3 != null)service3.shutdown();
		}

		// scheduleWithFixedDelay()　第3引数で指定された時間ごとにタスクを実行
		// scheduleAtFixedRate()　タスクの実行が終了した後、第3引数で指定された時間に従って遅延した後、タスクを実行

		ScheduledExecutorService service4 = null;
		try{
			service4 = Executors.newSingleThreadScheduledExecutor();
			Runnable task = () -> System.out.println(new Date());
			service4.scheduleWithFixedDelay(task,2,2,TimeUnit.SECONDS);
			Thread.sleep(10000); //10秒間スリープ
		}catch(InterruptedException e){
			e.printStackTrace();
		}finally{
			if(service4 != null)service4.shutdown();
		}
    }
}
