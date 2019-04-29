<?php
// 配列
// 宣言+初期化（空）
$list = [];
// 宣言+初期化
$list2 = [1, 2, 3];
print_r($list2);
// Array
// (
//     [0] => 1
//     [1] => 2
//     [2] => 3
// )
// 配列末尾に要素追加
$list2[] = 4;
print_r($list2);
// Array
// (
//     [0] => 1
//     [1] => 2
//     [2] => 3
//     [3] => 4
// )
// 配列末尾に要素追加（複数）
array_push($list2, 5, 6);
print_r($list2);
// Array
// (
//     [0] => 1
//     [1] => 2
//     [2] => 3
//     [3] => 4
//     [4] => 5
//     [5] => 6
// )
// 要素を挿入（array_spliceはもともと置換に使用する関数）
// array_splice(配列, 置き換える位置, 削除する文字数, 置き換える値)
array_splice($list2, 2, 0, 9);
print_r($list2);
// Array
// (
//     [0] => 1
//     [1] => 2
//     [2] => 9
//     [3] => 3
//     [4] => 4
//     [5] => 5
//     [6] => 6
// )
// 配列の先頭に要素追加
array_unshift($list2, 0);
print_r($list2);
// Array
// (
//     [0] => 0
//     [1] => 1
//     [2] => 2
//     [3] => 9
//     [4] => 3
//     [5] => 4
//     [6] => 5
//     [7] => 6
// )
// 配列の値を削除
unset($list2[5]);
print_r($list2);
// Array
// (
//     [0] => 0
//     [1] => 1
//     [2] => 2
//     [3] => 9
//     [4] => 3
//     [6] => 5
//     [7] => 6
// )
// 配列と配列を結合（array_merge）
// キーが数値　キー重複時、値は上書きされない。添字番号が振り直されて全て保持される。
// キーが文字列　キー重複時、値が上書きされる。
$list3 = [1, 2, 'a' => 'A'];
$list4 = [4, 5, 'a' => 'z'];
$mergeList = array_merge($list3, $list4);
print_r($mergeList);
// Array
// (
//     [0] => 1
//     [1] => 2
//     [a] => z
//     [2] => 4
//     [3] => 5
// )
// 配列と配列を結合（array_merge_recursive）
// キーが数値　キー重複時、値は上書きされない。添字番号が振り直されて全て保持される。
// キーが文字列　キー重複時、値が全て保持される。
$mergeList2 = array_merge_recursive($list3, $list4);
print_r($mergeList2);
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
// 配列と配列を結合（+演算子）
// キーが数値　キー重複時、値は前の配列を残す。添字番号の振り直しはされない。
// キーが文字列　キー重複時、値は前の配列を残す。
$mergeList3 = $list3 + $list4;
print_r($mergeList3);
// Array
// (
//     [0] => 1
//     [1] => 2
//     [a] => A
// )
// 配列のサイズ
echo count($list2); // 8
echo sizeof($list2); // 8
// 連想配列
$map = ['a' => 'A', 'b' => 'B'];
print_r($map);
// Array
// (
//     [a] => A
//     [b] => B
// )
// 連想配列・キーを指定して追加
$map['c'] = 'C';
print_r($map);
// Array
// (
//     [a] => A
//     [b] => B
//     [c] => C
// )
// 連想配列・末尾に追加
$map += ['d' => 'D'];
print_r($map);
// Array
// (
//     [a] => A
//     [b] => B
//     [c] => C
//     [d] => D
// )