## バリデーション
送信されたデータをチェックする  

### エラー時の挙動
#### old
エラーがある場合は、自動的に直前のページにリダイレクトする。
入力されていた値を設定しておきたい場合は、`value="{{old('name')}}"`のようにoldを使ってセットする。編集ページではデフォルト値を設定することで、バリデーションエラーの場合は直前の入力値を、編集ページに来たときは事前の入力値を表示するということができる。（例　`old('status', $card->status)`）

なお、selectボックスのselectedの設定は、三項演算子でかける
（例　`{{ $key == old('status', $card->status) ? 'selected' : '' }}`）

#### エラー内容
エラー内容については、$errorsに格納される（$errorsは常に存在するため、ビューで存在を気にする必要がない）

### チェックの仕方
モデルクラスに用意(バリデーションルールをモデルクラスにstaticで定義しておく)して利用もできるが、フォームリクエストで行うのが望ましい（formrequest.mdを参照）

コントローラで手動で行う例
（※フォームリクエストもしくはバリデータで行うのが推奨）
```php
    public function post(Request $request)
    {
        // バリデーションルール
        $validate_rule = [
            'name' => 'required',
            'mail' => 'email',
            'age' => 'numeric|between:0,150',
        ];
        // バリデーション実行
        // 問題がなければ続きの処理へ
        // 問題があれば例外が発生し、その場でフォームページを表示するレスポンスが生成され返される
        $this->validate($request, $validate_rule);
        return view('validate.index', ['msg' => '正しく入力されました']);
    }
```


#### バリデーションルール
- accept　true、on、yes、1といった値かどうか
- active_url, url　指定されたアドレスが実際に有効であるか
- after:日付　指定した日付より後であるか
- after_or_equal　指定した日かそれより後であるか
- before:日付　指定した日付より前であるか
- before_or_equal　指定した日かそれより前であるか
- alpha　すべてアルファベットであるか
- alpha-dash　アルファベット＋ハイフン＋アンダースコアであるか
- alpha-num　アルファベットと数字であるか
- array　配列であるか
- between:最小値, 最大値　値が指定の範囲内であるか
- boolean　真偽値かどうか
- date　日時の値として扱える値かどうか（strtotimeで変換できるか）
- date_format:フォーマット　値が指定フォーマットの定義に一致するか
- different:フィールド　指定フィールドと違う値かどうか
- same:フィールド　指定フィールドと同じ値かどうか
- digits:桁数　指定された桁数かどうか
- digits_between:最小桁数, 最大桁数　桁数が指定範囲内かどうか
- distinct　配列内に同じ値がないかどうか
- email　メールアドレス形式かどうか
- exists:テーブル, カラム　値が指定データベースの指定カラムにあるかどうか　
- filled　空でないか
- required　必須項目
- in:値1, 値2...　指定した値の中に含まれるかどうか
- not_in:値1, 値2...　指定した値の中に含まれないかどうか
- integer　整数であるか
- numeric　数値であるか
- ip　IPアドレスかどうか
- ipv4　IPv4であるかどうか
- ipv6　IPv6であるかどうか
- json　JSON形式であるかどうか
- min:値　指定値よりも小さいか
- max:値　指定値よりも大きいか
- regix:パターン　正規表現にマッチするかどうか
- size:値　文字列ならば文字数、数値の場合は整数値、配列の場合は要素数が一致するか
- string　文字列かどうか
- unique:テーブル, カラム　指定テーブルの指定カラムに同じ値が存在しないか
- confirmed　対象フィールドの値と「フィールド名_confirmation」の名をもつフィールドの値が同値であるか）

### バリデータ
独自ルールなど独自作成のバリデーションを利用する際に用いる。
$request->query()とすることで、クエリ文字列のチェックをすることも可能。
```php
   public function post(Request $request)
    {
        $rules = [
            'name' => 'required',
            'mail' => 'email',
            'age' => 'numeric|between:0,150'
        ];
        $messages = [
            'name.required' => '名前は必ず入力してください',
            'mail.email' => 'メールアドレスが必要です',
            'age.numeric' => '年齢を整数で記入ください',
            'age.between' => '年齢は0～150の間で入力してください'
        ];
        // バリデータの利用
        $validator = Validator::make($request->all(), $rules, $messages);
        // バリデーションチェックでエラーが出た場合の処理
        if ($validator->fails()) {
            // withErrors エラー情報リダイレクト先まで引き継ぐ
            // withInput　入力された情報リダイレクト先まで引き継ぐ
            return redirect('/validate')
                            ->withErrors($validator)
                            ->withInput();
        }
        return view('validate.index', ['msg' => '正しく入力されました']);
    }
```

独自ルール（条件によるルールの追加）の例
第3引数のクロージャがtrueの場合に第2引数のルールを適応する
```php
 $validator->sometimes('age', 'min:0', function($input) {
            return !is_int($input->age);
        });
```

### 独自バリデータ
パス(namespace)：App\Http\Validators
基底クラス：Illuminate\Validation\Validator(use必要)

Validatorクラスを継承したクラスを作成  
validate〇〇のメソッドで、〇〇の部分がルールの名前になる
```php
// 独自バリデータクラス
class HelloValidator extends Validator
{
    public function validateHello($attribute, $value, $parameters)
    {
        return $value % 2 == 0;
    }
}
```

サービスプロバイダで独自バリデータクラスを設定
（このサービスプロバイダもapp.phpに登録しておく）
```php
    public function boot()
    {
        $validator = $this->app['validator'];
        $validator->resolver(function($translator, $data, $rules, $messages) {
            return new HelloValidator($translator, $data, $rules, $messages);
        });
    }
```

これでこのルールが使用できるようになる

※サービスプロバイダやコントローラにルールを書く方法もある  
こちらは一つのコントローラーでのみ使うルールなどに有効であるが、汎用性はない
```php
    public function boot()
    {
        Validator::extend('hello', function($attribute, $value, $parameter, $validator) {
            return $value % 2 == 0;
        });
    }
```