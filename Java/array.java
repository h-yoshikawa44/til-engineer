import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        // 配列

        // 宣言
        int[] numList = new int[3];

        // 宣言+初期化
        int[] numList2 = new int[]{1, 2};

        System.out.println(numList2[0]); // 1
        System.out.println(numList2[1]); // 2
        // System.out.println(numList2[2]); // ArrayIndexOutOfBoundsException

        // 宣言済みの変数に以下の書き方は不可
        //numList = {3, 4}

        // 宣言+初期化 省略形
        int[] numList3 = {3, 4};

        for(int num : numList3) {
            System.out.println(num);
        }
        // 3
        // 4

        // 配列のサイズ
        System.out.println("配列の大きさ：" + numList3.length);

        // 配列のコピー
        // ※配列には値を追加する方法がないためコピーを使用する
        // arryacopy(コピー元配列, コピー元開始位置, コピー先配列, コピー先開始位置, 個数)
        int[] hoge = {1, 2, 3, 4, 5};
        int[] fuga = new int[10];
        System.arraycopy(hoge, 0, fuga, 0, 5);
        for(int num : fuga) {
            System.out.print(num); // 1234500000
        }
    }
}
