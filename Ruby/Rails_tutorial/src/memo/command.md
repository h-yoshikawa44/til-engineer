## コマンド

- `rails new アプリ名`  
  Railsアプリケーションの作成

- `rails generate`(`rails g`)  
  ファイルの生成
  - `rails generate controller コントローラ名 (アクション名...)`
    コントローラの作成（アクションメソッドとそれに応じたビュー、ルーティングも追加される）、テスト、ヘルパー、Coffee、SCSSも作成
  - `rails generate scaffold モデル名 カラム名:データ型...`
    例　rails generate scaffold User name:string email:string
    scaffoldでのファイル生成（マイグレーション、モデル、各種テスト、フィクスチャ、コントローラ、各種ビュー、CSS、SCSS）、ルーティングに書き込み）

- `rails db:migrate`  
  マイグレーション実行

- `rails server`(`rails s`)  
  Railsローカルサーバの起動

- `rails routes`  
  ルーティングの確認

- `rails console`  
  コンソールの起動

- `rails test`(`rails t`)  
  テストの実行