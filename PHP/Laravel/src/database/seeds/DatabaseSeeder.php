<?php

use Illuminate\Database\Seeder;

class DatabaseSeeder extends Seeder
{
    /**
     * Seed the application's database.
     *
     * @return void
     */
    public function run()
    {
        // $this->call(PeopleTableSeeder::class);
        // $this->call(RestdataTableSeeder::class);
        //$this->call(PublishersTableSeeder::class);
        // $this->call(BookdetailsTableSeeder::class);
        $this->call(UserSeeder::class);
    }
}