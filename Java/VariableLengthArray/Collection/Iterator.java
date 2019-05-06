import java.util.Iterator;
import java.util.TreeSet;

public class Iterator {
    public static void main(String[] args) throws Exception {
        // イテレータ
    	// コレクション内の要素に順番にアクセスする手段

    	TreeSet<String> set = new TreeSet<String>();
    	set.add("C");
    	set.add("B");
    	set.add("A");
    	Iterator<String> iter = set.iterator();

        // hasNext() 次の要素がある場合にtrueを返す
    	while(iter.hasNext()) {
            // next() 次の要素を返す
    		System.out.println(iter.next());
    	}
    	// A
    	// B
    	// C

        // remove() next()の呼び出しごとに1回だけ呼び出すことができ、
    	//          イテレータによって最後に返された要素を削除する
    }
}