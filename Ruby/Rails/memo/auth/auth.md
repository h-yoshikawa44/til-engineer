## 認証

### ログイン処理
ユーザオブジェクトを取得後、取得ユーザの有効性チェック＆`authenticate`メソッドで認証する（モデルクラス`has_secure_password`を追加することで使用できるようになるメソッド）  
認証で来たら`session`メソッドを使用して一時cookieを作成する（このcookieは自動的に暗号化される）
```ruby
def create
  user = User.find_by(email: params[:session][:email].downcase)
  if user && user.authenticate(params[:session][:password])
    log_in user
    redirect_to user
  else
    flash.now[:danger] = 'Invalid email/password combination'
    render 'new'
  end
end

※log_inメソッド
module SessionsHelper
  # 渡されたユーザーでログインする
  def log_in(user)
    session[:user_id] = user.id
  end
end
```

### ログインしているユーザの情報の取り出し
例としてヘルパーで取得メソッドを定義

以下では`@current_user`に何も代入されていないときだけ、find_by呼び出しが実行され、無駄なデータベースへの読み出しが行われなくなる
ヘルパー
```ruby
# 現在ログイン中のユーザーを返す (いる場合)
def current_user
  if session[:user_id]
    @current_user ||= User.find_by(id: session[:user_id])
  end
end
```

この`@current_user`がnilか否かでログインしているかを判定できる

### ログアウト
ヘルパーで作成例
```ruby
 # 現在のユーザーをログアウトする
  def log_out
    session.delete(:user_id)
    @current_user = nil
  end
```

### remember me
ログイン状態をブラウザを閉じた後でも有効にする機能
sessionメソッドを利用した保存ではブラウザを閉じると情報が消えてしまうため、Cookieを使用する

ただし、Cookieを使用する場合は自動的に安全が保たれるようにはなっていないため以下の危険性によりCookieを盗まれる可能性がある
- 管理の甘いネットワークを通過するネットワークパケットからパケットスニッファという特殊なソフトウェアで直接Cookieを取り出す
- データベースから記憶トークンを取り出す
- クロスサイトスクリプティング
- ユーザがログインしているパソコンやスマホを直接操作してアクセスを奪い取る

対応策としてそれぞれ
- SSLをサイト全体に適用して、ネットワークデータを暗号化で保護し、パケットスニッファよか読み取られないようにする
- 記憶トークンをそのままデータベースに保存するのでなく、記憶トークンのハッシュ値を保存するようにする
- Railsによって自動的に対策が行われる（ビューのテンプレートで入力した内容を自動的にエスケープする）
- 物理アクセスによる攻撃の防衛手段を講じることが困難であるが、二次被害を最小限にとどめることは可能（ユーザが別端末などでログアウトした時にトークンを必ず変更するようにし、セキュリティ上重要になる可能性のある情報を表示するときはデジタル署名を行うようにする）

↓以上を踏まえて

1. 記憶トークンにはランダムな文字列を生成して用いる
2. ブラウザのCookieにトークンを保存するときには、有効期限を設定する
3. トークンはハッシュ値に変換してからデータベースに保存する
4. ブラウザのCookieに保存するユーザIDは暗号化しておく
5. 永続ユーザIDを含むCookieを受け取ったら、そのIDでデータベースを検索し、記憶トークンのCookieがデータベース内のハッシュ値を一致することを確認する

Userテーブルに
`remember_digest`カラムを追加
```ruby
class AddRememberDigestToUsers < ActiveRecord::Migration[5.0]
  def change
    add_column :users, :remember_digest, :string
  end
end
```

モデル（一部抜粋）
```ruby
class User < ApplicationRecord
  # 仮想の属性を作成
  attr_accessor :remember_token

  # 渡された文字列のハッシュ値を返す
  def User.digest(string)
    cost = ActiveModel::SecurePassword.min_cost ? BCrypt::Engine::MIN_COST :
                                                  BCrypt::Engine.cost
    BCrypt::Password.create(string, cost: cost)
  end

  # ランダムなトークンを返す
  def User.new_token
    SecureRandom.urlsafe_base64
  end

  # 永続セッションのためにユーザーをデータベースに記憶する
  def remember
    self.remember_token = User.new_token
    update_attribute(:remember_digest, User.digest(remember_token))
  end

  # 渡されたトークンがダイジェストと一致したらtrueを返す
  def authenticated?(remember_token)
    # 一つのブラウザでログアウトし、一つのブラウザではログアウトせずにブラウザを終了し、再度ページを開いた場合の問題に対応
    # (remember_digestがnilになるので、bcryptライブラリ内部で例外が発生する)
    return false if remember_digest.nil?
    BCrypt::Password.new(remember_digest).is_password?(remember_token)
  end

  # ユーザーのログイン情報を破棄する
  def forget
    update_attribute(:remember_digest, nil)
  end
```

ヘルパー
```ruby
   # 渡されたユーザーでログインする
  def log_in(user)
    session[:user_id] = user.id
  end

  # ユーザーのセッションを永続的にする
  def remember(user)
    user.remember
    # permanent ユーザIDと記憶トークンはペアで扱う必要があるので、Cookieも永続化する
    # signed 署名付きCookieを使用（ブラウザに保存する前に暗号化する）
    cookies.permanent.signed[:user_id] = user.id
    cookies.permanent[:remember_token] = user.remember_token
  end

  # 記憶トークンcookieに対応するユーザーを返す
  def current_user
    if (user_id = session[:user_id])
      @current_user ||= User.find_by(id: user_id)
    # Cookieの値の取得 自動的にユーザIDのCookieの暗号が解除され元に戻る
    elsif (user_id = cookies.signed[:user_id])
      user = User.find_by(id: user_id)
      if user && user.authenticated?(cookies[:remember_token])
        log_in user
        @current_user = user
      end
    end
  end

  # ユーザーがログインしていればtrue、その他ならfalseを返す
  def logged_in?
    !current_user.nil?
  end

  # 永続的セッションを破棄する
  def forget(user)
    user.forget
    cookies.delete(:user_id)
    cookies.delete(:remember_token)
  end

  # 現在のユーザーをログアウトする
  def log_out
    forget(current_user)
    session.delete(:user_id)
    @current_user = nil
  end
```

