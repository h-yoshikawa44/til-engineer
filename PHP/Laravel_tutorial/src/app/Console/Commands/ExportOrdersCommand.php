<?php

namespace App\Console\Commands;

use App\UseCases\ExportOrdersUseCase;
use Carbon\Carbon;
use Illuminate\Console\Command;

class ExportOrdersCommand extends Command
{
    /**
     * The name and signature of the console command.
     *
     * @var string
     */
    protected $signature = 'app:export-orders {date} {--output=}';

    /**
     * The console command description.
     *
     * @var string
     */
    protected $description = '購入情報を出力する';

    /**
     * Create a new command instance.
     * @param ExportOrderUseCase $useCase
     * @return void
     */
    public function __construct(ExportOrdersUseCase $useCase)
    {
        parent::__construct();

        $this->usecase = $useCase;
    }

    /**
     * Execute the console command.
     *
     * @return mixed
     */
    public function handle()
    {
        // 引数dateの値を取得する
        $date = $this->argument('date');
        // $dateの値（文字列）からCarbonインスタンスを生成
        $targerDate = Carbon::createFromFormat('Ymd', $date);
        $tsv = $this->usecase->run($targetDate);

        // outputオプションの値を取得
        $outputFilePath = $this->option('output');
        // nullであれば未設定なので、標準出力に出力
        if (is_null($outputFilePath)) {
            echo $tsv;
            return;
        }

        // ファイルに出力
        file_put_contents($outputFilePath, $tsv);
    }
}
