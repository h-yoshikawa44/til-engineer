<?php

namespace App\Listeners;

use App\Events\PublishProcesssor;
use Illuminate\Queue\InteractsWithQueue;
use Illuminate\Contracts\Queue\ShouldQueue;

class MessageQueueSubscriber
{
    use InteractsWithQueue;

    /**
     * Create the event listener.
     *
     * @return void
     */
    public function __construct()
    {
        //
    }

    /**
     * Handle the event.
     *
     * @param  PublishProcesssor  $event
     * @return void
     */
    public function handle(PublishProcesssor $event)
    {
        \Log::info($event->getInt());
    }
}
