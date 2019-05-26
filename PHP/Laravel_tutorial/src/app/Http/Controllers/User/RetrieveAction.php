<?php

namespace App\Http\Controllers\User;

use App\Http\Controllers\Controller;
use Illuminate\Auth\AuthManager;
use Illuminate\Http\Request;

final class RetrieveAction extends Controller
{
    private $authManager;

    public function __construct(AuthManager $authManager)
    {
        $this->authManager = $authManager;
    }

    public functin __invoke(Request $request)
    {
        return $this->authManager->guard('api')->user();

        // こちらでも可能
        // return $this->authManager->setToken($request->bearerToken()->user());
    }
}