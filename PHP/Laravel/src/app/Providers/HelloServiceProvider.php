<?php

namespace App\Providers;

use Illuminate\Support\Facades\View;
use Illuminate\Support\ServiceProvider;

class HelloServiceProvider extends ServiceProvider
{
    /**
     * Register services.
     *
     * @return void
     */
    public function register()
    {
        //
    }

    /**
     * Bootstrap services.
     * アプリケーションが起動する際に割り込んで実行される処理
     *
     * @return void
     */
    public function boot()
    {
        // ビューコンポーザ
        View::composer(
            'hello.index6', 'App\Http\Composers\HelloComposer'
        );
    }
}
