<?php

namespace App\Console\Commands;

use Illuminate\Console\Command;
use Symfony\Component\Console\Output\OutputInterface;

class OutputCommand extends Command
{
    /**
     * The name and signature of the console command.
     *
     * @var string
     */
    protected $signature = 'output';

    /**
     * The console command description.
     *
     * @var string
     */
    protected $description = '出力テスト';

    /**
     * Create a new command instance.
     *
     * @return void
     */
    public function __construct()
    {
        parent::__construct();
    }

    /**
     * Execute the console command.
     *
     * @return mixed
     */
    public function handle()
    {
        $this->info('quiet', OutputInterface::VERBOSITY_QUIET);
        $this->info('normal', OutputInterface::VERBOSITY_NORMAL);
        $this->info('verbose', OutputInterface::VERBOSITY_VERBOSE);
        $this->info('very_verbose', OutputInterface::VERBOSITY_VERY_VERBOSE);
        $this->info('debug', OutputInterface::VERBOSITY_DEBUG);
    }
}
