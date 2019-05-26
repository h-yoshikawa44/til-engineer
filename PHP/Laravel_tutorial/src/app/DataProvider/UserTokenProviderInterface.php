<?php

namespace App\DataProvider;

interface UserTokenProviderInterface
{
    /**
     * @param string $token
     *
     * @return \stdClass|null
     */
    public function retrieveUserByToken(string $token);
}