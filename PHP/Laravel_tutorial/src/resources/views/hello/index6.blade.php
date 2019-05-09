<!-- レイアウトの継承 -->
@extends('layouts.helloapp')

<!-- titleというセクションにIndexという値を設定 -->
<!-- 親レイアウトのyieldの個所にはめ込む -->
<!-- 親レイアウトに同名のセクションがある場合は上書きになる -->
@section('title', 'Index')

@section('menubar')
    <!-- 親セクションを上書きするが、親セクションの中身も残したいときはparent -->
    @parent
    インデックスページ
@endsection

@section('content')
    <p>ここが本文のコンテンツです</p>
    <p>必要なだけ記述できます</p>

    @component('components.message')
        <!-- コンポーネントで使用する変数の値を設定 -->
        @slot('msg_title')
        CAUTION!
        @endslot

        @slot('msg_content')
        これはメッセージの表示です
        @endslot
    @endcomponent

    <!-- サブビューの利用 -->
    @include('components.message', ['msg_title' => 'OK',
        'msg_content' => 'サブビューです'])

    <!-- コレクションビュー -->
    @each('components.item', $data, 'item')

    <p>Contoroller value<br>'message' = {{$message}}</p>
    <p>ViewComposer value<br>'view_message' = {{$view_message}}</p>

@endsection

@section('footer')
copyright 2017 tuyano
@endsection