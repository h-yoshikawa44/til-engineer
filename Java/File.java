import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class File {
    public static void main(String[] args) throws Exception {
        // java.io.Fileクラス
        // ディスクに保存されているファイルやディレクトリをオブジェクトとして表現するためのクラス。

        // パス名がシステムに依存している状態であると、システム非依存というJava最大のメリットが損なわれてしまう。
        // そのため、Javaでは抽象パスの概念を導入することにより、システム非依存のパス名を実現している。
        // ユーザがパス名の代わりにシステムに依存しない方法で文字列を入力したものを、
        // Javaが次の2つの構成要素を持つ抽象パス名に変換する。

        // システムに依存する任意指定の接頭辞文字列
        // Windowsの「C:」のようなディスクドライブ記号、UNIXやLinuxのルートディレクトリ「/」

    	// 「C:」または「/」以降に続くパス文字列
   	    // パスの最後の一つの除くそれぞれの文字列はディレクトリを表し、最後の一つはディレクトリまたはファイル名を表す。
        // それぞれの名前は、システムに依存したセパレータ（Windowsの場合は￥、UNIXやLinuxは「/」）で区切られている。

    	// Fileクラスの主なメソッド

    	// File(String pathname)　指定されたパス名文字列を抽象パス名に変換して、Fileオブジェクトを生成
    	// File(String parent,Sring child)　親パス名文字列および子パス名文字列からFileオブジェクトを生成
    	// File(File parent,String child)　親抽象パス名および子パス名文字列からFileオブジェクトを生成

    	// File[] listFiles() この抽象パス名が示すディレクトリ内のファイル、ディレクトリをFile配列として返す

    	// boolean isFile()	この抽象パス名が示すファイルが普通のファイルかどうかを判定する
    	// boolean idDirectory() この抽象パス名が示すファイルが普通のディレクトリかどうかを判定する

    	// String getName()	この抽象パス名が示すファイルまたはディレクトリの名前を返す

        // boolean createNewFile() throws IOException　この抽象パス名が示す空の新しいファイルを作成する

        // boolean delete()	　この抽象パス名が示すファイルまたはディレクトリを削除する

        // boolean mkdir()	この抽象パス名が示すディレクトリを生成する

        // boolean mkdirs()			この抽象パス名が示すディレクトリを生成する。
	    //                          必要な存在していない親ディレクトリがあれば一緒に生成する

        // boolean renameTo(File dest) この抽象パス名が示すファイルの名前を変更する

        // String getAbsolutePath()  この抽象パス名の絶対パス名文字列を返す

    	File f1 = new File("ren/dir");
        File f2 = new File("ren","a.txt");
        File f3 = new File(f1,"x.txt");

        for(File f : f1.listFiles()){
             if(f.isFile()){
            	System.out.println("ファイル   : "+f.getName());
            }else if(f.isDirectory()){
                System.out.println("ディレクトリ : "+f.getName());
            }
        }


        // ---
        // ストリーム
        // プログラムからファイルへのデータの書き出しや、逆にファイルからプログラムへのデータの読み込みが発生など
        // データの送受信を連続的に行うものをストリーム（データの流れという意味）という。

        // 〇主なバイトストリーム(byte単位でデータ読み書きするストリーム）
        // FileInputStream	ファイルからbyte単位の読み込みを行うストリーム
        // FileOutputStream　ファイルからbyte単位の書き出しを行うストリーム

        // 主なメソッド
        // int read() throws IOException		//FileInputStream
    	// 入力ストリームからバイトデータを読み込む。ファイルの終わりに達すると‐1を返す

        // void close() throws IOException	//fileInputStream
        // このストリームを閉じる

        // void write(int b) throws IOException		//FileOutputStream
        // 引数で指定されたバイトデータをファイル出力ストリームに書き出す
        // void write(byte[] b) throws IOException	//FileOutputStream
        // 引数で指定されたバイト配列をファイル出力ストリームに書き出す

        // void close() throws IOExceotion	//FileOutputStream
        // このストリームを閉じる

        FileOutputStream fos = null;
	    FileInputStream fis = null;
	    try{
            fos = new FileOutputStream(new File("ren/8_2.txt"));
        	//FileOutputStreamコンストラクタでは指定したディレクトリに指定したファイルが存在しない場合は
            //ファイルを新規作成する
            fos.write(0); fos.write("suzuki".getBytes()); fos.write(99); //データの書き出し
            fis = new FileInputStream(new File("ren/8_2.txt"));
            int data = 0;
            while((data = fis.read()) != -1){
                System.out.print(data + " "); //読み込んだデータの表示
            }
        }catch(FileNotFoundException e){
            System.out.println("ファイルがありません");
        }catch(IOException e){
            System.out.println("IO Error");
        }finally{
            try{
                fos.close(); fis.close(); //各ストリームを閉じる
            } catch(IOException e){}
		}

	    // try-with-resourcesでの例
	    // こちらは明示的にcloseを書か必要がない
        try(FileOutputStream fos2 = new FileOutputStream(new File("ren/8_3.txt"));
            FileInputStream fis2 = new FileInputStream(new File("ren/8_3.txt"))){
                fos2.write(0); fos2.write("suzuki".getBytes()); fos2.write(99);
                int data = 0;
                while((data = fis2.read()) != -1){
                    //読み込んだデータの表示
                    System.out.print(data + " ");
                }
            }catch(FileNotFoundException e){
                System.out.println("ファイルがありません");
            }catch(IOException e){
                System.out.println("IO Error");
            }


        // DataInputStream　 基本データ型のデータを読み込めるストリーム
        // DateOutputStream　基本データ型のデータを書き出せるストリーム

        // int型やfloat型などの基本データ型およびString型のデータを読み書きできるストリーム。
        // 基本データ型およびString型で対応した読み書き用のメソッドが用意されている。
        // また、このストリームは単体では使用できないので、他のストリームを連結して使用する必要がある
        // 具体的には他のストリームをコンストラクタの引数に指定して生成する必要がある。

        // 主なメソッド
        // final int readint() throws IOException	//DataInputStream
    	// 4バイトの入力データを読み込む。
    	// 入力の途中で、予想外のファイルの終了または予想外のストリームの終了があった場合はEOFExceptionがスローされる

        // final String readUTF()　throws IOException	//DataInputStream
    	// UTF-8形式でエンコードされた文字列を読み込む。
    	// 予想外のファイルの終了または予想外のストリームの終了があった場合はEOFExceptionがスローされる

        // final void writeByte(int v) throws IOException  //DataOutputStream
    	// byte値を１バイト値として出力ストリームに書き出す。例外がスローされない場合、バイト数は1増加する

        // final void writeInt(int v) throws IOException //DataOutputStream
    	// int値を4バイト値として出力ストリームに書き出す。例外がスローされない場合、バイト数は4増加する

        // final void writeUTF(String str) throws IOException //DataOutputStream
    	// 引数で指定されたデータをUTF-8エンコーディングを使った形式にして出力ストリームに書き出す

        try(DataOutputStream dos = new DataOutputStream(new FileOutputStream("ren/8_4.txt"));
            DataInputStream dis = new DataInputStream(new FileInputStream("ren/8_4.txt"))){
                dos.writeInt(100); dos.writeUTF("tanaka"); dos.writeUTF("田中");
                System.out.println(dis.readInt());
                System.out.println(dis.readUTF());
                System.out.println(dis.readUTF());
                //文字列データはUTF-8でコーティングされているため
                //UTF-8以外のエンコーディングとしているテキストエディタで開くと文字化けする
        }catch(IOException e){ e.printStackTrace(); }


        // 〇キャラクタストリーム(char単位でデータ読み書きするストリーム、入出力データの文字コードは自動的に変換される）
        // FileReader		ファイルからchar単位の読み込みを行うストリーム
        // FileWriter		ファイルからchar単位の書き出しを行うストリーム

        // Java言語は1文字をUnicode（16ビットデータ）として扱っている
        // キャラクタストリームを使用すると、
        // Javaのプログラムからファイルが保存されているOSの文字コードを意識することなく入出力を行える

        // 主なメソッド
        // int read() throws IOException	//FileReader
    	// ストリームから、単一文字を読み込む。ファイルの終わりに達すると‐1を返す
        // （このメソッドはスーパークラスであるInputStreamReaderクラスのメソッド）

        // void write(String str) throws IOException  //FileWriter
    	// 引数で指定された文字列を書き出す。（このメソッドはスーパークラスであるWriterクラスのメソッド）

        // void flush() throws IOException	//FileWriter
    	// 目的の送信先に、直ちに文字を書き出す。
    	// （このメソッドはスーパークラスであるOutputStreamWriterクラスのメソッド）

        try(FileWriter fw = new FileWriter(new File("ren/8_5.txt"));
            //指定したディレクトリ以下にファイルが存在しない場合は新規作成する
            FileReader fr = new FileReader(new File("ren/8_5.txt"))){
                fw.write("田中");
                fw.flush();
                int i = 0;
                while((i = fr.read()) != -1){
                    System.out.print((char)i);
                }
        }catch(IOException e){e.printStackTrace();}


        // BufferedReader	char単位で、文字、配列、行をバッファリングしながら読み込むストリーム
        // BufferedWriter	char単位で、文字、配列、行をバッファリングしながら書き出すストリーム

        // 文字列をブロック単位で読み書きするためのストリームを作成する。
        // クラス名の一部にあるBufferedとはバッファを表し、データを一時的にためておくという意味。
        // 1文字ずつ入出力するのではなくバッファにためていき、
        // たまった文字列をまとめて読み込んだり書き出したりすることができる。

        // 主なメソッド
        // int read() throws IOException	//BufferedReader
    	// ストリームから単一文字を読み込む。ファイルの終わりに達すると‐1を返す

        // String readLine() throws IOException	//BufferedReader
    	// 1行のテキストを読み込む。1行の終わりは、改行(「\n」)か、復帰(「\r」)
    	// または復行とそれに続く改行のどれかで認識される。終わりに達するとnullを返す

        // void mark(int readAheadLimit) throws IOException	//BufferdReader
    	// ストリームの現在位置にマークを設定する。
    	// 引数にはマークを保持しながら読み込むことができる文字数の上限を指定する

        // void reset() throws IOException  //BufferdReader
    	// ストリームをmark()によりマークされた位置にリセットする

        // long skip(long n) throws IOException	//BufferedReader
    	// 引数で指定された文字数をスキップする

        // void write(String str) throws IOException		//BufferedWriter
    	// 引数で指定された文字列を書き出す。（このメソッドはスーパークラスであるWriterクラスのメソッド）

        // void newLine() throws IOException  //BufferedWriter
    	// 改行文字を書き出す。改行文字はシステムのlone.separatorプロパティにより定義

        // void flush() throws IOException  //BufferedWriter
    	// 目的の書き出し先に、ただちに文字を書き出す

        try(BufferedWriter bw = new BufferedWriter(new FileWriter("ren/8_6.txt"));
            BufferedReader br = new BufferedReader(new FileReader("ren/8_6.txt"))){
                bw.write("おはよう"); bw.newLine(); bw.write("こんにちは");
                bw.flush(); //目的のファイルに文字を書き出す
                String data = null;
                while((data = br.readLine()) != null){ //1行単位で読込
                    System.out.println(data);
                }
        }catch(IOException e){ e.printStackTrace(); }

        // コンソールから文字入力の例
        // public static final InputStream in	標準入力ストリーム（キーボード入力）
        // public static final PrintStream out	標準出力ストリーム（ディスプレイ出力）
        // pubilc static final PrintStream err	標準エラー出力ストリーム（ディスプレイ出力）

        try{
            System.out.println("文字を入力");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); //コンソールに入力
            String s = br.readLine();   //コンソールからデータを読み込む
            System.out.println("input : "+s);
        }catch(IOException e){}

        // java.io.PrintWriterクラス
        // 出力時に書式化を行うことができるメソッドを提供している。

        // 主なメソッド
        // wrire()　基本的には文字列もしくは単一文字を書き込みようとして提供されている
        // append()　write()と同様の処理を行う。
        // print(),println()　　基本データ型を引数に取るメソッドも提供。

        // これらはすべて書き込み処理を行うメソッドであるが、その違いは暗黙でflush()が呼ばれるか否か。
        // PrintWriterのインスタンス化を行う際にコンストラクタで自動フラッシュを行うよう
        // boolean値で指定（true)すると、println(),printf(),format()の各メソッドは、
        // 処理後、暗黙でflush()メソッドが実行される。

        try{
            FileOutputStream out = new FileOutputStream("my.txt");
            PrintWriter writer = new PrintWriter(out);
            writer.write("Gold");
            writer.append("Silver");
            writer.print("Bronze");
            writer.println(100.0);
            writer.flush();
            writer.close();
        }catch(FileNotFoundException e){ }
    }
}