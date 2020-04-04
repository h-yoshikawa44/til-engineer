import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Array {
    public static void main(String[] args) throws Exception {
        // 配列

        // 宣言
        int[] numArray = new int[3];
         System.out.println(numArray[0]); // 0（型に応じたデフォルト値が入る）

        // 宣言+初期化
        int[] numArray2 = new int[]{1, 2};

        System.out.println(Arrays.toString(numArray2)); // [1, 2]
        // System.out.println(numArray2[2]); // ArrayIndexOutOfBoundsException

        // 宣言済みの変数に以下の書き方は不可
        // numArray = {3, 4};

        // 宣言+初期化 省略形
        int[] numArray3 = {3, 4};

        for(int num : numArray3) {
            System.out.println(num);
        }
        // 3
        // 4

        // 配列のサイズ
        System.out.println("配列の大きさ：" + numArray3.length);

        // 要素の追加・削除
        // Javaの配列は固定長であるので、要素の追加や削除ができない。
        // 一度、可変長のリストに変換して操作を行なった後に配列に戻す例。
        String[] strArray = {"A", "B", "C"};
        List<String> arrayToList = new ArrayList<String>(Arrays.asList(strArray));
        arrayToList.add("D");
        arrayToList.remove("B");
        String[] listToArray = arrayToList.toArray(new String[arrayToList.size()]);
        for (String str : listToArray) {
        	System.out.println(str);
        }
        // A
        // C
        // D

        // 単純にasListだけでリストに変換した場合は固定長になる
        String[] strArray2 = {"A", "B", "C"};
        List<String> arrayToList2 = Arrays.asList(strArray2);
        // arrayToList2.add("D"); // 固定長のリストであるため不可（UnsupportedOperationException）
        // arrayToList2.remove("B"); // 固定長のリストであるため不可（UnsupportedOperationException）
        for (String str : arrayToList2) {
        	System.out.println(str);
        }
        // A
        // B
        // C

        // 配列の結合
        // 一度リストに変換して結合後に配列に再変換している例。
        String[] mergeArray1 = {"A", "B", "C"};
        String[] mergeArray2 = {"D", "E", "F"};
        List<String> mergeList = new ArrayList<String>();
        mergeList.addAll(Arrays.asList(mergeArray1));
        mergeList.addAll(Arrays.asList(mergeArray2));
        String[] mergedArray = mergeList.toArray(new String[mergeList.size()]);
        for (String str : mergedArray) {
        	System.out.println(str);
        }
        // A
        // B
        // C
        // D
        // E
        // F

        // ソート
        int[] sortArray = {1, 6, 3};
        Arrays.sort(sortArray);
        for (int num : sortArray) {
            System.out.println(num);
        }
        // 1
        // 3
        // 6

        // 二次元配列
        // 要素数はバラバラでも可能
        int[][] array = {
            {10,20,30,40},
            {100,200,300,400},
            {1000,2000,3000}
        };
        System.out.println(array[0][0]); // 10
        System.out.println(array[0][3]); // 40
        System.out.println(array[2][2]); // 3000
    }
}