コントローラ
```ruby
  def new
  end

  def create
    user = User.find_by(email: params[:session][:email].downcase)
    if user && user.authenticate(params[:session][:password])
      log_in user
      params[:session][:remember_me] == '1' ? remember(user) : forget(user)
      redirect_to user
    else
      flash.now[:danger] = 'Invalid email/password combination'
      render 'new'
    end
  end

  def destroy
    # 一つのタブでログアウトし、もう一つのタブでLogoutリンクをクリックした場合の問題対応
    # （currnet_userがnilになってしまうため、logoutメソッド内のforgetに失敗する）
    log_out if logged_in?
    redirect_to root_url
  end
```

ビュー(new.html.erb)
```ruby
<% provide(:title, "Log in") %>
<h1>Log in</h1>

<div class="row">
  <div class="col-md-6 col-md-offset-3">
    <%= form_for(:session, url: login_path) do |f| %>

      <%= f.label :email %>
      <%= f.email_field :email, class: 'form-control' %>

      <%= f.label :password %>
      <%= f.password_field :password, class: 'form-control' %>

      <%= f.label :remember_me, class: "checkbox inline" do %>
        <%= f.check_box :remember_me %>
        <span>Remember me on this computer</span>
      <% end %>

      <%= f.submit "Log in", class: "btn btn-primary" %>
    <% end %>

    <p>New user? <%= link_to "Sign up now!", signup_path %></p>
  </div>
</div>
```

### パスワードが空のままでも更新できるようにする
validateに`allow_nil: true`をつける
例
```ruby
has_secure_password
validates :password, presence: true, length: { minimum: 6 }, allow_nil: true
```

新規ユーザ登録時に空のパスワードが有効になってしまうように見えるが、`has_secure_password`をつけている場合、バリデーションとは別にオブジェクト生成時に存在性を検証するようになっているため、空のパスワードが新規ユーザ登録時に有効になることはない

### ログインを要求するページ
コントローラで`before_action`を使用して、ログインチェックを行うとよい

```ruby
class UsersController < ApplicationController
  before_action :logged_in_user, only: [:edit, :update]
  .
  .
  .
  private

    def user_params
      params.require(:user).permit(:name, :email, :password,
                                   :password_confirmation)
    end

    # beforeアクション

    # ログイン済みユーザーかどうか確認
    def logged_in_user
      unless logged_in? # logged_in?はログインしているかチェックするヘルパーメソッド
        flash[:danger] = "Please log in."
        redirect_to login_url
      end
    end
end
```

### 他ユーザのアクセスから保護
URLパスのユーザIDから取得したユーザとログインユーザが一致するかチェックする
```ruby
class UsersController < ApplicationController
  before_action :logged_in_user, only: [:edit, :update]
  before_action :correct_user,   only: [:edit, :update]
  .
  .
  .
  def edit
  end

  def update
    if @user.update_attributes(user_params)
      flash[:success] = "Profile updated"
      redirect_to @user
    else
      render 'edit'
    end
  end
  .
  .
  .
  private

    def user_params
      params.require(:user).permit(:name, :email, :password,
                                   :password_confirmation)
    end

    # beforeアクション

    # ログイン済みユーザーかどうか確認
    def logged_in_user
      unless logged_in?
        flash[:danger] = "Please log in."
        redirect_to login_url
      end
    end

    # 正しいユーザーかどうか確認
    def correct_user
      @user = User.find(params[:id])
      redirect_to(root_url) unless @user == current_user # current_userはトークンから現在のログインユーザを取得するヘルパーメソッド
    end
end
```

### フレンドリーフォワーディング
要求するページがログインを要求するページだった場合、ログインした後に元々要求していたページにリダイレクトするようにすること

ログインページにリダイレクトする前に、リクエストしていたURLをセッションに記憶しておき、ログイン後に記憶したURLへリダイレクトする

※似た動作をするメソッドとして`request.referrer`がある。こちらはひとつ前のURLを返すもの（元に戻すURLが見つからないときに備えて|でホームのURLを指定するなどしておくとよい）

ヘルパー
```ruby
# 記憶したURL (もしくはデフォルト値) にリダイレクト
  def redirect_back_or(default)
    redirect_to(session[:forwarding_url] || default)
    session.delete(:forwarding_url)
  end

  # アクセスしようとしたURLを覚えておく
  def store_location
    session[:forwarding_url] = request.original_url if request.get?
  end
```

コントローラ
```ruby
# ログイン済みユーザーかどうか確認
 def logged_in_user
   unless logged_in?
     store_location # アクセスしようとしたURLを覚えておく
     flash[:danger] = "Please log in."
     redirect_to login_url
   end
 end
```

セッションコントローラ
```ruby
def create
    user = User.find_by(email: params[:session][:email].downcase)
    if user && user.authenticate(params[:session][:password])
      log_in user
      params[:session][:remember_me] == '1' ? remember(user) : forget(user)
      redirect_back_or user # 記憶したURL (もしくはデフォルト値) にリダイレクト
    else
      flash.now[:danger] = 'Invalid email/password combination'
      render 'new'
    end
  end
```

