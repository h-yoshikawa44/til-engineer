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