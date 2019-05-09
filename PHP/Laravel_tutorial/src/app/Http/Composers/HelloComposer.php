<?php
namespace App\Http\Composers;

use Illuminate\View\View;

// ビューコンポーザクラス
class HelloComposer
{
    // composeメソッドが必要
    // サービスプロバイダのbootからView::composerが実行されたときに呼ばれる
    public function compose(View $view)
    {
        $view->with('view_message',
                    'this view is "' . $view->getName() . '"!!');
    }
}