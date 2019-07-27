<?php

namespace App\Http\Controllers;

use App\Person;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Auth;

class PaginateController extends Controller
{
    public function index(Request $request)
    {
        $sort = $request->sort;
        // DBクラス利用の場合（1ページに表示するデータ数を指定）
        //$items = DB::table('people')->simplePaginate(5);

        // モデル利用の場合（1ページに表示するデータ数を指定）
        //$items = Person::orderBy($sort, 'asc')->simplepaginate(5);
        $user = Auth::user();
        $items = Person::orderBy($sort, 'asc')->paginate(2);
        $param = ['items' => $items, 'sort' => $sort, 'user' => $user];
        return view('paginate.index', $param);
    }

    public function getAuth(Request $request)
    {
        $param = ['message' => 'ログインして下さい'];
        return view('paginate.auth', $param);
    }

    public function postAuth(Request $request)
    {
        $email = $request->email;
        $password = $request->password;
        if(Auth::attempt(['email' => $email, 'password' => $password])){
            $msg = 'ログインしました(' . Auth::user()->name . ')';
        } else {
            $msg = 'ログインに失敗しました';
        }
        return view('paginate.auth', ['message' => $msg]);
    }
}
