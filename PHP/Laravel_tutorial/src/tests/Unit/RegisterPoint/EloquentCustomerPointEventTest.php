<?php

namespace Tests\Unit\RegisterPoint;

use App\Eloquent\EloquentCustomer;
use App\Eloquent\EloquentCustomerPointEvent;
use App\Model\PointEvent;
use Carbon\Carbon;
use Illuminate\Foundation\Testing\RefreshDatabase;
use Tests\TestCase;

class EloquentCustomerPointEventTest extends TestCase
{
    use RefreshDatabase;

    /**
     * @test
     */
    public function register()
    {
        // テストに必要なレコードを登録
        $customerId = 1;
        factory(EloquentCustomer::class)->create([
            'id' => $customerId,
        ]);

        // テスト対象メソッドの実行
        $event = new PointEvent(
            $customerId,
            '加算イベント',
            100,
            Carbon::create(2018, 8, 4, 12, 34, 56)
        );
        $sut = new EloquentCustomerPointEvent();
        $sut->register($event);

        // テスト結果のアサーション
        $this->assertDatabaseHas('customer_point_events', [
            'customer_id' => $customerId,
            'event' => $event->getEvent(),
            'point' => $event->getPoint(),
            'created_at' => $event->getCreatedAt(),
        ]);
    }
}