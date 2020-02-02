---
id: java-if
title: 分岐処理
sidebar_label: 分岐処理
description: Javaの分岐処理ページ
keywords:
  - Java
---

- 検証バージョン：openjdk version 12

## if
```java
if (条件式1) {
    処理1;
} else if (条件式2) {
    処理2;
} else {
    処理3;
}
```
```java
public class Main {
    public static void main(String[] args) throws Exception {
        ifValue(6); // 大
        ifValue(4); // 中
        ifValue(2); // 小
    }

    public static void ifValue(int value) {
        if (value > 5) {
            System.out.println("大");
        } else if (value > 3) {
            System.out.println("中");
        } else {
            System.out.println("小");
        }
    }
}
```

## 三項演算子
```java
条件式 ? 真の時の処理 : 偽の時の処理
```
```java
public class Main {
    public static void main(String[] args) throws Exception {
        int num = 9;
        String str = "numの値は";
        str += num < 10 ? "10未満" : "10以上";
        System.out.println(str); // numの値は10未満
    }
}
```

## switch
```java
switch (対象) {
    case 値1:
        処理1;
        break;
    case 値2:
        処理2;
    default:
        処理3;
}
```
```java
public class Main {
    public static void main(String[] args) throws Exception {
        switchValue(6); // 大中
        switchValue(4); // 中
        switchValue(2); // 小終
    }

    public static void switchValue(int value) {
        switch(value) {
            case 6:
                System.out.print("大");
            case 4:
                System.out.print("中");
                break;
            case 2:
                System.out.print("小");
            default:
                System.out.print("終");
        }
    }
}
```
