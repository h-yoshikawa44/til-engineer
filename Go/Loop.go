package main
import "fmt"
func main(){
	// 繰り返し文

	// 制御
    // ループから抜ける：break
    // 次のループへ：continue

    /* for 変数の初期化; 条件式; 更新処理 {
     *     処理
     * }
     * 変数の初期化ではvar定義は不可
     * i := 0といった省略記法しか使用できない
     */
    for i := 0; i < 3; i++ {
        fmt.Println(i)
    }
    // 0
    // 1
    // 2
}