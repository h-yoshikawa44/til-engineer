<?php

namespace App\Eloquent;

use App\Model\PointEvent;
use Illuminate\Database\Eloquent\Model;

/**
 * @property int $id
 * @property int $customer_id
 * @property string $event
 * @property int $point
 * @property string $created_at
 */
final class EloquentCustomerPointEvent extends Model
{
    protected $table = 'customer_point_events';
    // 自動設定されるタイムスタンプは不要
    public $timestamps = false;

    /**
     * @param PointEvent $event
     */
    public function register(PointEvent $event)
    {
        $new = $this->newInstance();
        $new->customer_id = $event->getCustomerId();
        $new->event = $event->getEvent();
        $new->point = $event->getPoint();
        $new->created_at = $event->getCreatedAt()->toDateTimeString();
        $new->save();
    }
}