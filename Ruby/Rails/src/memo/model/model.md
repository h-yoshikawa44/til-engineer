## モデル
パス：app/models/モデルファイル

### バリデーション
validation.mdを参照

### リレーション
- hasmany モデル名(複数形)  
例　`has_many :microposts`

`dependent: :destroy`をつけると、親が削除されたときに子レコードも削除されるようになる

- belongs_to モデル名(単数形)  
例　`belongs_to :user`

明示的にクラスを指定する際は`class_name`  
外部キーを指定する際は`foreign_key`

### オブジェクト
#### 生成
オブジェクトを引数なしで`new`した場合、すべての属性がnilのオブジェクトを返す
引数を指定した場合は、属性に値がセットされる
```ruby
>> user = User.new(name: "Michael Hartl", email: "mhartl@example.com")
=> #<User id: nil, name: "Michael Hartl", email: "mhartl@example.com",
created_at: nil, updated_at: nil>
```

#### 有効性チェック
`valid`メソッドを使用  
バリデーションチェックに引っ掛かっている属性があるときは、falseが返る
```ruby
>> user.valid?
true
```

なお、バリデーションチェックに引っ掛かった属性は`errors`オブジェクトで確認できる
```ruby
>> user.errors.full_messages
=> ["Name can't be blank"]
```

#### 保存
`save`メソッドを使用  
成功すればtrue、失敗すればfalseを返す
```ruby
>> user.save
   (0.1ms)  SAVEPOINT active_record_1
  SQL (0.8ms)  INSERT INTO "users" ("name", "email", "created_at",
  "updated_at") VALUES (?, ?, ?, ?)  [["name", "Michael Hartl"],
  ["email", "mhartl@example.com"], ["created_at", 2016-05-23 19:05:58 UTC],
  ["updated_at", 2016-05-23 19:05:58 UTC]]
   (0.1ms)  RELEASE SAVEPOINT active_record_1
=> true
```

保存すると、id、created_at、updated_atに値がセットされる
```ruby
>>  user
=> #<User id: 1, name: "Michael Hartl", email: "mhartl@example.com", created_at: "2019-07-14 03:16:22", updated_at: "2019-07-14 03:16:22">
```

モデルのインスタンスはドット記法で属性にアクセスできる
```ruby
>> user.name
=> "Michael Hartl"
>> user.email
=> "mhartl@example.com"
>> user.updated_at
=> Mon, 23 May 2016 19:05:58 UTC +00:00
```

#### 作成と保存
`create`メソッドを使用
返り値はbooleanでなく、オブジェクト自身を返す
```ruby
>> User.create(name: "A Nother", email: "another@example.org")
#<User id: 2, name: "A Nother", email: "another@example.org", created_at:
"2016-05-23 19:18:46", updated_at: "2016-05-23 19:18:46">
```

#### 複製
`dup`メソッドを使用
同じ属性もつデータを複製する

ユニークのテストを行うときなどに使用する
```ruby
  def setup
    @user = User.new(name: "Example User", email: "user@example.com")
  end

  test "email addresses should be unique" do
    duplicate_user = @user.dup
    @user.save
    assert_not duplicate_user.valid?
  end
```

#### 削除
`destroy`メソッドを使用
返り値はそのオブジェクト自身を返すが、その戻り値を使用してもう一度destroyを呼ぶことはできない
```ruby
>> foo.destroy
   (0.1ms)  SAVEPOINT active_record_1
  SQL (0.2ms)  DELETE FROM "users" WHERE "users"."id" = ?  [["id", 3]]
   (0.1ms)  RELEASE SAVEPOINT active_record_1
=> #<User id: 3, name: "Foo", email: "foo@bar.com", created_at: "2016-05-23
19:19:06", updated_at: "2016-05-23 19:19:06">
```

#### 取得
`find`メソッド  
引数はID
```ruby
>>  User.find(1)
  User Load (1.4ms)  SELECT  "users".* FROM "users" WHERE "users"."id" = $1 LIMIT $2  [["id", 1], ["LIMIT", 1]]
=> #<User id: 1, name: "Michael Hartl", email: "mhartl@example.com", created_at: "2019-07-14 03:16:22", updated_at: "2019-07-14 03:16:22">
```

`find_by`メソッド  
特定の属性で検索
```ruby
>> User.find_by(email: "mhartl@example.com")
  User Load (0.3ms)  SELECT  "users".* FROM "users" WHERE "users"."email" = $1 LIMIT $2  [["email", "mhartl@example.com"], ["LIMIT", 1]]
=> #<User id: 1, name: "Michael Hartl", email: "mhartl@example.com", created_at: "2019-07-14 03:16:22", updated_at: "2019-07-14 03:16:22">
```

