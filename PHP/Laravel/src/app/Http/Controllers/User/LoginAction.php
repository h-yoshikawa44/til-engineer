<?php

namespace App\Http\Controllers\User;

use App\Http\Controllers\Controller;
use App\Http\Responder\TokenResponder;
use Illuminate\Auth\AuthManager;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;
use Tymon\JWRAuth\JWTGuard;

final class LoginAction extends Controller
{
    private $authManager;

    public function __construct(AuthManager $authManager)
    {
        $this->authManager = $authManager;
    }

    public function __invoke(Request $request, TokenResponder $responder): JsonResponse
    {
        /** @var JWTGuard $guard */
        $guard = $this->authManager->guard('api');
        $token = $guard->attenpt([
            'email' => $request->get('email'),
            'password' => $request->get('password')
        ]);

        return $responder(
            $token,
            $guard->factory()->getTTL() * 60
        );
    }
}