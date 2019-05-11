import java.util.Scanner;

public class Input {
    public static void main(String[] args) throws Exception {
        // 標準入力処理

    	Scanner scanner = new Scanner(System.in);
    	System.out.println("名前を入力してください");
    	String name = scanner.next();
    	System.out.println("名前：" + name); // 名前：（入力した名前）

    	System.out.println("年齢を入力してください");
    	int age = scanner.nextInt();
    	System.out.println("年齢：" + age); // 年齢：（入力した年齢）


        // java.io.Consoleクラス
    	// Consoleオブジェクトを使用することで、コンソール上での入力（標準入力）、出力（標準出力）を扱える
    	// Consoleクラスのコンストラクタはprivate指定されているため、newによるインスタンス化はできない
    	// ConsoleオブジェクトはSystemクラスのconsole()メソッドで取得する

    	// Consoleクラスはシングルトンパターンが適用されており、
    	// console()メソッドが呼ばれると、実行中は一意のConsoleオブジェクトが返る。
    	// ただし、入出力が可能な端末かどうかは使用する端末に依存する。
    	// したがって、コンソール・デバイスが利用できない場合、console()メソッドはnullを返す。
    	// (なお、Eclipseでは利用できないためScannerか、BufferedReaderを使う）

    	// 単純な標準入力であれば、System.inを使用した場合とほぼ変わらない。
    	// Consoleクラスの特長は、入力文字のエコーバックを抑制できる点(入力したパスワードが画面に表示されない）であり、
    	// パスワードなどを入力させるプログラムで効果を発揮する。

    	// Consoleクラスの主なメソッド
    	// PrintWriter writer()			PrintWriterオブジェクトを取得する

    	// Console format(String fmt,Object...args)
    	// 指定された書式文字列および引数を使用して、書式付き文字列をこのコンソールの出力ストリームに書き出す

    	// Console printf(String format,Object...args)
    	// 指定された書式文字列および引数を使用して、書式付き文字列をこのコンソールの出力ストリームに書き出す

    	// String readLine()		コンソールから単一行のテキストを読み込む
    	// String readLine(String fmt,Object...args)
    	// 書式設定されたプロンプトを提供し、次にコンソールから単一行のテキストを読み込む。

    	// char[] readPassword()		エコーを無効にしたコンソールからパスワードまたはパスフレーズを読み込む
    	// char[] readPaddword(String fmt,Object...args)
    	// 書式設定されたプロンプトを提供し、次にエコーを無効にしたコンソールからパスワードまたは
    	// パスフレーズを読み込む。

        Console console = System.console();
        PrintWriter pw = console.writer();
        while(true){
            String str = console.readLine();
            if(str.equals("")){ break;} //何も入力しないでEnterで終了
            pw.append("入力されたデータ : "+str+'\n');
            //pw.write("入力されたデータ : "+str+'\n'); //appendと同様
            //System.out.println("入力されたデータ : "+str);　でも可能（この場合はflushも含まれるため記述しない）
            pw.flush();
        }
    }
}