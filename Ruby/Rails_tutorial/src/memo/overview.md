## 概要

基本的な流れ

リクエスト
↓　↑
ルーティング(config/route.rb)
↓　↑
コントローラ(app/controllers/各種コントローラ)
↓　↑　　　　　　　　　　　↓　↑
ビュー　　　　　　　　　　モデル

### scaffold
指定したモデルに関するファイルを生成するコマンド

マイグレーション、モデル、各種テスト、フィクスチャ、コントローラ、各種ビュー、CSS、SCSSの生成、ルーティングに書き込みを行ってくれる

- db/migrate/マイグレーションファイル
- app/models/モデルファイル
- test/models/テストファイル
- test/fixtures/フィクスチャファイル
- app/controllers/コントローラファイル
- app/views/モデル名(複数形)
  - index.html.erb
  - edit.html.erb
  - show.html.erb
  - new.html.erb
  - _form.html.erb
- test/controllers/テストファイル
- test/system/テストファイル
- app/helpers/ヘルパーファイル
- app/views/モデル名(複数形)
  - index.json.jbuilder
  - show.json.jbuilder
  - _モデル名.jsonjbuilder
- app/assets/javascripts/coffeeファイル
- app/assets/stylesheets/scssファイル

不足する点としては
- バリデーションの実装
- ユーザ認証
- テストコード
など　

### 基底クラス
- コントローラ　ApplicationController（ActionContoller::Baseを継承）
- モデル　ApplicationRecord（ActiveRecord::Baseを継承）

基底クラスで定義したルールは、アプリケーションのすべてのアクションに反映される。

### デバッグ
本番環境では表示しないようにしたいので、以下のようなifにする（以下は開発環境のみ表示する）
```ruby
 <%= debug(params) if Rails.env.development? %>
```

出力結果の例
```
--- !ruby/object:ActionController::Parameters
parameters: !ruby/hash:ActiveSupport::HashWithIndifferentAccess
  controller: users
  action: show
  id: '3'
permitted: false
```

なお、Railsにはdevelopment、test、productionの3つの環境がある

### 本番環境でのSSL化
config/environments/produvtion.rbで以下のコメントアウトをはずすだけで、SSLを強制できる
```
config.force_ssl = true
```

サーバのSSLをセットアップする（ドメインごとにSSL証明書を購入し、セットアップする必要がある）

#### herokuでpumaを使う例
Gemfileに`puma`を追記してインストール  
(Rails 5ではもとからある)


本番環境用のwebサーバ設定ファイル設定例  
(config/puma.rb)
```ruby
workers Integer(ENV['WEB_CONCURRENCY'] || 2)
threads_count = Integer(ENV['RAILS_MAX_THREADS'] || 5)
threads threads_count, threads_count

preload_app!

rackup      DefaultRackup
port        ENV['PORT']     || 3000
environment ENV['RACK_ENV'] || 'development'

on_worker_boot do
  # Worker specific setup for Rails 4.1+
  # See: https://devcenter.heroku.com/articles/
  # deploying-rails-applications-with-the-puma-web-server#on-worker-boot
  ActiveRecord::Base.establish_connection
end
```

Procfileを作成
```
web: bundle exec puma -C config/puma.rb
```

デプロイ