<?php

use App\Http\Middleware\HelloMiddleware;
use App\Http\Middleware\Hello2Middleware;

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::get('/', function () {
    return view('welcome');
});

// {msg}の形式は必須パラメータ、？をつけると任意パラメータ（デフォルト値を設定しておく）
Route::get('helloworld/{msg?}', function ($msg='no message.') {
    $html = <<<EOF
    <html>
        <head>
            <title>Hello World</title>
            <style>
                body { font-size:16pt; color:#999; }
                h1 { font-size:100pt; text-aligh:right; color:#eee;
                margin:-40px 0px -50px 0px; }
            </style>
        </head>
        <body>
           <h1>Hello World</h1>
            <p>{$msg}</p>
            <p>これは、サンプルで作ったページです</p>
        </body>
    </html>
EOF;
    return $html;
});

Route::get('hello/sample/{id?}/{pass?}', 'HelloController@sample');
Route::get('hello/index', 'HelloController@index');
Route::get('hello/other', 'HelloController@other');

// シングルアクションコントローラはアクションは指定しない
Route::get('single', 'SingleController');

Route::get('hello/index2', 'HelloController@index2');
Route::get('hello3/{id?}', 'HelloController@index3');
Route::get('hello4', 'HelloController@index4');
Route::post('hello4', 'HelloController@post');
Route::get('hello5', 'HelloController@index5');
Route::get('hello6', 'HelloController@index6');

// ミドルウェアの利用（メソッドチェーンでミドルウェアを連続して記述も可能）
Route::get('middleware', 'MiddlewareController@index')->middleware(HelloMiddleware::class);
// グループミドルウェアの利用
Route::get('middleware2', 'MiddlewareController@index2')->middleware('hello');

Route::get('validate', 'ValidateController@index');
Route::post('validate', 'ValidateController@post');

Route::get('validate2', 'ValidateController@index2');
Route::post('validate2', 'ValidateController@post2');

Route::get('cookie', 'CookieController@index');
Route::post('cookie', 'CookieController@post');

Route::get('database', 'DatabaseController@index');
Route::get('database/add', 'DatabaseController@add');
Route::post('database/add', 'DatabaseController@create');
Route::get('database/edit', 'DatabaseController@edit');
Route::post('database/edit', 'DatabaseController@update');
Route::get('database/del', 'DatabaseController@del');
Route::post('database/del', 'DatabaseController@remove');
Route::get('database/show', 'DatabaseController@show');