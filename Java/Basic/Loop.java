public class Loop {
    public static void main(String[] args) throws Exception {
        // 繰り返し文

        // 制御
        // ループから抜ける：break
        // 次のループへ：continue

        /* while ( 条件式 ) {
         *     処理;
         * }
         */
        int testWhile = 1;
        while (testWhile < 4) {
            System.out.println(testWhile);
            testWhile++;
        }
        // 1
        // 2
        // 3

        /* do {
         *     処理;
         * } while (条件式);
         */
        int testDo = 1;
        do {
            System.out.println(testDo);
            testDo++;
        } while (testDo < 4);
        // 1
        // 2
        // 3

        /* for (変数初期化; 条件式; 更新処理) {
         *     処理;
         * }
         */
        for (int i = 0; i < 3; i++) {
            System.out.println(i);
        }
        // 0
        // 1
        // 2

        String[] forList = {"a", "b", "c"};
        for (int i = 0; i < 3; i++) {
            System.out.println(forList1[i]);
        }
        // a
        // b
        // c

        /* for(データ型 変数名 : 配列またはコレクション) {
         *     処理;
         * }
         */
        String[] forExList = {"A", "B", "C"};
        for (String value : forExList) {
            System.out.println(value);
        }
        // A
        // B
        // C
    }
}