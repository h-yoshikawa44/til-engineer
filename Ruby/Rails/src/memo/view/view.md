## ビュー
パス：app/views/フォルダ/各種ファイル

.erbファイルに記述していく  
HTMLのなかにRubyのコードを書くには`<% %>`や出力を意味する`<%= %>`で囲う

### link_to
リンクを作成する
第2引数には名前付きルートで指定できる（名前付きルートは`rails route`で確認）

```ruby
<td><%= link_to 'Show', user %></td>
<td><%= link_to 'Edit', edit_user_path(user) %></td>
<td><%= link_to 'Destroy', user, method: :delete,
                                     data: { confirm: 'Are you sure?' } %></td>
```

### フォーム

コントローラ
```ruby
  def new
    @user = User.new
  end
```

ビュー
form_for(@user)とすることで、フォームのactionは`/users`というURLへのPOSTであると自動的に判定する  
```ruby
<div class="row">
  <div class="col-md-6 col-md-offset-3">
    <%= form_for(@user) do |f| %>
      <%= f.label :name %>
      <%= f.text_field :name %>

      <%= f.label :email %>
      <%= f.email_field :email %>

      <%= f.label :password %>
      <%= f.password_field :password %>

      <%= f.label :password_confirmation, "Confirmation" %>
      <%= f.password_field :password_confirmation %>

      <%= f.submit "Create my account", class: "btn btn-primary" %>
    <% end %>
  </div>
</div>
```
※なお、モデルオブジェクトと紐づかない場合は、form_for(:sesstion, usl: login_path) などとすることで、params[:session][:email]といったようになる

また、Railsでは新規作成のPOSTと編集用のPATCHを区別するために`モデルインスタンス.new_record?`で判断する


展開されるHTML
```html
<form accept-charset="UTF-8" action="/users" class="new_user"
      id="new_user" method="post">
  <input name="utf8" type="hidden" value="&#x2713;" />
  <input name="authenticity_token" type="hidden"
         value="NNb6+J/j46LcrgYUC60wQ2titMuJQ5lLqyAbnbAUkdo=" />
  <label for="user_name">Name</label>
  <input id="user_name" name="user[name]" type="text" />

  <label for="user_email">Email</label>
  <input id="user_email" name="user[email]" type="email" />

  <label for="user_password">Password</label>
  <input id="user_password" name="user[password]"
         type="password" />

  <label for="user_password_confirmation">Confirmation</label>
  <input id="user_password_confirmation"
         name="user[password_confirmation]" type="password" />

  <input class="btn btn-primary" name="commit" type="submit"
         value="Create my account" />
</form>
```

コントローラ（送信先）
Strong Parametersを使用して、必須のパラメータと許可されたパラメータを指定する
```ruby
  def create
    @user = User.new(user_params)
    if @user.save
      redirect_to @user
    else
      render 'new'
    end
  end

  private
    def user_params
      params.require(:user).permit(:name, :email, :password, :password_confirmation)
    end
```

### flash
一度だけ表示するフラッシュメッセージを表示する
flashのクラス用のスタイルはsuccess、info、warning、dangerの４つ

コントローラでセット
```ruby
  def create
    @user = User.new(user_params)
    if @user.save
      flash[:success] = "Welcome to the Sample App!"
      redirect_to @user
    else
      render 'new'
    end
  end
```

ビューで表示
```ruby
  <% flash.each do |message_type, message| %>
    <div class="alert alert-<%= message_type %>"><%= message %></div>
  <% end %>
```

なお、renderメソッドで再レンダリングした場合、リクエストとみなされないため、リクエストのメッセージが消えない。その場合は、`flash`を`flash.now`に置き換えることで、`flash.now`のメッセージはその後リクエストが発生した時に消滅する

### ページネーション
will_paginateメソッドを使用する場合

[[Rails]will_paginateで表示件数を選択できるページネーションを実装する](https://qiita.com/chocode/items/57852db2eb6a1ed5e973)

Gemfileに`will_paginate`と`bootstrap-will_paginate`を追加

コントローラ  
モデルオブジェクト.paginateでは、:pageパラメータに基づいて、データベースから一塊のデータ（デフォルトは30）を取り出す
:pageは自動的に設定される  
:per_pageで１ページに表示する件数を指定
```ruby
def index
  @users = User.paginate(page: params[:page])
end
```

ビュー  
will_paginateの個所にページネーションが表示される
```ruby
<% provide(:title, 'All users') %>
<h1>All users</h1>

<%= will_paginate %>

<ul class="users">
  <% @users.each do |user| %>
    <li>
      <%= gravatar_for user, size: 50 %>
      <%= link_to user.name, user %>
    </li>
  <% end %>
</ul>

<%= will_paginate %>
```