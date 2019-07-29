<?php

/* @var $factory \Illuminate\Database\Eloquent\Factory */

use App\Report;
use Faker\Generator as Faker;

$factory->define(Report::class, function (Faker $faker) {
    return [
        'visit_date' => $faker->date(),
        'detail' => $faker->realText(),
    ];
});
