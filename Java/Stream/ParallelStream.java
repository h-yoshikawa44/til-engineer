import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ParallelStream {

    public static void main(String[] args) throws Exception {
        // パラレルストリーム

        // パラレルストリームの生成
        // - default Stream<E> parallelStream()
        //   Collectionインタフェースで提供。リストなどのコレクションをソースとして、パラレルストリームを返す。
        // - S parallel()
        //   BaseStreamインタフェースで提供。ストリームをソースとしてパラレルストリームを返す
        // - boolean isParallel()
        //   BaseStreamインタフェースで提供。このストリームがパラレルストリームであればtrueを返す
        // - S sequential()
        //   BaseStreamインタフェースで提供。ストリームをソースとしてシーケンシャルストリームを返す

        List<String> data = Arrays.asList("aaa","bb","c");
        Stream<String> pStream1 = data.parallelStream();
        Stream<String> sStream2 = data.stream();
        System.out.println("sStream2 : "+sStream2.isParallel()); //パラレルストリームでないのでfalse
        Stream<String> pStream2 = sStream2.parallel(); //ストリームをソースにパラレルストリームを返す
        System.out.println("pStream2 : "+pStream2.isParallel()); //パラレルストリームなのでtrue
        IntStream pStream3 = IntStream.range(0,10).parallel();
        //IntStreamのrange()メソッドで０～９（終値１０は含まない）を扱うIntStreamを取得後、
        //parallel()メソッドによりパラレルストリームを取得


        // 要素を並列に処理を行うため、どの要素から処理されるかは実行時によって異なる
        Arrays.asList("a","b","c","d","e")
            .stream()
            .forEach(s -> System.out.print(s+" ")); // シーケンシャルなストリームによる処理であるため実行ごとに同じ結果
        System.out.println();
        Arrays.asList("a","b","c","d","e")
            .parallelStream()
            .forEach(s -> System.out.print(s+" ")); // パラレルストリームによる処理であるため、実行ごとに異なる結果


        // ただ、多くのリソースに対して、パラレル処理を行うことによって実行時間が短縮される傾向がある

        // シーケンシャルストリームの場合、処理時間は約16秒
        // パラレルストリームの場合、処理時間は約4秒

        // パラレル処理はPCのプロセッサ数に比例するため、
        // CPU等、より多くのリソースを追加することで実行速度が改善される傾向にある。
        // しかし、すべてのストリーム処理をパラレルで行うことはベストではなく、
        // ストリーム内の要素の数が極端に多い場合、改善される傾向にあるが、
        // 要素の数が少ない場合、ほとんど改善は見込めない。
        // （パラレル処理はシーケンシャル処理より分散に伴うオーバーヘッドが発生するため）


        // forEachOrdered()メソッドを使用することで、各要素の検出順に処理させることが可能。
        // パラレル処理でも処理の順序を保証するが、
        // パフォーマンスが低下する可能性があるため、状況に応じて使用する。
        Arrays.asList("a","b","c","d","e")
            .parallelStream()
            .forEachOrdered(s ->System.out.print(s+" "));
            //forEachOrdered() 各要素が検出順に処理されることを保証するメソッド
        System.out.println();
        List<String> list = new CopyOnWriteArrayList<String>();
        Arrays.asList("a","b","c","d","e")
            .parallelStream()
            .map(s -> { list.add(s); return s.toUpperCase();}) //中間操作
            .forEachOrdered(s -> System.out.print(s+" "));
        System.out.println();
        for(String s : list){ System.out.print(s+" ");}
        //forEachOrdered()を使用していないため表示順序は順不同
        //(map,forEachOrderedはそれぞれ独立した処理である）


        // IntStreamインタフェースのメソッド
        // findFirst()  最初の要素を返す
        // findAny()    ストリームが保持する要素のうち、いずれかの要素を返す

        // パラレルストリームでも基本的に同じであるが、
        // 最初に処理される要素は不定のため、findFirst()メソッドが呼ばれても
        // 最初の要素が処理されるまで時間を要する。（結果自体は同じになる）

        // 同じように順序に依存する中間操作であるlimit()やskip()でも同様に時間を要する。

		List<String> data2 = Arrays.asList("c","a","d","b");
		Optional<String> rerult1 = data2.parallelStream().findFirst();
		Optional<String> rerult2 = data2.parallelStream().findAny();
		System.out.println(rerult1.get() + " "+rerult2.get());

		// reduce()の利用
		// オーバーロードされており、集約処理を行うメソッド

		// 構文３　<U> U reduce(U identity,BiFunction<U,? super T,U> accumulator.BinaryOperator<U> combiner)
        // 第1引数は初期値、第2引数は集約処理を指定する。第3引数はパラレルストリームの場合のみ適用される。
		// パラレル処理で集約処理を行っている場合、複数の場所で部分的な途中の集約処理が必要になる。
		// この途中の集約結果のマージ処理に使用するのは第3引数。

        /* reduce()のイメージ
         *　　10,20,30,40,50
         *			↓要素を分割
         * 10		20		30		40		50
         *			↓要素を分解
         * 0+10 	0+20	  0+30	   0+40		0+50
         * ↓(10) ↓(20)　　↓(30)　 ↓(40)　　↓(50)
         * →30←←	　↓		→→90←←←
         * ↓　　　　　↓　　　　　　↓
         * ↓　　　　　→→→120←←←←
         * ↓　　　　　　　　↓
         * →→→→↓←←←←←
         * ↓
         * total 150
         */

	    // reduce()では部分ごとに集約処理を行いながら、全体の計算を行う。
	    // それぞれの要素は、初期値を用いて指定された処理が行われ、その部分的な途中の集約結果を用いて
	    // 第3引数で指定された処理を行う。

		Integer total = Arrays.asList(10,20,30,40,50)
				.parallelStream()
				.reduce(0,
						(sum,a) -> {
							System.out.println("sum:"+sum+" a:"+a);
							return sum += a;
						},
						(b,c) -> {
							System.out.println("b:"+b+" c:"+c);
							return b + c;
						});
		System.out.println("total : "+total);


		// collect()メソッド
		// オーバーロードされており、ストリームから要素をまとめて一つのオブジェクトを取得するメソッド
		// 構文2　<R> collect(Supplier<R> supplier,BiConsumer<R,? super T> accumulator,BiConsumer<R,R> combiner)

		// 第1引数は、結果を格納するオブジェクトの生成を指定
		// 第2引数は、要素ごとに行う処理を指定
	    // 第3引数は、パラレルストリームの場合のみ適用され、reduce()と同様に集約結果のマージに使用する処理を指定。

		List<String> data3 = Arrays.asList("orange","banana","lemon");
		List<String> list3 =
				data3.parallelStream()
					.collect(() -> new CopyOnWriteArrayList<>(), //第1引数　結果を格納するオブジェクト
							(plist,s) -> plist.add(s.toUpperCase()), //第２引数　要素を大文字に変換
							(alist,blist) -> alist.addAll(blist)); //第3引数　複数の要素をまとめて格納
		for(String s : list3){ System.out.print(s + " "); } // ORANGE BANANA LEMON

		System.out.println();
		Set<String> set3 =
				data3.parallelStream() //メソッド参照を使用した例
					.collect(CopyOnWriteArraySet::new,
							Set::add,
							Set::addAll);
		for(String e : set3){ System.out.print(e + " ");} // orange banana lemon

        // groupingByConsurrent()メソッド
        // groupingBy()メソッドと同等の処理（要素のグループ化）を行うパラレルストリームでのメソッド
        // - static<T,K> Collector<T,?,Concurrent<ap<K,List<T>>>groupingByConcurrent
        // (Function<? super T,? extends K> classifier)
        // 指定した関数に従ってグループ化し、結果をConcurrentMapに格納して返す並行Collectorを返す

        Stream<String> stream1 =
                Stream.of("belle","akko","ami","bob","nao").parallel(); //パラレルストリームを取得
        Map<String,List<String>> map1=
                stream1.collect(Collectors.groupingByConcurrent(
                    s -> s.substring(0,1))); //頭文字でグルーピングするように指定
                    //グループ化したマップに対して行いたい処理があれば第2引数以降で指定
        System.out.println(map1); // {a=[ami, akko], b=[belle, bob], n=[nao]}
        System.out.println("map1のクラス名 :"+map1.getClass()); // map1のクラス名 :class java.util.concurrent.ConcurrentHashMap


        // toConcurrentMap()メソッド
        // toMap()メソッドと同等の処理（要素をもとにマップに変換）を行うパラレルストリームでのメソッド
        // static<T,K,U> Collector<T,?,ConcurrentMap<K,U>>toConcurrentMap<K,U>>toConcurrentMap
    	// (Function<? super T,? extends K> keyMapper,
        //      Function<? super T,? extends U>valueMapper,BinaryOperator<U>mergeFunction)
        // Mapに蓄積する並行Collectorを返す

        Stream<String> stream2=
            Stream.of("nao","akko","ami").parallel();
        Map<Integer,String> map2=
            stream2.collect(Collectors.toConcurrentMap(
                    String::length, //第1引数にキー
                    s -> s, //第2引数に値
                    (s1,s2) -> s1+" : "+s2)); //第3引数にマージ処理を指定
                    //値をコロンで区切りながら結合
        System.out.println(map2); // {3=nao : ami, 4=akko}
        System.out.println("map2のクラス名 :"+map2.getClass()); // map2のクラス名 :class java.util.concurrent.ConcurrentHashMap
    }
}
