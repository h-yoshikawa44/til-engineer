## レイアウト
resources/views/layoutsにレイアウトの土台となるファイルを作成  
そのレイアウトを継承したビューファイルを作成し、@yeildにはめ込む@sectionを書いていく

レイアウト　　　　　　　継承ファイル
(helloapp.blade.php)　(index6.blade.php)
　　　　　　　　　　　　　　@extends('layouts.helloapp')
@yeild('title')　　　←　　@section('title', 'Index')

　　　　　　　　　　　　　　@parent
@section('menubar')　←　　@section('menubar')
