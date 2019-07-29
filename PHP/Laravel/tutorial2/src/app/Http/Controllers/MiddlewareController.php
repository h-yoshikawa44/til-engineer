<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

class MiddlewareController extends Controller
{
    // $requestからミドルウェアで追加された値を使用
    public function index(Request $request)
    {
        return view('middleware.index', ['data' => $request->data]);
    }

    public function index2(Request $request)
    {
        return view('middleware.index2');
    }
}
