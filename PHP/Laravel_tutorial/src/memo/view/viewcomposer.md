## ビューコンポーザ
このビューでは常にこの処理をしてこの結果をビューに渡すといった際に使用する。  
サービスプロバイダに呼び出し処理を書いておく（config/app.phpにプロバイダを登録）ことで、指定したビューが呼び出されたときに割り込んで処理される

HelloServiceProvider.php
bootメソッド
```php
    public function boot()
    {
        // ビューコンポーザ
        View::composer(
            'hello.index6', 'App\Http\Composers\HelloComposer'
        );
    }
```
↓
HelloComposer.php
```php
    public function compose(View $view)
    {
        // ビューに値をセット
        $view->with('view_message',
                    'this view is "' . $view->getName() . '"!!');
    }
```