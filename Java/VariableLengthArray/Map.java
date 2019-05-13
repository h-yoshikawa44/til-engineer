import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

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


        // 同期化をサポートしたマップ
        // - ConcurrentMapインタフェース　アトミックなputIfAbsent()、remove()およびreplace()メソッドを提供するマップ
    	// - ConcurrentHashMapクラス　ConcurrentMapインタフェースを実装した同期をサポートしたマップクラス

    	// ConcurrentMapインタフェースに追加されたメソッド
    	// V putIfAbsent(K key,V value)	指定されたキーがまだ値と関連付けられていない場合は、指定された値に関連付ける

    	// boolean remove(Object key,Object value)
    	//   キー（およびそれに対応する値）をこのマップから削除する。そのキーがマップにない場合は、何もしない

    	// V replace(K key,V value)  キーが値に現在マッピングされている場合にのみ、そのキーのエントリを置換する

    	// boolean replace(K key,V oldValue,V newValue)
    	// キーに指定された値で現在マッピングされている場合にのみ、そのキーのエントリを置換する

		Map<Integer,String> map = new ConcurrentHashMap<>();
		map.put(1,"tanaka");
		map.put(2,"urai");
		for(Integer key : map.keySet()){ map.remove(key);}

    	// 通常、マップにおいてキーと値のセットを格納する際、まずcontainKey()やget()メソッドを使用して
    	// 指定されたキーが存在するかを確認する。
    	// しかし、複数のスレッドから利用されるマップを同期化したとしても、キーの確認や格納処理の間に
    	// 他のスレッドが入り、不整合が発生する可能性がある。
    	// （containsKey()やget()メソッド呼び出し時にロックを確保しても、処理が終わればロックは解放され
    	// 　put()メソッド呼び出し時に再度ロックを取得するため）

    	// したがって、ConcurrentMapインタフェースで提供されているメソッドは
    	// 1回のロックで二つの処理（containsKey()で確認してからput()メソッドで格納するなど）を
    	// 実行するよう制御されている。
    	// そのため、1回のロック内で処理を実行し、別スレッドからの割り込みが入らないことを保証する

    	// コード例
    	// if(!map.containsKey(key))
    	//     return map.put(key,value);
    	// else
    	//     reurn map.get(key);
    }
}