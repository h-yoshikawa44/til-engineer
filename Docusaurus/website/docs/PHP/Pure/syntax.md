---
id: php-syntax
title: 基本構文
sidebar_label: 基本構文
description: PHPの基本構文ページ
keywords:
  - PHP
---

- 検証バージョン：7.3.6

## 文末のセミコロン
必要。

## 変数
[公式ドキュメント](https://www.php.net/manual/ja/language.variables.php)

変数名はLowerCamelCaseで頭文字に`$`をつけるのが特徴。
動的型付けであるため、型指定は不要。

```php
<?php
$testValue = 'test';
echo $testValue; // test
```

## 定数
[公式ドキュメント](https://www.php.net/manual/ja/language.constants.php)

定数名はScreamingSnakeCase。
```php
<?php
define('TEST_VALUE', 'test');
echo TEST_VALUE; // test
```

## 関数
[公式ドキュメント](https://www.php.net/manual/ja/language.functions.php)

### 基本構文
関数名はLowerCamelCase。
```php
<?php
function testValue($value)
{
    echo $value;
}

testValue('test'); // test
```

### タイプヒンティング
以下のように引数の型を指定して、型のチェックを行うことも可能。

```php
<?php
function testValue(string $value)
{
    echo $value;
}

testValue('test'); // test
```

使用できる型一覧。
- クラス名・インタフェース名
- self（そのメソッドが定義されているクラスと同じクラスのインスタンス）
- array
- callable
- boolean
- float
- int
- string
- iterable（arrayまたはTraversable、instanceof）
- object

### 引数のデフォルト値
引数にデフォルト値指定も可能。

```php
<?php
function testValue($value = 'test')
{
    echo $value;
}

testValue(); // test
```

### 可変長引数
`...`で可変長の引数を受け取ることができる。

```php
<?php
function testValue(...$value)
{
    var_dump($value);
}
testValue('a', 'b');
// array(2) {
//   [0]=>
//   string(1) "a"
//   [1]=>
//   string(1) "b"
// }
```

### 無名関数
クロージャとも呼ばれる。  
関数名を指定せずに関数を作成できる。

コールバックパラメータとして使用できるが、変数の値としても使用できる。

```php
<?php
$testValue = function($value)
{
    echo $value;
}; // セミコロンを忘れずにつける

$testValue('test'); // test
```