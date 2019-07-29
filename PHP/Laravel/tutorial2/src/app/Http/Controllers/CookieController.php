<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Http\Response;

class CookieController extends Controller
{
    public function index(Request $request)
    {
        if ($request->hasCookie('msg'))
        {
            // クッキーの読み出し
            $msg = 'Cookie: ' . $request->cookie('msg');
        } else {
            $msg = '※クッキーはありません';
        }
        return view('cookie.index', ['msg' => $msg]);
    }

    public function post(Request $request)
    {
        $validate_rule = [
            'msg' => 'required'
        ];
        $this->validate($request, $validate_rule);
        $msg = $request->msg;
        $response = new Response(view('cookie.index', ['msg' => '「' . $msg . '」をクッキーに保存しました']));
        // クッキーのセット
        // このセットしたレスポンスを返送しないとクッキーは保存されない
        $response->cookie('msg', $msg, 100);
        return $response;
    }
}
