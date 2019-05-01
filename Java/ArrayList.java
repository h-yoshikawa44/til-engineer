import java.util.ArrayList;

public class ArrayList {
    public static void main(String[] args) throws Exception {
        // ArrayList

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

        // リストののサイズ
        System.out.println(array.size());
    }
}