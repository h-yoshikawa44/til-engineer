<?php

namespace App\Providers;

use Fluent\Logger\FluentLogger;
use Illuminate\Support\ServiceProvider;

class AppServiceProvider extends ServiceProvider
{
    /**
     * Register any application services.
     *
     * @return void
     */
    public function register()
    {
        $this->app->bind(
            \App\DataProvider\FavoriteRepositoryInterface::class,
            \App\DataProvider\FavoriteRepository::class
        );

        $this->app->singleton(FluentLogger::class, function() {
            // 実際に利用する場合は、.envファイルなどで管理する
            return new FluentLogger('192.168.99.100', 24224);
        });
    }

    /**
     * Bootstrap any application services.
     *
     * @return void
     */
    public function boot()
    {
        //
    }
}
