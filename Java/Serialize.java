import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

//クラス定義時にimplements Serializableと記述
class Sample implements Serializable{
	private int id;
	private String name;
	public Sample(int id,String name){
		this.id = id; this.name = name;
	}
	//Serializableインタフェースはメソッドを持たないため
	//オーバーライドしなければならないメソッドはない
	public int getId(){
		return this.id;
	}
	public String getName(){
		return this.name;
	}
}

public class Serialize {
    public static void main(String[] args) throws Exception {
        // シリアライズ
    	// Java言語では、独自定義したクラスから生成したオブジェクトをオブジェクトのまま入出力できる
    	// オブジェクトを出力ストリームに書き出すことをシリアライズまたは直列化と呼ぶ
        // また、シリアライズされたオブジェクトを読み込んで、メモリ上に復元することをデシリアライズまたは直列化復元と呼ぶ

    	// シリアライズしたいオブジェクトはクラス定義の際にjava.io.Serializableインタフェースを実装する
    	// （メソッドや定数を持たないインタフェースであるため、オーバーライドするメソッドはない）
        // また、あるクラスのオブジェクトをシリアライズする場合、その親クラスがSerializableインタフェースを実装していれば可能

    	// オブジェクトのシリアライズは、
    	// オブジェクトが保持する固有データ、インスタンス変数がシリアライズ対象データとなる
    	// シリアライズ可能なデータは基本データ型、配列、他のオブジェクトへの参照
    	// static変数はシリアライズ対象外
    	// また、明示的にシリアライズ対象外にしたいインスタンス変数がある場合は変数にtransient修飾子を指定する

    	// オブジェクトを入出力するためのストリーム
        // シリアライズ可能なオブジェクトの入出力にはこのクラスを用いる
        // ObjectInputStream
        // ObjectOutputStream

        // 主なメソッド
        // final Object readObject() throws IOException,ClassNotFoundException
        // ObjectInputStreamからオブジェクトを読み込む。戻り値はObject型。

        // final void writeObject(Object obj) throws IOException
        // 引数で指定されたオブジェクトをObjectOutputStreamに書き出す。
        // 引数のオブジェクトはシリアライズ可能なオブジェクトである必要がある。

        Sample writeEmp = new Sample(100,"tanaka");
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("ren/8_8.txt"));
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("ren/8_8.txt"))){
				oos.writeObject(writeEmp); //書き出し
				Sample readEmp = (Sample)ois.readObject(); //読み込み
				System.out.println("ID : "+readEmp.getId()); // 100
				System.out.println("Name : "+readEmp.getName()); // tanaka
		}catch(ClassNotFoundException | IOException e){
			//ClassNotFoundException例外もキャッチ。
			//readObject()メソッドがシリアライズされたオブジェクトを復元する際に
			//クラスファイルが見つからない場合にスローされるため
			e.printStackTrace();
		}
    }
}