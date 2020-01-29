---
id: php-array-basic
title: 配列基本操作
sidebar_label: 基本操作
description: PHPの配列基本操作ページ
keywords:
  - PHP
---

## 配列
### 宣言 + 初期化
```php
<?php
$list = [];
print_r($list);
// Array
// (
// )

$list2 = [1, 2, 3];
print_r($list2);
// Array
// (
//     [0] => 1
//     [1] => 2
//     [2] => 3
// )
```

### 要素追加
#### 配列末尾に追加
```php
<?php
$list = [1, 2, 3];
$list[] = 4;
print_r($list);
// Array
// (
//     [0] => 1
//     [1] => 2
//     [2] => 3
//     [3] => 4
// )
```

#### 配列末尾に追加（1つ～複数）：array_push
[公式ドキュメント](https://www.php.net/manual/ja/function.array-push)

```php
array_push(配列, 追加する値...);
```
```php
<?php
$list = [1, 2, 3];
array_push($list, 4, 5);
print_r($list);
// Array
// (
//     [0] => 1
//     [1] => 2
//     [2] => 3
//     [3] => 4
//     [4] => 5
// )
```

#### 配列間に追加：array_splise
[公式ドキュメント](https://www.php.net/manual/ja/function.array-splice)

array_spliceはもともと置換に使用する関数。
削除する文字数を0とすることで間に挿入としても使用できる。
```php
array_splice(配列, 置き換える位置, 削除する文字数, 置き換える値);
```
```php
<?php
$list = [1, 2, 3];
array_splice($list, 2, 0, 9);
print_r($list);
// Array
// (
//     [0] => 1
//     [1] => 2
//     [2] => 9
//     [3] => 3
// )
```

#### 配列先頭に追加（1つ～複数）：array_unshift
[公式ドキュメント](https://www.php.net/manual/ja/function.array-unshift)

```php
array_unshift(配列, 追加する値...);
```
```php
<?php
$list = [1, 2, 3];
array_unshift($list, 0);
print_r($list);
// Array
// (
//     [0] => 0
//     [1] => 1
//     [2] => 2
//     [3] => 3
// )
```

### 要素削除（1つ～複数）：unset
[公式ドキュメント](https://www.php.net/manual/ja/function.unset)

```php
unset(削除する変数...);
```
```php
<?php
$list = [1, 2, 3];
unset($list[1]);
print_r($list);
// Array
// (
//     [0] => 1
//     [2] => 3
// )
```

### 配列の結合
#### array_merge
[公式ドキュメント](https://www.php.net/manual/ja/function.array-merge)

3つ以上の配列の結合も可能。
```php
array_merge(配列...);
```

キーが数値：キー重複時、値は上書きされない。添字番号が振り直されて全て保持される。  
キーが文字列：キー重複時、値が上書きされる。
```php
<?php
$list = [1, 2, 'a' => 'A'];
$list2 = [4, 5, 'a' => 'z'];
$mergeList = array_merge($list, $list2);
print_r($mergeList);
// Array
// (
//     [0] => 1
//     [1] => 2
//     [a] => z
//     [2] => 4
//     [3] => 5
// )
```

#### array_merge_recursive
[公式ドキュメント](https://www.php.net/manual/ja/function.array-merge-recursive)

3つ以上の配列の結合も可能。
```php
array_merge_recursive(配列...);
```

キーが数値：キー重複時、値は上書きされない。添字番号が振り直されて全て保持される。  
キーが文字列：キー重複時、値が全て保持される。
```php
<?php
$list = [1, 2, 'a' => 'A'];
$list2 = [4, 5, 'a' => 'z'];
$mergeList = array_merge_recursive($list, $list2);
print_r($mergeList);
// Array
// (
//     [0] => 1
//     [1] => 2
//     [a] => Array
//         (
//             [0] => A
//             [1] => z
//         )

//     [2] => 4
//     [3] => 5
// )
```

#### +演算子
キーが数値：キー重複時、値は前の配列を残す。添字番号の振り直しはされない。  
キーが文字列：キー重複時、値は前の配列を残す。
```php
<?php
$list = [1, 2, 'a' => 'A'];
$list2 = [4, 5, 'a' => 'z'];
$mergeList = $list + $list2;
print_r($mergeList);
// Array
// (
//     [0] => 1
//     [1] => 2
//     [a] => A
// )
```

### 配列のサイズ
#### count
[公式ドキュメント](https://www.php.net/manual/ja/function.count)

```php
<?php
$list = [1, 2, 3];
echo count($list); // 3
```

#### sizeof
[公式ドキュメント](https://www.php.net/manual/ja/function.sizeof)


```php
<?php
$list = [1, 2, 3];
echo sizeof($list); // 3
```

## 連想配列
### 宣言 + 初期化
```php
<?php
$map = ['a' => 'A', 'b' => 'B'];
print_r($map);
// Array
// (
//     [a] => A
//     [b] => B
// )
```

### 要素の追加
#### キーを指定
```php
<?php
$map = ['a' => 'A', 'b' => 'B'];
$map['c'] = 'C';
print_r($map);
// Array
// (
//     [a] => A
//     [b] => B
//     [c] => C
// )
```

#### 配列末尾に追加
```php
<?php
$map = ['a' => 'A', 'b' => 'B'];
$map += ['d' => 'D'];
print_r($map);
// Array
// (
//     [a] => A
//     [b] => B
//     [c] => C
// )
```