import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Barrier {
    void a(){ System.out.print("a "); }
	void b(){ System.out.print("b "); }
	void c(){ System.out.print("c "); }
	void exec(CyclicBarrier c1,CyclicBarrier c2){
		//try{
		// await()メソッドにより3本のスレッドがそろうまで待機しながら実行
			a();
			//c1.await();
			b();
			//c2.await();
			c();
			//}catch(BrokenBarrierException | InterruptedException e){}
		}
    public static void main(String[] args) throws Exception {
        // java.util.concurrent.CyclicBarrier
    	// 同期処理を提供するクラス。
    	// 複数のスレッドで処理を行っている際に、バリアポイント（待機する箇所）を設定しておくと
    	// 全てのスレッドがその箇所に到達するまで待機する

    	ExecutorService service = null;
		try{
			service = Executors.newFixedThreadPool(3);
			CyclicBarrier c1 = new CyclicBarrier(3);
			CyclicBarrier c2 = new CyclicBarrier(3,
									() -> System.out.print("task "));
									//第二引数にRunnableオブジェクトを指定
									//最後にバリアポイントを通過したスレッドによって実行されるタスクを指定できる
			for(int i = 0;i < 3;i++){
				service.execute(() -> new Barrier().exec(c1,c2));
			}
		}finally{
			if(service != null) service.shutdown();
		}

    }
}