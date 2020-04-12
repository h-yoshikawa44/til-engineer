public class Main {
    public static void main(String[] args) throws Exception {
        MySingleton i1 = MySingleton.getInstance();
        MySingleton i2 = MySingleton.getInstance();
        if (i1 == i2) {
            System.out.println("一致"); // 〇
        } else {
            System.out.println("不一致");
        }
    }
}

// シングルトンパターンクラス
class MySingleton {
   // このクラスが読み込みされたときに一度だけインスタンス化され、変数に代入される。
   private static final MySingleton instance = new MySingleton();

   // private指定しているため、外部からは呼び出すことができない
   private MySingleton() {}

   // シングルトンパターンのクラスのインスタンスは、このメソッドからのみ取得できるようになっている
   // そのため、このクラスから作られたオブジェクトは1つのみであると保証できる
   public static MySingleton getInstance() {
       return instance;
   }
}
