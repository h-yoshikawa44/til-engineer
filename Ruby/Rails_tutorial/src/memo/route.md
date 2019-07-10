## ルーティング
パス：config/routes.rb

```ruby
Rails.application.routes.draw do
  # For details on the DSL available within this file, see http://guides.rubyonrails.org/routing.html
  # ルートパスのルーティング
  root 'application#goodbye'
end
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

