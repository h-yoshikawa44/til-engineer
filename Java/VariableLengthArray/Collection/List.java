import java.util.ArrayList;

public class List {
    public static void main(String[] args) throws Exception {
        // List
    	// 可変長な配列
    	// 要素の重複：可能

    	// 1.ArrayList
    	// ランダムアクセス（検索）：高速
    	// 挿入と削除：低速（線形的に実行されるため）
    	// 同期性：非対応

        // 宣言 + 領域確保
        // 型を指定し、他の型の値は代入できない（参照型、ラッパークラスで指定）
        // 宣言時に領域数を指定しない場合は、10がデフォルト
        ArrayList<String> array = new ArrayList<String>();

        // ダイアモンド演算子
        // 右辺のデータ型を省略できる
        ArrayList<String> array2 = new ArrayList<>();

        // 要素の追加
        array.add("山田");
        // インデックス指定での要素の追加
        array.add(0, "田中");

        // 要素の取得
        System.out.println(array.get(0));
        System.out.println(array.get(1));

        // リストのサイズ
        System.out.println(array.size());

        // リストを昇順にソートする（要素の「自然順序付け」に従う）
        Collections.sort(array);
        System.out.println(array.get(0)); // 山田
        System.out.println(array.get(1)); // 田中

        // リストの順序を逆にする
        Collections.reverse(array);
        System.out.println(array.get(0)); // 田中
        System.out.println(array.get(1)); // 山田

        // 2.LinkedList
        // List中の各要素には、個々のデータ項目に加えて次の要素に対するポインタを持つ
        // 挿入と削除：高速
        // 同期性：非対応

        // 3.Vector
        // 基本的にArrayListと同様
        // 同期性：対応（スレッドセーフなコレクションである）


        // 同期化をサポートしたArrayList
    	// - CopyOnWriteArrayListクラス
    	//   もとになる配列の新しいコピーを作成することにより、スレッドセーフを実現するArrayListを拡張

    	// mainスレッド側では、スレッドの作成、およびstart()メソッドの呼び出し後、sleep()により休止。
    	// その間に、iterator()メソッド呼び出しによりイテレータが作成されている
    	// CopyOnWriteArrayListでは、イテレータを作成した時点の状態を参照するため
    	// イテレータ取得後の、元のリストへ追加、削除、変更は反映されない。

    	List<String> list = new CopyOnWriteArrayList<String>();
        list.add("A"); list.add("B"); list.add("C"); list.add("D");
        new Thread(() -> {
            Iterator itr = list.iterator();
            while(itr.hasNext()){
                System.out.println("ThreadA : "+itr.next());
                try{
                    Thread.sleep(5000);
                }catch(InterruptedException e){ e.printStackTrace();}
            }
        }).start();
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){ e.printStackTrace();}
        list.add("E");System.out.println("main : add()");
        list.remove(2); System.out.println("main : remove()");
        /* ThreadA : A
         * main : add()
         * main : remove()
         * ThreadA : B
         * ThreadA : C
         * ThreadA : D
         */
    }
}