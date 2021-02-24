<?php

namespace App\Auth;

use App\DataProvider\UserTokenProviderInterface;
use App\Entity\User;
use Illuminate\Contracts\Aut\Authenticatable;
use Illuminate\Contracts\Auth\UserProvider;

final class UserTokenProvider implements UserProvider
{
    private $provider;

    public function __construct(UserTokenProviderInterface $provider)
    {
        $this->provider = $provider;
    }

    public function retrieveById($identifier)
    {
        return null;
    }

    public function retrieveByToken($identifier, $token)
    {
        return null;
    }

    public function updateRememberToken(Authenticatable $user, $token)
    {
        // APIアプリケーションで自動ログイン機能は利用できないため、処理は記述しない
    }

    public function retrieveByCredentials(array $credentials)
    {
        if(!isset($credentials['api_token'])) {
            return null;
        }
        // ユーザ情報を取得
        $result = $this->provider->retrieveUserByToken($credentials['api_token']);
        if (is_null($result)) {
            return null;
        }
        // Authenticatableインタフェースを実装クラスのApp\Entity\Userクラスのインスタンスを返却
        // このインタフェースを実装することえ認証処理後にユーザ情報にアクセス可能になる
        return new User(
            $result->user_id,
            $result->api_token,
            $result->name,
            $result->email
        );
    }

    public function validateCredentials(Authenticatable $user, array $credentials)
    {
        // APIアプリケーションではパスワード認証は利用しないため、
        // 利用された場合にログインできないことを示すためにfalseを記述する
        return false;
    }
}
