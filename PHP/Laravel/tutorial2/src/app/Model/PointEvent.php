<?php

namespace App\Model;

use Carbon\Carbon;

final class PointEvent
{
    /** @var int */
    private $customerId;

    /** @var string */
    private $event;

    /** @var int */
    private $point;

    /** @var Carbon */
    private $createdAt;

    /**
     * @param int $customerId
     * @param string $event
     * @param int $point
     * @param Carbon $createdAt
     */
    public function __construct(
        int $customerId,
        string $event,
        int $point,
        Carbon $createdAt
    ) {
        $this->customerId = $customerId;
        $this->event = $event;
        $this->point = $point;
        $this->createdAt = $createdAt;
    }

    /**
     * @return int
     */
    public function getCustomerId(): int
    {
        return $this->customerId;
    }

    /**
     * @return string
     */
    public function getEvent(): string
    {
        return $this->event;
    }

    /**
     * @return int
     */
    public function getPoint(): int
    {
        return $this->point;
    }

    /**
     * @return Carbon
     */
    public function getCreatedAt(): Carbon
    {
        return $this->createdAt->copy();
    }
}