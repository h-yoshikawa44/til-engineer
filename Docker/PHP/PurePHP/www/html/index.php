<?php
// phpinfo();

try {
    $dsn = "mysql:host=db;dbname=test;";
    $db = new PDO($dsn, 'root', 'secret');

    $sql = "SELECT * FROM user";
    $stmt = $db->prepare($sql);
    $stmt->execute();
    $result = $stmt->fetchAll(PDO::FETCH_ASSOC);
    var_dump($result);
} catch (PDOException $e) {
    echo $e->getMessage();
    exit;
}