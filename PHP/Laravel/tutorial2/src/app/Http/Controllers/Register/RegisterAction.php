<?php

namespace App\Http\COntrollers\Register;

use App\Http\Controllers\Controller;
use Laravel\Socialite\COntracts\Factory;
use Symfony\COmponent\HttpFoundation\RedirectResponse;

final class RegisterAction extends Controller
{
    public function __invoke(Factory $factory): RedirectResponse
    {
        // driverメソッドで外部サービスを指定
        // redirectメソッドで指定した外部サービスの認証画面へ遷移する
        return $factory->driver('github')->redirect();

        // Amazonドライバの例
        // return $factory->driver('amazon')->redirect();
        // もしくは
        // \Socialite::driver('amazon')->redirect();
    }
}