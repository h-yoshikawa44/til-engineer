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

|URL|メソッド|アクション|用途|名前付きルート|
|---|---|---|---|---|
|/users|GET|index|ユーザ一覧|user_path|
|/users|POST|create|ユーザ作成アクション|user_path|
|/users/new|GET|new|新規ユーザーを作成ページ|new_user_path|
|/users/:id/edit|GET|edit|idのユーザーを編集ページ|edit_user_path(user)|
|/users/:id|GET|show|idのユーザーを表示ページ|user_path(user)|
|/users/:id|PATCH|update|idのユーザを更新アクション|user_path(user)|
|/users/:id|PUT|update|idのユーザを更新アクション|user_path(user)|
|/users/:id|DELETE|destroy|idのユーザを削除アクション|user_path(user)|

