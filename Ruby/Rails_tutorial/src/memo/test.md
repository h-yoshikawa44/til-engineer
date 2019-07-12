## テスト

### テストはいつ行えばよいか
#### テストのメリット
1. テストが揃っていれば、機能停止に陥るような回帰バグ (Regression Bug: 以前のバグが再発したり機能の追加/変更に副作用が生じたりすること) を防止できる

2. テストが揃っていれば、コードを安全にリファクタリング (機能を変更せずにコードを改善すること) ができる

3. テストコードは、アプリケーションコードから見ればクライアントとして動作するので、アプリケーションの設計やシステムの他の部分とのインターフェイスを決めるときにも役に立つ

#### テスト駆動か一括テストかの目安
- アプリケーションのコードよりも明らかにテストコードの方が短くシンプルになる (=簡単に書ける) のであれば、「先に」書く

- 動作の仕様がまだ固まりきっていない場合、アプリケーションのコードを先に書き、期待する動作を「後で」書く

- セキュリティが重要な課題またはセキュリティ周りのエラーが発生した場合、テストを「先に」書く

- バグを見つけたら、そのバグを再現するテストを「先に」書き、回帰バグを防ぐ体制を整えてから修正に取りかかる

- すぐにまた変更しそうなコード (HTML構造の細部など) に対するテストは「後で」書く

- リファクタリングするときは「先に」テストを書く。特に、エラーを起こしそうなコードや止まってしまいそうなコードを集中的にテストする

### テスト実行で色を付ける
Gemfileに`minitest-reporters`を追加して、bundle install

test_helper.rbに追記
```ruby
ENV['RAILS_ENV'] ||= 'test'
require_relative '../config/environment'
require 'rails/test_help'
require "minitest/reporters"　←追記
Minitest::Reporters.use!　←追記

class ActiveSupport::TestCase
  # Setup all fixtures in test/fixtures/*.yml for all tests in alphabetical order.
  fixtures :all

  # Add more helper methods to be used by all tests here...
end
```

### テストコード
頭にtestをつける

static_pages/homeのリクエストURLのテスト  
(ルーティングを設定していると、static_pages_home_urlというヘルパーが使える)
```ruby
 test "should get home" do
    get static_pages_home_url
    assert_response :success
  end
```

### guardによる自動テスト
- Gemfileに`guard`を追加して、bundle install

- 初期化(Guardfileの作成)  
  `$ bundle exec guard init`

- Guardfileを編集  
  統合テストとビューが更新されたら自動的に適切なテストが実行されるようにする

- 実行  
  `$ bundle exec guard`  
  (終了する場合は`Ctrl + D`)

```ruby
guard :minitest, spring: "bin/rails test", all_on_start: false do
```
この行ではGuardからSpringサーバーを使って読み込み時間を短縮している (SpringはRailsの機能の１つ)。また、開始時にテストスイートをフルで実行しないようGuardに指示していている

Guard使用時のSpringとGitの競合を防ぐには、.gitignoreファイルにspring/ディレクトリを追加する

Springサーバーは若干不安定な点が残っていて、Springのプロセスが起動したまま多数残留すると、テストのパフォーマンスが低下してしまうことがある  
テストの実行が異常に遅くなってきたと感じたら、プロセスをチェックし、必要に応じてSpringをkillするとよい
(`$ ps aux | grep spring`で確認)