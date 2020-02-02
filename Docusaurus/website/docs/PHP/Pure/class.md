---
id: php-class
title: クラス
sidebar_label: クラス
description: PHPのクラスページ
keywords:
  - PHP
---

検証バージョン：7.3.6。

[公式ドキュメント](https://www.php.net/manual/ja/language.oop5.php)

## 基本構文
クラス名はUpperCamelCase。  
クラスの中に定義された定数や変数を`プロパティ`と呼ぶ。  
また、クラス内に定義された関数を`メソッド`と呼ぶ。

これらは通常インスタンス単位で割り当てられるものである。  
クラス内で他の変数やメソッドにアクセスする際は`$this->変数名 or メソッド名`とする。

`new クラス名()`でクラスインスタンスを作成。
変数やメソッドへのアクセスは`クラスインスタンス->変数名 or メソッド名`で可能。

```php
<?php
class TestClass
{
    public $value = 'test';

    function getValue() {
        echo $this->value;
    }
}

$tc = new TestClass();
echo $tc->value; // test
$tc->getValue(); // test
```

## 静的なプロパティとメソッド
`static`をつけて定義したプロパティとメソッドは静的なものとなり、クラス単位で割り当てられる。  
また`const`で定義された定数も同様である。

クラス内で他の静的な変数やメソッドにアクセスする際は`self::プロパティ名 or メソッド名`となる。  
インスタンス化せずに呼び出しが可能であり、`クラス名::プロパティ名 or メソッド`で呼び出す。

**注意点として、静的なメソッドからは静的でない変数やメソッドにアクセスすることはできない。**
```php
<?php
class TestClass
{
    public static $value = 'test';

    const TEST_VALUE = 'TEST';

    static function getValue() {
        echo self::$value;
        echo ' ';
        echo self::TEST_VALUE;
    }
}

echo TestClass::$value; // test
echo TestClass::TEST_VALUE; // TEST
TestClass::getValue(); // test TEST
```

## アクセス修飾子
プロパティとメソッドに指定することで、アクセスできる範囲の制御ができる。
変数には指定が必須。定数やメソッドに指定しなかった場合はpublicが適用される。

- public：クラス外から自由にアクセスできる
- protected：クラス内および継承したクラス内からのみアクセスできる
- private：クラス内からのみアクセスできる

## コンストラクタ
クラスのインスタンス化時に実行されるメソッド。  
`__construct()`で定義する。引数に関しては任意で指定可能。

```php
<?php
class TestClass
{
    public $value;

    function __construct() {
        $this->value = 'test';
    }

    function getValue() {
        echo $this->value;
    }
}

$tc = new TestClass();
$tc->getValue(); // test
```

## 継承
### 子クラスの定義
クラス定義時に`extends`で継承するクラスを指定。
アクセス修飾子がprivate以外のプロパティやメソッドは、子クラスへ継承される。

なお、多重継承は不可。

```php
<?php
class TestClass
{
    public $value;

    function __construct() {
        $this->value = 'test';
    }

    function getValue() {
        echo $this->value;
    }
}

class SubTestClass extends TestClass
{
    function __construct() {
        $this->value = 'sub test';
    }
}

$tc = new TestClass();
$tc->getValue(); // test
$stc = new SubTestClass();
$stc->getValue(); // sub test
```

### オーバーライド
親クラスで定義されているメソッドと同名、同引数のメソッドを子クラスで定義して上書きできる。

```php
<?php
class TestClass
{
    function getValue() {
        echo 'parent';
    }
}

class SubTestClass extends TestClass
{
    function getValue() {
        echo 'sub';
    }
}

$tc = new TestClass();
$tc->getValue(); // parent
$stc = new SubTestClass();
$stc->getValue(); // sub
```

### 親クラスのメソッドを使用
明示的に親クラスのメソッドを使用する際は、`parent::メソッド名(引数)`で呼び出せる。

```php
<?php
class TestClass
{
    function getValue() {
        echo 'parent';
    }
}

class SubTestClass extends TestClass
{
    function getValue() {
        parent::getValue();
        echo ' ';
        echo 'sub';
    }
}

$tc = new TestClass();
$tc->getValue(); // parent
$stc = new SubTestClass();
$stc->getValue(); // parent sub
```