<?php

namespace App\Exceptions;

use Fluent\Logger\FluentLogger;
use Exception;
use Illuminate\Foundation\Exceptions\Handler as ExceptionHandler;
use Symfony\Component\HttpFoundation\Response as Res;

class Handler extends ExceptionHandler
{
    /**
     * A list of the exception types that are not reported.
     *
     * @var array
     */
    protected $dontReport = [
        //
    ];

    /**
     * A list of the inputs that are never flashed for validation exceptions.
     *
     * @var array
     */
    protected $dontFlash = [
        'password',
        'password_confirmation',
    ];

    /**
     * Report or log an exception.
     *
     * @param  \Exception  $exception
     * @return void
     */
    public function report(Exception $exception)
    {
        // Illuminate\FOundation\Exceptions\Handlerクラスのreportメソッドを実行
        parent::report($exception);
        $fluentLogger = $this->container->make(FluentLogger::class);
        $fluentLogger->post('report', ['error' => $exception->getMessage()]);
    }

    /**
     * Render an exception into an HTTP response.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \Exception  $exception
     * @return \Illuminate\Http\Response
     */
    public function render($request, Exception $exception)
    {
        // PreConditionExceptionスロー時のレスポンス
        if ($exception instanceof PreConditionException) {
            return response()->json(
                ['message' => trans($exception->getMessage())],
                Res::HTTP_BAD_REQUEST
            );
        }
        return parent::render($request, $exception);
    }
}
