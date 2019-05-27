## JWT認証
JWT(JSON Web Token)とは、JSONに電子署名を用いて必要に応じてJSONを検証して認証可否を決定する仕様  
IoT等にも利用されている認証方式でトークン認証よりもセキュア

JWT認証の利用はユーザ情報を特定するため、Illuminate\Contracts\Auth\Authenticatableインタフェースと。Tymon\JWTAuth\COntracts\JWTSubjectインタフェースを実装しておく必要がある

- tymon/jwt-authのインストール
  ※https://packagist.org/packages/tymon/jwt-authでバージョン確認
  `composer require tymon/jwt-auth 1.0.0-rc4.1`

- パッケージの設定ファイルをconfig配下に追加
  `php artisan vendor:publish --provider="Tymon\JWTAuth\Providers\LaravelServiceProvider"`

- JWT認証で利用する秘密鍵生成コマンド
  `php artisan jwt:secret`

実装例
- App\UserでTymon\JWTAuth\COntracts\JWTSubjectインタフェースを実装
- Config.auth.phpに認証ドライバを追加
```php
   'defaults' => [
        'guard' => 'api',
        'passwords' => 'users',
    ],

    // JWT認証
    'api' => [
        'driver' => 'jwt',
        'provider' => 'users',
        'hash' => false,
    ],
```
- routes/api.phpにルート追加
- App\Http\Responder\TokenResponderにトークン発行の実装
- APP\Http\Controllers\User\LoginActionにログイン処理の実装

トークン情報の取得
curl -X POST 'http://localhost/api/users/login' \
  -H 'accept: application/json' \
  -H 'content-type: application/json' \
  -d '{
      "email": "mail@example.com,
      "password": "password"
  }'

このリクエストで取得したaccess_tokenを利用することで、認証ユーザの情報にアクセス可能になる
access_tokenの送信の仕方はAuthorization: Bearerヘッダで送信

- App\Http\Controllers\user\RetrieveActionでjwtドライバを介したユーザ情報アクセス

上記にアクセスするとユーザ情報返却例
```json
{
    "id": 1,
    "name": "laravel user",
    "email": "mail@example.com",
    "created_at": "2018-08-05 20:44:05",
    "updated_at": null
}
```