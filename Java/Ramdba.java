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
        System.out.println(func.apply("yamada")); // Hello Yamada

        // Consumer<T>
        // 抽象メソッド： void accept(T t)
        // 引数としてTを受け取り、結果を返さない
        Consumer<String> cons = (String str) -> {
        	System.out.println(str);
        };
        cons.accept("tanaka"); // tanaka

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
    }
