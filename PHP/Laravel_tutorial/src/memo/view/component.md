## コンポーネント
resources/views/componentsにビューの部品となるコンポーネントを書いていくファイルを作成  
ヘッダーやフッターなどはコンポーネントで作成するとよい  
コンポーネントをはめ込むところで@componentで指定  
コンポーネント側で使用する変数の値は@slotで設定する

コンポーネント　　　　　ビューファイル
(message.blade.php)　　(index6.blade.php)
※コンテンツを定義　　→　　@component('components.message)
{{$msg_title}}　　　→　　　　　@slot('msg_title')
　　　　　　　　　　　　　　　　　　CAUTION!
　　　　　　　　　　　　　　　　@endslot
　　　　　　　　　　　　　 @endcomponent