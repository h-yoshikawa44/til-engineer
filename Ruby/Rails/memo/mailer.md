## メーラー

### テンプレート作成
`rails generate mailer メーラー名 メソッド名...`

`rails generate mailer UserMailer account_activation password_reset`の例
- app/mailers/user_mailer.rb
- app/views/user_mailer/account_activation.text.erb
- app/views/user_mailer/account_activation.html.erb
- app/views/user_mailer/password_reset.text.erb
- app/views/user_mailer/password_reset.html.erb
- test/mailers/user_mailer_test.rb
- test/mailers/previews/user_mailer_preview.rb

from：発信元
mail to：送信先
subject：タイトル
```ruby
class UserMailer < ApplicationMailer

  def account_activation(user)
    @user = user
    mail to: user.email, subject: "Account activation"
  end
end
```

### 送信
メーラーオブジェクト.メソッド.deliver_now
例　UserMailer.account_activation(@user).deliver_now

アカウント有効化に使用する場合は、メールでアカウント有効化を行うようフラッシュメッセージを送信後に設定するとよい

### メールのプレビュー
config/environments/development.rbで以下を設定
```ruby
config.action_mailer.raise_delivery_errors = true
  config.action_mailer.delivery_method = :test
  host = 'localhost:3000'
  config.action_mailer.default_url_options = { host: host, protocol: 'http' }
```

サーバを再起動で上記設定を読み込み

test/mailers/preview/プレビューファイルでプレビューコードを実装
```ruby
# Preview all emails at http://localhost:3000/rails/mailers/user_mailer
class UserMailerPreview < ActionMailer::Preview

  # Preview this email at
  # http://localhost:3000/rails/mailers/user_mailer/account_activation
  def account_activation
    user = User.first
    user.activation_token = User.new_token
    UserMailer.account_activation(user)
  end

  # Preview this email at
  # http://localhost:3000/rails/mailers/user_mailer/password_reset
  def password_reset
    UserMailer.password_reset
  end
end
```

指定のURLにブラウザでアクセス