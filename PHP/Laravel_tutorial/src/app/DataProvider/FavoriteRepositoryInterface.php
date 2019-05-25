<?php

namespace App\DataProvider;

interface FavoriteRepositoryInterface
{
    public function switch(int $bookId, int $userId, string $createdAt);
}