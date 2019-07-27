<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Restdata extends Model
{
    // 連携するテーブルの指定
    protected $table = 'restdata';
    protected $guarded = array('id');

    public static $rules = array(
        'message' => 'required',
        'url' => 'required',
    );

    public function getData()
    {
        return $this->id . '：' . $this->message . '(' . $this->url . ')';
    }
}
