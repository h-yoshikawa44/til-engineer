## モデル
パス：app/models/モデルファイル

### バリデーション
validates カラム名, バリデーション内容

```ruby
class Micropost < ApplicationRecord
  validates :content, length: { maximum: 140 }
end
```

- length: { maximum: 140}　最大文字数
- presence: true　必須

### リレーション
- hasmany モデル名(複数形)  
例　`has_many :microposts`

- belongs_to モデル名(単数形)  
例　`belongs_to :user`

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