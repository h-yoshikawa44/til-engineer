<?php

namespace App\DataProvider;

use \App\DataProvider\Eloquent\Favorite;

class FavoriteRepository implements FavoriteRepositoryInterface
{
    private $favorite;

    public function __construct(Favorite $favorite)
    {
        $this->favorite = $favorite;
    }

    public function switch(int $bookId, int $userId, string $createdAt): int
    {
        return \DB::transaction(
            function() use ($bookId, $userId, $createdAt) {
                $count = $this->favorite->where('book_id', $bookId)
                    ->where('user_id', $userId)
                    ->count();
                if ($count == 0) {
                    $this->favorite->create([
                        'book_id' => $bookId,
                        'user_id' => $userId,
                        'created_at' => $createdAt
                    ]);
                    return 1;
                }
                $this->favorite->where('book_id', $bookId)
                    ->where('user_id', $userId)
                    ->delete();
                return 0;
            }
        );
    }
}