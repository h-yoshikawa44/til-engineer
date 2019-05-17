import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// DB接続クラス
class DbConnector {
	public static Connection getConnect() throws SQLException{
		// 1.データベースの指定
		String url = "jdbc:mysql://localhost/golddb";
		String user = "root"; // ユーザ名
		String passwd = "secret"; // パスワード
		// 2.データベースとの接続
		Connection con = DriverManager.getConnection(url,user,passwd);
		return con;
	}
}

// 	従来のJDBCでは、Class.forName()メソッドによるドライバクラスのロードが必要であったが
// JDBC4.0によりドライバの自動ロード機能でCLASSPATHに含まれるドライバクラスを自動的に検出しロードするようになった
// （そのため、JDBC4.0以降のドライバを使用する場合は、Class.forName()メソッドの呼び出しは不要）

// JDBC3.0ではCLASSPATH環境変数への登録は必要で、Class.forName()メソッドの実行が必須であった
// また、ドライバクラスが見つからなかった場合、ClassnotFoundException例外をスローされるため例外処理が必須。
// JDBC4.0ではDriverManager.getConnection()メソッドが実行されると、
// JDBCドライバクラス自身がDriverManagerクラスへ登録まで行うようになっている。

public class JDBC {

    public static void main(String[] args) throws Exception {
        // JDBC
        // Javaプログラムから様々なデータベースにアクセスするための共通インタフェース

        // データベースにアクセスするクラスは、共通インタフェースとして提供されているJDBC APIを使用することで
        // 接続するデータベース製品の違いを意識することなく実装できる。
        // つまり、JDBC APIにより、データベース製品に依存しないJavaプログラムを作成できる。

        // JDBCドライバ
        // Javaプログラムとデータベースを結び付けるのに必要なJDBCインタフェースを実装したクラス。
        // JDBC APIを使用してデータベースにアクセスするためには、接続するデータベース製品向けのJDBCドライバが必要になる

        // java.sqlパッケージで提供されている主なクラス、インタフェース
        // - DriverManagerクラス		データベースのドライバを管理し、データベースとの接続を支援する
        // - Connectionインタフェース	特定のデータベースとの接続（セッション）を表現する
        // - Statementインタフェース	静的SQL文を実行し、作成された結果を返すために使用されるオブジェクト
        // - ResultSetインタフェース	データベースの結果セットを表すデータオブジェクト

    	// 4. JDBCでは3種類のステートメントを使用できる。
    	// -  Statement				標準的なSQL文を実行
    	// - PrepareStatement		プリコンパイルされたSQL文を実行
    	// - CallableStatement		ストアドプロシージャを実行

    	// 5. Statementインタフェースの主なメソッド
    	// ResultSet executeQuery(String sql)throws SQLException
    	// 単一のResultSetオブジェクトを返す。指定されたSQL文を実行する。
    	// 該当するレコードがない場合でも、nullにはならない

    	// int executeUpdate(String sql)throws SQLException
    	// 指定されたSQL文を実行する。SQL文は、INSERT文、UPDATE文、DELETE文のようなSQLデータ操作言語（DML)文、
    	// あるいはDDL文のような何も返さないSQL文を指定する。戻り値は、引数がDML文の場合は行数を返し、
    	// 何も返さないSQL文の場合は0を返す

    	// boolean execute(String sql)throws SQLException
    	// SQL文の実行結果がResultSetオブジェクトの場合はtrueを、更新行数または結果がない場合はfalseを返す

        // 6. 結果の取得と処理
        // executeQuery()メソッドの実行により、ResultSetオブジェクトとして検索結果が返る。
        // ResultSetオブジェクトは問い合わせにより返されたデータを表し、その結果を1行ずつ処理できる。
        // 値を取り出すには次の手順で行および列へアクセス

        // - 行へのアクセス
        // next()メソッドにより、1行ずつデータを取り込む。next()メソッドは1行ずつアクセスし、
        // 次に行が存在する場合はtrue,存在しない場合はfalseを返す。
        // なお、ResultSetオブジェクトの生成時には、カーソルは先頭行の前に置かれているため
        // 必ず1回はnext()メソッドを実行しなければ行を取り込めない。
        // （next()を1回も呼び出さずにデータの取り出しを行うとSQLExceptionSQLException例外が発生）

        // - 列へのアクセス
        // getterメソッドで引数に列番号を指定することによりデータを取り出せる。
        // 列番号はResultSetオブジェクト内の左から右へ順にふられた番号で、最初の列番号は１。
        // getterメソッドは、getXXX(列名）またはgetXXX(列番号）というメソッド名で定義されており
        // XXXの部分にはJavaのデータ型名が入る。

    	/* SQL型			Java型					メソッド名
    	 * CHAR		  java.lang.String			getString()
    	 * VARCHAR	  java.lang.String			getString()
    	 * NUMBER	  java.math.BigDecimal  	getBigDecimal()
    	 * INTERGER	  int					    getInt()
    	 * INT		  int					    getInt()
    	 * DOUBLE	  double				    getDouble()
    	 * DATE		  java.sql.Date				getDate()
    	 * TIME		  java.sql.Time				getTime()
    	 * TIMESTAMP  java.sql.Timestamp	    getTimestamp()
    	 *
    	 * Java.sql配下の日時の型は以下のメソッドで変換可能
    	 * toLocalDate()　　java.sql.Date → java.time.LocalDate
    	 * toLocalTime()　　java.sql.Time → java.time.LocalTime
    	 * toLocalDateTime()  java.sql.Timestamp → java.timeLocalDateTime
    	 *
         */

        Connection con = null;
        Statement stmt = null;
        ResultSet rs =  null;
        try{
            con = DbConnector.getConnect();
            //④ステートメントの取得
            stmt = con.createStatement();
            //⑤SQL文の実行
            String sql = "SELECT dept_code,dept_name FROM department";
            rs = stmt.executeQuery(sql);
            //⑥結果の取得と処理
            while(rs.next()){
                System.out.println("dept_code : "+rs.getInt(1)); //1列目のInt値を取得
                System.out.println("dept_name : "+rs.getString(2)); //2列目の文字列を取得
            }
        //}catch(ClassNotFoundsException e){e.printStackTrace();}  JDBC3.0まででは必要なコード
        }catch(SQLException e){
            e.printStackTrace();
        //⑦接続のクローズ
        }finally{
            try{
                if(rs != null) rs.close();
                if(stmt != null) stmt.close();
                if(con != null) con.close();
            }catch(SQLException e){ e.printStackTrace();}
		}

        // 例外
        // Javaアプリケーションがデータベースと通信した時に発生したエラーは、
        // データベースからアプリケーションへ通知される。
        // エラーに通知にはSQLExceptionオブジェクトが使用される。SQLException例外が発生する要因は様々。
        // - ネットワークケーブルの物理的な問題などにより通信が切断した
        // - SQLコマンドのフォーマットが不適切である
        // - サポートされていない機能を使用した
        // - 存在しない列を参照した

        // SQLExceptionクラスのメソッドを使用して、エラーメッセージを取得することができる
        // 主なメソッド
        // - int getErrorCode()	ベンダ固有の例外コードを取得する
        // - String getSQLState()	SQLStateを取得する


        // ResultSetオブジェクト上でデータの挿入・更新
        // 更新可能なResultSetオブジェクトでは、問い合わせ結果を使用してレコードの挿入や更新・削除を行うことができる

        // ResultSetインタフェースの更新処理用メソッド
        // void updateString(int columnIndex,String x)throws SQLException
     	// 第1引数で指定された列を、第2引数で指定したString値で更新する

        // void updateInt(int columnIndex,int x)throws SQLException
     	// 第1引数で指定された列を、第2引数で指定したint値で更新する

        // void updateRow()throws SQLException
     	// 変更内容をデータベースに反映する

        // void moveToInsertRow()throws SQLException
     	// カーソルを挿入行に移動する

        // void insertRow()throws SQLException
     	// 挿入行の内容をデータベースに挿入する

        // void deleteRow()throws SQLException
     	// データベースから、現在の行を削除する

        String sql = "SELECT dept_code,dept_address FROM department "+
                     "WHERE dept_code = 4";
        try(Connection con = DbConnector.getConnect();
            Statement stmt = con.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(sql)){
                if(rs.next()) System.out.println(rs.getString(2));
                rs.updateString(2, "Chiba"); //2列目の要素をChibaに更新
                rs.updateRow(); //変更内容をデータベースに変更
        }catch(SQLException e){e.printStackTrace();}
    }
}
