<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Http\Response;

global $head, $style, $body, $end;
$head = '<html><head>';
$style = <<<EOF
<style>
body { font-size:16pt; color:#999 }
h1 { font-size:100pt; text-aligh:right; color:#eee;
     margin:-40px 0px -50px 0px; }
</style>
EOF;
$body = '</head><body>';
$end = '</body></head>';

function tag($tag, $txt) {
    return "<{$tag}>" . $txt . "</{$tag}>";
}


class HelloController extends Controller
{
    public function sample($id='noname', $pass='unknown') {
        global $head, $style, $body, $end;

        $html = $head . tag('title', 'Hello/Sample') . $style . $body
                . tag('h1', 'Sample') . tag('p', 'This is Sample page')
                . '<ul>' . tag('li', "ID: {$id}") . tag('li', "PASS: {$pass}")
                . $end;
        return $html;
    }

    public function index() {
        global $head, $style, $body, $end;

        $html = $head . tag('title', 'Hello/Index') . $style . $body
                . tag('h1', 'Index') . tag('p', 'This is Index page')
                . '<a href="/hello/other">go to Other page</a>'
                . $end;
        return $html;
    }

    public function other() {
        global $head, $style, $body, $end;

        $html = $head . tag('title', 'Hello/Other') . $style
                . tag('h1', 'Other') . tag('p', 'this is Other page')
                . $end;

        return $html;
    }

    public function index2(Request $request, Response $response) {
        $html = <<<EOF
        <html>
        <head>
        <title>Hello/Index2</title>
        <style>
        body { font-size:16pt; color:#999; }
        h1 { font-size:120pt; text-aligh:right; color:#fafafa;
            margin:-50px 0px -120px 0px; }
        </style>
        </head>
        <body>
            <h1>Hello2</h1>
            <h3>Request</h3>
            <pre>{$request}</pre>
            <h3>Response</h3>
            <pre>{$response}</pre>
        </body>
        </html>
EOF;
        $response->setContent($html);
        return $response;

        // Request
        // GET /hello/index2 HTTP/1.1
        // Accept:                    text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3
        // Accept-Encoding:           gzip, deflate
        // Accept-Language:           ja,en-US;q=0.9,en;q=0.8
        // Cache-Control:             max-age=0
        // Connection:                keep-alive
        // Content-Length:
        // Content-Type:
        // Cookie:                    pma_lang=ja; XSRF-TOKEN=eyJpdiI6ImlwOTNYNUY4VDlUY0xEcWtTWjZNTHc9PSIsInZhbHVlIjoiXC8zS1dxa2NtYkJzaWx1NHhaRmxRV1VcL0YxbUhJVlwvazFnTlwvWWVnSkNHT3JrSndPN2tkcXNYR2NDMWpud3R4QXQiLCJtYWMiOiI1NGY5ZWYzYmNjYzIxNGY3M2FlZDNhMWM3ZDI1ZWIzZDg4NTY4MmY0NmM5MTM3ZDc1MzA3NDBkOTkyM2EzOTlmIn0%3D; laravel_session=eyJpdiI6Im1hMVZ6Tmc0OWJLM2lyUjVERGVEMlE9PSIsInZhbHVlIjoiT3hiZ1JZZG1wWkc5VWh4blJpWCtpTldCSVpobWxZS05XN2F1V1YycWUxSDNpMHFMaDZPdktVeGRpTEdaaUVGWCIsIm1hYyI6ImJmYWE3ZWMyOTA5MzRhMjYwMzc0ZTIwM2JhN2E0NTFlYmFmMDkxNmNmN2VhYTY4ZDU0ODQyMzdkMWU3YTAxNDUifQ%3D%3D
        // Dnt:                       1
        // Host:                      192.168.99.100
        // Upgrade-Insecure-Requests: 1
        // User-Agent:                Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36
        // Cookie: pma_lang=; XSRF-TOKEN=YkK2Irdp8qOuqBXG1un87OyAfO87Tcr1kiOk4jqB; laravel_session=06EA1t3oA2IN6lya1o5dd28jBCjcoYp1r7qjCI9L

        // Response
        // HTTP/1.0 200 OK
        // Cache-Control: no-cache, private
        // Date:          Mon, 06 May 2019 05:57:04 GMT
    }
}

