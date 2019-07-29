## トークン認証
Laravelで用意されている認証ドライバにtokenドライバがある。  
これを利用するには、データベースにapi_tokenカラムを作成する必要がある

作成例
- 2014_10_12_000000_create_user_table
  api_tokenカラムを追加
- CreateUserTokensTable（マイグレーション）を作成
- UserSeederを作成（DatabaseSeederに追記）
- App\DataProvider\UserTokenProviderInterfaceを作成
- App\DataProvider\Database\UserToken（UserTokenProviderInterfaceの実装）を作成
- App\Entity\User（Authenticatableの実装）を作成
- App\Auth\UserTokenProvider（UserProviderの実装）を作成
- UserTokenProviderをApp\Providers\AuthServiceProviderに登録
- config/auth.phpにドライバを登録

```php
    'guards' => [
        // 省略

        'api' => [
            'driver' => 'token',
            'provider' => 'user_tokens',
            'hash' => false,
        ],
    ],

    'providers' => [
        // 省略

        'user_token' => [
            'driver' => 'user_tokens'
        ]
    ],
```

利用例
- app\Http\Controllers\UserActionを作成
- routes/api.phpにルート追加

Bearerヘッダを用いたリクエスト例
curl 'http://localhost/api/users' \
 -H 'accept: application/json' \
 -H 'authorization: Bearer BxPGFEK2Rw4503UBueE2G1Z8LRTRmt\TWvOkTbz58JatUqhwqkP0PYB1NEi58E'
