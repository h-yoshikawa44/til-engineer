---
id: php-loop
title: 繰り返し処理
sidebar_label: 繰り返し処理
description: PHPの繰り返し処理ページ
keywords:
  - PHP
---

検証バージョン：7.3.6。

## while
```php
while ( 条件式 ) {
    処理;
}
```
```php
<?php
$testWhile = 1;
while ($testWhile < 4) {
    echo $testWhile;
    $testWhile++;
}
// 123
```

## do~while
```php
do {
    処理;
} while (条件式);
```
```php
<?php
$testDo = 1;
do {
    echo $testDo;
    $testDo++;
} while ($testDo < 4);
// 123
```

## for
```php
for (変数定義; 条件式; 更新処理) {
    処理;
}
```
```php
<?php
for ($i = 0; $i < 3; $i++) {
    echo $i;
}
// 012
```

## foreach
### 配列形式
```php
foreach (配列 as 変数名) {
    処理;
}
```
```php
<?php
$list = ['A', 'B', 'C', 'D', 'E'];
foreach ($list as $value) {
    echo $value;
}
// ABCDE
```

### 連想配列形式
```php
foreach (配列 as キー => 値) {
    処理;
}
```
```php
<?php
$keyValues = ['a' => 'A', 'b' => 'B', 'c' => 'C'];
foreach ($keyValues as $key => $value) {
    echo $key . ':' . $value . "\n";
}
// a:A
// b:B
// c:C
```

