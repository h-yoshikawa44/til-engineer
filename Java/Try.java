import java.sql.SQLException;

// 独自例外クラス
class MyException extends Exception {
	private int age;
	public void setAge(int age){
		this.age = age;
	}
	public int getAge(){
		return this.age;
	}
}

class MyResourse implements AutoCloseable{
	private String msg;
	public MyResourse(String msg){ this.msg = msg;}
	public void close() throws Exception{
		System.out.println("close() : "+msg);
	}
}

public class Try {
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
         * また、catchブロックを複数定義することや、
         * 一つのcatchブロックで複数種類の例外をキャッチ（マルチキャッチ）することも可能
         * ※こちらは継承関係のある例外クラスは列挙できない
         */
        try{
            int age = -10;
            checkAge(age);
        }catch(MyException e){
            System.out.println("不正な値です。age : "+e.getAge());
        }

        /* try-with-resources
         * finallyブロックの使用例として、リソースの解放があげられるが
         * これらの処理はリソースのアクセスするロジックでは必須となるため、Java　SE７からtry-with-resourse文が導入された
         * tryブロックにリソースに関する実装を記述することで、
         * tryブロックが終了する際に暗黙的にclose()メソッドが呼び出され、リソースが解放される
         *（明示的にClose()メソッドを呼び出すコードを記述しなくてよい）
         */
        try(MyResourse obj1 = new MyResourse("obj1");
        MyResourse obj2 = new MyResourse("obj2")){
            System.out.println("try ブロック内の処理");
            throw new SQLException();
        }catch(SQLException e){
            //SQLException例外がキャッチされる前にclose処理が呼び出されている
            //close()の順番はリソースの取得の順と逆（今回はobj2から）
            System.out.println("catch ブロック : SQLException");
        }catch(Exception e){
            System.out.println("catch ブロック : Exception");
        }finally{
            System.out.println("finally ブロック");
        }
        // try ブロック内の処理
        // close() : obj2
        // close() : obj1
        // catch ブロック : SQLException
        // finally ブロック
    }

    // throws
    // throws指定された例外クラスのオブジェクトがメソッド内で発生した場合、
    // その例外オブジェクトはメソッドの呼び出しもとに転送される
    public static void checkAge(int age) throws MyException{
        if(age >= 0){
            System.out.println("OK");
        }else{
            MyException e = new MyException();
            e.setAge(age);
            // throw
            // プログラム内で明示的に例外をスロー
            throw e;
		}
    }

}