## オブジェクト
※RailsというよりRubyのメモ

定義例
```ruby
ファイル名 example_user.rb
class User
  # アクセサを作成
  # 作成することで、getterとsetterを定義してくれる
  attr_accessor :name, :email

  def initialize(attributes = {})
    @name  = attributes[:name]
    @email = attributes[:email]
  end

  def formatted_email
    "#{@name} <#{@email}>"
  end
end
```

コンソールで使用
```ruby
>> require './example_user'     # example_userのコードを読み込む方法
=> true
>> example = User.new
=> #<User:0x224ceec @email=nil, @name=nil>
>> example.name                 # attributes[:name]は存在しないのでnil
=> nil
>> example.name = "Example User"           # 名前を代入する
=> "Example User"
>> example.email = "user@example.com"      # メールアドレスを代入する
=> "user@example.com"
>> example.formatted_email
=> "Example User <user@example.com>"
```