`first`メソッド
最初のレコードを返す
```ruby
>>  User.first
  User Load (0.3ms)  SELECT  "users".* FROM "users" ORDER BY "users"."id" ASC LIMIT $1  [["LIMIT", 1]]
=> #<User id: 1, name: "Michael Hartl", email: "mhartl@example.com", created_at: "2019-07-14 03:16:22", updated_at: "2019-07-14 03:16:22">
```

`all`メソッド
全てのレコードを返す
```ruby
 User.all
  User Load (0.3ms)  SELECT  "users".* FROM "users" LIMIT $1  [["LIMIT", 11]]
=> #<ActiveRecord::Relation [#<User id: 1, name: "Michael Hartl", email: "mhartl@example.com", created_at: "2019-07-14 03:16:22", updated_at: "2019-07-14 03:16:22">]>
```

#### 更新
1. 属性を個別に代入してsaveする
```ruby
>> user
=> #<User id: 1, name: "Michael Hartl", email: "mhartl@example.com", created_at: "2019-07-14 03:16:22", updated_at: "2019-07-14 03:16:22">
>> user.email = "mhartl@example.net"
=> "mhartl@example.net"
>> user.save
   (0.3ms)  SAVEPOINT active_record_1
  User Update (0.8ms)  UPDATE "users" SET "email" = $1, "updated_at" = $2 WHERE "users"."id" = $3  [["email", "mhartl@example.net"], ["updated_at", "2019-07-14 03:30:59.356612"], ["id", 1]]
   (0.9ms)  RELEASE SAVEPOINT active_record_1
=> true
```

保存を行わずに`reload`を実行すると、データベースの情報をもとにオブジェクトを再読み込みする
```ruby
>> user.email
=> "mhartl@example.net"
>> user.email = "foo@bar.com"
=> "foo@bar.com"
>> user.reload.email
=> "mhartl@example.net"
```

2. `update_attributes`を使用
成功時には更新と保存を続けて同時に行う  
ただし、検証に一つでも失敗すると、呼び出しは失敗する
```ruby
>> user.update_attributes(name: "The Dude", email: "dude@abides.org")
=> true
>> user.name
=> "The Dude"
>> user.email
=> "dude@abides.org"
```

3. `update_attribute`を使用（特定の属性のみ更新したい場合）
```ruby
>> user.update_attribute(:name, "El Duderino")
=> true
>> user.name
=> "El Duderino"
```

### データベースレベルでの一意性
同じ内容のリクエストを連続して送信した場合、二つとも保存されてしまう（一意性の検証が行われているとしても、同じ値を持つレコードが作成されてしまう）  
これはトラフィックが多い沖に発生する可能性がある  
この場合、データベースレベルでも一意性を強制することで解決する  
具体的にはカラムにインデックスを追加する

インデックス自体は一意性を強制しないが、オプション`unique: true`を指定することで強制できるようになる
```ruby
class AddIndexToUsersEmail < ActiveRecord::Migration[5.0]
  def change
    add_index :users, :email, unique: true
  end
end
```

### ハッシュ化されたパスワード
モデルクラスに`has_secure_password`を追加するだけで機能が使えるようになる  
(使用条件としては、モデル内に`password_digest`という属性が含まれていること、`bcrypt`gemが必要)

- セキュアにハッシュ化したパスワードを、データベース内のpassword_digestという属性に保存できるようになる
- 2つのペアの仮想的な属性 (passwordとpassword_confirmation) が使えるようになる。また、存在性と値が一致するかどうかのバリデーションも追加される
- `authenticate`メソッドが使えるようになる (引数の文字列がパスワードと一致するとUserオブジェクトを、間違っているとfalseを返すメソッド)

なお、`has_secure_password`を追加すると、仮想的な`password`属性と`password_confirmation`属性に対してバリデーションが追加される  
ただしレコードの新規作成の時のみのため、空文字で更新しようとすると更新できてしまう

そのため、バリデーションを別途追加する
```ruby
validates :password, presence: true, length: { minimum: 6 }
```

`authenticate`メソッドでログイン処理が実装できる
パスワードが一致する場合、オブジェクトを返すが`!!`で論理値に変換することでtrue、falseにできる
```ruby
>> !!user.authenticate("foobar")
=> true
```