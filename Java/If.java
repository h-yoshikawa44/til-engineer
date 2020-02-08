public class If {
    public static void main(String[] args) throws Exception {
        // 条件文

        /* if (条件式1) {
         *      処理1;
         *  } else if (条件式2) {
         *      処理2;
         *  } else {
         *      処理3;
         *  }
         */
        ifValue(6); // 大
        ifValue(4); // 中
        ifValue(2); // 小

        /* switch (対象) {
         *     case 値1:
         *         処理1;
         *         break;
         *     case 値2:
         *         処理2;
         *     default:
         *         処理3;
         * }
         */
        switchValue(6); // 大中
        switchValue(4); // 中
        switchValue(2); // 小終

        // 三項演算子
        int num = 9;
        String str = "numの値は";
        str += num < 10 ? "10未満" : "10以上";
        System.out.println(str); // numの値は10未満
    }
    }

    public static void ifValue(int value) {
        if (value > 5) {
            System.out.println("大");
        } else if (value > 3) {
            System.out.println("中");
        } else {
            System.out.println("小");
        }
    }

    public static void switchValue(int value) {
        switch(value) {
            case 6:
                System.out.println("大");
            case 4:
                System.out.println("中");
                break;
            case 2:
                System.out.println("小");
            default:
                System.out.println("終");
        }
    }
}