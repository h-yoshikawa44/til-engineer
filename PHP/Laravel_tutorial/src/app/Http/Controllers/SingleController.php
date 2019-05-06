<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

// シングルアクションコントローラ
class SingleController extends Controller
{
    // __invokeアクションのみ持つ
    public function __invoke() {
        return <<<EOF
        <html>
        <head>
        <title>Single</title>
        <style>
        body { font-size:16pt; color:#999; }
        h1 { font-size:30pt; text-aligh:right; color:#eee;
             margin:-15px 0px 0px 0px; }
        </style>
        </head>
        <body>
            <h1>Single Action</h1>
            <p>これは、シングルアクションコントローラのアクションです</p>
        </body>
        </html>
EOF;
    }
}
