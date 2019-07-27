<?php

namespace Tests\Feature\Api;

use Tests\TestCase;
use Illuminate\Foundation\Testing\WithFaker;
use Illuminate\Foundation\Testing\RefreshDatabase;

class ApiPingTest extends TestCase
{
    /**
     * @test
     */
    public function get_ping()
    {
        $response = $this->get('/api/ping');

        // HTTPステータスコードを検証
        $response->assertStatus(200);
        // レスポンスボディのJSONを検証
        $response->assertExactJson(['message' => 'pong']);
    }
}
