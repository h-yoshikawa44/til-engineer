public class Main {
    public static void main(String[] args) throws Exception {
        // 例外処理
        // Javaの例外クラスは大きく二つに分類される
        // - checked例外
    	//   DBなどJava実行環境以外の環境が原因で発生する例外。例外処理が必須。
        // - unchecked例外
    	//   実行中のプログラムが原因で発生する例外（実行時例外）や、メモリ不足などプログラムの例外処理では復旧できない例外。
    	//   例外処理は任意。

        // さらに例外クラスは次の３つに分けられる
        // - Errorクラスおよびそのサブクラス（unchecked例外）
        // - RumtimeExceptionクラスおよびそのサブクラス（unchecked例外）
        // - RuntimeExceptionクラス以外のExceptionのサブクラス（checked例外）

        /* 階層
         * Throwableクラス(例外クラスのルートクラス)
         * ↑
         * - Errorクラス（Throwableのサブクラス・unchecked例外）
         * 　メモリ不足などJava実行環境で発生する致命的なエラーを表現する。
         *   この例外をアプリケーションで対応するのは困難であるため、このクラス及びサブクラスの例外に対する処理は任意。
         * - Exceptionクラス（Throwableのサブクラス）
         *   アプリケーションで発生するエラーを表現。（書き込みをしようとしたファイルが存在しなかった、配列の範囲外にアクセスしたなど）
         *   コンパイルに成功するため、これらの例外に対する処理を必ず記述しないといけない場合と、記述しなくていい場合がある。
         *  （処理内容でなく、処理した結果、発生する可能性のある例外クラスがなんであるかによって変わる）
         *   ↑
         *   - RuntimeExceptionクラスおよびそのサブクラス（Exceptionクラスのサブクラス）
         *     例外処理の記述は任意
         *   - RuntimeExceptionクラス以外のExceptionのサブクラス
         *     例外処理の記述が必須
         */

        /* try {
         *     処理
         * } catch(キャッチするException 変数) {
         *     例外発生時の処理;
         * } finally {
         *     後処理(例外キャッチの有無にかかわらず実行する）;
         * }
         * ※try~catchやtry~finallyでも可能
         * また、catchブロックを複数定義することや、一つのcatchブロックで複数種類の例外をキャッチすることも可能
         */
        try{
            int age = -10;
            checkAge(age);
        }catch(MyException e){
            System.out.println("不正な値です。age : "+e.getAge());
        }
    }

    // throw
    // プログラム内で明示的に例外をスロー
    public static void checkAge(int age) throws MyException{
        if(age >= 0){
            System.out.println("OK");
        }else{
            MyException e = new MyException();
            e.setAge(age);
            throw e;
		}
    }