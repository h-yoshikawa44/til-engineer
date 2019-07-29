<?php

namespace Tests\Feature;

use Tests\TestCase;
use Illuminate\Foundation\Testing\WithFaker;
use Illuminate\Foundation\Testing\RefreshDatabase;
use App\User;
use App\Person;

class HelloTest extends TestCase
{
    use RefreshDatabase;

    public function testHello()
    {
       $this->assertTrue(true);

       $response = $this->get('/');
       $response->assertStatus(200);

       $response = $this->get('/hello');
       $response->assertStatus(404);

    //    $user = factory(User::class)->create();
    //    $response = $this->actingAs($user)->get('/hello');
    //    $response->assertStatus(200);

       $response = $this->get('/no_route');
       $response->assertStatus(404);
    }

    public function testHello2()
    {
        // ダミーで利用するデータ
        factory(User::class)->create([
            'name' => 'AAA',
            'email' => 'BBB@CCC.COM',
            'password' => 'ABCABC',
        ]);
        factory(User::class, 10)->create();

        $this->assertDatabaseHas('users', [
            'name' => 'AAA',
            'email' => 'BBB@CCC.COM',
            'password' => 'ABCABC',
        ]);

        // ダミーで利用するデータ
        factory(Person::class)->create([
            'name' => 'XXX',
            'mail' => 'YYY@ZZZ.COM',
            'age' => 123,
        ]);
        factory(Person::class, 10)->create();

        $this->assertDatabaseHas('people', [
            'name' => 'XXX',
            'mail' => 'YYY@ZZZ.COM',
            'age' => 123,
        ]);
    }
}
