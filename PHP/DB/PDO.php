<?php
// データベース接続処理
// userテーブル例
// id　name　　　email
// 1　 taro   taro@example.com
// 2   jiro   jiro@example.com

// PDO
try {
    // DB種類：host=ホスト名;dbname=DB名;
    $dsn = "mysql:host=db;dbname=test;";
    // 接続先情報、ユーザ名、パスワード
    $db = new PDO($dsn, 'root', 'secret');

    $sql = "SELECT * FROM user";
    $stmt = $db->prepare($sql);
    $stmt->execute();
    $result = $stmt->fetchAll(PDO::FETCH_ASSOC);
    print_r($result);
    // Array
    // (
    //     [0] => Array (
    //         [id] => 1
    //         [name] => taro
    //         [email] => rato@example.com
    //     )
    //     [1] => Array (
    //         [id] => 2
    //         [name] => jiro
    //         [email] => jiro@example.com
    //     )
    // )
} catch (PDOException $e) {
    echo $e->getMessage();
    exit;
}

// バインド
try {
    $name = "taro";
    $sql = "SELECT * FROM user WHERE name = :name";
    $stmt = $db->prepare($sql);
    $stmt->bindParam(":name", $name, PDO::PARAM_STR);
    $stmt->execute();
    $result = $stmt->fetchAll(PDO::FETCH_ASSOC);
    print_r($result);
    // Array
    // (
    //     [0] => Array (
    //         [id] => 1
    //         [name] => taro
    //         [email] => rato@example.com
    //     )
    // )
} catch (PDOException $e) {
    echo $e->getMessage();
    exit;
}