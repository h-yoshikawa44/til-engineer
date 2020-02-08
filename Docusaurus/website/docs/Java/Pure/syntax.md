---
id: java-syntax
title: 基本構文
sidebar_label: 基本構文
description: Javaの基本構文ページ
keywords:
  - Java
---

- 検証バージョン：openjdk version 12

## 実行箇所
mainメソッドから。  
なお、mainメソッドを持つクラスしか実行することはできない。

mainメソッドは必ず以下のようになる（throwsの部分は任意）
最初に実行されるメソッド = `public static`である必要があるため。

```java
public class Main {
    public static void main(String[] args) throws Exception {
        // 処理
    }
}
```

## 文末のセミコロン
必要。

## 変数
変数名はLowerCamelCase。  
静的型付けであるため、型指定が必要。

### プリミティブ型
使用できるデータ型。

|データ型|種別|値の範囲|
|---|---|---|
|byte|符号付整数(8bit)|-128~127|
|short|符号付整数(16bit)|-32768~32767|
|int|符号付整数(32bit)|-2147483648~2147483647|
|long|符号付整数(64bit)|-9223372036854775808~9223372036854775807|
|float|浮動小数点数(32bit)||
|double|浮動小数点数(64bit)||
|boolean|真偽値(8bit)|true, false|
|char|Unicode文字(16bit)|'¥u0000'~'¥uffff'/0~65535|

```java
public class Main {
    public static void main(String[] args) throws Exception {
        byte bNum = 5;
        System.out.println(bNum); // 5

        short sNum = 10;
        System.out.println(sNum); // 10

        int num1 = 15;
        System.out.println(num1); // 15

        int num2 = 010; // 8進数(先頭に0をつける)
        System.out.println(num2); // 8

        int num3 = 0xa; // 16進数(先頭に0xをつける)
        System.out.println(num3); // 10

        int num4 = 0b11; // 2進数(先頭に0bをつける)
        System.out.println(num4); // 3

        long lNum = 100L; // long型のリテラル(末尾にLをつける)
        System.out.println(lNum); // 100

        float fNum = 3.14f; // float型のリテラル(末尾にfをつける)
        System.out.println(fNum); // 3.14

        double dNum = 6.28;
        System.out.println(dNum); // 6.28

        boolean bool = true;
        System.out.println(bool); // true

        char ch = 'A'; // 文字リテラル(シングルクォートで囲む)
        System.out.println(ch); // A
    }
}
```

### 参照型
|データ型|種別|
|---|---|
|String|文字列を扱うクラス|
|配列|各種データ型の配列|
|クラス|各種クラス|

```java
public class Main {
    public static void main(String[] args) throws Exception {
        String str = "test";
        System.out.println(str); // test

        String[] arr = {"a", "b", "c"};
        System.out.println(arr[0]); // a
        System.out.println(arr[1]); // b
        System.out.println(arr[2]); // c

        Test test = new Test();
        System.out.println(test.num); // 5
    }
}

class Test {
    public int num = 5;
}
```

## 定数
定数名はScreamingSnakeCase。

`final`がついたものは、あとから値の変更ができなくなる。

```java
public class Main {
    public static void main(String[] args) throws Exception {
        final String TEST_VALUE = "test";
        System.out.println(TEST_VALUE); // test
    }
}
```

## メソッド
※後で書く。