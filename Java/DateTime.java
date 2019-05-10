import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTime {
    public static void main(String[] args) throws Exception {
        // 日付、時間
        // ※従来のjava.util.Dateクラスは不備があり、多くが非推奨となっている

        // 現在日時
        System.out.println(LocalDate.now()); // 現在の日付（YYYY-MM-ｄｄ）
        System.out.println(LocalTime.now()); // 現在の時刻（HH:mm:ss.XXXXXXXXX)
        System.out.println(LocalDateTime.now()); // 現在の日付と時刻（YYYY-MM-ddTHH:mm:ss.XXXXXX）

        // 日時指定
        System.out.println(LocalDate.of(2019, 5, 2)); // 2019-05-02
        System.out.println(LocalDate.of(2019, Month.MAY, 2)); // 2019-05-02
        System.out.println(LocalTime.of(12, 0, 0)); //  12:00:00
        System.out.println(LocalDateTime.of(2019, 5, 2, 12, 0, 0)); // // 2019-05-02T12:00:00

        // テキスト文字列で日時指定
        System.out.println(LocalDate.parse("2019-05-02")); // 2019-05-02
        System.out.println(LocalTime.parse("12:00:00")); // 12:00:00
        System.out.println(LocalDateTime.parse("2019-05-02T12:00:00")); // 2019-05-02T12:00:00

        // 日付情報取得
        System.out.println(LocalDate.of(2019, 5, 2).getYear()); // 2019
		System.out.println(LocalDate.of(2019, 5, 2).getMonth()); // MAY 戻り値はMonth型
		System.out.println(LocalDate.of(2019, 5, 2).getMonthValue()); //5 戻り値はint型
		System.out.println(LocalDate.of(2019, 5, 2).getDayOfMonth()); // 2

        // フォーマット指定
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter fmt1 = DateTimeFormatter.ISO_DATE;
        System.out.println(fmt1.format(dateTime)); // YYYY-MM-dd
        DateTimeFormatter fmt2 = DateTimeFormatter.ISO_TIME;
        System.out.println(fmt2.format(dateTime)); // HH:mm:dd.XXXXX
        DateTimeFormatter fmt3 = DateTimeFormatter.ISO_DATE_TIME;
        System.out.println(fmt3.format(dateTime)); // YYYY-mm-ddTHH:mm:dd.XXXXX

        // 独自フォーマット指定
        // ロケールの指定がない場合はデフォルトで日本になる
        DateTimeFormatter fmt4 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String target = "2019/05/02 12:00:00";
        LocalDateTime dateTime2 = LocalDateTime.parse(target, fmt4);
        System.out.println(dateTime2); // 2019-05-02T12:00

        // 日付計算
        LocalDate date = LocalDate.of(2019,  5, 2);
        System.out.println(date.plusDays(3)); // 2019-05-05
        System.out.println(date.plusMonths(4)); // 2019-09-02
        System.out.println(date.plusWeeks(5)); // 2019-06-06
        System.out.println(date.plusYears(10)); // 2029-05-02
        System.out.println(date.minusDays(3)); // 2019-04-29
        System.out.println(date.minusMonths(4)); // 2019-01-02
        System.out.println(date.minusWeeks(5)); // 2019-03-28
        System.out.println(date.minusYears(10)); // 2009-05-02

        // タイムゾーン
        ZoneId zone1 = ZoneId.systemDefault();
        LocalDateTime lDateTime1 = LocalDateTime.of(2019, 5,10,12,00,00,200);
        ZonedDateTime zDateTime1 = ZonedDateTime.of(lDateTime1, zone1);
        System.out.println(zDateTime1); // 2019-05-10T12:00:00.000000200+09:00[GMT+09:00]

        ZoneId zone2 = ZoneId.of("America/Los_Angeles");
        ZonedDateTime zDateTime2 = ZonedDateTime.of(2019, 5,10,  //日付
				                                    12,00,00,200, //時刻
				                                    zone2);  //ゾーン
        System.out.println(zDateTime2); // 2019-05-10T12:00:00.000000200-07:00[America/Los_Angeles]

        // 時差
        ZoneOffset offset = ZoneOffset.of("+09:00");
        LocalDateTime lDateTime3 = LocalDateTime.of(2019, 12,20,10,30,45,200);
    	OffsetDateTime oDateTime3 = OffsetDateTime.of(lDateTime3, offset);
    	System.out.println(oDateTime3); // 2019-12-20T10:30:45.000000200+09:00

        /* 夏時間
         * いくつかの国や地域では、夏時間（daylight saving time)を取り入れている。
         * 明るい時間をより有効に活用するために時間を年に2回調整する仕組み。
         *
         * 夏時間を調整する日、時刻、時間は国や地域によって異なるが
         * 米国では3月に1時間進めて、11月に戻す。
         * 例　・2016年度の夏時間開始日時　2016年3月13日(日）2時0分EST(East Standard Time:東部標準時)
         * ・2016年度の夏時間終了日時　2016年11月6日(日) 2時0分EDT(East Daylight Time:東部夏時間)
         *
         * ZonedDateTimeクラスはタイムゾーンを含んだ日付・時刻クラスのため、この夏時間が考慮されている。
         * 今回の場合、zoneDt2で扱っている日時が夏時間の開始時間と重なるため、暗黙で1時間進めている。
         *
         * 夏時間の加算処理
         * ・通常日　　01:30 AM 　→1時間後→　　02:30 AM
         * ・夏時間開始日　　01:30 AM  →1時間後（02:00～02:59のスキップ)→　　03:30 AM
         * ・夏時間終了日　　01:30 AM  →1時間後（01:00～01:59 2回目)→1時間後→　　02:30 AM
         *
         * 夏時間の終了日
         * 夏時間の終了日の「01:30」の1時間後は「01:30」となる。
         * これは夏時間の終了に伴い1時間戻すために「01:00～01:59」を2周するため。
         */

        // 日付間隔
        System.out.println(Period.ofYears(1)); // P1Y
        System.out.println(Period.ofMonths(1)); // P1M
        System.out.println(Period.ofWeeks(1)); // P7D
        System.out.println(Period.ofDays(1)); // P1D

        LocalDate start = LocalDate.of(2016, Month.JANUARY, 1); //2016年1月1日
        LocalDate end = LocalDate.of(2017, Month.MARCH, 5); //2017年3月5日
        // 二つの日付間の年数、月数、および日数で構成されるPeriodを取得
        Period period = Period.between(start, end);
        System.out.println("period    : "+period); // P1Y2M4D
        System.out.println("getYears  : "+period.getYears()); // 1
        System.out.println("getMonths(): "+period.getMonths()); // 2
        System.out.println("getDays()  : "+period.getDays()); // 4

        // 日付間隔の計算
        LocalDate start2 = LocalDate.of(2016, Month.JANUARY, 1);
		LocalDate end2 = LocalDate.of(2016, Month.JANUARY, 15);
		Period period2 = Period.between(start2, end2);
		System.out.println(period2); // P14D
		System.out.println(period2.plusYears(2)); // P2Y14D
		System.out.println(period2.plusMonths(4)); // P4M14D
		System.out.println(period2.plusDays(6)); // P20D
		System.out.println(period2.minusYears(2)); // P-2Y14D
		System.out.println(period2.minusMonths(4)); // P-4M14D
		System.out.println(period2.minusDays(6)); // P8D

        // periodを用いた計算
        // LocalDateクラス、LocalDateTimeクラス、ZonedDateTimeクラスで可能
        LocalDate date2 = LocalDate.of(2016, Month.JANUARY, 10);
        LocalTime time2 = LocalTime.of(7, 30);
        LocalDateTime dateTime2 = LocalDateTime.of(date, time);
        Period period3 = Period.ofMonths(1);
        System.out.println(dateTime2.plus(period3)); // 2016-02-10T07:30

        // 時間間隔
        System.out.println(Duration.ofDays(1)); // PT24H
        System.out.println(Duration.ofHours(1)); // PT1H
        System.out.println(Duration.ofMinutes(1)); // PT1M
        System.out.println(Duration.ofSeconds(1)); // PT1S
        System.out.println(Duration.ofMillis(1)); // PT0.001S
        System.out.println(Duration.ofNanos(1)); // PT0.000000001S

        // durationを用いた計算
        // LocalTimeクラス、LocalDateTimeクラス、ZonedDateTimeクラスで可能
        LocalDate date3 = LocalDate.of(2016, Month.JANUARY, 10);
        LocalTime time3 = LocalTime.of(7, 30);
        LocalDateTime dateTime3 = LocalDateTime.of(date3, time3);
        Duration duration = Duration.ofHours(3);
        System.out.println(dateTime3.plus(duration)); // 2016-01-10T10:30
    }
}