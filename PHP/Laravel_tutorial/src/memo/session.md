## セッション
それぞれのユーザごとにIDとなる値をクッキーとして保管（セッションID）  
そのIDに関連付けてデータベースに保存する。取り出す際もそのIDをもとに取り出す。  
デフォルトではファイルに保存（storage/framewark/sessions）  
セッションに関する設定はconfig/session.phpにある

値取得
```php
$sesdata = $request->session()->get('msg');
```

値セット
```php
$request->session()->put('msg', $msg);
```

session.phpでdriverをdaabaseに変更することでデータベースに保存するように変更可能  
なお、セッション用のテーブルは特殊なので以下のコマンドでマイグレーションファイルを生成できるようになっている  
` php artisan session:table`