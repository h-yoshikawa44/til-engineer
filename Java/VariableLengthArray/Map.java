import java.util.HashMap;

public class Map {
    public static void main(String[] args) throws Exception {
    	// Map
    	// データをキーと値のペアで管理する場合に用いる

    	// 1.HashMap
    	// マップ内に各々のキーと値のペアは順不同で格納される
    	// 同期性：非対応

    	// 宣言 + 領域確保
        // 型を指定し、他の型の値は代入できない（参照型、ラッパークラスで指定）
    	HashMap<Integer,String> map = new HashMap<Integer,String>();

    	// 値の追加
    	map.put(0, "AAA");
        map.put(1, "BBB");
        map.put(2, "CCC");

        // 値の取得
        System.out.println(map.get(0)); // AAA

        //引数で指定されたキーに対応するキーと値のペアがこのマップに存在する場合にtrueを返す
        System.out.println(map.containsKey(1)); // true
        //引数で指定された値に対応するキーと値のペアがこのマップに一つ以上存在する場合はtrueを返す
        System.out.println(map.containsValue("DDD")); // false

        // マップのサイズ
        System.out.println(map.size()); // 3

        // 2.LikedHashMap
        // 全てのエントリに対する二重リンクリストを保持する
        // 順序：保持（挿入順）
        // 同期性：非対応

        // 3.TreeMap
        // SortedMapインタフェースを実装したクラス
        // 順序：保持（キーの昇順）
        // 同期性：非対応
    }
}