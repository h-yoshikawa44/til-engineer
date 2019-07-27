<?php

namespace App\Console\Commands;

use App\UseCases\SendOrdersUseCase;
use Carbon\Carbon;
use Illuminate\Console\Command;
use Psr\Log\LoggerInterface;

class SendOrderCommand extends Command
{
    /** @var SendOrdersUseCase */
    private $useCase;

    /** @var LoggerInterface */
    private $logger;

    /**
     * The name and signature of the console command.
     *
     * @var string
     */
    protected $signature = 'app:send-orders {date}';

    /**
     * The console command description.
     *
     * @var string
     */
    protected $description = '購入情報を送信する';

    /**
     * Create a new command instance.
     *
     * @param SendOrdersUseCase $useCase
     * @param LoggerInterface $logger
     * @return void
     */
    public function __construct(SendOrdersUseCase $useCase, LoggerInterface $logger)
    {
        parent::__construct();

        $this->useCase = $useCase;
        $this->logger = $logger;
    }

    /**
     * Execute the console command.
     *
     * @return mixed
     */
    public function handle()
    {
        // バッチ処理開始ログ
        $this->logger->info(__METHOD__ . ' ' . 'start');

        // 引数dateの値を取得する
        $date = $this->argument('date');

        // $dateの値（文字列）からCarbonインスタンスを生成
        $targetDate = Carbon::createFromFormat('Ymd', $date);

        $count = $this->useCase->run($targetDate);

        // バッチ処理終了ログ
        $this->logger->info(__METHOD__ . ' ' . 'done sent_count' . $count);

        $this->info('ok');
    }
}
