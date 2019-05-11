<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

class HelloRequest extends FormRequest
{
    /**
     * Determine if the user is authorized to make this request.
     * フォームのリクエストの利用が許可されているかどうか
     *
     * @return bool
     */
    public function authorize()
    {
        // if ($this->path() == 'validate')
        // {
        //     return true;
        // } else {
        //     return false;
        // }
        return true;
    }

    /**
     * Get the validation rules that apply to the request.
     * 適用されるバリデーションの検証ルールを設定
     *
     * @return array
     */
    public function rules()
    {
        return [
            'name' => 'required',
            'mail' => 'email',
            'age' => 'numeric|hello'
        ];
    }

    public function messages()
    {
        return [
            'name.required' => '名前は必ず入力してください',
            'mail.email' => 'メールアドレスが必要です',
            'age.numeric' => '年齢を整数で記入ください',
            'age.hello' => 'Hello! 入力は偶数のみ受け付けます',
        ];
    }
}
