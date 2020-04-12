---
id: java-class
title: クラス
sidebar_label: クラス
description: Javaのクラスページ
keywords:
  - Java
---

- 検証バージョン：openjdk version 12

## 基本構文
クラス名はUpperCamelCase。

- アクセス修飾子：public（任意で指定）
- 修飾子：final（任意で指定）

なお、クラスについて1つのファイルの中に複数クラスを定義できるが、`public`なクラスは1つしか定義できない。  
また、`public`なクラスの名前は、そのファイル名と一致させなければならない。

`final`がついたクラスに関しては、子クラスを作成できない。

```java
アクセス修飾子 修飾子 class クラス名 {
    // プロパティやメソッド定義
}
```

クラスの中に定義された定数や変数を`プロパティ`（`属性`とも）と呼ぶ。  
また、クラスの中で処理を定義したものを`メソッド`（`ファンクション`や`関数`とも）と呼ぶ。

これらは通常インスタンス単位で割り当てられるものである。  
クラス内で他のプロパティやメソッドにアクセスは、そのまま`プロパティ名`、`メソッド名(引数)`とする。  
ただ、引数名とプロパティ名が同一の時などに、明示的にプロパティの方を指定したいときは`this.プロパティ名`と書くこともできる。

`new クラス名()`でクラスインスタンスを作成。
プロパティやメソッドへのアクセスは`クラスインスタンス.プロパティ名`、`クラスインスタンス.メソッド名(引数)`で可能。

```java
public class Main {
    public static void main(String[] args) throws Exception {
        Test test = new Test();
        System.out.println(test.num); // 10
        System.out.println(test.STR); // test
        test.printValue();
        // 10
        // test
    }
}

class Test {
    int num = 10;
    final String STR = "test";

    public int getNum() {
        return num;
    }

    public String getStr() {
        return STR;
    }

    public void printValue() {
        System.out.println(getNum());
        System.out.println(getStr());
    }
}
```

## 静的なプロパティとメソッド
`static`をつけて定義したプロパティとメソッドは静的なものとなり、クラス単位で割り当てられる。

クラス内で他の静的なプロパティやメソッドにアクセスは、静的でないプロパティやメソッドにアクセスする時と同様で、そのまま`プロパティ名`、`メソッド名(引数)`となる。  
インスタンス化せずに呼び出しが可能であり、`クラス名.プロパティ名`、`クラス名.メソッド名(引数)`で呼び出す。

:::caution
注意点として、静的なメソッドからは静的でないプロパティやメソッドにアクセスすることはできない。  
:::

```java
public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println(Test.num); // 10
        System.out.println(Test.STR); // test
        Test.printValue();
        // 10
        // test
    }
}

class Test {
    static int num = 10;
    static final String STR = "test";

    public static int getNum() {
        return num;
    }

    public static String getStr() {
        return STR;
    }

    public static void printValue() {
        System.out.println(getNum());
        System.out.println(getStr());
    }
}
```

## アクセス修飾子
クラス、プロパティ、メソッドに指定することで、アクセスできる範囲の制御ができる。

- public：クラス外から自由にアクセスできる
- protected：クラス内および継承したクラス内、もしくは同パッケージのクラスからのみアクセスできる
- （指定なし）：同パッケージのクラスからのみアクセスできる
- private：クラス内からのみアクセスできる

## コンストラクタ
クラスのインスタンス化時に実行される特殊なメソッドで、プロパティの初期化処理などに使われることが多い。

### 明示的に定義するコンストラクタ
`クラス名()`で定義する。

アクセス修飾子と引数に関しては任意で指定可能。  
引数に関してはインスタンス化時に`new クラス名(引数)`とすることで渡すことができる。

```java
アクセス修飾子 クラス名(データ型 引数名) {
    // 処理
}
```

```java
public class Main {
    public static void main(String[] args) throws Exception {
        Test tc = new Test();
        tc.printValue(); // test
    }
}

class Test {
    public String value;

    Test() {
        value = "test";
    }

    public void printValue() {
        System.out.println(value);
    }
}
```

