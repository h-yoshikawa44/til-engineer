## フォームリクエスト
パス(namespace)：app\Http\Requests
基底クラス：Illuminate/Foundation/Http/FormRequest(use必要)

コントローラに渡されるリクエスト情報でバリデーションチェックを行うことができる。

### authorize
ファームの利用が許可されているかの定義  
下記は特定パスのみ使用するようにした例
```php
    /**
     * Determine if the user is authorized to make this request.
     *
     * @return bool
     */
    public function authorize()
    {
        if ($this->path() == 'validate')
        {
            return true;
        } else {
            return false;
        }
    }
```

### rules
適用されるバリデーションの検証ルールを設定
バリデーションルールについてはvalidator.mdを参照
```php
    /**
     * Get the validation rules that apply to the request.
     *
     * @return array
     */
    public function rules()
    {
        return [
            'name' => 'required',
            'mail' => 'email',
            'age' => 'numeric|hello'
        ];
    }
```

### messages
フォームリクエストで、バリデーションのエラーメッセージを日本語にしたい場合はmessageメソッドをオーバーライドする  
デフォルトではlang/en/validationの設定値が使用される。
（app.phpの設定を変えて、lang/jp/配下に日本語用の設定を書き、それとはまた別にメッセージを設定したいときにこちらでオーバーライドするとよい）
```php
    public function messages()
    {
        return [
            'name.required' => '名前は必ず入力してください',
            'mail.email' => 'メールアドレスが必要です',
            'age.numeric' => '年齢を整数で記入ください',
            'age.between' => '年齢は0~150の間で入力ください',
        ];
    }
```