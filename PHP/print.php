<?php
// 標準出力処理

/* echo
 * 一つ以上の文字列を出力する
 * 関数でなく言語構造であるため、引数を括弧でくくる必要がない
 */
$test = 'ABC' . "\n";
echo $test; // ABC
echo 'A', 'B', 'C', 'D' . "\n"; // ABCD

/* print
 * 単一の引数のみ受け付ける出力
 * 関数でなく言語構造であるため、引数を括弧でくくる必要がない
 */
$testPrint = 'DEF' . "\n";
print $testPrint; // DEF
print "alphabet $testPrint\n"; // alphabet DEF

/* printf
 * フォーマット済みの文字列を出力する
 */
$testPrintF = 'alphabet A:%s B:%s';
printf($testPrintF, 'a', 'b'); // alphabet A:a B:b

/* var_dump
 * 指定した式に関して、その型や値を含む構造化された情報を返す
 */
$testVar = ['A', 'B', 'C', 'D'];
var_dump($testVar);
// array(4) {
//     [0] =>
//     string(1) "A"
//     [1] =>
//     string(1) "B"
//     [2] =>
//     string(1) "C"
//     [3] =>
//     string(1) "D"
//   }

/* print_r
 * 指定した変数に関する情報をわかりやすく出力する
 */
$testPrintR = ['A', 'B', 'C', 'D'];
print_r($testPrintR);
// Array
// (
//     [0] => A
//     [1] => B
//     [2] => C
//     [3] => D
// )

