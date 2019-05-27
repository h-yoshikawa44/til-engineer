<?php

namespace App\Foundation\Socialite;

use Laravel\Socialite\Two\AbstractPrivder;
use Laravel\Socialite\Two\ProviderInterface;
use Laravel\Socialite\Two\User;
use function strval;
use function GuzzleHttp\json_decode;

final class AmazonProvider extends AbstractProvider implements ProviderInterface
{
    protected $scopes = [
        'profile'
    ];

    protected function getAuthUrl($state): string
    {
        // OAuth認証を行うURLを記述
        return $this->buildAuthUrlFromBase('https://www.amazon.com/ap/oa', $state);
    }

    protected function getTokenUrl(): string
    {
        // トークンを取得するURL
        return 'https://api.amazon.com/auth/o2/token';
    }

    protected function getUserByToken($token): array
    {
        // トークンを用いてユーザ情報の取得
        // リクエストパラメータにaccess_tokenを利用する、Authorization Bearerヘッダを利用する、
        // x-amz-access-tokenヘッダを利用するの3種類
        $response = $this->getHttpClient()
            ->get('https://api.amazon.com/user/profile', [
                'header' => [
                    'x-amz-access-token' => $token,
                ]
            ]);
        return json_decode(strval($response->getBody(), true));
    }

    protected function mapUserToObject(array $user): User
    {
        // 問い合わせ結果をLaravel\Socialite\Two\Userインスタンスに渡して返却
        return (new User())->serRaw($user)->map([
            'id' => $user['user_id'],
            'nickname' => $user['name'],
            'name' => $user['name'],
            'email' => $user['email'],
            'avatar' => '',
        ]);
    }

    protected function getTokenFields($code): array
    {
        return parent::getTokenFields($code) + [
            'grant_type' => 'authorization_code'
        ];
    }
}