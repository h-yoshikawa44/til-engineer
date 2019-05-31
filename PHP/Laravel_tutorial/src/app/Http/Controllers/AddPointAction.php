<?php

namespace App\Http\Controllers;

use App\Http\Requests\AddPointRequest;
use App\UseCases\AddPointUseCase;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;
use Illuminate\Support\Carbon;

class AddPointAction extends Controller
{
    /** @var AddPointUseCase */
    private $useCase;

    /**
     * @param AddPointUseCase $useCase
     */
    public function __construct(AddPointUseCase $useCase)
    {
        $this->useCase = $useCase;
    }

    /**
     * @param AddPointRequest $request
     * @return JsonResponse
     * @throws \Throwable
     */
    public function __invoke(AddPointRequest $request): JsonResponse
    {
        // JSONからパラメータを取得
        $customerId = filter_var($request->json('customer_id'), FILTER_VALIDATE_INT);
        $addPoint = filter_var($request->json('add_point'), FILTER_VALIDATE_INT);

        // ポイント加算ユースケース実行
        $customerPoint = $this->useCase->run(
            $customerId,
            $addPoint,
            "ADD_POINT",
            Carbon::now()
        );

        // レスポンス生成
        return response()->json(['customer_point' => $customerPoint]);
    }
}
