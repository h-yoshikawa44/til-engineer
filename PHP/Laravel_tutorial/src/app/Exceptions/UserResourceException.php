<?php

namespace App\Exceptions;

use Illuminate\Contracts\Support\Responsable;
use Illuminate\Http\JsonResponse;
use RuntimeException;
use Symfony\Component\HttpFoundation\Response;
use Throwable;

class UserResourceException extends RuntimeException implements Responsable
{
    public function __construct(
        string $message = "",
        int $code = 0,
        Throwable $previous = null
    ) {
        parent::__construct($message, $code, $previous)
    }

    public function toResponse($request): Response
    {
        return new JsonResponse([
            'message' => $this->message,
            'path' => $request->getRequestUri(),
            'logref' => 44,
            '_links' => [
                'about' => [
                    'href' => $request->gerUri()
                ]
            ],
        ], Response::HTTP_NOT_FOUND, [
            'content-type' => 'application/vnd.error+json'
        ]);
    }
}