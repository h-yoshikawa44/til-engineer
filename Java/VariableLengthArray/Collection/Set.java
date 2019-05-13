import java.util.HashSet;

public class Set {
    public static void main(String[] args) throws Exception {
        // Set
    	// 要素の重複：不可

    	// 1. HashSet
    	// アクセス速度：高速
    	// 順序：保持しない
    	// 同期性：非対応

    	// 宣言 + 領域確保
        // 型を指定し、他の型の値は代入できない（参照型、ラッパークラスで指定）
    	HashSet<String> set = new HashSet<String>();

        // 要素の追加
        set.add("山田");
        set.add("田中");
        for (String name : set) {
        	System.out.println(name);
        }
        // 田中
        // 山田

        // セットのサイズ
        System.out.println(set.size()); // 2

        // 2.TreeSet
        // アクセス速度：低速
        // 順序：保持（ソートされたデータ項目）
        // 同期性：非対応

        // 3.LikedHashSet
        // 基本的にはHashSetと同じ
        // 全てのデータ項目に対する二重リンクリストを追加したもの
        // 順序：保持（挿入順）
        // 同期性：非対応


        // 同期化をサポートしたセット
        // - CopyOnWriteArraySet
        // 内部でCopyOnWriteArrayListオブジェクトを使用して、スレッドセーフを実現するSetインタフェースの拡張
    }
}
