<?php

namespace App\Http\Controllers\User;

use App\Http\Controllers\Controller;
// use App\User;
// use App\Content;
use Illuminate\Auth\AuthManager;
use Illuminate\Http\Request;
use Illuminate\Contracts\Auth\Access\Gate;

final class RetrieveAction extends Controller
{
    private $authManager;

    private $gate;

    public function __construct(AuthManager $authManager, Gate $gate)
    {
        $this->authManager = $authManager;
        $this->gate = $gate;
    }

    public function __invoke(Request $request, string $id)
    {
        return $this->authManager->guard('api')->user();

        // こちらでも可能
        // return $this->authManager->setToken($request->bearerToken()->user());

        // 認可処理の適用
        if ($this->gate->allows('user-access', $id)) {
            // 実行が許可される場合に実行
        }
        // または \Gate::allows('user-access', $id)

        // 認可処理実行例
        // $content = Content::find((int) $id);
        /** @var User $user */
        // $user = $this->authManager->guard()->user();
        // if ($user->can('update', $content)) {
        //     // 実行可能な場合処理される
        // }

        // ポリシークラスの使用例
        // $class = new \stdClass();
        // $class->id = 1;
        // 認可されない場合はIIluminate\Auth\Access\AuthorizationExceptionがスローされる
        // $this->gate->forUser(
        //     $this->authManager->guard()->user()
        // )->allows('edit', $class);
    }
}