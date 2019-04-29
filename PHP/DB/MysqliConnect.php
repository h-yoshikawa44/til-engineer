<?php
// データベース接続処理
// usersテーブル例
// id　email　　　　　password
// 1　 example.com 　mypassword1
// 2   example.com   mypassword2
// mysqli_connect
// サーバ名、DBユーザ名、パスワード
$connect = mysqli_connect("localhost", "root", "root");
if (mysqli_connect_error()) { // 接続エラーが出ている場合はエラーを出す
    // 失敗時は以降の処理をしない
    die("データベースへの接続に失敗しました")
}
$query = "SELECT * FROM users";
if ($result = mysql_query($connect, $query) {
    echo "クエリの発行に成功しました";
}
// 複数のデータの取り出し
while ($row = mysqli_fetch_array($result)) {;
    print_r($row);
    // Array
    // (
    //     [0] => 1
    //     [id] => 1
    //     [1] => example.com
    //     [email] => example.com
    //     [2] => mypassword1
    //     [password] => mypassword1
    // )
    // Array
    // (
    //     [0] => 2
    //     [id] => 2
    //     [1] => example.com
    //     [email] => example.com
    //     [2] => mypassword2
    //     [password] => mypassword2
    // )
}
// データ件数の取得
echo mysqli_num_rows($result);
// バインド
$password = mypassword1;
$query = "SELECT * FROM users WHERE password = " . mysqli_real_escape_string($password);
if ($result = mysql_query($connect, $query) {
    echo "クエリの発行に成功しました";
}
$row = mysqli_fetch_array($result);
print_r($row);
    // Array
    // (
    //     [0] => 1
    //     [id] => 1
    //     [1] => example.com
    //     [email] => example.com
    //     [2] => mypassword1
    //     [password] => mypassword1
    // )