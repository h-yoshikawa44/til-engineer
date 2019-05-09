import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Stream {
    public static void main(String[] args) throws Exception {
        // Stream
        // コレクション、配列、I/Oリソースなどのデータ提供元となるデータソースをもとに集計操作を行う
        // パイプライン処理：複数の処理の入出力をつなぐための仕組み
        // 中間操作：ストリーム・パイプラインを形成（パイプラインの途中で行う処理）
        // 終端操作：ストリーム・パイプラインを実行して結果を生成（パイプラインの最後に行う処理）
        // 各中間操作のメソッドは、その中間操作結果をソースとする新しいストリームを返す。
        // 中間操作では「何を行うか」のみをパイプラインでつなげていき、
        // 終端操作のメソッドが実行された際に初めて、すべての処理が行われる。

        List<String> list = Arrays.asList("bb","aa","cc");
        list.stream().sorted().forEach( s -> System.out.print(s + " ")); // aa bb cc

        // default Stream<E> stream()
        // Collectionインタフェースで提供。このコレクションをソースとして使用して、
        // 逐次的なStreamオブジェクトを返す。
        List<String> data1 = Arrays.asList("a","b","c");
        Stream<String> stream1 = data1.stream();

        // static<T>Stream<T>stream(T[]array)
        // Arraysクラスで提供。指定された配列をソースそして使用して、
        // 逐次的なStreamオブジェクトを返す。

        // static<T>Stream<T>of(T t)
        // Streamインタフェースで提供。指定された単一の要素をソースとして使用して、
        // 逐次的なStreamオブジェクトを返す。
        Stream<String> stream3 = Stream.of("abc");

        // static<T>Stream<T>of(T...values)
        // Streamインタフェースで提供。指定された要素をソースとして使用して、
        // 逐次的なStreamオブジェクトを返す
        Stream<Long> stream4 = Stream.of(1L,2L,3L);

        // static intStream stream(int[] array)
        // Arraysクラスで提供。指定されたint型の配列をソースとして使用して、
        // 逐次的なIntStreamオブジェクトを返す
        int[] data2 ={ 1,2,3};
        IntStream stream5 = Arrays.stream(data2);

        // static<T>Stream<T>empty()
        // Streamインタフェースで提供。空のStreamオブジェクトを返す
        Stream<Integer> stream6 = Stream.empty();

        // static<T>Stream<T>generate(Supplier<T>s)
        // Streamインタフェースで提供。指定されたSupplier（ラムダ式）によって生成される要素に対する
        // 順序付けされていないStreamオブジェクトを返す

        // static<T>Stream<T>iterate(T seed,UnaryOperator<T>f)
        // Streamインタフェースで提供。順序付けされた無限順次Streamを返す

        // static DoubleStream of(double...values)
        // DoubleStreamインタフェースで提供。指定された要素をソースとして使用して、
        // 逐次的なStreamオブジェクトを返す
        DoubleStream stream9 = DoubleStream.of(1.0,2.0,3.0);

        // static IntStream range(int startInclusive,int endExclusive)
        // IntStreamインタフェースで提供。startInclusive(含む)からendExclusive(含まない)の範囲の値を含む、
        // 順序付けされた順次IntStreamを返す
        IntStream stream10 = IntStream.range(1,10);

        // static IntStream rangeClosed(int startInclusive,int endInclusive)
        // IntStreamインタフェースで提供。startInclusive(含む)からendInclusive(含む)の範囲の値を含む、
        // 順序付けされた順次IntStreamを返す
        IntStream stream11 = IntStream.rangeClosed(1,10);


        // 終端操作の主なメソッド（メソッド名、説明、リダクション操作のサポートの有無）
        // リダクション操作とは、入力要素をもとに結合操作を繰り返し実行して単一の結果を得る操作。

        // boolean allMatch(Predicate<? super T> predicate)
        // 全ての要素が指定された条件に一致するかどうかを返す。
        // 一致しているか、ストリームが空の場合はtrue、それ以外の場合はfalse。　　無し

        // boolean anyMatch(Predicate<? super T>predicate)
        // いずれかの要素が指定された条件に一致するかどうかを返す。
        // 存在する場合はtrue、そうでない場合か、ストリームが空の場合はfalse　無し

        // boolean noneMatch(Predicate<? super T>predicate)
        // どの要素も指定された条件に一致しないか、ストリームが空の場合はtrue、それ以外の場合はfalseを返す。　無し

        // <R,A>R collect(Collector<? super T,A,R>collector)
        // 要素に対する可変リダクション操作を実行する　　有り

        // <R>R collect(Supplier<R>supplier,BiConsumer<R,? super T>accumulator,BiConsumer<R,R> combiner)
    	// 要素に対する可変リダクション操作を実行する　　有り
        Stream<String> stream16_1 = Stream.of("naoki","akko","ami");
        // toList ストリームをリストに変換
		List<String> result16_1 = stream16_1.collect(Collectors.toList());
		System.out.println(result16_1); // [naoki, akko, ami]

        Stream<String> stream16_2 = Stream.of("naoki","akko","ami");
        // joining ストリームを文字列に変換（指定された文字を区切り文字に使用）
        String result16_2 = stream16_2.collect(Collectors.joining(" | "));
        System.out.println(result16_2); // naoki | akko | ami

		Stream<String> stream16_3 = Stream.of("naoki","akko","ami");
		// summingInt 合計
		Integer result16_3 = stream16_3.collect(Collectors.summingInt(t -> t.length()));
		System.out.println(result16_3); // 12

        Stream<String> stream16_4 = Stream.of("naoki","akko","ami");
        // averagingInt 平均
        Double result16_4 = stream16_4.collect(Collectors.averagingInt(t -> t.length()));
        System.out.println(result16_4); // 4.0

        Stream<String> stream16_5 = Stream.of("naoki","akko","ami");
        // toSet セットに変換
        Set<String> set = stream16_5.collect(Collectors.toSet());
        System.out.println(set); // [naoki, akko, ami]

        Stream<String> stream16_6 = Stream.of("naoki","akko","ami");
        // toMap　Mapはキーと値をセットで保持するため第一引数にキー、第二引数に値を指定
        Map<String,String> map = stream16_6.collect(Collectors.toMap(s -> s,String::toUpperCase));
        System.out.println(map); // {naoki=NAOKI, akko=AKKO, ami=AMI}

        Stream<String> stream16_7 = Stream.of("belle","akko","bob","nao");
        // groupingBy グループ化　各要素の１文字目をsubstring()メソッドで取り出している
        Map<String,List<String>> map16_7 = stream16_7.collect(Collectors.groupingBy(s -> s.substring(0,1)));
        System.out.println(map16_7); // {a=[akko], b=[belle, bob], n=[nao]}

        Stream<Integer> stream16_8 = Stream.of(3,5,7,9);
        // partitioningBy グループ化
        Map<Boolean,List<Integer>> map16_8 = stream16_8.collect(Collectors.partitioningBy(s -> s > 5));
        System.out.println(map16_8); // {false=[3, 5], true=[7, 9]}

        Stream<String> stream16_9 = Stream.of("naoki","akko","ami");
        // mapping 第１引数に要素に対して行いたい処理、第２引数はマップ後に行いたい処理を指定する。
        String result16_9 = stream16_9.collect(Collectors.mapping(s -> s.toUpperCase(), Collectors.joining(":")));
        System.out.println(result16_9); // NAOKI:AKKO:AMI

        // long count()   要素の個数を返す　　有り
        long result17 = Stream.of("a","b","c").count(); //count() 要素の個数を返す
        System.out.println(result17); // 3

        // Optional<T>findAny()
        // いずれかの要素を返す。ストリームが空の場合空のOptionalを返す　無し
        Optional<String> result18 = Stream.of("aa","bbb","c").findAny();
        System.out.println(result18.get()); // aa

        // Optional<T>findFirst()
        // 最初の要素を返す。ストリームが空の場合は空のOptionalを返す　無し
        Optional<String> result19 = Stream.of("aa","bbb","c").findFirst();
        System.out.println(result19.get()); // aa

        // void forEach(Consumer<? super T>action)
        // 各要素に対して指定されたアクションを実行する　無し
        Stream.of("a","b","c").forEach(System.out::print); // abc

        // Optional<T>min(Comparator<? super T>comparator)
	    // 指定されたComparatorに従って最小要素を返す。ストリームが空の場合は空のOptionalを返す　有り

        // Optional<T>max(Comparator<? super T>comparator)
        // 指定されたComparatorに従って最大要素を返す。ストリームが空の場合は空のOptionalを返す　有り
        Optional<String> result22 = Stream.of("aa","bbb","c").max(Comparator.naturalOrder());
        result22.ifPresent(System.out::println); // c

        // T reduce(T identity,BibaryOperator<T>accumulator)
        // 元の値と結合的な累積関数を使ってこのストリームの要素に対してリダクションを実行し、
        // リデュースされた値を返す　有り
        int result = IntStream.of(10,20,30).reduce(0, (a,b) -> a + b);
        System.out.println(result); // 60

        // Object[] toArray() 　　要素を含む配列を返す　　無し
        int[] ary1 = IntStream.range(1,10).toArray();

        // <A>A[]toArray(IntFunction<A[]>generator)
        // 引数に結果となる配列の要素の型を指定し、配列を返す　無し
        String[] ary2 = Stream.of("a2","b").toArray(String[]::new);


        // 中間操作の主なメソッド
        // 取得したストリームに対して何かしらの処理を指定し、新しいストリームを生成する。

        // Stream<T>filter(Predicate<? super T> predicate)
        // 指定された条件に一致するものから構成されるストリームを返す
        Stream<String> stream26 = Stream.of("ami","naoki","akko");
		stream26.filter(s -> s.startsWith("a")).forEach(x -> System.out.print(x + " ")); // ami akko

        // Stream<T> distinct()　　重複を除いた要素から構成されるストリームを返す
		Stream<String> stream27 = Stream.of("ami","naoki","akko","ami");
		stream27.distinct().forEach(x -> System.out.print(x + " ")); // ami naoki akko

        // Stream<T>limit(long maxSize)    maxSize以内の長さに切り詰めた結果から構成されるストリームを返す
        IntStream.iterate(1,n -> n + 1) //初期値に１を指定し、１ずつ加算した要素を無限に用意
        .limit(10) //要素を１０個に制限
        .forEach(x -> System.out.print(x + " ")); // 1 2 3 4 5 6 7 8 9 10

        // Steram<T>skip(lomg n)   先頭からｎ個の要素を破棄した残りの要素で構成されるストリームを返す
        IntStream.rangeClosed(1,10) //1～１０の範囲の要素を生成
        .skip(5) //先頭から５つ破棄し、残りのストリームを返す
        .forEach(x -> System.out.print(x + " ")); // 6 7 8 9 10

        // <R> Stream<R>map(Function<? super T,? extends R> mapper)
        // 指定された関数を適用した結果から構成されるストリームを返す
        Stream.of("naoki","akko","ami").map(s -> s.toUpperCase())
        .forEach(x -> System.out.print(x + " ")); // NAOKI AKKO AMI

        // <R>Stream<R> flatMap(Function< ? super T,? extends Stream< ? extends R>> mapper)
        // 指定された関数を適用した結果から構成される一つのストリームを返す
        List<Integer> data31_1 = Arrays.asList(10);
        List<Integer> data31_2 = Arrays.asList(20,30);
        List<Integer> data31_3 = Arrays.asList(40,50,60);
        List<List<Integer>> dataList = Arrays.asList(data31_1,data31_2,data31_3);
		dataList.stream()
		.flatMap(data -> data.stream())
		// flatMapでは入れ子のストリームを平たん化するので一回のforEachでいい
		// ※mapを使用する場合は入れ子のforEachにする必要がある
		.forEach(x -> System.out.print(x + " ")); // 10 20 30 40 50 60

        // Stream<T>sorted()     自然順序に従ってソートした結果から構成されるストリームを返す
		Stream.of("naoki","akko","ami")
		.sorted()
		.forEach(x -> System.out.print(x + " ")); // akko ami naoki

        // Stream<T> sorted(Comparator<? super T>comparator)
        // 指定されたComparatorに従ってソートした結果から構成されるストリームを返す
		Stream.of("naoki","akko","ami")
		.sorted(Comparator.reverseOrder())
		.forEach(x -> System.out.print(x + " ")); // naoki ami akko

        // Stream<T>peek(Comsumer< ? super T> action)
        // このストリームの要素からなるストリームを返す。要素がパイプラインを通過する際に
        // その内容を確認するようなデバッグとして使用する。
        List<String> list35 =
                Stream.of("one","three","two","three","four")
                .filter(s -> s.length() > 3) //３文字より多い文字数の要素を排除
                .peek(e -> System.out.println("フィルタ後 : "+ e)) //残る要素の先頭を表示
                .distinct() //重複している要素を排除
                .map(String::toUpperCase) //要素を大文字に変換
                .peek(e -> System.out.println("マップ後   : "+e))
                .collect(Collectors.toList());
        // フィルタ後 : three
        // マップ後 ：THREE
        // フィルタ後：three
        // フィルタ後：four
        // マップ後 ：FOUR
    }
}