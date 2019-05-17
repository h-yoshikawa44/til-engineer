<?php

namespace App\Http\Controllers;

use App\Person;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;

class PaginateController extends Controller
{
    public function index(Request $request)
    {
        $sort = $request->sort;
        // DBクラス利用の場合（1ページに表示するデータ数を指定）
        //$items = DB::table('people')->simplePaginate(5);

        // モデル利用の場合（1ページに表示するデータ数を指定）
        //$items = Person::orderBy($sort, 'asc')->simplepaginate(5);
        $items = Person::orderBy($sort, 'asc')->paginate(2);
        $param = ['items' => $items, 'sort' => $sort];
        return view('paginate.index', $param);
    }
}
