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
        System.out.println(LocalTime.of(12, 0, 0)); //  12:00:00
        System.out.println(LocalDateTime.of(2019, 5, 2, 12, 0, 0)); // // 2019-05-02T12:00:00

        // テキスト文字列で日時指定
        System.out.println(LocalDate.parse("2019-05-02")); // 2019-05-02
        System.out.println(LocalTime.parse("12:00:00")); // 12:00:00
        System.out.println(LocalDateTime.parse("2019-05-02T12:00:00")); // 2019-05-02T12:00:00

        // フォーマット指定
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter fmt1 = DateTimeFormatter.ISO_DATE;
        System.out.println(fmt1.format(dateTime)); // YYYY-MM-dd
        DateTimeFormatter fmt2 = DateTimeFormatter.ISO_TIME;
        System.out.println(fmt2.format(dateTime)); // HH:mm:dd.XXXXX
        DateTimeFormatter fmt3 = DateTimeFormatter.ISO_DATE_TIME;
        System.out.println(fmt3.format(dateTime)); // YYYY-mm-ddTHH:mm:dd.XXXXX

        // 独自フォーマット指定
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
    }