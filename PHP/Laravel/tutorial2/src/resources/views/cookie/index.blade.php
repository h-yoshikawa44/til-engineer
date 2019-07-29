@extends('layouts.helloapp')

@section('title', 'Index')

@section('menubar')
    @parent
    インデックスページ
@endsection

@section('content')
    <p>{{$msg}}</p>
    @if (count($errors) > 0)
    <p>入力に問題があります。再入力してください。
    @foreach ($errors->all() as $error)
    {{$error}}
    @endforeach
    @endif
    <table>
    <form action="/cookie" method="post">
        {{ csrf_field()}}
        @if ($errors->has('msg'))
        <tr><th>ERROR</th><td>{{$errors->first('msg')}}</td></tr>
        @endif
       <tr><th>Message: </th><td><input type="text" name="msg" value="{{old('msg')}}"></td></tr>
       <tr><th></th><td><input type="submit" value="send"></td></tr>
    </form>
    </table>
@endsection

@section('footer')
copyright 2017 tuyano
@endsection