### デフォルトコンストラクタ
コンストラクタを明示的に記述していなかった場合はデフォルトコンストラクタが自動で追加される。

こちらの場合は、引数なし、処理内容は空となる。  
（親クラスがある場合は親クラスのコンストラクタを呼び出す、`super()`の処理のみとなる）

## イニシャライザブロック
### static イニシャライザ
クラスファイルがロードされたタイミングで１度だけ実行されるブロック。  
クラスをインスタンス化する前や、main()メソッドを呼び出す前に実行したい処理を定義する。

static変数の初期化に使うとよい。

```java
static { 処理 }
```

### イニシャライザ
インスタンス変数の初期化はコンストラクタがよくつかわれるが、イニシャライザでも可能。
コンパイラは、全てのコンストラクタにイニシャライザをコピーするので、複数のコンストラクタで共通の処理を行いたい時に使うとよい。

```java
{ 処理 }
```

```java
public class Main {
    public static void main(String[] args) throws Exception {
        new Initialize();
        System.out.println("---");
        new Initialize();

        // 実行結果
        // static イニシャライザ
        // イニシャライザ
        // コンストラクタ
        // ---
        // イニシャライザ
        // コンストラクタ
    }
}

class Initialize {
    {
		System.out.println("イニシャライザ");
	}
	static {
		System.out.println("static イニシャライザ");
	}
	Initialize() {
		System.out.println("コンストラクタ");
	}
}
```

## 継承
### 子クラスの定義
クラス定義時に`extends`で継承するクラスを指定。  
アクセス修飾子が`private`以外のプロパティやメソッドは、子クラスへ継承される。

なお、多重継承は不可。  
`final`がついたクラスは子クラスを作成できない。

```java
public class Main {
    public static void main(String[] args) throws Exception {
        Test tc = new Test();
        tc.printValue(); // test
        SubTest stc = new SubTest();
        stc.printValue(); // sub test
    }
}

class Test {
    public String value;

    Test() {
        value = "test";
    }

    public void printValue() {
        System.out.println(value);
    }
}

class SubTest extends Test {
    SubTest() {
        value = "sub test";
    }
}
```

### オーバーライド
親クラスで定義されているメソッドと同名、同引数のメソッドを子クラスで定義して上書きできる。

なお、`final`がついたメソッドはオーバーライドできない。

```java
public class Main {
    public static void main(String[] args) throws Exception {
        Test tc = new Test();
        tc.printValue(); // parent
        SubTest stc = new SubTest();
        stc.printValue(); // child
    }
}

class Test {
    public void printValue() {
        System.out.println("print");
    }
}

class SubTest extends Test {
    public void printValue() {
        System.out.println("child");
    }
}
```

### 親クラスのメソッドを使用
明示的に親クラスのメソッドを使用する際は、`super.メソッド名(引数)`で呼び出せる。

```java
public class Main {
    public static void main(String[] args) throws Exception {
        Test tc = new Test();
        tc.printValue(); // parent
        SubTest stc = new SubTest();
        stc.printValue();
        // parent
        // child
    }
}

class Test {
    public void printValue() {
        System.out.println("print");
    }
}

class SubTest extends Test {
    public void printValue() {
        super.printValue();
        System.out.println("child");
    }
}
```

なお、親クラスのコンストラクタを呼び出す場合は`super(引数)`とする。  
**注意点として、子クラスのコンストラクタの処理の先頭からしか呼び出せない（通常のメソッドからは呼び出せない）**

```java
public class Main {
    public static void main(String[] args) throws Exception {
        Test tc = new Test(); // parent
        SubTest stc = new SubTest();
        // parent
        // child
    }
}

class Test {
    Test() {
        System.out.println("print");
    }
}

class SubTest extends Test {
    SubTest() {
        super();
        System.out.println("child");
    }
}
```