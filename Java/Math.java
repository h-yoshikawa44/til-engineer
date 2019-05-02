public class Math {
    public static void main(String[] args) throws Exception {
        // java.lang.Mathクラスは数学ライブラリを提供するクラス。
        // Mathクラスの定数、およびメソッドはすべてstaticなので、インスタンス化することなく利用可能。

        // 自然対数の底eに最も近いdouble値を表す
        System.out.println(Math.E); // 2.718281828459045
        // 円周率（周とその直径の比piに最も近いdouble値を表す）
        System.out.println(Math.PI); // 3.141592653589793

    	int num1 = 10;
        int num2 = 50;
        // 大きい方
        System.out.println(Math.max(num1, num2)); // 50
        // 小さい方
        System.out.println(Math.min(num1, num2)); // 10

        // 絶対値
        System.out.println(Math.abs(-3)); // 3

        // 第一引数の第二引数乗
        System.out.println(Math.pow(2, 4)); // 16.0

        // 平方根
        System.out.println(Math.sqrt(9)); // 3.0
        // 立方根
        System.out.println(Math.cbrt(8)); // 2.0

        //0.0以上で1.0より小さい正の符号のついたdouble値をランダムに返す
        System.out.println(Math.random());

        // 切り上げ
        System.out.println(Math.ceil(1.78)); // 2.0
        System.out.println(Math.ceil(-5.67)); // -5.0

        // 切り捨て
        System.out.println(Math.floor(1.78)); // 1.0
        System.out.println(Math.floor(-5.67)); // -6.0

        // 四捨五入
        // 引数がdouble型の場合→戻り値はlong型、引数がfloat型の場合→戻り値はint型
        // 実際は 1/2 を加えて、floor メソッドで切り捨てて、int にキャストして整数に丸められる
        System.out.println(Math.round(1.49)); // 1
        System.out.println(Math.round(1.50)); // 2
        System.out.println(Math.round(-5.50)); // -5
        System.out.println(Math.round(-5.51)); // -6

        // 四捨五入
        // 引数はdouble型で、戻り値もdouble型
        System.out.println(Math.rint(1.49)); // 1.0
        System.out.println(Math.rint(1.50)); // 2.0
        System.out.println(Math.rint(-5.50)); // -5.0
        System.out.println(Math.rint(-5.51)); // -6.0
    }
}