<?php

namespace App\Services;

use Carbon\Carbon;
use Generator;
use Illuminate\Database\Connection;

final class ExportOrdersService
{
    /** @var Connection */
    private $connection;

    public function __construct(Connection $connection)
    {
        $this->connection = $connection;
    }

    /**
     * 対象日の購入情報を取得
     *
     * @param Carbon $date
     * @return Generator
     */
    public function findOrders(Carbon $date): Generator
    {
        // 購入情報の取得
        return $this->connection
            ->table('orders')
            ->join('order_details', 'orders.order_code', '=', 'order_details.order_code')
            ->select([
                'orders.order_code',
                'orders.order_date',
                'orders.total_price',
                'orders.total_quantity',
                'orders.customer_name',
                'orders.customer_email',
                'orders.destination_name',
                'orders.destination_zip',
                'orders.destination_prefecture',
                'orders.destination_address',
                'orders.destination_tel',
                'order_details.*'
            ])
            ->where('order_date', '>=', $date->toDateString())
            ->where('order_date', '<', $date->copy()->addDay()->toDateString())
            ->orderBy('order_date')
            ->orderBy('orders.id')
            ->orderBy('order_details.detail_no')
            ->cursor(); // ジェネレータとして取得（この時点ではレコードは読み込まない）
    }
}