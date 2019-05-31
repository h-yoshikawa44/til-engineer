<?php

namespace App\UseCases;

use App\Eloquent\EloquentCustomer;
use App\Eloquent\EloquentCustomerPoint;
use App\Exceptions\PreConditionException;
use App\Model\PointEvent;
use App\Services\AddPointService;
use Illuminate\Support\Carbon;

final class AddPointUseCase
{
    /** @var AddPointService */
    private $service;

    /** @var EloquentCustomer */
    private $eloquentCustomer;

    /** @var EloquentCustomerPoint */
    private $eloquentCustomerPoint;

    public function __construct(
        AddPointService $service,
        EloquentCustomer $eloquentCustomer,
        EloquentCustomerPoint $eloquentCustomerPoint
    ) {
        $this->service = $service;
        $this->eloquentCustomer = $eloquentCustomer;
        $this->eloquentCustomerPoint = $eloquentCustomerPoint;
    }

    /**
     * @param int $customerId
     * @param int addPoint
     * @param string $pointEvent
     * @param Carbon $now
     * @return int
     * @throws \Throwable
     */
    public function run(
        int $customerId,
        int $addPoint,
        string $pointEvent,
        Carbon $now
    ): int {
        // 事前条件検証
        if ($addPoint <= 0) {
            throw new PreConditionException(
                'add_point should be equals or greater than 1'
            );
        }

        if (!$this->eloquentCustomer->where('id', $customerId)->exists()) {
            $message = sprintf('customer_id:%d does not exists', $customerId);
            throw new PreConditionException($message);
        }

        // ポイント加算処理
        $event = new PointEvent($customerId, $pointEvent, $addPoint, $now);
        $this->service->add($event);

        // 保有ポイント取得
        return $this->eloquentCustomerPoint->findPoint($customerId);
    }
}