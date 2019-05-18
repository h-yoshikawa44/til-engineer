@extends('layouts.helloapp')

@section('title', 'ユーザ認証')

@section('menubar')
    @parent
    ユーザ認証ページ
@endsection

@section('content')
<p>{{$message}}</P>
    <table>
    <form action="/paginate/auth" method="post">
        {{csrf_field()}}
        <tr><th>mail: </th><td><input type="text" name="email"></td></tr>
        <tr><th>password: </th><td><input type="password" name="password"></td></tr>
        <tr><th></th><td><input type="submit" value="send"></td></tr>
    </form>
    </table>
@endsection

@section('footer')
copyright 2017 tuyano
@endsection