<?php

namespace App\Providers;

use App\Events\PublishProcessor;
use App\Listeners;
use Illuminate\Support\Facades\Event;
use Illuminate\Auth\Events\Registered;
use Illuminate\Auth\Listeners\SendEmailVerificationNotification;
use Illuminate\Foundation\Support\Providers\EventServiceProvider as ServiceProvider;

class EventServiceProvider extends ServiceProvider
{
    /**
     * The event listener mappings for the application.
     *
     * @var array
     */
    protected $listen = [
        Registered::class => [
            SendEmailVerificationNotification::class,
        ],
        'App\Events\Event' => [
            'App\Listeners\EventListener',
        ],
        // 会員登録イベントのリスナーを発行
        'Illuminate\Auth\Events\Registered' => [
            'App\Listeners\RegisteredListener',
        ],
        PublishProcessor::class => [
            Listeners\MessageSubscriber::class,
            Listeners\MessageQueueSubscriber::class,
        ],
    ];

    /**
     * Register any events for your application.
     *
     * @return void
     */
    public function boot()
    {
        parent::boot();

        // Facadeを利用した例
        // Event::listen(
        //     PublishProcessor::class,
        //     MessageSubscriber::class
        // );
        // フレームワークのDIコンテナにアクセスする場合
        // $this->app['events']->listen(
        //     PublishProcessor::class,
        //     MessageSubscriber:::class
        // );
    }
}
