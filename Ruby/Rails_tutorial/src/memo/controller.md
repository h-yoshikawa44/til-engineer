## コントローラ
パス：app/controllers

単純なHTML出力の例
```ruby
class ApplicationController < ActionController::Base
  protect_from_forgery with: :exception

  def hello
    render html: "hello, mundo!"
  end
end
```

### インスタンス変数
モデル情報の取得例
```ruby
def index
    @users = User.all
end
```

コントローラ内で宣言した、@記号で始まるインスタンス変数はビューでも使うことができる

### パス変数
paramsで使用できる
```ruby
  def show
    @user = User.find(params[:id])
  end
```
