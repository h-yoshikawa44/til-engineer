---
id: java-loop
title: 繰り返し処理
sidebar_label: 繰り返し処理
description: Javaの繰り返し処理ページ
keywords:
  - Java
---

- 検証バージョン：openjdk version 12

## while
```java
while ( 条件式 ) {
    処理;
}
```
```java
public class Main {
    public static void main(String[] args) throws Exception {
        int testWhile = 1;
        while (testWhile < 4) {
            System.out.println(testWhile);
            testWhile++;
        }
        // 1
        // 2
        // 3
    }
}
```

## do~while
```java
do {
    処理;
} while (条件式);
```
```java
public class Main {
    public static void main(String[] args) throws Exception {
        int testDo = 1;
        do {
            System.out.println(testDo);
            testDo++;
        } while (testDo < 4);
        // 1
        // 2
        // 3
    }
}
```

## for
```java
for (変数定義; 条件式; 更新処理) {
    処理;
}
```
```java
public class Main {
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 3; i++) {
            System.out.println(i);
        }
        // 0
        // 1
        // 2
    }
}
```

## 拡張for
```java
for(データ型 変数名 : 配列またはコレクション) {
    処理;
}
```
```java
public class Main {
    public static void main(String[] args) throws Exception {
        String[] list = {"A", "B", "C"};
        for (String value : list) {
            System.out.println(value);
        }
        // A
        // B
        // C
    }
}
```