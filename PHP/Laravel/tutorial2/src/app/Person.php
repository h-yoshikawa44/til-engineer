<?php

namespace App;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Builder;
use App\Scopes\ScopePerson;

class Person extends Model
{
    public function getData()
    {
        return $this->id . '：' . $this->name . '(' . $this->age . ')';
    }

    public function scopeNameEqual($query, $str)
    {
        return $query->where('name', $str);
    }

    public function scopeAgeGreaterThan($query, $n)
    {
        return $query->where('age', '>=', $n);
    }

    public function scopeAgeLessThan($query, $n)
    {
        return $query->where('age', '<=', $n);
    }

    // protected static function boot()
    // {
    //     parent::boot();

    //     // グローバルスコープ使用例
    //     // static::addGlobalScope('age', function (Builder $builder) {
    //     //     $builder->where('age', '>', 20);
    //     // });

           // スコープクラス使用例
    //     static::addGlobalScope(new ScopePerson);
    // }

    // 値を用意しておかないカラム
    protected $guarded = array('id');

    public static $rules = array(
        'name' => 'required',
        'mail' => 'email',
        'age' => 'integer|min:0|max:150'
    );

    public function board()
    {
        // return $this->hasOne('App\Board');
        return $this->hasMany('App\Board');
    }
}

