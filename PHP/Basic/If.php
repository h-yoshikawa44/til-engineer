<?php
// 条件文
/* if (条件式1) {
 * 　処理１;
 * } elseif (条件式2) {
 * 　処理２;
 * } else {
 * 　処理３;
 * }
 */
ifValue(6); // 大
ifValue(4); // 中
ifValue(2); // 小
function ifValue($value)
{
    if ($value > 5) {
        echo '大' . "\n";
    } elseif ($value > 3) {
        echo '中' . "\n";
    } else {
        echo '小' . "\n";
    }
}

/* switch(対象) {
 * 　case 値1:
 * 　　処理1;
 * 　　break;
 * 　case 値2:
 * 　　処理2;
 * 　default:
 *     処理3;
 * }
 */
switchValue(6); // 大中
switchValue(4); // 中
switchValue(2); // 小終
function switchValue($value)
{
    switch($value) {
        case 6:
            echo '大';
        case 4:
            echo '中';
            break; //breakがある場合はそこで処理を切り上げる
        case 2:
            echo '小';
        default:
            echo '終';
    }
}