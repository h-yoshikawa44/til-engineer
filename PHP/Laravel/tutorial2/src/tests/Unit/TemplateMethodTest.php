<?php

namespace Tests\Unit;

use Tests\TestCase;
use Illuminate\Foundation\Testing\WithFaker;
use Illuminate\Foundation\Testing\RefreshDatabase;

class TemplateMethodTest extends TestCase
{
    public static function setUpBeforeClass()
    {
        parent::setUpBeforeClass();

        echo __METHOD__, PHP_EOL;
    }

    protected function setUp(): void
    {
        parent::setUp();

        echo __METHOD__, PHP_EOL;
    }

    /**
     * @test
     */
    public function テストメソッド１()
    {
        echo __METHOD__, PHP_EOL;
        $this->assertTrue(true);
    }

    /**
     * @test
     */
    public function テストメソッド２()
    {
        echo __METHOD__, PHP_EOL;
        $this->assertTrue(true);
    }

    protected function tearDown(): void
    {
        parent::tearDown();

        echo __METHOD__, PHP_EOL;
    }

    public static function tearDownAfterClass()
    {
        parent::tearDownAfterClass();

        echo __METHOD__, PHP_EOL;
    }
}
