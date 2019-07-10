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