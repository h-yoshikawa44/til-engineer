<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Customer extends Model
{
    public function reports()
    {
        return $this->hasMany(Report::class);
    }
}
