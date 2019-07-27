## コマンド

- `rails new アプリ名`  
  Railsアプリケーションの作成

- `rails generate`(`rails g`)  
  ファイルの生成
  - `rails generate controller コントローラ名(複数形) (アクション名...)`
    例　rails generate controller Users new
    コントローラの作成（アクションメソッドとそれに応じたビュー、ルーティングも追加される）、テスト、ヘルパー、Coffee、SCSSも作成
  - `rails generate model モデル名(単数形) カラム名:データ型...`
    モデルの作成、マイグレーション、テスト、フィクスチャも作成
  - `rails generate scaffold モデル名(単数形) カラム名:データ型...`
    例　rails generate scaffold User name:string email:string  
    scaffoldでのファイル生成（マイグレーション、モデル、各種テスト、フィクスチャ、コントローラ、各種ビュー、CSS、SCSS）、ルーティングに書き込み）
  - `rails generate integration_test ファイル名
    例　rails generate integration_test site_layout  
    総合テストファイルを作成（ファイル名末尾に`_testが`がつく）

- `rails db:migrate`  
  マイグレーション実行
  - `rails db:rollback`  
    ロールバック実行
  - `rails db:migrate:reset`  
    一度、データベースを削除してマイグレーション実行

- `rails server`(`rails s`)  
  Railsローカルサーバの起動

- `rails routes`  
  ルーティングの確認

- `rails console`(`rails c`)  
  コンソールの起動（irbを拡張したもの）  
  デフォルトではdevelopment環境で起動する
  - `rails console --sandbox`  
    サンドボックスモードでコンソール起動（DBを変更したくない場合などに用いる）

- `rails test`(`rails t`)  
  テストの実行
  - `rails test:integration`  
  　総合テストの実行