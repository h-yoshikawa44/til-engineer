public class ObjectClass {
    public static void main(String[] args) throws Exception {
        // Objectクラス
        // java.lang.Objectクラスはjavaのクラス階層を構成するためのルート（最上層）となるクラス。

        // 主なメソッド
        // - boolean equals(Object obj)  自オブジェクトとobjectを比較し、同じオブジェクトであればtrueを返す
        // - final Class<?> getClass()  このオブジェクトの実行時クラスを返す
        // - int hashCode()　  オブジェクトのハッシュコード値を返す
        //                   異なるオブジェクトから取り出したハッシュコードは異なる値となる
        // - String toString()  オブジェクトの文字列表現を返す
        //                      (オブジェクトのクラス名、＠、オブジェクトのハッシュコードの符号なし１６進数表現から構成される文字列)
        // - void finalize()  オブジェクトへの参照がないとガベージコレクタによって判断されたときにガベージコレクタによって呼び出される

        int[] ary = {1,2};
        String obj1 = "yamada";
        Foo obj2 = new Foo();
        Bar obj3 = new Bar();

        //println()メソッドに参照変数名を指定すると内部ではオブジェクトのtoString()が呼び出される

        // [ は配列、I はint型を意味する
        System.out.println(ary); // 例　[I@880ec60

        // StringクラスではtoString()がオーバーライドされ、保持する文字列を返す
        System.out.println(obj1); // yamada

        //オーバーライドされていないため「クラス名＠ハッシュコード」
        System.out.println(obj2); // 例　パッケージ名.Foo@3f3afe78

        // ４行目のオーバーライドのとおり
        System.out.println(obj3); // Bar

        /* オーバーライド時のルール
         * - object.hashCode();
         *   同一のオブジェクトに対して、hashCode()メソッドが複数呼び出されても同一の整数値を返す。
         * - object1.equals(object2) →trueなら→ object1.hashCode() == object2.hashCode() はtrue
         *   二つのオブジェクトをequals()メソッドで比較してtrueが返る場合は、二つのオブジェクトのハッシュコードの値は同じとなる
         * - object1.equals(object2) →falseなら→ object1.hashCode() == object2.hashCode() はtrue,falseいずれか
         *   二つのオブジェクトをequals()メソッドで比較してfalseが返る場合は、二つのオブジェクトのハッシュコードは同じでも
         *   異なる値でもどちらでもよい。ただし、異なる値を返した方がパフォーマンスが向上する場合がある
         * - object1.hashCode() == object2.hashCode() →falseなら→ object1.equals(object2) はfalseが返る
         *   二つのオブジェクトのハッシュコードの値が異なる場合は、二つのオブジェクトをequals()メソッドで比較してもfalseを返す
         */
    }