## ヘルパー
パス：app/helpers

ヘルパーとして定義しておいた処理をビュー側で呼び出すことができる  
Railsでは自動的にヘルパーモジュールを読みこんでくれるため、includeは不要

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