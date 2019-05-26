<?php

use Illuminate\Http\Request;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware('auth:api')->get('/user', function (Request $request) {
    return $request->user();
});

Route::post('/action/favorite', 'FavoriteAction@switchFavorite');

// トークン認証
Route::get('/users', 'UserAction');

// JWT認証
Route::group(['middleware' => 'api'], function ($router) {
    // ログインを行い、アクセストークンを発行する
    Route:post('/users/login', 'User\\LoginAction');
    // アクセストークンを用いて、認証ユーザの情報を取得するルート
    Route::post('/users/', 'User\\RetrieveAction')->middleware('auth:api');
});