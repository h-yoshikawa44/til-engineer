import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

class ExamRecursiveAction extends RecursiveAction{
    private Double[] nums;
    private int start;
    private int end;
    public ExamRecursiveAction(String name,Double[] nums,int start,int end){
        //ランダムな値を格納する配列と、配列への格納を開始するインデックス、終端のインデックスを受け取る
        System.out.println("name : "+name+" "+start+" "+end);
        this.nums = nums;
        this.start = start;
        this.end = end;
    }
    protected void compute(){
        if(end - start <= 3){ //開始・終端のインデックスの差が3以下になったら
            for(int i = start;i < end;i++){
                nums[i] = Math.random()*100; //ランダムな値を作成し、
                System.out.println("nums["+i+"] "+nums[i]); //配列の値を格納
            }
        }else{
            int middle = start +(end - start)/2;
            //Fork/Joinフレームワークでは、タスクの分割手法として分割統治法を用いる
            //分割の割合はどちらかに偏りがないよう半分で分割
            //開始・終端のインデックスの差が3以下になるまで、（else以降の）分割処理を行う
            System.out.println("start:"+start+" middle:"+middle+" end:"+end);
            invokeAll(new ExamRecursiveAction("f1",nums,start,middle),
                      new ExamRecursiveAction("f2",nums,middle,end));
                      //分割したタスクを生成し、invokeAll()で実行
        }
    }
}

class ExamRecursiveTask extends RecursiveTask<Double>{
	 //RecursiveTaskの型パラメータでは処理結果の型を指定。（今回はDouble型）
	private Double[] nums;
	private int start;
	private int end;
	public ExamRecursiveTask(String name,Double[] nums,int start,int end){
		System.out.println("name : "+name+" "+start+" "+end);
		this.nums = nums;
		this.start = start;
		this.end = end;
	}
	protected Double compute(){
		if(end - start <= 3){
			double sum = 0.0;
			for(int i = start;i < end;i++){
				nums[i] = Math.random() * 100; //それぞれのタスクでランダムな値を作成
				System.out.println("nums["+i+"] "+nums[i]);
				sum += nums[i]; //合計値を格納する変数に加算代入を行う
			}
			return sum;
		}else{
			int middle = start + (end - start)/2;
			System.out.println("start:"+start+" middle:"+middle+" end:"+end);
			ExamRecursiveTask task1 = new ExamRecursiveTask("f1",nums,start,middle);
			ExamRecursiveTask task2 = new ExamRecursiveTask("f2",nums,middle,end); //それぞれタスクを作成
			task1.fork(); //一つのタスクをフォークし、パラレルに実行
			Double sum1 = task2.compute(); //compute()メソッドでタスクを処理
			Double sum2 = task1.join(); //フォークした処理結果をjoin()メソッドで受け取り
			return sum1 + sum2; //それぞれの結果を統合して戻り値として返す
		}
	}
}

public class ForkJoin {

    public static void main(String[] args) throws Exception {
        // Fork/Joinフレームワーク
    	// ExecutorServiceインタフェースの実装。
    	// 重い計算を小さなタスクに分割し、複数のスレッドによって並列実行することで高速に処理することを目的とする

    	// ork/Joinフレームワークではスレッドプール内のスレッドにタスクを分散し、
    	// work-stealingアルゴリズムにより、処理が終わったスレッドはビジー状態のほかのスレッドから
    	// タスクをスティールする（盗む）ことができる

    	// Fork/Joinフレームワークの主なインタフェースとクラス
    	// - ForkJoinPoolクラス	Fork/Joinタスクを実行するためのExecutorServiceインタフェースの実装クラス
    	// - ForkJoinTaskクラス	ForkJoinPool内で実行する抽象基底クラス
    	// - RecursiveActionクラス　結果を返さない再帰的なForkJoinTaskのサブクラス
    	// - RecursiveTaskクラス　結果を生成する再帰的なForkJoinTaskのサブクラス

        // 通常はForkJoinTaskクラスを直接継承したサブクラスを定義するのではなく
    	// RecursiveActionクラスもしくはRecursiveTaskをサブクラス化する。
    	// 結果を返すか、返さないかで二つのクラスを使い分ける。

    	// ForkJoinTaskクラスの主なメソッド
    	// final ForkJoinTask<V> fork()		このタスクを非同期で実行するための調整を行う

    	// static void invokeAll(ForkJoinTask<?>t1,ForkJoinTask<?> t2)
    	// 指定されたタスクをフォークしてパラレルに処理を実行する

    	// final V join()	計算が完了した後、計算の結果を返す

    	// RecursiveActionクラスの主なメソッド
    	// protected abstract void compute()	　このタスクによって実行される計算処理を実装する。戻り値はない。

        Double[] nums = new Double[10]; //ランダムな値を格納する配列を準備
        ForkJoinTask<?> task =
                new ExamRecursiveAction("main",nums,0,10);
                //配列、開始インデックス、終端インデックスをもとにExamRecursiveActionをインスタンス化
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(task);

        /* 結果例
         * name : main 0 10
         * start:0 middle:5 end:10
         * name : f1 0 5
         * name : f2 5 10
         * start:0 middle:2 end:5
         * name : f1 0 2
         * name : f2 2 5
         * start:5 middle:7 end:10
         * name : f1 5 7
         * name : f2 7 10
         * nums[2] 85.14137388654004
         * nums[3] 83.7065312313618
         * nums[4] 2.816326374043132
         * nums[0] 98.31509453405002
         * nums[5] 98.73561588453555
         * nums[6] 58.66058972368364
         * nums[7] 53.320131629492764
         * nums[8] 99.90176789640186
         * nums[1] 5.77400588655027
         * nums[9] 84.02144818929209
         */

        // RecursiveTaskクラスを使用した例
        // RecursiveActionクラスとの違いは、処理結果を受け取ることが可能なこと

        // Fork/Joinフレームワークを使用することで、タスクの分割や、結果の取得など簡単に行うことができる。
        // ただし、パラレル処理は分割に伴うオーバーヘッドが発生するため、扱う要素数が大きくないと
        // パフォーマンスの改善は見込めない。
        // また、分割の割合を適切に行わないと、同じように改善されないため留意する。

        // RecursiveTaskクラスの主なメソッド
        // protected abstract V compute()	このタスクによって実行される計算処理を実装する。戻り値は任意のオブジェクト。

        // 各compute()メソッドは抽象メソッドであるため、オーバーライドしてタスク処理を実装する。

        Double[] nums2 = new Double[10];
        ForkJoinTask<Double> task2 = new ExamRecursiveTask("main",nums2,0,10);
        ForkJoinPool pool2 = new ForkJoinPool();
        Double sum = pool2.invoke(task2); //戻り値を受け取っている（Sample10_30との違い）
        System.out.println("sum : "+sum);
    }
}
