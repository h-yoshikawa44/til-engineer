<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

class ValidateController extends Controller
{
    public function index(Request $request)
    {
        return view('validate.index', ['msg' => 'フォーム入力：']);
    }

    public function post(Request $request)
    {
        // バリデーションルール
        $validate_rule = [
            'name' => 'required',
            'mail' => 'email',
            'age' => 'numeric|between:0,150',
        ];
        // バリデーション実行
        // 問題がなければ続きの処理へ
        // 問題があれば例外が発生し、その場でフォームページを表示するレスポンスが生成され返される
        $this->validate($request, $validate_rule);
        return view('validate.index', ['msg' => '正しく入力されました']);
    }
}
