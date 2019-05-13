import java.util.ArrayDeque;
import java.util.Deque;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Queue {
    public static void main(String[] args) throws Exception {
        // Queue
        // FIFO形式によりデータの追加、削除、検査を行う場合に用いる
        // 三つの操作に関するメソッドが提供されているが、それぞれ二つの種類がある。
        // （オペレーション失敗時に例外が発生するもの、特殊な値（オペレーションに応じてnullまたはfalse）を返すもの）
        // キューが空の場合NoSuchElementException例外をスローする

    	// 宣言 + 領域確保
        // 型を指定し、他の型の値は代入できない（参照型、ラッパークラスで指定）
    	Queue<String> queue = new PriorityQueue<String>();

        // 値の追加（キューの末尾に追加）
        // add() オペレーション失敗時に例外スロー
        // offer() オペレーション失敗時に特殊な値を返す
        queue.add("A");
        queue.add("B");
        queue.offer("C");

        // 値の削除（キューの先頭を取得および削除）
        // remove() オペレーション失敗時に例外スロー
        // poll() オペレーション失敗時に特殊な値を返す
        queue.remove();
        queue.poll();

        // 値の取得（キューの先頭を取得）
        // element() オペレーション失敗時に例外スロー
        // peek() オペレーション失敗時に特殊な値を返す
        System.out.println(queue.element()); // C
        System.out.println(queue.peek()); // C

        // Deque
        // LIFO形式によりデータの追加、削除、検査を行う場合に用いる
        // Queueインタフェースのサブインタフェース

        Deque<String> deq = new ArrayDeque<String>();

        // 先頭操作用

        // 値の追加（キューの先頭に追加）
        // addFirst() オペレーション失敗時に例外スロー
        // offerFirst() オペレーション失敗時に特殊な値を返す
    	deq.addFirst("D");
    	deq.addFirst("E");
    	deq.offerFirst("F");

    	// 値の削除（キューの先頭を取得および削除）
    	// removeFirst() オペレーション失敗時に例外スロー
    	// pollFirst() オペレーション失敗時に特殊な値を返す
        deq.removeFirst();
        deq.pollFirst();

        // 値の取得（キューの先頭を取得）
        // getFirst() オペレーション失敗時に例外スロー
        // peekFirst() オペレーション失敗時に特殊な値を返す
        System.out.println(deq.getFirst()); // D
        System.out.println(deq.peekFirst()); // D

        Deque<String> deq2 = new ArrayDeque<String>();

        // 末尾操作用

        // 値の追加（キューの末尾に追加）
        // addLast() オペレーション失敗時に例外スロー
        // offerLast() オペレーション失敗時に特殊な値を返す
        deq2.addLast("G");
        deq2.addLast("H");
        deq2.offerLast("I");

    	// 値の削除（キューの末尾を取得および削除）
    	// removeLast() オペレーション失敗時に例外スロー
    	// pollLast() オペレーション失敗時に特殊な値を返す
        deq2.removeLast();
        deq2.pollLast();

        // 値の取得（キューの末尾を取得）
        // getLast() オペレーション失敗時に例外スロー
        // peekLast() オペレーション失敗時に特殊な値を返す
        System.out.println(deq2.getLast()); // G
        System.out.println(deq2.peekLast()); // G

        // Dequeをスタックとして利用
        Deque<String> deq3 = new ArrayDeque<String>();

        deq3.push("J");
        deq3.push("K");
        deq3.push("L");
        System.out.println(deq3.pop()); // L
        System.out.println(deq3); // [K, J]


        // 同期化をサポートしたキュー
        // - BlockingQueueインタフェース　要素を取り出すときに、キューを空にしないために待機するようQueueインタフェースを拡張
        // ↓実装クラス
        // - SynchronousQueueクラス　BlockingQueueインタフェースを実装した基本的なブロッキングキュー
        // - LinkedBlockingQueueクラス　リンクノードに基づいたFIFOブロッキングキュー
        // - ArrayBlockingQueueクラス　配列に基づいたFIFOブロッキングキュー
        // - PriorityBlockingQueueクラス　キュー内の要素を指定された順序でソートするブロッキングキュー
        // - DelayQueueクラス　遅延時間が経過後にのみ、要素を取得できるブロッキングキュー

        // また、BlockingQueueの実装クラスではないが、スレッドセーフなQueueインタフェースの実装クラスとして、
        // ConcurrentLinkedQueueクラスも提供されている
        // （リンクノードに基づいたキューを提供するが、こちらはブロッキングを使用していない分高速）

    	// BlockingQueueインタフェースの主なメソッド
    	// 		例外のスロー　　特殊な値　　　ブロック　　タイムアウト
    	// 挿入　　add(e)		offer(e)　　put(e)　　offer(e,time,utit)
    	// 削除　　remove()		poll()	　　take()　　poll(time,unit)
    	// 検査	element()	peek()	　　 適用外　　 適用外

    	// ブロック…操作が正常に完了するまで現在のスレッドを無期限にブロック
    	// タイムアウト…処理を中止するまで指定された制限時間内のみブロック

        //このコードは無限ループになるため強制終了させること
        BlockingQueue<Double> queue = new LinkedBlockingQueue<>(3);
	    //容量が3に固定されたLinkedBlockingQueueオブジェクトの生成
        new Thread(() ->{ //キューに要素を追加するスレッド
            while(true){
                try{
                    queue.offer(Math.random(),2,TimeUnit.SECONDS);
                    //offer(キューに要素を追加）キューに空きがない際は待機するためにタイムアウト情報を引数に指定
                    System.out.println("offer() : "+queue.size());
                }catch(InterruptedException e){e.printStackTrace();}
            }
        }).start();

        new Thread(() -> { //キューから要素を取得および削除するスレッド
            while(true){
                try{
                    double pNum = queue.poll(2,TimeUnit.SECONDS);
                    //poll(キューから取得および削除）キューに要素がない場合は待機するためにタイムアウト情報を引数に指定
                    System.out.println("poll() : "+pNum);
                }catch(InterruptedException e){ e.printStackTrace();}
            }
        }).start();
    }
}