<?php

namespace App\Eloquent;

use Illuminate\Database\Eloquent\Model;

/**
 * @property int $customer_id
 * @property int $point
 */
final class EloquentCustomerPoint extends Model
{
    protected $table = 'customer_points';
    // 自動設定されるタイムスタンプは不要
    public $timestamp = false;

    /**
     * @param int $customerId
     * @param int $point
     * @return bool
     */
    public function addPoint(int $customerId, int $point): bool
    {
        return \DB::update('update customer_points set point=point+:point where customer_id=:customer_id',
                [
                    'point'       => $point,
                    'customer_id' => $customerId,
                ]
            ) === 1;
    }
}