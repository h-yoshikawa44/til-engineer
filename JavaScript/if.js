// 条件文

/* if (条件式1) {
 * 　処理１;
 * } else if (条件式2) {
 * 　処理２;
 * } else {
 * 　処理３;
 * }
 */
ifValue(6); // 大
ifValue(4); // 中
ifValue(2); // 小

function ifValue(value) {
    if (value > 5) {
        console.log("大");
    } else if (value > 3) {
        console.log("中");
    } else {
        console.log("小");
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
switchValue(6);
// 大
// 中
switchValue(4); // 中
switchValue(2);
// 小
// 終

function switchValue(value) {
    switch(value) {
       case 6:
           console.log("大");
       case 4:
           console.log("中");
           break;
       case 2:
           console.log("小");
       default:
           console.log("終");
   }
}
