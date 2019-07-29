<?php
namespace App\Http\Validators;

use Illuminate\Validation\Validator;

// 独自バリデータクラス
class HelloValidator extends Validator
{
    public function validateHello($attribute, $value, $parameters)
    {
        return $value % 2 == 0;
    }
}