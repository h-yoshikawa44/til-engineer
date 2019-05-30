<?php

namespace App\Eloquent;
use Illuminate\Database\Eloquent\Model;

/**
 * @property int $id
 * @property string $name
 */
final class EloquentCustomer extends Model
{
    protected $table = 'customers';
}