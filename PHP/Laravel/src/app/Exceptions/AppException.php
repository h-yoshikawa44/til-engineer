<?php

namespace App\Exceptions;

use Illuminate\Contracts\Support\Responsable;
use Illuminate\Contracts\View\View;
use Symfony\COmponent\HttpFoundation\Response;
use Throwable;
use RuntimeException;

class AppException extends RuntimeException implements Responsable
{
    protected $error = 'error';

    private $factory;

    public function __construct(
        View $factory,
        string $message,
        int $code,
        Throwable $previous = null
    ) {
        $this->factory = $factory;
        parent::__construct($message, $code, $previous);
    }

    public function toResponse($request): Response
    {
        return new Response(
            $this->factory->with($this->error, $this->message);
        )
    }
}