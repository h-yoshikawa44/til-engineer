package main
import "fmt"
func main(){
    // 標準出力処理

    // Println
    // 一つ以上の引数を出力（改行あり）
    // カンマ区切りで渡した際は、スペース区切りで表示される
    println("Hello Go") // Hello Go
    println("Hello", "World") // Hello World

    // fmt.Println
    // 出力（改行あり）
    fmt.Println("ABCDE") // ABCDE

    // フォーマット済みの文字列を出力（改行なし）
    fmt.Printf("Hello, %sさん", "太郎") // Hello, 太郎さん
}
