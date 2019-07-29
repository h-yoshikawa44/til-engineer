<html>
<head>
    <title>Hello/Index</title>
    <style>
    body { font-size:16pt; color:#999;}
    h1 { font-size:50pt; text-align:right; color:#f6f6f6;
         margin:-20px 0px -30px 0px; letter-spacing:-4pt; }
    </style>
</head>
<body>
    <h1>Blade/Index</h1>
    <!-- 条件がfalseの時に表示 -->
    @unless ($msg != '')
    <p>ようこそ</p>
    @endunless
    <!-- 条件がtrueの時に表示 -->
    @if ($msg != '')
    <!-- Bladeでの埋め込みは全て自動でエスケープされる -->
    <!-- エスケープしたくないときは括弧のなかを!! !!で囲う -->
    <p>こんにちは、{{$msg}}さん</p>
    @else
    <p>何か書いてください</p>
    @endif
    <!-- 変数が空の場合に表示 -->
    @empty ($cond)
    <p>体調はいかがですか？</p>
    @endempty
    <!-- 変数が定義済みの場合に表示 -->
    @isset ($age)
    <p>{{$age}}歳なんですね</p>
    @else
    <p>年齢不詳</p>
    @endisset
    <form method="POST" action="/hello4">
        <!-- CSRF対策のためのヘルパ関数 -->
        <!-- Laravelのフォームでは必ず付け足す必要がある -->
        {{ csrf_field() }}
        <input type="text" name="msg">
        <input type="submit">
    </form>
</body>
</html>