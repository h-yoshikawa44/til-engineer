<?php

namespace App\Gate;

use App\User;
use function intval;

final class UserAccess
{
    public function __invoke(User $user, string $id): bool
    {
        return intval($user->getAuthIdentifier()) ===intval($id);
    }
}