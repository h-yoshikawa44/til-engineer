<?php
// 繰り返し文
// 制御
// ループから抜ける：break
// 次のループへ：continue

/* while ( 条件式 ) {
 *     処理;
 * }
 */
$testWhile = 1;
while ($testWhile < 4) {
    echo $testWhile;
    $testWhile++;
}
// 123
echo "\n";

/* do {
 *    処理;
 * } while (条件式);
 * 繰り返し処理が必ず一回は実行される
 */
$testDo = 1;
do {
    echo $testDo;
    $testDo++;
} while ($testDo < 4);
// 123
echo "\n";

/* for (変数定義; 条件式; 更新処理) {
 *     処理;
 * }
 */
for ($i = 0; $i < 3; $i++) {
    echo $i;
}
// 012
echo "\n";

/* foreach (配列 as 変数名) {
 *     処理;
 * }
 */
$list = ['A', 'B', 'C', 'D', 'E'];
foreach ($list as $value) {
    echo $value;
}
// ABCDE
echo "\n";

/* foreach (配列 as キー => 値) {
 *     処理;
 * }
 */
$keyValues = ['a' => 'A', 'b' => 'B', 'c' => 'C'];
foreach ($keyValues as $key => $value) {
    echo $key . ':' . $value . "\n";
}
// a:A
// b:B
// c:C