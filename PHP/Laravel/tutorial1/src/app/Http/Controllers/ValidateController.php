<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Http\Requests\HelloRequest;
use Validator;

class ValidateController extends Controller
{
    public function index(Request $request)
    {
        // クエリ文字列のバリデーションチェック
        $validator = Validator::make($request->query(), [
            'id' => 'required',
            'pass' => 'required',
        ]);
        if ($validator->fails()) {
            $msg = 'クエリに問題があります';
        } else {
            $msg ='ID/PASSを受け付けました。フォームを入力ください';
        }
        return view('validate.index', ['msg' => $msg]);
    }

    // 引数で使用するフォームリクエストを指定
    // public function post(HelloRequest $request)
    // {
        // コントローラでの手動バリデーション
        // バリデーションルール
        // $validate_rule = [
        //     'name' => 'required',
        //     'mail' => 'email',
        //     'age' => 'numeric|between:0,150',
        // ];
        // バリデーション実行
        // 問題がなければ続きの処理へ
        // 問題があれば例外が発生し、その場でフォームページを表示するレスポンスが生成され返される
        // $this->validate($request, $validate_rule);
    //     return view('validate.index', ['msg' => '正しく入力されました']);
    // }

    public function post(Request $request)
    {
        $rules = [
            'name' => 'required',
            'mail' => 'email',
            'age' => 'numeric|between:0,150'
        ];
        $messages = [
            'name.required' => '名前は必ず入力してください',
            'mail.email' => 'メールアドレスが必要です',
            'age.numeric' => '年齢を整数で記入ください',
            'age.between' => '年齢は0～150の間で入力してください'
        ];
        // バリデータの利用
        $validator = Validator::make($request->all(), $rules, $messages);
        // バリデーションチェックでエラーが出た場合の処理
        if ($validator->fails()) {
            // withErrors エラー情報リダイレクト先まで引き継ぐ
            // withInput　入力された情報リダイレクト先まで引き継ぐ
            return redirect('/validate')
                            ->withErrors($validator)
                            ->withInput();
        }
        return view('validate.index', ['msg' => '正しく入力されました']);
    }

    public function index2(Request $request)
    {
        return view('validate.index2', ['msg' => 'フォームを入力ください']);
    }

    public function post2(HelloRequest $request)
    {
        // $rules = [
        //     'name' => 'required',
        //     'mail' => 'email',
        //     'age' => 'numeric',
        // ];
        // $messages = [
        //     'name.required' => '名前は必ず入力してください',
        //     'mail.email' => 'メールアドレスが必要です',
        //     'age.numeric' => '年齢は整数で記入ください',
        //     'age.min' => '年齢はゼロ歳以上で記入ください',
        //     'age.max' => '年齢は200歳以下で記入ください'
        // ];
        // $validator = Validator::make($request->all(), $rules, $messages);

        // $validator->sometimes('age', 'min:0', function($input) {
        //     // 整数の場合はfalseが返され、ルールが追加される
        //     return !is_int($input->age);
        // });
        // $validator->sometimes('age', 'max:200', function($input) {
        //     return !is_int($input->age);
        // });

        // if ($validator->fails()){
        //     return redirect('/validate2')
        //             ->withErrors($validator)
        //             ->withInput();
        // }
        return view('validate.index2', ['msg' => '正しく入力されました']);
    }
}
