<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;

class DatabaseController extends Controller
{
    public function index(Request $request)
    {
        // DBクラスでのCRUD
        // if(isset($request->id))
        // {
        //     $param = ['id' => $request->id];
        //     $items = DB::select('select * from people where id = :id', $param);
        // } else {
        //     $items = DB::select('select * from people');
        // }

        // クエリビルダ
        // $items = DB::table('people')->get();

        // orderByを使用した取得
        $items = DB::table('people')->orderBy('age', 'asc')->get();
        return view('database.index', ['items' => $items]);
    }

    public function post(Request $request)
    {
        $items = DB::select('select * from people');
        return view('database.index', ['items' => $item]);
    }

    public function add(Request $request)
    {
        return view('database.add');
    }

    public function create(Request $request)
    {
        $param = [
            'name' => $request->name,
            'mail' => $request->mail,
            'age' => $request->age,
        ];
        // DBクラスのINSERTの例
        // DB::insert('insert into people (name, mail, age) values (:name, :mail, :age)', $param);

        // クエリビルダ
        DB::table('people')->insert($param);
        return redirect('/database');
    }

    public function edit(Request $request)
    {
        // DBクラスのSELECTの例
        // $param = ['id' => $request->id];
        // $item = DB::select('select * from people where id = :id', $param);
        // return view('database.edit', ['form' => $item[0]]);

        // クエリビルダ
        $item = DB::table('people')
            ->where('id', $request->id)->first();
        return view('database.edit', ['form' => $item]);
    }

    public function update(Request $request)
    {
        $param = [
            'id' => $request->id,
            'name' => $request->name,
            'mail' => $request->mail,
            'age' => $request->age,
        ];

        // DBクラスのUPDATEの例
        // DB::update('update people set name = :name, mail = :mail, age = :age where id = :id', $param);

        // クエリビルダ
        DB::table('people')
            ->where('id', $request->id)
            ->update($param);
        return redirect('/database');
    }

    public function del(Request $request)
    {
        // DBクラスのSELECTの例
        // $param = ['id' => $request->id];
        // $item = DB::select('select * from people where id = :id', $param);
        // return view('database.del', ['form' => $item[0]]);

        // クエリビルダ
        $item = DB::table('people')
            ->where('id', $request->id)->first();
        return view('database.del', ['form' => $item]);
    }

    public function remove(Request $request)
    {
        // DBクラスのDELETEの例
        // $param = ['id' => $request->id];
        // DB::delete('delete from people where id = :id', $param);

        // クエリビルダ
        DB::table('people')
            ->where('id', $request->id)->delete();
        return redirect('/database');
    }

    public function show(Request $request)
    {
        // where->firstを使った取得
        // $id = $request->id;
        // $item = DB::table('people')->where('id', $id)->first();
        // return view('database.show', ['item' => $item]);

        // whereに演算記号を使用した取得
        // $id = $request->id
        // $items = DB::table('people')->where('id', '<=', $id)->get();

        // orwhereを使用した取得
        // $name = $request->name;
        // $items = DB::table('people')
        //     ->where('name', 'like', '%' . $name . '%')
        //     ->orwhere('mail', 'like', '%' . $name . '%')
        //     ->get();

        // whereRawを使用した取得
        // $min = $request->min;
        // $max = $request->max;
        // $items = DB::table('people')
        //     ->whereRaw('age >= ? and age <= ?', [$min, $max])->get();

        // offset、limitを使用した例
        $page = $request->page;
        $items = DB::table('people')
            ->offset($page * 3)
            ->limit(3)
            ->get();

        return view('database.show', ['items' => $items]);
    }
}
