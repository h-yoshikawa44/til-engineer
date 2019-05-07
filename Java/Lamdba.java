import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class Rambda {
    public static void main(String[] args) throws Exception {
    	// SE8で導入された主な関数型インターフェース

    	// ラムダ式の左辺の部分は引数が一つの場合は省略可能
    	// ラムダ式の右辺の部分は処理が1文の場合は省略可能
    	// 例　Function<String, String> func = str -> "Hello " + str;

    	// Function<T, R>
    	// 抽象メソッド： R apply(T t)
    	// 引数としてTを受け取り、結果としてRを返す
        Function<String, String> func = (String str) -> {
        	return "Hello " + str;
        };
        System.out.println(func.apply("yamada")); // Hello yamada

    	// BiFunction<T, R>
    	// 抽象メソッド： R apply(T t, U u)
    	// 引数としてTとUを受け取り、結果としてRを返す
        BiFunction<String, String, String> biFunc = (String str1, String str2) -> {
        	return "Hello " + str1 + "," + str2;
        };
        System.out.println(biFunc.apply("yamada", "tanaka")); // Hello yamada tanaka

        // Consumer<T>
        // 抽象メソッド： void accept(T t)
        // 引数としてTを受け取り、結果を返さない
        Consumer<String> cons = (String str) -> {
        	System.out.println(str);
        };
        cons.accept("tanaka"); // tanaka

        // BiConsumer<T>
        // 抽象メソッド： void accept(T t, U u)
        // 引数としてとUを受け取り、結果を返さない
        BiConsumer<String, String> biCons = (String str1, String str2) -> {
        	System.out.println(str1 + "," + str2);
        };
        biCons.accept("tanaka", "yamada"); // tanaka,yamada

        // Predicate<T>
        // 抽象メソッド： boolean test<T t>
        // 引数としてTを受け取り、boolean値を結果として返す
        Predicate<Integer> pre = (Integer num) -> {
        	if (num > 10) {
        		return true;
        	} else {
        		return false;
        	}
        };
        System.out.println(pre.test(15)); // true

        // BiPredicate<T>
        // 抽象メソッド： boolean test<T t, U u>
        // 引数としてTとUを受け取り、boolean値を結果として返す
        BiPredicate<Integer, Integer> biPre = (Integer num1, Integer num2) -> {
        	if (num1 > 10 && num2 > 10) {
        		return true;
        	} else {
        		return false;
        	}
        };
        System.out.println(biPre.test(15, 12)); // true

        // Supplier<T>
        // 抽象メソッド： T get()
        // 何も引数として受け取らず、結果としてTを返す
        Supplier<String> sup = () -> {
        	return "Hello World";
        };
        System.out.println(sup.get()); // Hello World

        // UnaryOperator<T>
        // 抽象メソッド： T apply(T t)
        // 引数としてTを受け取り、結果としてTを返す(Fucntionを拡張したもの）
        UnaryOperator<String> una = (String str) -> {
        	return "Hello " + str;
        };
        System.out.println(una.apply("sato")); // Hello sato

        // BinaryOperator<T>
        // 抽象メソッド： T apply(T t1, T t2)
        // 引数としてTを二つ受け取り、結果としてTを返す(BiFucntionを拡張したもの）
        BinaryOperator<String> Bina = (String str1, String str2) -> {
        	return "Hello " + str1 + "," + str2;
        };
        System.out.println(Bina.apply("sato", "tanaka")); // Hello sato,tanaka

        // また、int、long、doubleに特化した関数型インタフェースもある
        // 例　IntFunction<R>    R apply(int value)   など接頭尾に型名がつく
        // なお、booleanについては、BooleanSupplierインタフェースのみ

        // 異なるデータ型を元に、基本データ型の結果を返す関数型インタフェースもある
        // 以下はInt型に関するもの　doubleやlongでも同様の命名規則
        // ToIntFunction<T>
        // 抽象メソッド：int applyAsInt(T value)
        // 引数としてTを受け取り、結果としてintを返す

        // ToIntBiFunction<T, U>
        // 抽象メソッド：int applyAsInt(T t, U u)
        // 引数としてTとUを受け取り、結果としてintを返す

        // IntToDoubleFunction
        // 抽象メソッド：double applyAsDoucle(int value)
        // 引数としてintを受け取り、結果としてdoubleを返す

        // IntToLongFunction
        // 抽象メソッド：long applyAsLong(int value)
        // 引数としてintを受け取り、結果としてlongを返す

        // ObjIntConsumer<T>
        // 抽象メソッド：void accept(T t, int value)
        // 引数としてTとintを受け取り、結果として返さない

        // 匿名クラスを使用した場合
        String str1 = new Function<String,String>(){
            public String apply(String str){
                return "Hello "+str;
            }
        }.apply("naoki");
        System.out.println("匿名クラス ："+str1); // 匿名クラス：Hello naoki

        //ラムダ式（省略なし）-----------------
        Function<String,String> func2 = (String str) ->{
            return "Hello "+str;
        };
        String str2 = func2.apply("naoki");
        System.out.println("ラムダ式（省略なし) ："+str2); // ラムダ式（省略なし）：Hello naoki

        //ラムダ式（省略あり）----------------
        Function<String,String> func3 = str -> "Hello "+str;
        String str3 = func3.apply("naoki");
        System.out.println("ラムダ式(省略あり)： "+str3); // ラムダ式（省略あり）：Hello naoki


        // メソッド参照
        // ラムダ式内で呼び出されるメソッドが一つの場合に、ラムダ式を使用せずに記述する方法（SE8から導入）
        // 構文 クラス名/インスタンス変数名::メソッド名
        // 種類　staticメソッド参照、インスタンスメソッド参照、コンストラクタ参照

        // staticメソッド参照
        // メソッド参照無しの場合
		Function<String,Integer> f1 = str -> Integer.parseInt(str);
		int num1 = f1.apply("100");
		System.out.println(num1); // 100
		// メソッド参照の場合
        Function<String,Integer> f2 = Integer::parseInt;
        int num2 = f2.apply("200");
        System.out.println(num2); // 200

        // インスタンスメソッド参照
        List<Integer> list = Arrays.asList(3,1,2);
        // インスタンス参照無しの場合
        list.forEach(a -> System.out.print(a) ); // 312
        //インスタンス参照の場合
        list.forEach(System.out::print); // 312

        // コンストラクタ参照
        // コンストラクタ参照無しの場合
        Supplier<Foo> obj1 = () -> new Foo();
        // コンストラクタ参照の場合
        Supplier<Foo> obj2 = Foo::new;
        // コンストラクタ参照（配列の生成）
		Function<Integer,String[]> obj3 = String[]::new;
		System.out.println(obj3.apply(5).length); // 5
    }
}
