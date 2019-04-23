import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        // 標準入力処理

    	Scanner scanner = new Scanner(System.in);
    	System.out.println("名前を入力してください");
    	String name = scanner.next();
    	System.out.println("名前：" + name); // 名前：（入力した名前）

    	System.out.println("年齢を入力してください");
    	int age = scanner.nextInt();
    	System.out.println("年齢：" + age); // 年齢：（入力した年齢）

    }