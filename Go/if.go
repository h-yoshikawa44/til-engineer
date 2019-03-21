package main
import "fmt"
func main(){
    // 条件文

    /* if 条件式1 {
     *      処理1
     *  } else if 条件式2 {
     *      処理2
     *  } else {
     *      処理3
     *  }
     */
    ifValue(6) // 大
    ifValue(4) // 中
    ifValue(2) // 小

    /* switch 対象 {
     *     case 値1:
     *         処理1
     *     case 値2:
     *         処理2
     *     default:
     *         処理3
     * }
     * 一つのケースあたりに、カンマ区切りで複数の値を指定可能
     * その場合はOR条件になる
     * 他言語のようにbreakは不要
     */
    switchValue(6) // 大
    switchValue(5) // 中
    switchValue(4) // 中
    switchValue(2) // 小
}

func ifValue(value int) {
   if value > 5 {
       fmt.Println("大")
   } else if value > 3 {
       fmt.Println("中")
   } else {
       fmt.Println("小")
   }
}

func switchValue(value int) {
    switch value {
        case 6:
            fmt.Println("大")
        case 4, 5:
            fmt.Println("中")
        case 2:
            fmt.Println("小")
        default:
            fmt.Println("終")
    }
}