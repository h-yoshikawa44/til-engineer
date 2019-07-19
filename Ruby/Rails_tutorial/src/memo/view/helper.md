## ヘルパー
パス：app/helpers

ヘルパーとして定義しておいた処理をビュー側で呼び出すことができる  
ビューで使用する場合、Railsでは自動的にヘルパーモジュールを読みこんでくれるため、includeは不要

コントローラではincludeが必要。application_controllerに定義することで、すべてのコントローラで使用することができる

定義側
```ruby
module ApplicationHelper

  # ページごとの完全なタイトルを返します。
  def full_title(page_title = '')
    base_title = "Ruby on Rails Tutorial Sample App"
    if page_title.empty?
      base_title
    else
      page_title + " | " + base_title
    end
  end
end
```

使用側
```ruby
<title><%= full_title(yield(:title)) %></title>
```

### 組み込みヘルパー
- image_tag
app/assets/imagesディレクトリの中から指定画像を探す
例
```ruby
image_tag("rails.png", alt: "Rails logo")
```

- pluralize
最初の引数に整数が与えられると、それに基づいて2番目の引数の英単語を複数形に変更したものを返す

```ruby
>> helper.pluralize(1, "error")
=> "1 error"
>> helper.pluralize(5, "error")
=> "5 errors"
>> helper.pluralize(2, "woman")
=> "2 women"
>> helper.pluralize(3, "erratum")
=> "3 errata
```