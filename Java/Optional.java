import java.util.Optional;

public class Optional {
    public static void main(String[] args) throws Exception {
        // Optionalクラス
    	// SE8から追加されたクラスで、実体は一つの値を保持しているクラス。
    	// このクラスのメソッドは、保持している値がnullかnot nullによって処理が分かれる
    	// 保持している値がnullの場合は、emptyというオブジェクトになる。
    	// （Optionalオブジェクト自体がnullでない点に注意）

        // Optionalクラスの主なメソッド

        // static <T>Optional<T>empty()
        // 空のOptionalインスタンスを返す。このOptionalの値は存在しない
        Optional<Integer> op1 = Optional.empty();
        //System.out.println("op1.get() : "+op2.get());  op2はempty(値がない）ためエラーになる
        //nullの場合はfalse
        System.out.println(op1.isPresent()); // false

        // static <T>Optional<T>of(T value)
        // 引数で指定された非null値を含むOptionalを返す
        Optional<Integer> op2 = Optional.of(10);
        System.out.println(op2.get()); // 10
        System.out.println(op2.isPresent()); // true

        // T get()
        // 値が存在する場合は値を返し、それ以外の場合はNoSuchElementExceptionをスローする

        // boolean isPresent()
        // 存在する値がある場合はtrueを返し、それ以外の場合はfalseを返す

        // void isPresent(Consumer<? super T>consumer)
        // 値が存在する場合は指定されたコンシューマをその値で呼び出し、それ以外は何も行われない

        // T orElse(T other)
        // 存在する場合は値を返し、それ以外の場合はotherを返す

        // T orElseGet(Supplier<? extends T>other)
        // 値が存在する場合はその値を返し、そうでない場合はサプライヤを呼び出し、その呼び出しの結果を返す

        // <X extends Throwable> T orElseThrow(Supplier<? extends X>exceptionSupplier)throws X extends Throwable
        // 値が存在する場合は、その含まれている値を返し、
        // それ以外の場合は、指定されたサプライヤによって作成された例外をスローする
    }
}
