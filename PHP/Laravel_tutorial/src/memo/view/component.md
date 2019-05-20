## コンポーネント
パス：App\resources\view\components

ビューの部品となるコンポーネントを書いていく  
ヘッダーやフッターなどは複数画面で使用するものはコンポーネントで作成するとよい  
コンポーネントをはめ込むところで@componentで指定  
コンポーネント側で使用する変数の値は@slotで設定する

コンポーネント　　　　　ビューファイル
(message.blade.php)　　(index6.blade.php)
※コンテンツを定義　　→　　@component('components.message)
{{$msg_title}}　　　→　　　　　@slot('msg_title')
　　　　　　　　　　　　　　　　　　CAUTION!
　　　　　　　　　　　　　　　　@endslot
　　　　　　　　　　　　　 @endcomponent