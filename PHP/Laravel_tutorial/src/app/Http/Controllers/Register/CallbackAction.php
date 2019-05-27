<?php

namespace App\Http\Controllers\Register;

use App\Http\Controllers\Controller;
use App\User;
// use GuzzleHttp\Client;
// use GuzzleHttp\HandlerStack;
// use GuzzleHttp\MessageFormater;
// use GuzzleHttp\Middleware;
use Illuminate\Auth\AuthManager;
use Laravel\Socialite\Contracts\Factory;
// use Laravel\Socialite\Two\GitHubProvider;
// use Psr\Log\LoggetInterface;

final class CallbackAction extends Controller
{
    public function __invoke(
        Factory $factory,
        AuthManager $authManager,
        // LoggerInterface $log
    ) {
        $user = $factory->driver('github')->user();
        $authManager->guard()->login(
            User::firstOrCreate([
                'name' => $user->getName(),
                'email' => $user->getEmail(),
                'password' => '',
            ]),
            true
        );

        // Facadeを使用する場合
        // $user = \Socialite::driver('github')->user();
        // \Auth::login(
        //     User::firstOrCreate([
        //         'name' => $user->getName(),
        //         'email' => $user->getEmail()
        //     ]),
        //     true
        // );

        // ログ出力をする場合
        // $driver = $factory->driver('github');
        // $user = $driver->setHttpClient(
        //     new Client([
        //         'handler' => tap(
        //             HandlerStack::create(),
        //             function(HandlerStack $stack) use ($log) {
        //                 $stack->push(Middleware::log($log, new MessageFormatter()));
        //             }
        //         )
        //     ])
        // )->user();

        return redirect('/home');

    }
}