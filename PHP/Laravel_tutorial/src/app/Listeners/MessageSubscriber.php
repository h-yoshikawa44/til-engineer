<?php

namespace App\Listeners;

use App\Events\PublishProcesssor;
use Illuminate\Queue\InteractsWithQueue;
use Illuminate\Contracts\Queue\ShouldQueue;

class MessageSubscriber
{
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
        var_dump($event->getInt());
    }
}
