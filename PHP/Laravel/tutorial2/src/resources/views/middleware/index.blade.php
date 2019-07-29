@extends('layouts.helloapp')

@section('title', 'Index')

@section('menubar')
    <!-- 親セクションを上書きするが、親セクションの中身も残したいときはparent -->
    @parent
    インデックスページ
@endsection

@section('content')
    <p>ここが本文のコンテンツです</p>
    <table>
    @foreach($data as $item)
    <tr><th>{{$item['name']}}</th><td>{{$item['mail']}}</td></tr>
    @endforeach
    </table>
@endsection

@section('footer')
copyright 2017 tuyano
@endsection