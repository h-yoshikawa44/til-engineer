<?php

namespace Tests\Unit;

use App\Eloquent\EloquentCustomer;
use App\Eloquent\EloquentCustomerPoint;
use App\Model\PointEvent;
use App\Services\AddPointService;
use Illuminate\Foundation\Testing\RefreshDatabase;
use Illuminate\Support\Carbon;
use Tests\TestCase;

class AddPointServiceTest extends TestCase
{
    use RefreshDatabase;

    const CUSTOMER_ID = 1;

    protected function setUp(): void
    {
        parent::setUp();

        // テストに必要なレコードを登録
        factory(EloquentCustomer::class)->create([
            'id' => self::CUSTOMER_ID,
        ]);
        factory(EloquentCustomerPoint::class)->create([
            'customer_id' => self::CUSTOMER_ID,
            'point' => 100,
        ]);
    }

    /**
     * @test
     * @throws \Throwable
     */
    public function add()
    {
        // テスト対象メソッドの実行
        $event = new PointEvent(
            self::CUSTOMER_ID,
            '加算イベント',
            10,
            Carbon::create(2018, 8, 4, 12, 34, 56)
        );
        /** @var AddPointService $service */
        $service = app()->make(AddPointService::class);
        $service->add($event);

        // テスト結果のアサーション
        $this->assertDatabaseHas('customer_point_events', [
            'customer_id' => self::CUSTOMER_ID,
            'event' => $event->getEvent(),
            'point' => $event->getPoint(),
            'created_at' => $event->getCreatedAt(),
        ]);
        $this->assertDatabaseHas('customer_points', [
            'customer_id' => self::CUSTOMER_ID,
            'point' => 110
        ]);
    }
}