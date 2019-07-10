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