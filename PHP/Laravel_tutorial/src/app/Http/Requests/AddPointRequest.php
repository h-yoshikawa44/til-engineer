<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

class AddPointRequest extends FormRequest
{
    /**
     * @return bool
     */
    public function authorize()
    {
        return true;
    }

    /**
     * @return array
     */
    public function rules()
    {
        return [
            'customer_id' => 'required|int',
            'add_point' => 'required|int'
        ];
    }
}