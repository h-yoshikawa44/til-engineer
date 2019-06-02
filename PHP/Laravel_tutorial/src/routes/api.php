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
    Route::post('/users/login', 'User\\LoginAction');
    // アクセストークンを用いて、認証ユーザの情報を取得するルート
    Route::post('/users/', 'User\\RetrieveAction')->middleware('auth:api');
});

Route::post('/import-orders', function(Request $request) {
    $json = $request->getContent();
    file_put_contents('/tmp/orders', $json);

    return response('ok');
});

Route::get('/ping', function() {
    return response()->json(['message' => 'pong']);
});

Route::put('/customers/add_point', AddPointAction::class);

Route::get('customers', 'ApiController@getCustomers');
Route::post('customers', 'ApiController@postCustomers');
Route::get('customers/{customer_id}', 'ApiController@getCustomer');
Route::put('customers/{customer_id}', 'ApiController@putCustomer');
Route::delete('customers/{customer_id}', 'ApiController@deleteCustomer');
Route::get('reports', 'ApiController@getReports');
Route::post('reports', 'ApiController@postReport');
Route::get('reports/{report_id}', 'ApiController@getReport');
Route::put('reports/{report_id}', 'ApiController@putReport');
Route::delete('reports/{report_id}', 'ApiController@deleteReport');