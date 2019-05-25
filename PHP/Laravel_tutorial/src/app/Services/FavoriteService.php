<?php

namespace App\Services;

use App\DataProvider\FavoriteRepositoryInterface;

class FavoriteService
{
    private $favorite;

    public function __construct(FavoriteRepositoryInterface $favorite)
    {
        $this->favorite = $favorite;
    }
    public function switchFavorite(int $bookId, int $userId, string $createdAt)
    {
        $this->favorite->switch($bookId, $userId, $createdAt);
    }
}