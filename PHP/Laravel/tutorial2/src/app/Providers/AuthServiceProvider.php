<?php

namespace App\Providers;

use App\User;
use Illuminate\Contracts\Auth\Access\Gate as GateContent;
use Illuminate\Support\Facades\Gate;
use Illuminate\Foundation\Support\Providers\AuthServiceProvider as ServiceProvider;

use function intval;
use Psr\Log\LoggerInterface;

class AuthServiceProvider extends ServiceProvider
{
    /**
     * The policy mappings for the application.
     *
     * @var array
     */
    protected $policies = [
        // 'App\Model' => 'App\Policies\ModelPolicy',
        \App\Content::class => \App\Policies\ContentPolicy::class
    ];

    /**
     * Register any authentication / authorization services.
     *
     * @return void
     */
    public function boot(GateContent $gate, LoggerInterface $logger)
    {
        $this->registerPolicies();

        // UserTokenProviderの登録
        $this->app['auth']->provider(
            'user_token',
            function(Application $app) {
                return new UserTokenProvider(new UserToken($app['db']));
            }
        );

        // ログインしているユーザのみアクセスを許可する認可処理
        // 認可処理に名前を付けて、紐づく処理をクロージャで記述
        $gate->define('user-access', function (User $user, $id) {
            return intval($user->getAUthIdentifier()) === intval($id);
        });
        // または
        // \Gate::define('user-access', ...)

        // 認可処理のクラスを使用する例
        // $gate->define('user-access', new UserAccess());

        // 認可処理の前に実行したい処理
        $gate->before(function ($user, $ability) use ($logger) {
            $logger->info($ability, [
                'user_id' => $user->getAuthIdentifier()
            ]);
        });

    }
}
