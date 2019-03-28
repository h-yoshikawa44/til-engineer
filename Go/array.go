package main
import "fmt"
func main(){
    // 配列（arrayとsliceがある）
    // array...要素の個数が決まっている。
    //         配列全てを表す型が存在しない。以下は全て別々の型として扱われる
    //         例 [3]int [3]string [4]int
    // slice...arrayより柔軟。Rubyのarrayに近いが、そこまでは柔軟でない。
    //         arrayを参照しているラッパーのようなもの。
    //         型名は[]型名のように表現する。

    // array宣言
    // 足りない値が自動的に埋められる
    var arr [3]int
    fmt.Println(arr) // [0 0 0]

    // array 宣言+初期化 省略形
    arr2 := [...] int{1, 2, 3}
    fmt.Println(arr2) // [1 2 3]

    // array 要素の追加
    // 要素の個数が決まっているため、追加という概念がない

    // slice宣言
    var slice []int
    fmt.Println(slice) // []

    // slice 宣言+初期化
    slice2 := []int{1, 2, 3}
    fmt.Println(slice2) // [1 2 3]

    // slice 配列末尾に要素の追加
    slice2 = append(slice2, 4)
    fmt.Println(slice2) // [1 2 3 4]

    // slice スライス操作
    // slice[開始位置:終了位置]　startかたend-1 まで
    fmt.Println(slice2[0:2]) // [1 2]

    // slice 長さと容量
    // len...要素数
    // cap...スライスの最初の要素から数えて、元となる配列の要素数
    slice3 := [...] string{"A", "B"}
    slice4 := slice3[0:1]
    fmt.Println(slice4) // [A]
    fmt.Println(len(slice4)) // 1
    fmt.Println(cap(slice3)) // 2
}
