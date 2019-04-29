// 配列

// 宣言+初期化（空）
let list = new Array();

//宣言+初期化
let list2 = [1, 2, 3];
console.log(list2); // [ 1, 2, 3 ]

// 配列末尾に要素追加（複数指定可能）
list2.push(4);
console.log(list2); // [ 1, 2, 3, 4 ]

// 要素を挿入（spliceは置換にも使用できる関数）
// 配列.splice(置き換える位置, 削除する文字数, 置き換える値)
list2.splice(2, 0, 5);
console.log(list2); // [ 1, 2, 5, 3, 4 ]

// 配列先頭に要素追加
list2.unshift(0);
console.log(list2); // [ 0, 1, 2, 5, 3, 4 ]

// 要素の削除
list2.shift(1);
console.log(list2); // [ 1, 2, 5, 3, 4 ]

// 末尾要素の取り出し
var test = list2.pop();
console.log(list2); // [ 1, 2, 5, 3 ]
console.log(test); // 4

// 配列のサイズ
console.log(list2.length);  // 4