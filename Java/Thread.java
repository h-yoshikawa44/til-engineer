class ThreadA extends Thread{ //スレッドクラス
	public void run(){	//スレッドが実行する処理
		for(int i = 0;i < 10; i++){
			System.out.print("A:"+i+" ");
		}
	}
}
class ThreadB extends Thread{ //スレッドクラス
	public void run(){ //スレッドが実行する処理
		for(int i = 0;i < 10;i++){
			System.out.print("B:"+i+" ");
		}
	}
}

//Runnableインタフェースの実装クラス
class ThreadA2 implements Runnable{
	public void run(){ //スレッドが実行する処理
		for(int i = 0;i < 10; i++){
			System.out.print("A:"+i+" ");
		}
	}
}
//Runnableインタフェースの実装クラス
class ThreadB2 implements Runnable{
	public void run(){ //スレッドが実行する処理
		for(int i = 0;i < 10;i++){
			System.out.print("B:"+i+" ");
		}
	}
}

class Share4{  //共有して使用されるオブジェクト
	private int a = 0;
	private String b;
	public synchronized void set(){
		a++; b = "data";
		System.out.println("set() a: "+a+" b: "+b);
	}
	public synchronized void print(){
		a--; b = null;
		System.out.println("  print(); a: "+a+" b: "+b);
	}
}

class ThreadA4 extends Thread{
	private Share4 share;
	public ThreadA4(Share4 share){this.share = share;}
	public void run(){
		for(int i = 0;i < 5;i++){ share.set();}
	}
}
class ThreadB4 extends Thread{
	private Share4 share;
	public ThreadB4(Share4 share){this.share = share;}
	public void run(){
		for(int i = 0;i < 5;i++){ share.print();}
	}
}

class Share5{ //共有して使用されるオブジェクト
	private int a = 0;
	private String b;
	public synchronized void set(){
		while(a != 0){ //aが0でないときにwait()を呼び出す
			try{
				wait();
			}catch(InterruptedException e){}
		}
		notify();
		a++; b = "data";
		System.out.println("set() a: "+a+" b: "+b);
	}
	public synchronized void print(){
		while(b == null){
			try{
				wait();
			}catch(InterruptedException e){}
		}
		notify();
		a--; b = null;
		System.out.println("  print() a: "+a+" b: "+b);
	}
}

