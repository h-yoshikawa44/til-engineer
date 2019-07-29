<?php

namespace App\Http\Middleware;

use Closure;

class HelloMiddleware
{
    /**
     * Handle an incoming request.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \Closure  $next
     * @return mixed
     */
    public function handle($request, Closure $next)
    {
        $data = [
            ['name' => 'taro', 'mail' => 'tato@yamada'],
            ['name' => 'hanako', 'mail' => 'hanako@flower'],
            ['name' => 'sachiko', 'mail' => 'sachico@happy'],
        ];
        // フォーム送信などで送られる値に新たな値を追加するもの
        $request->merge(['data' => $data]);
        // コントローラーの前処理
        return $next($request);

        // 以下のようにするとコントローラーの後処理が書ける
        // $response = $next($request)
        // 処理
        // return $response
    }
}
