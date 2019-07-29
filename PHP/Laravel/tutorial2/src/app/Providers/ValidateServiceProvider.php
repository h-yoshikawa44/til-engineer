<?php

namespace App\Providers;

use Illuminate\Support\ServiceProvider;
use Illuminate\Validation\Validator;
use App\Http\Validators\HelloValidator;

class ValidateServiceProvider extends ServiceProvider
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
        $validator = $this->app['validator'];
        $validator->resolver(function($translator, $data, $rules, $messages) {
            return new HelloValidator($translator, $data, $rules, $messages);
        });

        // 独自バリデーションルール追加
        // 一つのコントローラーでのみ使うような処理の場合に有効
        // Validator::extend('hello', function($attribute, $value, $parameter, $validator) {
        //     return $value % 2 == 0;
        // });
    }
}