class ThreadA5 extends Thread{
	private Share5 share;
	public ThreadA5(Share5 share){this.share = share;}
	public void run(){
		for(int i = 0;i < 5;i++){ share.set();}
	}
}
class ThreadB5 extends Thread{
	private Share5 share;
	public ThreadB5(Share5 share){this.share = share;}
	public void run(){
		for(int i = 0;i < 5;i++){ share.print();}
	}
}
public class Thread {
    public static void main(String[] args) throws Exception {
        // スレッド
        // プログラムを実行した場合の処理の最小単位のこと

        /* マルチスレッド
         * Java言語はこのような一つのスレッド（シングルスレッド）だけでなく、複数のスレッドを使用したプログラムを作成できる。
         * プログラムの実行単位を複数のスレッドに分割して実行すること
         * CPUを一つしか搭載していないマシンでは、同時に二つの処理を実行することはできない
         * そのため、マルチスレッドでは通常、時分割処理という方法が用いられている
         * 時分割処理とは、短い時間間隔で実行する処理を切り替える方法
         * 複数の処理を頻繁に切り替えて実行することで。仮想的に複数の処理を同時に実行しているように見せている
         */

    	/* 1.Threadクラスのサブクラスを定義するやり方
         * スレッド開始を指示する側
         * TestThread test = new TestThread(); ①インスタンス化
         * test.start();　②start()メソッドを呼ぶ
         * ↓
         * Java実行環境　OS
         * ↓
         * Threadクラスのサブクラス側
         * class TestThread extends Thread{
         *     public void run(){  ③run()メソッドが実行される
         *         スレッドが処理するコード
         *	   }
         * }
         */

        //スレッドの作成
        ThreadA a = new ThreadA();
        ThreadB b = new ThreadB();
        //スレッドの実行開始
        a.start();
        b.start();
        //各スレッドの実行を切り替えるタイミングはOSが管理している。
        //そのため、OSの割り当てのタイミングによって出力結果が異なる場合がある


        /* 2.Runnableインタフェースを実装するやり方
         * スレッド開始を指示する側
         * TestThread test = new TestThread(); ①インスタンス化する
         * Thread thread = new Thread(test);　②Threadクラスをインスタンス化する
         * thread.start(); ③start()メソッドを呼ぶ
         * ↓
         * Java実行環境　OS
         * ↓
         * Runnableインタフェースを実装するクラス側
         * class TestThread implements Runnable{
         *     public void run(){
         *         スレッドが処理するコード
         *	   }
         *  }
         */

        //スレッドの作成
        ThreadA2 threadA = new ThreadA2();
        ThreadB2 threadB = new ThreadB2();
        Thread a2 = new Thread(threadA);
        Thread b2 = new Thread(threadB);
        //スレッドの実行開始
        a2.start();
        b2.start();
        //各スレッドの実行を切り替えるタイミングはOSが管理している。
        //そのため、OSの割り当てのタイミングによって出力結果が異なる場合がある


        // 2のRunnableを匿名クラスで実装した場合
        new Thread(new Runnable(){
            public void run(){
                System.out.println("hello");
            }
        }).start();

        // 2のRunnableをラムダ式で実装した場合
        // Runnableインタフェースは関数型インタフェースである
        new Thread(() -> {
            System.out.println("hello");
        }).start();


        // スレッドの制御

        // 制御用メソッド
        // static void sleep(long millis) throws InterruptedException
        // このメソッドを呼び出したスレッドが、millisミリ秒休止する

        // final void join() throws InterruptedException
        // 実行中のスレッドが終了するまで待機する

        // static void yield()	現在実行しているスレッドを一時的に休止し、他のスレッドに実行の機会を与える

        // void interrupt()
        // 休止中のスレッドに割り込みを入れる。割り込みを入れられたスレッドは、java.lang.InterruptedException例外を
        // Java実行環境から受け取り、処理を再開する

        Thread threadA3 = new Thread(() -> {
            System.out.println("threadA3 : sleeep 開始");
            try{
                Thread.sleep(5000); //ThreadA3スレッドのsleep
            }catch(InterruptedException e){
                System.out.println("threadA3 : 割り込みをキャッチしました");
            }
            System.out.println("threadA3 : 処理再開");
        });
        threadA3.start();
            try{
            System.out.println("main : sleep 開始");
            Thread.sleep(2000); //mainスレッドのsleep
            System.out.println("main : sleep 終了");
            threadA3.interrupt(); //スレッドへ割り込み
        }catch(InterruptedException e){
            System.out.println("main : 割り込みをキャッチしました");
        }

        /* 処理の流れ
         * mainスレッド
         * ↓
         * mainスレッドsleep・threadAスレッドstart
         * ↓
         * threadAスレッド sleep
         * ↓
         * mainスレッド sleep終了・sleep中threadAスレッドにinterruptで割り込み
         * ↓
         * threadAスレッド InterruptedException例外を受け取り、sleepを解除して処理を再開
         *
         * なお、sleep時間を経過しても、スレッドは即材に実行状態に移行しない。
         * sleep()メソッドが呼び出すときに指定する時間は、スレッドが実行を再開するまでの最短時間ということになる。
         */


        // synchronizedによる排他制御
        // synchronizedを使用することにより、同時に一つのスレッドからしか実行されないことが保証される

        // synchronizedが指定された箇所をあるスレッドが実行している間、共有のオブジェクトはロックがかかった状態になる
        // ロックがかかっている状態の時に別スレッドに制御が移り、そのスレッドが共有オブジェクトのsynchronizedが指定された
        // 箇所を実行しようとしても、そのスレッドは待たされることになる
        // そして、synchronizedが指定された箇所の処理が終了するとロックは解放される

        /* class Share4{  //共有して使用されるオブジェクト
         *     private int a = 0;
         *     private String b;
         *     public synchronized void set(){
         *         a++; b = "data";
         *         System.out.println("set() a: "+a+" b: "+b);
         *     }
         *     public synchronized void print(){
         *         a--; b = null;
         *         System.out.println("  print(); a: "+a+" b: "+b);
         *     }
         * }
         */

        Share4 share4 = new Share4();
        ThreadA4 threadA4 = new ThreadA4(share4);
        ThreadB4 threadB4 = new ThreadB4(share4);
        threadA4.start(); threadB4.start();


        // wait(),notify(),notifyAll()による同期制御

        // final void wait() throws InterruptedException
        // 他のスレッドがこのオブジェクトのnotify()メソッドまたはnotifyAll()メソッドを呼び出すまで、
        // 現在のスレッドを待機させる

        // final void wait(lomg timeout) throws InterruptedException
        // 他のスレッドがこのオブジェクトのnotify()メソッドまたはnotifyAll()メソッドを呼び出すか、
        // 指定された時間が経過するまで、現在のスレッドを待機させる

        // final void notify()
        // このオブジェクトの待機中のスレッドを一つ再開する。
        // 再開するスレッドを指定することはできない。

        // final voidnotifyAll()
        // このオブジェクトの待機中のすべてのスレッドを再開する

        // synchronized指定されていないメソッドやブロック（ロックの取得、解放がない）で
        // これらのメソッドを使用した場合、IllegalMonitorStateException例外が発生する

        /* class Share5{ //共有して使用されるオブジェクト
         *     private int a = 0;
         *     private String b;
         *     public synchronized void set(){
         *         while(a != 0){ //aが0でないときにwait()を呼び出す
         *             try{
         *                 wait();
         *             }catch(InterruptedException e){}
         *         }
         *         notify();
         *         a++; b = "data";
         *         System.out.println("set() a: "+a+" b: "+b);
         *     }
         *     public synchronized void print(){
         *         while(b == null){
         *             try{
         *                 wait();
         *             }catch(InterruptedException e){}
         *         }
         *         notify();
         *         a--; b = null;
         *         System.out.println("  print() a: "+a+" b: "+b);
         *     }
         * }
         */
        Share5 share5 = new Share5();
        ThreadA5 threadA5 = new ThreadA5(share5);
        ThreadB5 threadB5 = new ThreadB5(share5);
        threadA5.start(); threadB5.start();

        /* 資源の競合
         * 同期制御を行っている場合、すべてのスレッドが待機状態になってしまい、
         * notifyy()メソッドを呼ぶスレッドがないという状況は避けなければならない
         *
         * 全てのスレッドがロックの解放を同時に待ってしまい、ロックが永久に解けなくなる状況をデッドロックと呼ぶ
         * また、複数のスレッドが共有資源の獲得と解放を行っているが、獲得が必要な時には他のスレッドにロックされ、
         * 進まない処理を繰り返し続ける状況が発生することがある。このように実質的に処理が進まない状態をライブロックと呼ぶ
         * デッドロックやライブロックはコンパイルのタイミングで検出できず、例外などで通知もないため、
         * プログラムで制御する必要がある。（ロックの順番を決めておく、タイムアウト時間を指定しておくなど）
         *
         * また、複数のスレッドが同時実行しているとき、共有オブジェクトをなかなか解放しないスレッドがあると、
         * その旧友オブジェクトを使用するほかのスレッドが実行を長時間待たされることがある。
         * この状態をスレッドスタベーションと呼ぶ。
         * （優先度を見直したり、ロックの粒度を小さくしたり、ロックする回数を減らすなどして、
         *　他のスレッドに実行の機会を与える対応が必要）
         */
	}
}
