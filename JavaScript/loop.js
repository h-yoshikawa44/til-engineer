// 繰り返し文

/* while ( 条件式 ) {
 *     処理;
 * }
 */
let testWhile = 1;
while (testWhile < 4) {
    console.log(testWhile);
    testWhile++;
}
// 1
// 2
// 3

/* do {
 *    処理;
 * } while (条件式);
 * 繰り返し処理が必ず一回は実行される
 */
let testDo = 1;
do {
    console.log(testDo);
    testDo++;
} while (testDo < 4);
// 1
// 2
// 3

/* for (変数定義; 条件式; 更新処理) {
 *     処理;
 * }
 */
for (i = 0; i < 3; i++) {
    console.log(i);
// 0
// 1
// 2
}

/* forEach (コールバック関数)
 *
 */
let list = [1, 2, 3];
list.forEach(function(num) {
    console.log(num);
})
// 1
// 2
// 3

/* forEach (コールバック関数) アロー関数
 */
list.forEach(num =>  {
    console.log(num);
})
// 1
// 2
// 3