## ルーティング
パス：config/routes.rb

基本的な書き方
```ruby
Rails.application.routes.draw do
  # For details on the DSL available within this file, see http://guides.rubyonrails.org/routing.html
  # ルートパスのルーティング
  root 'application#goodbye'
  # GETメソッドのルーティング
  get  'static_pages/home'
end
```

`get  '/home'`を
```ruby
get '/help', to: 'static_pages#help'
```
といったようにもかける  
これで`help_path`や`help_url`といった名前付きルートが使えるようになる

上記の場合、root_urlとstatic_pages_home_urlというヘルパーが使えるようになる

また以下のように名前付きルートを別の名前に指定も可能
```ruby
get 'static_pages/help', to: 'static_pages#help' as: 'helf'
```

### scaffoldでのルーティング
例
resources :users

- /users　　　　　　GET　　index　　ユーザ一覧
  　　　　　　　　　POST　　create　ユーザ作成アクション
- /users/new　　　　GET　　new　　　新規ユーザーを作成ページ
- /users/:id/edit　GET　　edit　　idのユーザーを編集ページ
- /users/:id　　　　GET　　show　 idのユーザーを表示ページ
  　　　　　　　　　PATCH　update　idのユーザを更新アクション
  　　　　　　　　　PUT　　update　idのユーザを更新アクション
　　　　　　　　　　DELETE destroy idのユーザを削除アクション

