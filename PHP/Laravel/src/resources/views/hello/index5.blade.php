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
    <h1>Blade/Index5</h1>
    <p>&#064;forディレクティブの例</p>
    <ol>
    @for ($i = 1;$i < 100; $i++)
    @if ($i % 2 == 1)
        @continue
    @elseif ($i <= 10)
    <li>No, {{$i}}
    @else
        @break
    @endif
    @endfor
    </ol>

    <p>&#064;forディレクティブの例</p>
    @foreach ($data as $item)
    @if ($loop->first)
    <p>※データ一覧</p>
    <!--
    $loop->index　現在のインデックス（ゼロから開始）
    $loop->iteration　現在の繰り返し数（１から開始）
    $loop->rermaining　あと何回繰り返すか（残り回数）
    $loop->count　繰り返しで使っている配列の要素数
    $loop->first　最初の繰り返しかどうか（最初ならtrue）
    $loop->last　最後の繰り返しかどうか（最後ならtrue）
    $loop->depth　繰り返しのネスト数
    $loop->parent　ネストしている場合、親の繰り返しのループ変数を示す
    -->
    <ul>
    @endif
        <li>No,{{$loop->iteration}}. {{$item}}</li>
        @if ($loop->last)
        </ul><p>--ここまで</p>
        @endif
    @endforeach

    <p>&#064;foreachディレクティブの例</p>
    <ol>
    @foreach($data as $item)
    <li>{{$item}}</li>
    @endforeach
    </ol>

    <p>&#064;foreach-elseディレクティブの例</p>
    <ol>
    @forelse($data as $item)
    <li>{{$item}}</li>
    @empty
    <li>Last</li>
    @endforelse
    </ol>

    <p>&#064;whileディレクティブの例</p>
    <ol>
    <!-- @phpの利用は最小限にとどめる -->
    @php
    $num = 0;
    @endphp
    @while($num < 5)
    <li>{{$data[$num]}}</li>
    @php
    $num++;
    @endphp
    @endwhile
    </ol>
</body>