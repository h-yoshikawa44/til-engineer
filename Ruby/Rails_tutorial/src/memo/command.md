## コマンド

- `rails new アプリ名`  
  Railsアプリケーションの作成

- `rails generate`  
  ファイルの生成
  - `rails generate scaffold モデル名 カラム名:データ型...`
    例　rails generate scaffold User name:string email:string
    scaffoldでのファイル生成（マイグレーション、モデル、各種テスト、フィクスチャ、コントローラ、各種ビュー、CSS、SCSS）、ルーティングに書き込み）

- `rails db:migrate`  
  マイグレーション実行

- `rails server`  
  Railsローカルサーバの起動

- `rails routes`  
  ルーティングの確認

- `rails console`  
  コンソールの起動