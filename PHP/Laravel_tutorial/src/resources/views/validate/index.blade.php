@extends('layouts.helloapp')

@section('title', 'Index')

@section('menubar')
    @parent
    インデックスページ
@endsection

@section('content')
    <p>{{$msg}}</p>
    <!-- $errors バリデーションで発生したエラーメッセージを管理するオブジェクト -->
    @if (count($errors) > 0)
    <p>入力に問題があります。再入力してください。
    @endif
    <table>
    <form action="/validate" method="post">
        {{ csrf_field()}}
        <!-- エラーが発生しているかどうか -->
        @if ($errors->has('name'))
        <!-- first 最初のエラーメッセージを取り出す -->
        <!-- 指定した項目のすべてのエラーメッセージを取得の場合はget -->
        <!-- すべてのエラーメッセージを取得の場合はall -->
        <tr><th>ERROR</th><td>{{$errors->first('name')}}</td></tr>
        @endif
        <!-- valueのoldは前回送信した内容を設定するもの -->
        <tr><th>name: </th><td><input type="text" name="name" value="{{old('name')}}"></td></tr>
        @if ($errors->has('mail'))
        <tr><th>ERROR</th><td>{{$errors->first('mail')}}</td></tr>
        @endif
        <tr><th>mail: </th><td><input type="text" name="mail" value="{{old('mail')}}"></td></tr>
        @if ($errors->has('age'))
        <tr><th>ERROR</th><td>{{$errors->first('age')}}</td></tr>
        @endif
        <tr><th>age: </th><td><input type="text" name="age" value="{{old('age')}}"></td></tr>
        <tr><th></th><td><input type="submit" name="send"></td></tr>
    </form>
    </table>
@endsection

@section('footer')
copyright 2017 tuyano
@endsection