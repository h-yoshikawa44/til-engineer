## レイアウト
パス：app/views/layouts

### デフォルトのレイアウト
Rails new時に、デフォルトのレイアウトである`application.html.erb`が作られる

`<%= csrf_meta_tags %>`の個所は、Web攻撃手法の１つであるクロスサイトリクエストフォージェリー (Cross-Site Request Forgery: CSRF)を防ぐために使われるRailsのメソッド

### HTML5非対応のブラウザ対策
旧式のIEではHTML5のサポートが不完全である場合がある  
そのため、問題回避策としてheadタグに以下をつける
```html
<!--[if lt IE 9]>
  <script src="//cdnjs.cloudflare.com/ajax/libs/html5shiv/r29/html5.min.js">
  </script>
<![endif]-->
```

### Bootstrapの導入
例
Gemfileに`bootstrap-sass`を追記

app/assets/images/stylesheets/custom.scssを作成
```scss
@import "bootstrap-sprockets";
@import "bootstrap";
```

Railsサーバを再起動

### Rubyの埋め込み
provideメソッドを使用して埋め込みをしている例  
(yieldで指定して埋め込まれる)
```html
<% provide(:title, "Home") %>
<!DOCTYPE html>
<html>
  <head>
    <title><%= yield(:title) %> | Ruby on Rails Tutorial Sample App</title>
  </head>
  <body>
    <h1>Sample App</h1>
    <p>
      This is the home page for the
      <a href="https://railstutorial.jp/">Ruby on Rails Tutorial</a>
      sample application.
    </p>
  </body>
</html>
```

### パーシャル
他のレイアウトファイルを読み込む仕組み  
呼び出されるファイルの命名規則として、接頭辞がアンダースコアではじまる

```ruby
<%= render 'layouts/shim' %>
```
とすると、`app/views/layouts/_shim.html.erb`のファイルを探して、その内容をビューに挿入する