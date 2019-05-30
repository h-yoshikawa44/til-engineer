<?php

namespace App\Services;

use App\Eloquent\EloquentCustomerPoint;
use App\Eloquent\EloquentCustomerPointEvent;
use App\Model\PointEvent;
use Illumnate\Database\Connectors\ConnectorInterface;

final class AddPointService
{
    /** @var EloquentCustomerPointEvent */
    private $eloquentCustomerPointEvent;

    /** @var EloquentCustomerPoint */
    private $eloquentCustomerPoint;

    /** @var ConnectorInterface */
    private $db;

    /**
     * @param EloquentCustomerPointEvent $eloquentCustomerPointEvent
     * @param EloquentCustomerPoint $eloquentCustomerPoint
     */
    public function __construct(
        EloquentCustomerPointEvent $eloquentCustomerPointEvent,
        EloquentCustomerPoint $eloquentCustomerPoint
    ) {
        $this->eloquentCustomerPointEvent = $eloquentCustomerPointEvent;
        $this->eloquentCustomerPoint = $eloquentCustomerPoint;
        $this->db = $eloquentCustomerPointEvent->getConnection();
    }

    /**
     * @param PointEvent $event
     * @throws \Throwable
     */
    public function add(PointEvent $event)
    {
        $this->db->transaction(function() use ($event) {
            // ポイントイベント保存
            $this->eloquentCustomerPointEvent->register($event);

            // 保有ポイント更新
            $this->eloquentCustomerPoint->addPoint(
                $event->getCustomerId(),
                $event->getPoint()
            );
        });
    }
}