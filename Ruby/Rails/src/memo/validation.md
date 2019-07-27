## バリデーション
モデルクラスに記述

validates カラム名　バリデーション項目

例
```ruby
class User < ApplicationRecord
  validates :name,  presence: true, length: { maximum: 50 }
  VALID_EMAIL_REGEX = /\A[\w+\-.]+@[a-z\d\-.]+\.[a-z]+\z/i
  validates :email, presence: true, length: { maximum: 255 },
                    format: { with: VALID_EMAIL_REGEX },
                    uniqueness: true
end
```

### 種類
- presence: true　必須
- length:
  - {maximum: 文字数}　最大文字数
  - {minimum: 文字数}　最小文字数
- format: {with: 正規表現}　フォーマット指定
- uniqueness: true　ユニーク


### 大文字小文字を区別しないようにする
例として、一意性なメールアドレスを保存する際に、保存前に小文字に変換することで、対応できる

以下をバリデーションの記述より前に記述
```ruby
before_save { self.email = email.downcase }
```
